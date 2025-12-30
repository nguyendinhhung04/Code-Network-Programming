/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package TCP;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author dinhh
 */
public class TCP_ByteStream {

    /**
     * @param args the command line arguments
     */
    
    public static String Solve(String receiveString) {
        // Tách chuỗi thành mảng số
        String[] parts = receiveString.trim().split(",");
        int n = parts.length;
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(parts[i].trim());
        }

        int[] dp = new int[n];      // dp[i]: độ dài LIS kết thúc tại i
        int[] prev = new int[n];    // truy vết
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            prev[i] = -1;
        }

        int maxLen = 1;
        int lastIndex = 0;

        // Quy hoạch động LIS
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (a[j] < a[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }

        // Truy vết LIS
        int[] lis = new int[maxLen];
        int idx = maxLen - 1;
        while (lastIndex != -1) {
            lis[idx--] = a[lastIndex];
            lastIndex = prev[lastIndex];
        }

        // Ghép chuỗi kết quả
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lis.length; i++) {
            sb.append(lis[i]);
            if (i < lis.length - 1) sb.append(",");
        }
        sb.append(";").append(maxLen);

        return sb.toString();
    }
    
    public static void main(String args[]) {
        try(Socket socket = new Socket()){
            
            String serverHost = "203.162.10.109"; // hoặc IP server
            int serverPort = 2206;
            String studentCode = "B22DCDT147"; // đổi theo mã sinh viên của bạn
            String qCode = "14shBC14";
            
            socket.connect(new InetSocketAddress(serverHost, serverPort));
            socket.setSoTimeout(5000);
            
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            
            out.write( (studentCode+";"+qCode).getBytes() );
            out.flush();
            
            byte[] data = new byte[1024];
            int length = in.read(data);
            String receiveString = new String(data, 0 , length);
            System.out.println(receiveString);
            
            String result = Solve(receiveString);
            
            out.write(result.getBytes());
            out.flush();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
