/*
Employee
 Chuẩn hóa name: viết hoa chữ cái đầu của mỗi từ, ví dụ "john doe" thành "John Doe".
    Tăng salary: tăng x% lương, với x bằng tổng các chữ số của năm sinh.
    Chuyển đổi hireDate từ định dạng yyyy-mm-dd sang dd/mm/yyyy. Ví dụ: "2023-07-15" thành "15/07/2023".
    Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Employee đã được sửa đổi.
 */
package UDP;

import UDP.Employee;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class New_UDP_Object_9Z66lNMo {
    public static void main(String[] args) {
        String studentCode = "B22DCDT147"; // 🔹 Thay mã sinh viên
        String qCode = "9Z66lNMo";
        String host = "203.162.10.109"; // 🔹 Server thật
        int port = 2209;

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000);
            InetAddress serverAddr = InetAddress.getByName(host);

            // --- a. Gửi thông điệp ";studentCode;qCode" ---
            String message = ";" + studentCode + ";" + qCode;
            byte[] sendData = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddr, port);
            socket.send(sendPacket);
            System.out.println("Sent: " + message);

            // --- b. Nhận dữ liệu từ server ---
            byte[] receiveData = new byte[8192];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            byte[] data = receivePacket.getData();
            int length = receivePacket.getLength();

            // Lấy 8 byte đầu làm requestId
            byte[] requestIdBytes = new byte[8];
            System.arraycopy(data, 0, requestIdBytes, 0, 8);
            String requestId = new String(requestIdBytes, StandardCharsets.UTF_8).trim();

            // Phần còn lại là đối tượng Employee
            byte[] objectData = new byte[length - 8];
            System.arraycopy(data, 8, objectData, 0, length - 8);

            // Deserialize Employee
            ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Employee emp = (Employee) ois.readObject();
            ois.close();

            System.out.println("Received Employee: " + emp);

            // --- c. Xử lý chuẩn hóa ---
            emp.setName(capitalizeWords(emp.getName()));
            emp.setHireDate(convertDate(emp.getHireDate()));

            int birthYear = getBirthYear(emp.getHireDate());
            int sumDigits = sumOfDigits(birthYear);
            emp.setSalary(emp.getSalary() * (1 + sumDigits / 100.0));

            System.out.println("Updated Employee: " + emp);

            // --- Gửi lại: 8 byte requestId + object ---
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(emp);
            oos.flush();
            byte[] empBytes = baos.toByteArray();

            ByteBuffer buffer = ByteBuffer.allocate(8 + empBytes.length);
            buffer.put(requestIdBytes);
            buffer.put(empBytes);

            byte[] sendBack = buffer.array();
            DatagramPacket responsePacket = new DatagramPacket(sendBack, sendBack.length, serverAddr, port);
            socket.send(responsePacket);
            System.out.println("Sent back Employee with ID: " + emp.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Viết hoa chữ cái đầu của mỗi từ ---
    private static String capitalizeWords(String input) {
        String[] words = input.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (w.length() > 0)
                sb.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    // --- Chuyển yyyy-mm-dd -> dd/mm/yyyy ---
    private static String convertDate(String date) {
        String[] parts = date.split("-");
        if (parts.length != 3) return date;
        return parts[2] + "/" + parts[1] + "/" + parts[0];
    }

    // --- Lấy năm sinh từ hireDate ---
    private static int getBirthYear(String hireDate) {
        String[] parts = hireDate.split("/");
        if (parts.length == 3) {
            try {
                return Integer.parseInt(parts[2]);
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    // --- Tổng các chữ số ---
    private static int sumOfDigits(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}
