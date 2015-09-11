

import java.util.Scanner;
import java.io.*;
import java.net.*;

public class EchoServer{
    public static void main(String [] args)
    {
        EchoServer myServer = new EchoServer();
        
        ServerSocket mySocket = null;
        Socket myClientSocket = null;

        BufferedReader myIS = null; 
        PrintWriter myOS =  null;


        //create server Socket
        try{
            mySocket = new ServerSocket(888);
        }
        catch (IOException e){
            System.out.println("Could not listen on port 8888.");
            System.exit(1);
        }
        System.out.println("Server is listenning on port 8888");


        //accept client 
        try{
            myClientSocket = mySocket.accept();
            myIS = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream()));
            myOS = new PrintWriter(new BufferedOutputStream(myClientSocket.getOutputStream(), 1024), false);
        }
        catch(IOException e) {
            System.out.println("Accpet failed on port 8888");
            System.exit(1);
        }


        String myMessage;
        try{
            //String inMessage;
            while(true)
            {
                //get message from client socket
                myMessage = myIS.readLine();
                if(myMessage==null)continue;
                //local echo
                System.out.println("Get Input: " + myMessage);
                System.out.println("New Input ["+ myMessage+"] comes in");
                //echo to the client
                myOS.write(myMessage + "\n");
                myOS.flush();
                if(myMessage.equals("QUIT"))break;

            }
            //close socket
            if(myIS != null)myIS.close();
            if(myOS != null)myOS.close();
            if(mySocket != null)mySocket.close();
            if(myClientSocket != null)myClientSocket.close();
            System.out.println("Connetion Closed");
        }
        catch(Exception e){
            System.out.println("Exception:"+e);
            e.printStackTrace();
        }

    }
}

