/*
Customer
Thông tin khách hàng cần thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
 */

package TCP;

import java.io.*;
import java.net.*;
import java.util.Locale;


public class New_TCP_Object_WOTt5fbM {

    public static void main(String[] args) {
        String serverHost = "203.162.10.109"; // hoặc IP server
        int serverPort = 2209;
        String studentCode = "B22DCDT147"; // đổi theo mã sinh viên của bạn
        String qCode = "WOTt5fbM";

        try (Socket socket = new Socket()) {
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
            Customer customer = (Customer) ois.readObject();
            System.out.println("Received customer:");
            System.out.println(customer);

            // --- 3) Chuẩn hóa thông tin ---
            customer.setName(formatName(customer.getName()));
            customer.setDayOfBirth(formatDate(customer.getDayOfBirth()));
            customer.setUserName(generateUserName(customer.getName()));
            System.out.println("Formatted customer:");
            System.out.println(customer);

            // Gửi đối tượng đã chuẩn hóa về server
            oos.writeObject(customer);
            oos.flush();
            System.out.println("Sent formatted customer.");

            // --- 4) Đóng kết nối ---
            socket.close();
            System.out.println("Connection closed.");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // --- Hàm chuẩn hóa tên ---
    private static String formatName(String fullName) {
        String[] parts = fullName.trim().toLowerCase(Locale.ROOT).split("\\s+");
        if (parts.length == 0) return fullName;

        String lastName = parts[parts.length - 1].toUpperCase();
        StringBuilder firstNames = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            firstNames.append(Character.toUpperCase(parts[i].charAt(0)))
                      .append(parts[i].substring(1));
            if (i < parts.length - 2) firstNames.append(" ");
        }
        return lastName + ", " + firstNames.toString();
    }

    // --- Hàm chuẩn hóa ngày sinh mm-dd-yyyy -> dd/mm/yyyy ---
    private static String formatDate(String dob) {
        String[] parts = dob.split("-");
        if (parts.length != 3) return dob;
        return parts[1] + "/" + parts[0] + "/" + parts[2]; // dd/mm/yyyy
    }

    // --- Sinh userName từ họ tên ---
private static String generateUserName(String formattedName) {
    String[] split = formattedName.split(",");
    if (split.length != 2) return formattedName.toLowerCase().replaceAll("\\s+", "");
    String lastName = split[0].toLowerCase().trim();
    String[] firstNames = split[1].trim().split("\\s+");
    StringBuilder userName = new StringBuilder();
    for (String fn : firstNames) {
        userName.append(Character.toLowerCase(fn.charAt(0)));
    }
    userName.append(lastName);
    return userName.toString();
}
    
}
