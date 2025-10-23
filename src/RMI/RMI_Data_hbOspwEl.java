/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RMI;

/**
 *
 * @author dinhh
 */
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

interface DataService extends Remote {
    public Object requestData(String studentCode, String qCode) throws RemoteException;
    public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
}

public class RMI_Data_hbOspwEl {

    public static void main(String[] args) {
        try {
            // (a) Kết nối đến RMI registry
            String host = "203.162.10.109"; // IP server
            int port = 1099;
            Registry registry = LocateRegistry.getRegistry(host, port);

            DataService service = (DataService) registry.lookup("RMIDataService");

            String studentCode = "B22DCDT147";
            String qCode = "hbOspwEl";

            // Nhận dữ liệu chuỗi từ server, ví dụ: "4, 2, 4, 5, 5"
            Object obj = service.requestData(studentCode, qCode);
            if (!(obj instanceof String)) {
                System.out.println("Dữ liệu nhận không phải là chuỗi!");
                return;
            }

            String dataStr = ((String) obj).trim();
            System.out.println("Dữ liệu gốc nhận từ server: " + dataStr);

            // (b) Chuyển chuỗi sang mảng số nguyên
            int[] arr = parseToIntArray(dataStr);
            System.out.println("Mảng ban đầu: " + Arrays.toString(arr));

            // Tìm tổ hợp kế tiếp
            int[] next = nextPermutation(arr.clone());
            System.out.println("Tổ hợp kế tiếp: " + Arrays.toString(next));

            // (c) Chuyển mảng kết quả về chuỗi "x, y, z"
            String result = arrayToString(next);
            System.out.println("Chuỗi kết quả gửi lên server: " + result);

            // Gửi dữ liệu đã mã hóa lên server
            service.submitData(studentCode, qCode, result);

            System.out.println("Đã gửi tổ hợp kế tiếp về server.");
            System.out.println("Client kết thúc.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Chuyển chuỗi "4, 2, 4, 5, 5" -> int[] {4, 2, 4, 5, 5}
    private static int[] parseToIntArray(String str) {
        String[] parts = str.split(",");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i].trim());
        }
        return arr;
    }

    // ✅ Chuyển int[] -> "4, 2, 5, 4, 5"
    private static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(",");
        }
        return sb.toString();
    }

    // ✅ Hàm tìm tổ hợp kế tiếp (next permutation)
    private static int[] nextPermutation(int[] arr) {
        int n = arr.length;
        int i = n - 2;

        while (i >= 0 && arr[i] >= arr[i + 1]) {
            i--;
        }

        if (i >= 0) {
            int j = n - 1;
            while (arr[j] <= arr[i]) {
                j--;
            }
            swap(arr, i, j);
        }

        reverse(arr, i + 1, n - 1);
        return arr;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            swap(arr, start++, end--);
        }
    }
}
