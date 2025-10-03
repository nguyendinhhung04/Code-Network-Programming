/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package UDP;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author dinhh
 */
public class Object_UDP_4USpka5b {

    
    public static String capitalizeWords(String input) {
    if (input == null || input.isEmpty()) return input;

    String[] words = input.trim().toLowerCase().split("\\s+"); // tách từ theo khoảng trắng
    StringBuilder result = new StringBuilder();

    for (String word : words) {
        if (word.length() > 0) {
            result.append(Character.toUpperCase(word.charAt(0))) // chữ cái đầu
                  .append(word.substring(1))                     // phần còn lại
                  .append(" ");
        }
    }
    return result.toString().trim(); // bỏ khoảng trắng cuối
}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        String studentCode = "B22DCDT147"; // thay bằng mã của bạn
        String qCode = "4USpka5b ";         // thay bằng mã câu hỏi
        String message = ";" + studentCode + ";" + qCode;

        String serverIp = "203.162.10.109"; // đổi thành IP của server thật
        int serverPort = 2209;
        
        
        try
        {
            
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(serverIp);
            
            byte[] initBytes = (";" + studentCode + ";" + qCode).getBytes();
            DatagramPacket packet = new DatagramPacket(initBytes, initBytes.length, serverAddress, serverPort);
            socket.send(packet);
            
            byte[] buffer = new byte[8192];
            DatagramPacket receivedBytes = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            
            String requestId = new String(receivedBytes.getData(), 0, 8, "UTF-8");
            
            ByteArrayInputStream bais = new ByteArrayInputStream(receivedBytes.getData(),
                    8, receivedBytes.getLength()-8);
            ObjectInputStream ios = new ObjectInputStream(bais);
            Book book = (Book) ios.readObject();
            
            book.title = capitalizeWords(book.title);
            
        }
        catch(Exception e)
        {
            System.err.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
              
        
    }
}

