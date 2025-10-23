/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

// ===== Interface từ xa =====
interface ByteService extends java.rmi.Remote {
    byte[] requestData(String studentCode, String qCode) throws java.rmi.RemoteException;
    void submitData(String studentCode, String qCode, byte[] data) throws java.rmi.RemoteException;
}

// ===== Chương trình Client =====
public class RMI_Byte_isDG6RUk {

    public static void main(String[] args) {
        try {
            // (a) Kết nối đến RMI Registry
            String host = "203.162.10.109";  // Địa chỉ server
            int port = 1099;                 // Cổng RMI mặc định
            Registry registry = LocateRegistry.getRegistry(host, port);

            // Lấy đối tượng từ xa
            ByteService service = (ByteService) registry.lookup("RMIByteService");

            // Thông tin sinh viên & mã câu hỏi
            String studentCode = "B22DCDT147";
            String qCode = "isDG6RUk";

            // (a) Gọi requestData để nhận dữ liệu nhị phân
            byte[] data = service.requestData(studentCode, qCode);
            System.out.println("Dữ liệu gốc (byte[]): " + Arrays.toString(data));
            System.out.println("Dữ liệu gốc (chuỗi): " + new String(data));

            // (b) Mã hóa Caesar – độ dịch = độ dài mảng
            int shift = data.length;
            byte[] encoded = caesarEncode(data, shift);
            System.out.println("Sau mã hóa (byte[]): " + Arrays.toString(encoded));
            System.out.println("Sau mã hóa (chuỗi): " + new String(encoded));

            // (c) Gửi dữ liệu mã hóa trở lại server
            service.submitData(studentCode, qCode, encoded);
            System.out.println("Đã gửi dữ liệu mã hóa trở lại server.");

            // (d) Kết thúc chương trình
            System.out.println("Client kết thúc.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== Hàm mã hóa Caesar =====
    private static byte[] caesarEncode(byte[] data, int shift) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            int shifted = (data[i] + shift) % 128; // Giữ trong phạm vi ASCII 0–127
            result[i] = (byte) shifted;
        }
        return result;
    }
}