
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EchoClient extends Thread {
	PrintStream os;
	FileInputStream is;

	public void run() {
		Socket echoSocket = null;
		try {

			System.out
			.println("Please enter full path for single file or mutiple files seperated by one white space:");
			String filepath;
			Scanner cin = new Scanner(System.in);
	    	//create filename from console
			filepath = cin.nextLine();

			String[] paths = filepath.split(" ");
			//Build the connection
			for (String name : paths) {
				echoSocket = new Socket("127.0.0.1", 8888);
				os = new PrintStream(echoSocket.getOutputStream());
				File file = new File(name);
				is = new FileInputStream(file);

				os.print(name);
				if (file.exists()) {

					byte[] data = new byte[(int) file.length()];
					is.read(data);
					os.write(data);

					os.flush();
					os.close();
					is.close(); 
					echoSocket.close();
				} else {
					System.out.println("The file dosen't exist!");
				}
			}

		} catch (UnknownHostException e) {
			System.err
			.println("Don't know about host: richard.rchland.ibm.com");
		} catch (IOException e) {
			System.err
			.println("Couldn't get I/O for the connection to: 127.0.0.1");
			e.printStackTrace();
		} 
	}

	public static void main(String[] args) throws IOException {
		Thread t = new Thread(new EchoClient());
		t.start();
	}

}