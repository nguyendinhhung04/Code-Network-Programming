/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author dinhh
 */
public class TCP_CharacterStream_loaibokytu {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try(Socket socket = new Socket()) {
            
            socket.connect( new InetSocketAddress("203.162.10.109", 2208) );
            
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
            BufferedWriter out = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream()));

            out.write( ("B22DCDT147;tYdwqJIx") );
            out.newLine();
            out.flush();
            
            String receivedString = in.readLine();
            System.out.println(receivedString);
            Map<String, Boolean> map = new LinkedHashMap<>();
            
            for(int i=0; i<receivedString.length();i++)
            {
                String temp = receivedString.charAt(i) + "";
                if( (receivedString.charAt(i) >= 65 && receivedString.charAt(i) <=90) || (receivedString.charAt(i)>=97 && receivedString.charAt(i)<=122))
                {
                    if(!map.containsKey(temp))
                    {
                        map.put(temp, Boolean.TRUE);
                    }
                }
            }
            
            String result = String.join( "",map.keySet());
            System.out.println(result);
            out.write(result);
            out.flush();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
