/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package prepare;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dinhh
 */
public class UDP_DataType {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String studentCode = "B22DCDT147"; // 👉 đổi theo MSSV của bạn
        String qCode = "fwBu65Ky";
        String host = "203.162.10.109"; // server chấm tự động
        int port = 2207;
        
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000); // timeout 5s
            InetAddress serverAddr = InetAddress.getByName(host);

            // --- a. Gửi ";studentCode;qCode" ---
            String message = ";" + studentCode + ";" + qCode;
            byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddr, port);
            socket.send(sendPacket);
            System.out.println("Sent: " + message);

            // --- b. Nhận thông điệp từ server ---
            byte[] receiveData = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            String received = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
            System.out.println("Received: " + received);

            socket.close();
            System.out.println("Done.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
