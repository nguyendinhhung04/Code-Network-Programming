/*
 Mật mã caesar, còn gọi là mật mã dịch chuyển, 
 */

package TCP;

import java.io.*;
import java.net.*;
public class New_TCP_Data_O4PersEA {

    public static void main(String[] args) {
        String serverHost = "203.162.10.109"; // hoặc IP server
        int serverPort = 2207;
        String studentCode = "B22DCDT147"; // đổi theo mã sinh viên của bạn
        String qCode = "O4PersEA";

        try (Socket socket = new Socket()) {
            // Kết nối với server, timeout 5s
            socket.connect(new InetSocketAddress(serverHost, serverPort), 5000);
            socket.setSoTimeout(5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            // --- a. Gửi mã sinh viên + qCode ---
            String request = studentCode + ";" + qCode;
            out.writeUTF(request);
            out.flush();
            System.out.println("Sent: " + request);

            // --- b. Nhận chuỗi mã hóa và giá trị dịch chuyển s ---
            String cipherText = in.readUTF(); // chuỗi đã bị mã hóa Caesar
            int s = in.readInt();             // giá trị dịch chuyển
            System.out.println("Received cipher: " + cipherText);
            System.out.println("Shift s: " + s);

            // --- c. Giải mã Caesar ---
            String plainText = caesarDecrypt(cipherText, s);
            System.out.println("Decrypted text: " + plainText);

            // Gửi kết quả về server
            out.writeUTF(plainText);
            out.flush();
            System.out.println("Sent decrypted text.");

            // --- d. Đóng kết nối ---
            socket.close();
            System.out.println("Connection closed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm giải mã Caesar
    private static String caesarDecrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        shift = shift % 26; // đảm bảo dịch trong bảng chữ cái
        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                char ch = (char) (c - shift);
                if (ch < 'A') ch += 26;
                result.append(ch);
            } else if (c >= 'a' && c <= 'z') {
                char ch = (char) (c - shift);
                if (ch < 'a') ch += 26;
                result.append(ch);
            } else {
                result.append(c); // giữ nguyên ký tự không phải chữ
            }
        }
        return result.toString();
    }
}
