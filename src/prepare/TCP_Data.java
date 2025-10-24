/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prepare;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author dinhh
 */
public class TCP_Data {
    
    public static void main(String[] args) {
        
        String serverHost = "203.162.10.109"; // hoặc IP server
        int serverPort = 2207;
        String studentCode = "B22DCDT147"; // đổi theo mã sinh viên của bạn
        String qCode = "O4PersEA";
        
        try (Socket socket = new Socket())
        {
            socket.connect(new InetSocketAddress(serverHost, serverPort));
            socket.setSoTimeout(5000);
            
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());
            
            // --- a. Gửi mã sinh viên + qCode ---
            String request = studentCode + ";" + qCode;
            out.writeUTF(request);
            out.flush();
            System.out.println("Sent: " + request);
            
            //Nhận Data
            String sentData = in.readUTF(); //Dang String
            


            // --- d. Đóng kết nối ---
            socket.close();
        }
        catch(Exception e)
        {
            
        }
    }
    
}
