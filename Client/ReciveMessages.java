import java.io.DataInputStream;

public class ReciveMessages implements Runnable
{
   DataInputStream inputStream;
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
            System.out.println(recivedText);
         } catch(Exception e)
         {
            e.printStackTrace();    
         }
      }
   }
}
