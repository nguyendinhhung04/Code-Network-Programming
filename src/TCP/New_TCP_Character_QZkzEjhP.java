/*
 Tìm kiếm các tên miền .edu và gửi lên server
 */

package TCP;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class New_TCP_Character_QZkzEjhP {

    public static void main(String[] args) {
        String serverHost = "203.162.10.109"; // hoặc IP server
        int serverPort = 2208;
        String studentCode = "B22DCDT147"; // đổi theo mã sinh viên của bạn
        String qCode = "QZkzEjhP";

        try (Socket socket = new Socket()) {
            // Kết nối với server, timeout 5s
            socket.connect(new InetSocketAddress(serverHost, serverPort), 5000);
            socket.setSoTimeout(5000);

            // Khởi tạo luồng đọc/ghi
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // --- a. Gửi mã sinh viên + qCode ---
            String request = studentCode + ";" + qCode;
            writer.write(request);
            writer.newLine(); // quan trọng để server nhận đúng
            writer.flush();
            System.out.println("Sent: " + request);

            // --- b. Nhận dữ liệu từ server ---
            String response = reader.readLine(); // ví dụ: danh sách tên miền
            System.out.println("Received: " + response);

            // --- c. Lọc các tên miền .edu ---
            String[] domains = response.split(",");
            List<String> eduDomains = new ArrayList<>();
            for (String domain : domains) {
                domain = domain.trim();
                if (domain.endsWith(".edu")) {
                    eduDomains.add(domain);
                }
            }

            // Gửi danh sách .edu về server
            String result = String.join(", ", eduDomains);
            writer.write(result);
            writer.newLine();
            writer.flush();
            System.out.println("Sent edu domains: " + result);

            // --- d. Đóng kết nối ---
            socket.close();
            System.out.println("Connection closed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
