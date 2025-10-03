
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dinhh
 */
public class TCP_ByteStream_pP4Kcd32 {
    
    public static long CalculateSum(String s)
    {
        String[] arr = s.split("\\|");
            Integer sum = 0;
            for(String i : arr)
            {
                System.out.print(Integer.parseInt(i) + " ");
                sum += Integer.parseInt(i);
               
            }
            System.out.println("\n");
            return sum;
    }
    
    public static void main(String[] args) {
        String studentCode = "B22DCDT147";
        String qCode = "pP4Kcd32";
        int port = 2206;

        try(Socket socket = new Socket("203.162.10.109", port);)
        {
            socket.setSoTimeout(5000);

            //init a
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            String sent = studentCode + ";" + qCode;
            out.write(sent.getBytes());
            out.flush();
            System.out.println("Sent: " + sent);

            byte[] data = new byte[1024];
            int length = in.read(data);
            String received = new String(data, 0, length);
            System.out.println(received);
            
            long sum = CalculateSum(received);
            out.write(String.valueOf(sum).getBytes());
            out.flush();
            System.out.println(sum);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }
}
