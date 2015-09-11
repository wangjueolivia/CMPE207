
import java.io.*;
import java.net.ServerSocket;
import java.net.*;


public class EchoServer {

	public static void main(String[] args) throws IOException {
		System.out.println("The EchoServer is now working.");
		ServerSocket listener = new ServerSocket(8888);

		try {
			while (true) {
				new ServerService(listener.accept()).start();
			}
		} finally {
			listener.close();
		}
	}
}

class ServerService extends Thread {
	private Socket ClientSocket;
	private PrintWriter os;
	private BufferedInputStream is;
	static int cnt = 1;

	public ServerService(Socket socket) {
		this.ClientSocket = socket;
	}

	public void run() {
		try {
			String inputLine;
			is = new BufferedInputStream(ClientSocket.getInputStream());
			os = new PrintWriter(new OutputStreamWriter(
				ClientSocket.getOutputStream()));
	    //get filename
			char[] tmp = new char[30];
			int count = 3;
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = (char) is.read();
				if (tmp[i] == '.') {
					while (count > 0) {
						tmp[++i] = (char) is.read();
						count--;
					}
					break;
				}
			}
	    //create the file 
			String filename = new String(tmp);
			String ext = filename.substring(filename.lastIndexOf('\\')+1,filename.indexOf("."));
			filename = System.getProperty("user.dir")+"\\Client"+ext +".txt";
			File f = new File(filename);
			if(!f.exists())
				f.createNewFile();
			FileOutputStream fos =new FileOutputStream(f);
			int data;
	    //write data to file
			while ((data = is.read()) != -1) {
				fos.write(data);
			}
			is.close();
			os.close();
			fos.flush();
			fos.close();
			ClientSocket.close();
		} catch (UnknownHostException e) {
			System.err
			.println("Don't know about host: richard.rchland.ibm.com");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't get I/O for the connection");
		}
	}

}
