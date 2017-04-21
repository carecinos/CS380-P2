import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 
 */

/**
 * @author cesar
 *
 */
public class PhysLayerClient {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try(Socket socket = new Socket("codebank.xyz", 38002)){
			
			System.out.println("Connected to server.");
			
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			double baseline = 0;
			int[] array = new int[1000];

			for(int i = 0; i <64; i++){
				int signal = is.read();
				baseline += signal;
			}
			baseline /= 64;
			
			System.out.println("Baseline established from preamble:"+baseline);
			

		}

	}

}
