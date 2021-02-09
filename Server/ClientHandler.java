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
import java.net.*;

public class ClientHandler implements Runnable
{
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
      System.out.println(client + "Name is : " + name );
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
            
            }
         } catch(Exception e)
         {
            System.out.println(e);
         }
      }
   }
}