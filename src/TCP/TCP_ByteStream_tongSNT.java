package TCP;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dinhh
 */
public class TCP_ByteStream_tongSNT {
    
    public static boolean CheckPrime(Long a)
    {
        if(a<2) return false;
        
        if(a==2) return true;
      
        if (a==3) return true;
        
        if(a%2==0) return false;
     
        boolean result = true;
        for(int i = 3; i<=Math.sqrt(a); i+=2)
        {
            if (a%i==0)
            {
                result = false;
                return result;
            }
        }
        return result;
    }
    
    public static Long HandleSum(String s)
    {
        
        String[] arr = s.split("\\,");
        Long sum = 0L;
        for(String i : arr)
        {
            if(CheckPrime(Long.parseLong(i)))
            {
                sum += Long.parseLong(i);
            }
            
        }
        return sum;
        
    }
    
    public static void main(String[] args) {
        
        String studentCode = "B22DCDT147";
        String qCode = "D2cn8lTd";
        int port = 2206;

        try(Socket socket = new Socket("203.162.10.109", port);)
        {
            socket.setSoTimeout(5000);

            //init a
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            
            out.write((studentCode + ";" + qCode).getBytes());
            out.flush();
            System.out.println(studentCode + ";" + qCode);
            
            byte[] data = new byte[1024];
            int length = in.read(data);
            String received = new String(data, 0 , length);
            System.out.println(received);
            
            Long sum = HandleSum(received);
            out.write( String.valueOf(sum).getBytes() );
            out.flush();
            System.out.println(sum);
 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        

    }
}
