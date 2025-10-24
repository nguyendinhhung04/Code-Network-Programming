/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package prepare;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author dinhh
 */
public class TCP_Character {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try(Socket socket = new Socket())
        {
          String serverHost = "203.162.10.109"; // hoặc IP server
            int serverPort = 2208;
            String studentCode = "B22DCDT147"; // đổi theo mã sinh viên của bạn
            String qCode = "QZkzEjhP"; 
            
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // --- a. Gửi mã sinh viên + qCode ---
            String request = studentCode + ";" + qCode;
            writer.write(request);
            writer.newLine(); // quan trọng để server nhận đúng
            writer.flush();
            System.out.println("Sent: " + request);
            
            // --- b. Nhận dữ liệu từ server ---
            String response = reader.readLine(); // ví dụ: danh sách tên miền
            System.out.println("Received: " + response);
            
                
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
