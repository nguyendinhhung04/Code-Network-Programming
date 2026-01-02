/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author dinhh
 */
public class UDP_Object_Laptop_saiNameQuanity {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        String studentCode = "B22DCDT147"; // thay bằng mã của bạn
        String qCode = "gSGu4asI";         // thay bằng mã câu hỏi
        String message = ";" + studentCode + ";" + qCode;

        String serverIp = "203.162.10.109"; // đổi thành IP của server thật
        int Port = 2209;
        int serverPort = 2209;
        
        try
        {
            InetAddress serverAddress = InetAddress.getByName(serverIp);
            DatagramSocket socket = new DatagramSocket();
            
            byte[] sentData = ( ";" + studentCode + ";" + qCode).getBytes();
            DatagramPacket packet = new DatagramPacket(sentData,sentData.length, serverAddress, Port);
            socket.send(packet);
            
            // 2. Nhận phản hồi từ server
            byte[] receiveBuffer = new byte[8192]; // đủ lớn để chứa Product
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            byte[] data = receivePacket.getData();
            int length = receivePacket.getLength();

            // 3. 8 byte đầu là requestId
            String requestId = new String(data, 0, 8, "UTF-8");
            System.out.println("RequestId: " + requestId);

            // 4. Deserialize Product từ byte[] còn lại
            ByteArrayInputStream bais = new ByteArrayInputStream(data, 8, length - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Product product = (Product) ois.readObject();
            ois.close();
            System.out.println(product.toString());
            
            String[] parts = product.name.split(" ");
            String realName = parts[parts.length-1];
            for (int i = 1; i<= parts.length-2; i++)
            {
                realName += " " + parts[i];
            }
            realName += " " + parts[0];

            product.name = realName;
            String num = String.valueOf(product.quantity);
            String reversed = new StringBuilder(num).reverse().toString();
            product.quantity = Integer.parseInt(reversed);
            System.out.println(product.toString());
            
            // Serialize product + prepend requestId
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(requestId.getBytes("UTF-8"));     // ghi luôn 8 byte requestId vào đầu
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(product);                    // serialize product nối ngay sau requestId
            oos.flush();

            byte[] sendBack = baos.toByteArray();        // dữ liệu cuối cùng để gửi
            DatagramPacket sendBackPacket = new DatagramPacket(
                    sendBack, sendBack.length, serverAddress, serverPort);
            socket.send(sendBackPacket);
            
            socket.close();
                                                                        
        }
        catch(Exception e)
        {
            System.err.println("Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
}
