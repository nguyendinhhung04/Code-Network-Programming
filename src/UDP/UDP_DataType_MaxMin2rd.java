/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author dinhh
 */
public class UDP_DataType_MaxMin2rd {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        String studentCode = "B22DCDT147";
        String qCode = "ZWvHamrW";
        String serverIp = "203.162.10.109"; // đổi thành IP của server thật
        int Port = 2207;
        
        try
        {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(serverIp);
            
            byte[] sendData = (";" + studentCode + ";" + qCode).getBytes();
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length,serverAddress, Port);
            socket.send(packet);
            
            byte[] buffer = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);
            
            String receiveString = new String(receivePacket.getData(), 0, receivePacket.getLength());
            String[] parts = receiveString.split(";");
            String requestId = parts[0];
            String[] nums = parts[1].split(",");
            
            int MIN1 = Integer.MAX_VALUE - 1;
            int MIN2 = Integer.MAX_VALUE;
            
            int MAX1 = Integer.MIN_VALUE + 1;
            int MAX2 = Integer.MIN_VALUE;
            
            for( String i : nums )
            {
                if(Integer.parseInt(i) <= MIN2 )
                {
                    if (Integer.parseInt(i) <= MIN1 )
                    {
                        MIN2 = MIN1;
                        MIN1 = Integer.parseInt(i);
                    }
                    else
                    {
                        MIN2 = Integer.parseInt(i);
                    }
                }
                if (Integer.parseInt(i) >= MAX2 )
                {
                    if (Integer.parseInt(i) >= MAX1)
                    {
                        MAX2 = MAX1;
                        MAX1 = Integer.parseInt(i);
                    }
                    else
                    {
                        MAX2 = Integer.parseInt(i);
                    }
                }
                
            }
            
            byte[] result = (requestId + ";" + MAX2 + "," + MIN2).getBytes();
            packet = new DatagramPacket(result, result.length, serverAddress, Port);
            socket.send(packet);
            
            socket.close();
            
        }
        catch(Exception e)
        {
            
        }
        
    }
}
