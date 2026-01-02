/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;

/**
 *
 * @author dinhh
 */
public class TCP_ObjectStream_laptop {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try(Socket socket = new Socket()) {
            
            socket.connect( new InetSocketAddress("203.162.10.109", 2209) );
            
            ObjectInputStream in = new ObjectInputStream( socket.getInputStream() );
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            
            String initString = "B22DCDT147;TQXPG0Az";
            out.writeObject(initString);
            
            Laptop laptop = (Laptop) in.readObject();
            System.out.println(laptop.toString());
            
            String[] list =laptop.name.split("\\s+") ;
            String temp = list[list.length-1];
            list[list.length-1] = list[0];
            list[0] = temp;
            
            String nameResult = "";
            
            for(int i=0; i<list.length-1;i++)
            {
               nameResult +=  list[i] + " ";
            }
            nameResult+=list[list.length-1];
            
            laptop.name = nameResult;
            Integer soLuong = laptop.quantity;
            StringBuilder slString = new StringBuilder(soLuong.toString());
            laptop.quantity = Integer.parseInt(slString.reverse().toString());
            
            System.out.println(laptop.toString());
            out.writeObject(laptop);
            out.flush();    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
