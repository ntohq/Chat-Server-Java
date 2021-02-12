import java.io.DataInputStream;
import java.io.EOFException;
import java.util.StringTokenizer;

public class ReciveMessages implements Runnable
{
   DataInputStream inputStream;
   static StringTokenizer parsedTextRecived;
   static String user, messageRecived;
   public static final String ANSI_CYAN = "\u001B[36m";
   public static final String ANSI_WHITE = "\u001B[37m";

   public ReciveMessages(DataInputStream EstablishedInputStream)
   {
      inputStream = EstablishedInputStream;
   }

   public void run()
   {
      String recivedText;
      while(true)
      {
         try 
         {
            recivedText = inputStream.readUTF();
            parsedTextRecived = new StringTokenizer(recivedText, "#");
            user = parsedTextRecived.nextToken();
            messageRecived = parsedTextRecived.nextToken();
            System.out.println(ANSI_CYAN +  "@" + user + ":" + ANSI_WHITE + messageRecived);
         }
         catch(EOFException a)
         {
            try 
            {
               System.out.println("Error: \n  It seems as the CCHT server is offline. \n Please try to connect again later.");
               inputStream.close();
               System.exit(0);
            } catch (Exception e) 
            {
               e.printStackTrace();
            }
         } 
         catch(Exception e)
         {
            e.printStackTrace();    
         }
      }
   }
}
