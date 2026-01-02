/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package UDP;

import java.net.DatagramSocket;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author dinhh
 */
public class UDP_DataType_MaxMin {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try(DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(5000); // timeout 5s

            InetAddress serverAddress  = InetAddress.getByName("203.162.10.109");
            
            String message = ";B22DCDT147;yLHAgDav";
            byte[] sentByte = message.getBytes();
            DatagramPacket sentPacket = new DatagramPacket(sentByte,sentByte.length, serverAddress, 2207);
            socket.send(sentPacket);
            
            byte[] receivedBytes = new byte[4096];
            DatagramPacket receivedPacket = new DatagramPacket(receivedBytes, receivedBytes.length);
            socket.receive(receivedPacket);
            String receivedString = new String(receivedPacket.getData(),0,receivedPacket.getLength());
            System.out.println(receivedString);
            String[] parts = receivedString.split(";");
            String requestId = parts[0];
            String myString = parts[1];
            
            String[] splitString = myString.split("\\,");
            Integer MAX = Integer.parseInt(splitString[0]);
            Integer MIN = Integer.parseInt(splitString[0]);
            for(String i:splitString)
            {
                Integer temp = Integer.parseInt(i);
                if(temp >= MAX)
                {
                    MAX = temp;
                }
                else if(temp<=MIN)
                {
                    MIN = temp;
                }
            }
            
            String result = requestId + ";" + MAX.toString() + "," + MIN.toString();
            System.out.println(result);
            DatagramPacket resultPacket = new DatagramPacket(result.getBytes(), result.getBytes().length, serverAddress, 2207);
            socket.send(resultPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
