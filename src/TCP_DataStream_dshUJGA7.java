
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

/**
 *
 * @author dinhh
 */
public class TCP_DataStream_dshUJGA7 {

    /**
     * @param args the command line arguments
     */
    
    //Tach ra thanh fuction rieng cho de test
    public static String ReverseString(String s, int k)
    {
        String[] arr = s.split("\\,");
        String result = "";
        
        
        for(int i=0; i<arr.length && i<k; i++)
        {
            if (result.length()== 0)
                {
                    result =  arr[i];
                }
                else
                {
                    result =  arr[i] + "," + result;
                }
        }
        
        for(int i=k;i<arr.length; i+=k )
        {
            String temp = "";
            for(int j=i; j<i+k && j<arr.length; j++)
            {
                if (temp.length()== 0)
                {
                    temp =  arr[j];
                }
                else
                {
                    temp =  arr[j] + "," + temp;
                }
                    
            }
            result += ("," + temp);
        }
        return result;
    }
    
    public static void main(String args[]) {
        String studentCode = "B22DCDT147";
        String qCode = "dshUJGA7 ";
        int port = 2207;
        
      
        //Create socket 
        try(Socket socket = new Socket("203.162.10.109", port);)
        {
            //set timeout
            socket.setSoTimeout(5000);

            //init 
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            
            DataInputStream dataIn = new DataInputStream(in);
            DataOutputStream dataOut = new DataOutputStream(out);
            
            //Sent student and question code, if not server can not detect student sent request
            dataOut.writeUTF( (studentCode + ";" + qCode ) );
            //flush de chac chan da write leen server du bo dem chua day (cai nay e nho the =))) )
            dataOut.flush();
            System.out.println((studentCode + ";" + qCode ));
            
            //read data from server
            int k = dataIn.readInt();
            System.out.println(k);
            String str = dataIn.readUTF();
            System.out.println(str);
            
            String result = ReverseString(str, k);
            //sent data back to server
            dataOut.writeUTF(result);
            dataOut.flush();
            System.out.println(result);
            
 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
