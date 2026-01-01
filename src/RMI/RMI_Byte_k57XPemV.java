///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package RMI;
//
///**
// *
// * @author dinhh
// */
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.util.Arrays;
//import java.rmi.Remote;
//import java.util.Base64;
//
//
//interface ByteService extends Remote {
//    public byte[] requestData(String studentCode, String qCode) throws java.rmi.RemoteException;
//    public void submitData(String studentCode, String qCode, byte[] data) throws java.rmi.RemoteException;
//}
//
//public class RMI_Byte_k57XPemV {
//    public static void main(String[] args) {
//        try
//        {
//            // (a) Kết nối đến RMI Registry
//            String host = "203.162.10.109";  // Địa chỉ server
//            int port = 1099;                 // Cổng RMI mặc định
//            Registry registry = LocateRegistry.getRegistry(host, port);
//
//            // Lấy đối tượng từ xa
//            ByteService service = (ByteService) registry.lookup("RMIByteService");
//
//            // Thông tin sinh viên & mã câu hỏi
//            String studentCode = "B22DCDT147";
//            String qCode = "k57XPemV";
//
//            // (a) Gọi requestData để nhận dữ liệu nhị phân
//            byte[] data = service.requestData(studentCode, qCode);
//            System.out.println("Dữ liệu gốc (byte[]): " + Arrays.toString(data));
//            System.out.println("Dữ liệu gốc (chuỗi): " + new String(data));
//            
//             String base64String = new String(data);  // chuyển về chuỗi
//             byte[] originalData = Base64.getDecoder().decode(base64String);
//             
//            service.submitData(studentCode, qCode, originalData);
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//}
