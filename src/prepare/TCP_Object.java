/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package prepare;

import TCP.Customer;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author dinhh
 */
public class TCP_Object {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String serverHost = "203.162.10.109"; // hoặc IP server
        int serverPort = 2209;
        String studentCode = "B22DCDT147"; // đổi theo mã sinh viên của bạn
        String qCode = "WOTt5fbM";
        
        try(Socket socket = new Socket())
        {
            socket.connect(new InetSocketAddress(serverHost, serverPort), 5000);
            socket.setSoTimeout(5000);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            
            // --- 1) Gửi mã sinh viên + qCode ---
            String request = studentCode + ";" + qCode;
            oos.writeObject(request);
            oos.flush();
            System.out.println("Sent: " + request);
            
             // --- 2) Nhận đối tượng Customer từ server ---
            Customer customer = (Customer) ois.readObject(); //ép về kiểu objct mong muốn
            System.out.println("Received customer:");
            System.out.println(customer);
            
            // Gửi đối tượng đã chuẩn hóa về server
            oos.writeObject(customer);
            oos.flush();
            System.out.println("Sent formatted customer.");

            // --- 4) Đóng kết nối ---
            socket.close();
            System.out.println("Connection closed.");
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
