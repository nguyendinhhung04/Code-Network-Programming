/*
 shippingType
 */
package RMI;

import java.io.Serializable;
import java.util.Arrays;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author dinhh
 */

interface ObjectService extends Remote {
    public Serializable requestObject(String studentCode, String qCode) throws RemoteException;
    public void submitObject(String studentCode, String qCode, Serializable object) throws RemoteException;
}

public class New_RMI_Object_8TCFq6QZ {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // (a) Kết nối đến RMI Registry
            String host = "203.162.10.109";
            int port = 1099;
            Registry registry = LocateRegistry.getRegistry(host, port);
            ObjectService service = (ObjectService) registry.lookup("RMIObjectService");

            String studentCode = "B22DCDT147";
            String qCode = "8TCFq6QZ";

            // Nhận đối tượng Order từ server
            Order order = (Order) service.requestObject(studentCode, qCode);
            System.out.println("Đơn hàng nhận được từ server:");
            System.out.println(order);

            // (b) Tạo mã orderCode theo quy tắc
            String orderCode = generateOrderCode(order);
            order.setOrderCode(orderCode);

            System.out.println("Mã orderCode mới tạo: " + orderCode);

            // (c) Gửi lại đối tượng Order đã cập nhật
            service.submitObject(studentCode, qCode, order);
            System.out.println("Đã gửi đối tượng Order sau khi cập nhật về server.");

            // (d) Kết thúc
            System.out.println("Client kết thúc.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ====== Hàm sinh orderCode ======
    private static String generateOrderCode(Order order) {
        // shippingType: lấy 2 ký tự đầu in hoa
        String prefix = order.getShippingType().substring(0, 2).toUpperCase();

        // customerCode: lấy 3 ký tự cuối
        String customerPart = order.getCustomerCode();
        customerPart = customerPart.substring(customerPart.length() - 3);

        // orderDate: yyyy-MM-dd -> lấy ddMM
        String[] parts = order.getOrderDate().split("-");
        String dd = parts[2];
        String MM = parts[1];
        String datePart = dd + MM;

        return prefix + customerPart + datePart;
    }
}
