/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author dinhh
 */
public class DataType_UDP_nWTqUnGc {

    public static void main(String[] args) {
        String studentCode = "B22DCDT147"; // thay bằng mã của bạn
        String qCode = "nWTqUnGc";         // thay bằng mã câu hỏi
        String message = ";" + studentCode + ";" + qCode;

        String serverIp = "203.162.10.109"; // đổi thành IP của server thật
        int serverPort = 2207;
        
        try
        {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(serverIp);
            
            // 1. Gửi chuỗi đến server
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);
            System.out.println("Đã gửi: " + message);
            
             // 2. Nhận dữ liệu từ server
            byte[] receiveBuffer = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Nhận từ server: " + response);
            
            String[] parts = response.split(";");
            String requestId = parts[0];
            String[] nums = parts[1].split(",");
            
            
            int MIN = Integer.MAX_VALUE;
            int MAX = Integer.MIN_VALUE;
            for (String i : nums)
            {
                if(Integer.parseInt(i) < MIN )
                {
                    MIN = Integer.parseInt(i);
                }
                else if (Integer.parseInt(i) > MAX)
                {
                    MAX = Integer.parseInt(i);
                }
            }
            
            byte[] result = ( requestId + ";" + MAX + "," + MIN ).getBytes();
            sendPacket = new DatagramPacket(result, result.length,serverAddress, serverPort );
            socket.send(sendPacket);
            System.out.println("Đã gửi: " + message);
            
            socket.close();
        }
        catch(Exception e)
        {
            
        }
        
    }
    
}
