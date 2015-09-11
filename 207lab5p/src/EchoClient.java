import java.net.*;
import java.util.Scanner;
import java.io.*;

public class EchoClient {

	public static void main(String [] argu){
		
		try 
		{
			Socket echoSocket = new Socket("127.0.0.1", 8888);
			//PrintWriter output = new PrintWriter(echoSocket.getOutputStream(), true);
			DataOutputStream output = new DataOutputStream(echoSocket.getOutputStream());
			BufferedReader is = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			
			String userInput = "";
			
			Scanner reader = new Scanner(System.in);
			
			while ((userInput = reader.nextLine()) != null) 
			{   
				if(userInput.equals("QUIT")){
		        	break;
		        }
		        output.writeBytes(userInput);
		        output.writeByte('\n');
		        //output.flush();
		        System.out.println("echo: " + is.readLine());
		        
		    }
			
			echoSocket.close();
		} catch (UnknownHostException e) 
		{
			System.err.println("Don't know about host: 127.0.0.1");
		} catch (IOException e) 
		{
			System.err.println("Couldn't get I/O for the connection to: 127.0.0.1");
		}
		
	}
}
