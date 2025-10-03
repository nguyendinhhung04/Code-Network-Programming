/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package UDP;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author dinhh
 */
public class DataType_UDP_OP85i7lg {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        String studentCode = "B22DCDT147";
        String qCode = "OP85i7lg";
        String serverIp = "203.162.10.109"; // đổi thành IP của server thật
        int Port = 2207;
        
        try
        {
            InetAddress serverAddress = InetAddress.getByName(serverIp);
            DatagramSocket socket = new DatagramSocket();
            
            byte[] sentData = ( ";" + studentCode + ";" + qCode).getBytes();
            DatagramPacket packet = new DatagramPacket(sentData,sentData.length, serverAddress, Port);
            socket.send(packet);
            
            byte[] buffer = new byte[4096];
            packet = new DatagramPacket(buffer,buffer.length);
            socket.receive(packet);
            
            String receiveString = new String(packet.getData(), 0, packet.getLength());
            System.out.println(receiveString);
            String[] parts = receiveString.split(";");
            String requestId = parts[0];
            BigInteger num1 = new BigInteger(parts[1].trim());
            BigInteger num2 = new BigInteger(parts[2].trim());
            
            
            BigInteger sum = num1.add(num2);
            BigInteger diff =  num1.subtract(num2) ;
            
            
            byte[] result = (requestId + ";" + sum.toString() + "," + diff.toString()).getBytes();
            packet = new DatagramPacket(result, result.length, serverAddress, Port);
            socket.send(packet);
            System.out.println((requestId + ";" + sum.toString() + "," + diff.toString()));
            socket.close();
            
        }
        catch(Exception e)
        {
            System.out.println("Error");
        }
        
    }
}
