/********************************************************************************
Written By: Wesley Ford (ntohq), and Seth Ivy (SethIvry)
Date:       02-04-2021 
Version:    0.001
********************************************************************************
Documentation:
   This program hosts a chat room CCHT for others to connect to with special software
   over a LAN (Local Area Network)
********************************************************************************/
import java.io.*;
import java.util.*;
import java.net.*;

class Main {
   public static Vector<ClientHandler> clientsConnected = new Vector<>();
   static int clientCount = 0;
   final static int ServerPort = 7600; 
   public static void main(String[] args) {
      Boolean isOnline = true;
      Socket userSocket;
      ServerSocket serverSocketHandler;
      int connectionCount = 1;
      Thread newThread;
      ClientHandler newUserConnection;

      while (true) {
         try {
            serverSocketHandler = new ServerSocket(ServerPort);

            while (isOnline) {
               userSocket = serverSocketHandler.accept();
               System.out.printf("Connected made! Transfering Socket(" + userSocket + ") to Handler. \n");

               DataInputStream inputStream = new DataInputStream(userSocket.getInputStream());
               DataOutputStream outputStream = new DataOutputStream(userSocket.getOutputStream());

               System.out.println("Sending user connection information to handler");
               String name = inputStream.readUTF();
               newUserConnection = new ClientHandler(userSocket, ("connection #" + connectionCount), name, outputStream, inputStream);
               clientsConnected.add(newUserConnection);
               newThread = new Thread(newUserConnection);
               newThread.start();
               connectionCount++;
            }
            serverSocketHandler.close();
         } catch (Exception e) {
            System.out.println(e);
         }
      }
   }
}