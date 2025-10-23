/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WS;

/**
 *
 * @author dinhh
 */
import java.net.URL;
import java.util.*;
import javax.xml.ws.Service;
import javax.xml.namespace.QName;

public class DataService_WS_PIBif50J {

    public static void main(String[] args) {
        try {
            // ===== (a) Khởi tạo Web Service Client =====
            String wsURL = "http://203.162.10.109:8080/JNPWS/DataService?wsdl";
            URL url = new URL(wsURL);

            // namespaceURI và serviceName dựa theo WSDL
            QName qname = new QName("http://RMI/", "DataService"); 
            Service service = Service.create(url, qname);

            // Lấy đối tượng proxy (port)
            DataService dataService = service.getPort(DataService.class);

            // Thông tin bài làm
            String studentCode = "B22DCDT147";
            String qCode = "PIBif50J";

            // ===== (a) Gọi phương thức getData =====
            List<Integer> numbers = dataService.getData(studentCode, qCode);
            System.out.println("Dữ liệu số nguyên nhận được: " + numbers);

            // ===== (b) Chuyển sang chuỗi nhị phân =====
            List<String> binaryStrings = new ArrayList<>();
            for (Integer num : numbers) {
                String binary = Integer.toBinaryString(num);
                binaryStrings.add(binary);
            }

            System.out.println("Chuỗi nhị phân sau chuyển đổi: " + binaryStrings);

            // ===== (c) Gửi lại server =====
            dataService.submitDataStringArray(studentCode, qCode, binaryStrings);
            System.out.println("Đã gửi dữ liệu nhị phân trở lại server.");

            // ===== (d) Kết thúc =====
            System.out.println("Client hoàn thành.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== Interface khớp với WSDL =====
    public interface DataService {
        List<Integer> getData(String studentCode, String qCode);
        void submitDataStringArray(String studentCode, String qCode, List<String> data);
    }
}
