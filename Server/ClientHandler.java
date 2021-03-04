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
   Boolean isConnected;

   public ClientHandler(Socket usersSocket, String client, String newUsersName, DataOutputStream DataOutStream, DataInputStream DataInStream) 
   {
      inputStream = DataInStream;
      outputStream = DataOutStream;
      userSocket = usersSocket;
      name = newUsersName;
      isConnected = true;
      System.out.println(client + "\n User's name is : " + name);
   }

   public void run()
   {
      String recievedTexted;

      while(isConnected)
      {
         try
         {
            recievedTexted = inputStream.readUTF();

            System.out.println(recievedTexted);

            StringTokenizer recievedTextTokenized = new StringTokenizer(recievedTexted, "#");
            String senderId = recievedTextTokenized.nextToken();
            String recivedMessage = recievedTextTokenized.nextToken();
            String recipient, dm; 
            recipient = recivedMessage.substring(2).split(" ")[0];
            dm = recivedMessage.replaceFirst("^\\s*", "").substring((recipient.length() + 1));
                  
            
            for (ClientHandler client : Main.clientsConnected)
            {
               if(recivedMessage.replaceFirst("^\\s*", "").substring(0,1).equals("/")) {
                  if(client.name.equals(name))
                  {
                     String command = recivedMessage.replaceFirst("^\\s*", "").substring(1).toLowerCase();
                     System.out.println(command);
                     if (command.equalsIgnoreCase("help"))
                     {
                        client.outputStream.writeUTF("Server# I'm Here to help! 911!");
                     } 
                     else if(command.equalsIgnoreCase("time"))
                     {
                        client.outputStream.writeUTF("Server# " + new SimpleDateFormat("'Date:' MM-dd-yyyy 'Time' HH:mm:ss").format(new Date(System.currentTimeMillis())));
                     }
                     else 
                     {
                        client.outputStream.writeUTF("Server# The command you have entered is not recognized.");
                     }
                     
                  }
               }
               else if(recivedMessage.replaceFirst("^\\s*", "").substring(0,1).equals("@"))
               {
                  if(client.name.equals(recipient))
                  {
                     client.outputStream.writeUTF("Direct Message from: " + name + "#" + dm);
                  }
               } else
               {
                  if(!(client.name.equals(name)))
                  {
                     client.outputStream.writeUTF(senderId + "#" + recivedMessage);
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
               isConnected = false;
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