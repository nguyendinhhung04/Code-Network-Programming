/*
Tìm chuỗi con tăng dần dài nhất và 
gửi độ dài của chuỗi đó lên server theo format "chuỗi tăng dài nhất; độ dài". 
Ví dụ: 5,10,20,25,50;5
*/

package TCP;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

/**
 *
 * @author dinhh
 */
public class TCP_Byte_1aoZQquc {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String studentCode = "B22DCDT147";
        String qCode = "1aoZQquc";
        int port = 2206;
        
        try (Socket socket = new Socket("203.162.10.109", port)) {
            socket.setSoTimeout(5000);

            // --- Khởi tạo luồng ---
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            // --- a. Gửi mã sinh viên và qCode ---
            out.write((studentCode + ";" + qCode + "\n").getBytes());
            out.flush();

            // --- b. Nhận dữ liệu từ server ---
            byte[] data = new byte[8246];
            int length = in.read(data);
            String received = new String(data, 0, length).trim(); // loại bỏ whitespace
            System.out.println("Received: " + received);

            // Chuyển sang ArrayList<Integer>
            String[] t = received.split(",");
            ArrayList<Integer> arr = new ArrayList<>();
            for (String i : t) {
                arr.add(Integer.parseInt(i.trim()));
            }

            // --- c. Tìm chuỗi con tăng dài nhất ---
            int max = 1;
            int count = 1;
            String temp = arr.get(0).toString();
            String maxString = temp;

            for (int i = 0; i < arr.size() - 1; i++) {
                if (arr.get(i) < arr.get(i + 1)) {
                    count++;
                    temp += "," + arr.get(i + 1);
                    if (count > max) {
                        max = count;
                        maxString = temp;
                    }
                } else {
                    count = 1;
                    temp = arr.get(i + 1).toString();
                }
            }

            // --- Gửi kết quả về server ---
            String result = maxString + ";" + max;
            out.write((result + "\n").getBytes());
            out.flush();
            System.out.println("Sent: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }    
    }
}
