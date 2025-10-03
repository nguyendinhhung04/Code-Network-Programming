
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

/**
 *
 * @author dinhh
 */
public class TCP_ObjectStream_wCxBX9Sw {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        String maSV = "B22DCDT147";
        String qCode = "";
        int port = 2209;
        
        try (Socket socket = new Socket("http://203.162.10.109", port)){
            
            //Init
            InputStream in  = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
    }
    
    
    
}
