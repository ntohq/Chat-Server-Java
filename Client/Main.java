/********************************************************************************
Written By: Wesley Ford (ntohq), and Seth Ivy (SethIvry)
Date:       02-04-2021 
Version:    0.001
********************************************************************************
Documentation:
   This program allows you tp connect to CCHT chat room over a LAN (Local Area Network)
********************************************************************************/
import java.io.*; 
import java.net.*; 
import java.util.Scanner; 

class Main
{
   final static int ServerPort = 7600; 
   public static DataInputStream inputStream;
   public static DataOutputStream outputStream;
   public static void main(String[] args)
   {
      String name;
      InetAddress ip;
      int port;
      Socket socketConnection;
      Scanner userInput = new Scanner(System.in);

      System.out.println("Welcome to CCHT");
      System.out.print("Please enter your name: ");
      name = userInput.nextLine();
      System.out.print("Now please enter a CCHT server's ip you would like to connect to: ");
      ip = InetAddress.getByName(userInput.nextLine());
      System.out.println("Connecting to " + ip + ":" + (String) ServerPort);
      socketConnection = new Socket(ip, ServerPort); 

      inputStream = new DataInputStream(socketConnection.getInputStream());
      outputStream = new DataOutputStream(socketConnection.getOutputStream());
      outputStream.writeUTF(name);
      new Thread(SendMessages(outputStream, name)).start();
      new Thread(ReciveMessages(inputStream)).start();
   }
}