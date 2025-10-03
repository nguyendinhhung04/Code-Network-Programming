/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

    

public class String_UDP_SHQCe2TV {

    // Hàm chuẩn hóa chuỗi: viết hoa chữ cái đầu, các ký tự còn lại viết thường
    public static String normalize(String input) {
        if (input == null || input.isEmpty()) return input;

        String[] words = input.trim().toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1))
                      .append(" ");
            }
        }
        return result.toString().trim();
    }
    
    public static void main(String[] args) {
        String studentCode = "B15DCCN001"; // sửa lại cho đúng mã sinh viên
        String qCode = "5B35BCC1";
        String serverHost = "localhost"; // hoặc IP server
        int serverPort = 2208;

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(serverHost);

            // a. Gửi thông điệp ";studentCode;qCode"
            String message = ";" + studentCode + ";" + qCode;
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);
            System.out.println("Đã gửi: " + message);

            // b. Nhận thông điệp "requestId;data"
            byte[] receiveBuffer = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            String received = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Nhận từ server: " + received);

            // Tách requestId và data
            String[] parts = received.split(";", 2);
            if (parts.length < 2) {
                System.out.println("Thông điệp không hợp lệ!");
                return;
            }
            String requestId = parts[0];
            String data = parts[1];

            // c. Chuẩn hóa chuỗi data
            String normalizedData = normalize(data);
            System.out.println("Chuỗi sau chuẩn hóa: " + normalizedData);

            // Gửi lại "requestId;normalizedData"
            String response = requestId + ";" + normalizedData;
            byte[] responseBytes = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, serverAddress, serverPort);
            socket.send(responsePacket);
            System.out.println("Đã gửi lại: " + response);

            // d. Socket sẽ tự đóng vì dùng try-with-resources
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
