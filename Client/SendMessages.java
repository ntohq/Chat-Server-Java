import java.io.DataOutputStream;
import java.util.Scanner;

public class SendMessages implements Runnable
{
   DataOutputStream outputStream;
   String textToSend;
   Scanner userInput = new Scanner(System.in);
   String name;

   public SendMessages(DataOutputStream EstablishedOutputStream, String userName)
   {
      outputStream = EstablishedOutputStream;
      name = userName;
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
