    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package prepare;

import static TCP.TCP_ByteStream_chuoicontangdandainhat.Solve;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author dinhh
 */
public class TCP_ByteStream {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try(Socket socket = new Socket()){
            
            String serverHost = "203.162.10.109"; // hoặc IP server
            int serverPort = 2206;
            String studentCode = "B22DCDT147"; // đổi theo mã sinh viên của bạn
            String qCode = "14shBC14";
            
            socket.connect(new InetSocketAddress(serverHost, serverPort));
            socket.setSoTimeout(5000);
            
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            
            out.write( (studentCode+";"+qCode).getBytes() );
            out.flush();
            
            byte[] data = new byte[1024];
            int length = in.read(data);
            String receiveString = new String(data, 0 , length);
            System.out.println(receiveString);
            
            String result = "";
            
            out.write(result.getBytes());
            out.flush();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
