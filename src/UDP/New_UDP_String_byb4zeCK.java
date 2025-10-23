/*
Str1, String 2 bỏ phần trùng lặp
*/

package UDP;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class New_UDP_String_byb4zeCK {
    public static void main(String[] args) {
        String serverHost = "203.162.10.109"; // hoặc IP server
        int serverPort = 2208;
        String studentCode = "B22DCDT147"; // đổi theo mã sinh viên của bạn
        String qCode = "byb4zeCK";

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000); // timeout 5s

            // --- a. Gửi thông điệp ";studentCode;qCode" ---
            String request = ";" + studentCode + ";" + qCode;
            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);
            InetAddress serverAddress = InetAddress.getByName(serverHost);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);
            System.out.println("Sent: " + request);

            // --- b. Nhận thông điệp "requestId;str1;str2" ---
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            String received = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
            System.out.println("Received: " + received);

            String[] parts = received.split(";", 3);
            if (parts.length < 3) {
                System.out.println("Invalid response from server.");
                return;
            }
            String requestId = parts[0];
            String str1 = parts[1];
            String str2 = parts[2];

            // --- c. Loại bỏ các ký tự trong str1 xuất hiện trong str2 ---
            String strOutput = removeChars(str1, str2);

            // Gửi kết quả về server "requestId;strOutput"
            String result = requestId + ";" + strOutput;
            byte[] resultData = result.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, serverAddress, serverPort);
            socket.send(resultPacket);
            System.out.println("Sent: " + result);

            // --- d. Đóng socket ---
            socket.close();
            System.out.println("Connection closed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm loại bỏ ký tự trong str1 xuất hiện trong str2, giữ thứ tự
    private static String removeChars(String str1, String str2) {
        Set<Character> charsToRemove = new HashSet<>();
        for (char c : str2.toCharArray()) {
            charsToRemove.add(c);
        }

        StringBuilder sb = new StringBuilder();
        for (char c : str1.toCharArray()) {
            if (!charsToRemove.contains(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
