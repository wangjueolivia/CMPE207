
import java.io.*;
import java.net.*;

public class EchoClient
{
    public static void main(String [] args)
    {
        EchoClient myClient = new EchoClient();

        Socket mySocket = null;
        DataOutputStream myOS = null;
        BufferedReader myIS = null;

        InputStreamReader myIn = new InputStreamReader(System.in);
        BufferedReader myInput = new BufferedReader(myIn);

        try{
            mySocket = new Socket("localhost",888);
            myOS = new DataOutputStream(mySocket.getOutputStream());
            //myIS = new DataInputStream(mySocket.getInputStream());
            myIS = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        }
        catch (UnknownHostException e) {
            System.out.println("unknow host");
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println("get I/O error for the connection");
            System.exit(1);
        }

        try{
            String myMessage;
            String myResp;
            while (true)
            {
                System.out.print("> ");

                myMessage = myInput.readLine();
                if(myMessage==null)continue;

                //send message to server
                myOS.writeBytes(myMessage+"\n");

                //get response from server
                myResp = myIS.readLine();
                if(myResp!=null)System.out.println("Echo:" +myResp);

                //if QUIT, terminate the program
                if(myMessage.equals("QUIT"))break;
            }

            //close socket
            if(myOS != null)myOS.close();
            if(myIS != null)myIS.close();
            if(mySocket != null)mySocket.close();

            System.out.println("Connetion Closed");
        }
        catch(Exception e){
            System.out.println("Exception:"+e);
            e.printStackTrace();
        }
    }
}

