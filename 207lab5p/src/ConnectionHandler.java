import java.io.*;
import java.net.*;


public class ConnectionHandler implements Runnable {

	private Socket cs;
	
	public ConnectionHandler(Socket cs){
		this.cs = cs;
	}
	@Override
	public void run() {
		try {
			System.out.println("Starting serve one client");
			BufferedReader input = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			//PrintWriter output = new PrintWriter(cs.getOutputStream(), true);
			DataOutputStream output = new DataOutputStream(cs.getOutputStream());
		    
		    String inputLine = "";
		    
			while ((inputLine = input.readLine()) != null) {
			       // Echo the line out
			       if (inputLine.equals("QUIT")){
			    	   System.out.println("One client goes out");
			    	   return;
			       }
			      
			       System.out.println("Input [" + inputLine + "] comes in"); 
			       output.writeBytes(inputLine);
			       output.writeByte('\n');
			       //output.print(inputLine);
			       //output.flush();    
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String [] argu){
		try {
			ServerSocket serverSocket = new ServerSocket(8888);
			System.out.println("Listening on port 8888");
			//int p = 10;
			while(true){
					
			        Socket clientSocket = serverSocket.accept();
			        System.out.println("One client comes in");
			        Thread th = new Thread (new ConnectionHandler(clientSocket));
			        th.setPriority(1);
			        th.start();
			}
			} catch (IOException ee) {
		        System.out.println("Could not listen on port: " + 8888 + ", " + ee);
		        System.exit(1);
		    }
	}

}
