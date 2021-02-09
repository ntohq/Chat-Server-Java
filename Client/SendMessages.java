import java.io.DataOutputStream;
import java.util.Scanner;

public class SendMessages implements Runnable
{
   DataOutputStream outputStream;
   String textToSend;
   Scanner userInput;
   String name;

   public SendMessages(DataOutputStream EstablishedOutputStream, String userName, Scanner EstablishedUserInput)
   {
      outputStream = EstablishedOutputStream;
      name = userName;
      userInput = EstablishedUserInput;
   }

   public void run() 
   {
      while(true)
      {
         textToSend = userInput.nextLine();

         try 
         {
            outputStream.writeUTF(name + "# " + textToSend);

            if (textToSend.toLowerCase().equals("logout"))
            {
               System.exit(0);
            }
         } catch(Exception e) 
         {
            e.printStackTrace(); 
         }
      }
   }
}
