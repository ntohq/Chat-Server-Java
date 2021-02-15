/********************************************************************************
Written By: Wesley Ford (ntohq), and Seth Ivy (SethIvry)
Date:       02-04-2021 
Version:    0.001
********************************************************************************
Documentation:
   This program host a chat room for others to connect to with special software
   over a LAN (Local Area Network)
********************************************************************************/

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.*;

public class ClientHandler implements Runnable {
   Scanner inputScanner = new Scanner(System.in);
   DataInputStream inputStream;
   DataOutputStream outputStream;
   Socket userSocket;
   String name;

   public ClientHandler(Socket usersSocket, String client, String newUsersName, DataOutputStream DataOutStream, DataInputStream DataInStream) 
   {
      inputStream = DataInStream;
      outputStream = DataOutStream;
      userSocket = usersSocket;
      name = newUsersName;
      System.out.println(client + "\n User's name is : " + name);
   }

   public void run()
   {
      String recievedTexted;

      while(true)
      {
         try
         {
            recievedTexted = inputStream.readUTF();

            System.out.println(recievedTexted);

            StringTokenizer recievedTextTokenized = new StringTokenizer(recievedTexted, "#");
            String senderId = recievedTextTokenized.nextToken();
            String recivedMessage = recievedTextTokenized.nextToken();
            
            for (ClientHandler client : Main.clientsConnected)
            {
               if (!(recivedMessage.replaceFirst("^\\s*", "").substring(0,1).toLowerCase().equals("/")))
               {
                  if(!(client.name.equals(name)))
                  {
                     client.outputStream.writeUTF(senderId + "#" + recivedMessage);
                     continue;
                  }
                  continue;
               } else {
                  if(client.name.equals(name))
                  {
                     if (recivedMessage.replaceFirst("^\\s*", "").toLowerCase().substring(1).equals("help"))
                     {
                        client.outputStream.writeUTF("Server# I'm Here to help! 911!");
                        break;
                     } else if(recivedMessage.replaceFirst("^\\s*", "").toLowerCase().substring(1).equals("time"));
                     {
                        client.outputStream.writeUTF("Server# " + new SimpleDateFormat("'Date:' MM-dd-yyyy 'Time' HH:mm:ss").format(new Date(System.currentTimeMillis())));
                        break;
                     }
                     
                  }
               }
            }
         } catch(EOFException a)
         {
            System.out.println("Error...Disconecting " + name);
            try 
            {
               for(ClientHandler client : Main.clientsConnected)
               {
                  if (client.name.equals(name))
                  {
                     Main.clientsConnected.removeElement(client);
                  }
               }
               Main.clientCount--;
               userSocket.close();
               inputStream.close();
               outputStream.close();
               break;
            } catch(Exception b)
            {
               b.printStackTrace();
            }
         } catch (Exception e)
         {
            System.out.println(e);
         } 
       
      }
   }
}