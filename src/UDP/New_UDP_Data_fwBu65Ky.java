/*
 Thực hiện tìm giá trị lớn nhất trong mỗi cửa sổ trượt với kích thước k trên mảng số nguyên nhận được,
và gửi thông điệp lên server theo định dạng "requestId;max1,max2,...,maxm", 
trong đó max1 đến maxm là các giá trị lớn nhất tương ứng trong mỗi cửa sổ.
 */
package UDP;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class New_UDP_Data_fwBu65Ky {

    public static void main(String[] args) {
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

            // --- Parse dữ liệu: requestId;n;k;z1,z2,...,zn ---
            String[] parts = received.split(";", 4);
            if (parts.length < 4) {
                System.out.println("Invalid format from server!");
                return;
            }

            String requestId = parts[0];
            int n = Integer.parseInt(parts[1]);
            int k = Integer.parseInt(parts[2]);
            String[] nums = parts[3].split(",");
            List<Integer> arr = new ArrayList<>();
            for (String num : nums) arr.add(Integer.parseInt(num.trim()));

            // --- c. Tính max trong mỗi cửa sổ trượt ---
            List<Integer> resultList = slidingWindowMax(arr, k);

            // Tạo chuỗi kết quả
            StringBuilder resultStr = new StringBuilder();
            for (int i = 0; i < resultList.size(); i++) {
                resultStr.append(resultList.get(i));
                if (i < resultList.size() - 1)
                    resultStr.append(",");
            }

            String sendResult = requestId + ";" + resultStr;
            byte[] resultData = sendResult.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, serverAddr, port);
            socket.send(resultPacket);
            System.out.println("Sent result: " + sendResult);

            socket.close();
            System.out.println("Done.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Hàm tính giá trị lớn nhất trong mỗi cửa sổ trượt kích thước k ---
    private static List<Integer> slidingWindowMax(List<Integer> arr, int k) {
        List<Integer> result = new ArrayList<>();
        Deque<Integer> dq = new ArrayDeque<>(); // lưu chỉ số phần tử

        for (int i = 0; i < arr.size(); i++) {
            // loại bỏ phần tử ngoài cửa sổ
            while (!dq.isEmpty() && dq.peekFirst() <= i - k)
                dq.pollFirst();

            // loại bỏ phần tử nhỏ hơn phần tử hiện tại
            while (!dq.isEmpty() && arr.get(dq.peekLast()) <= arr.get(i))
                dq.pollLast();

            dq.offerLast(i);

            // ghi nhận giá trị lớn nhất khi cửa sổ đủ k phần tử
            if (i >= k - 1)
                result.add(arr.get(dq.peekFirst()));
        }

        return result;
    }
}
