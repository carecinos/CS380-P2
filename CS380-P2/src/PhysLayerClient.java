import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.TreeMap;

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
			
			TreeMap<String, String> conversion = new TreeMap<>();
			fiveToFour(conversion);
			
			double baseline = 0;
			int[] array = new int[1000];

			for(int i = 0; i <64; i++){
				int signal = is.read();
				baseline += signal;
			}
			baseline /= 64;
			
			System.out.println("Baseline established from preamble: "+baseline);
			
			String[] byteHalf = new String[64];
			
			boolean current = false;
			boolean previous = false;
			
			for(int i = 0; i < 64; i++){
				String fiveB = "";
				
				for(int j = 0; j < 5; j++){
					if(is.read()> baseline){
						current = true;
					}
					else
						current = false;
					
					if(current == previous){
						fiveB+="0";
					}
					else{
						fiveB+="1";
					}
					
					previous = current;
				}

				byteHalf[i] = conversion.get(fiveB);

			}
			

		/*	for(int i = 0; i < 64; i++){
				System.out.println(byteHalf[i]);
			}*/
			
			System.out.print("Received 32 bytes: ");
			byte[] newByte = new byte[32];
			for(int i = 0; i < 32; i++){
				
				String first = byteHalf[2*i];
				String second = byteHalf[2*i+1];
				System.out.printf("%X", Integer.parseInt(first, 2));
				System.out.printf("%X", Integer.parseInt(second, 2));
				newByte[i]=(byte)Integer.parseInt((first + second),2);
			}
			
			System.out.println();
			os.write(newByte);
			
			if(is.read()==1)
				System.out.println("Response is good.");
		}
		System.out.println("Disconnected from server. ");

	}

	public static void fiveToFour(TreeMap<String,String> table){
		// TODO Auto-generated method stub
		table.put("11110","0000");
		table.put("01001","0001");
		table.put("10100","0010");
		table.put("10101","0011");
		table.put("01010","0100");
		table.put("01011","0101");
		table.put("01110","0110");
		table.put("01111","0111");
		table.put("10010","1000");
		table.put("10011","1001");
		table.put("10110","1010");
		table.put("10111","1011");
		table.put("11010","1100");
		table.put("11011","1101");
		table.put("11100","1110");
		table.put("11101","1111");
	}


}
