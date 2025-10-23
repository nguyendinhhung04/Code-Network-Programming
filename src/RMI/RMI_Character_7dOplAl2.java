/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package RMI;

/**
 *
 * @author dinhh
 */

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.LinkedHashMap;


interface CharacterService extends Remote {
    public String requestCharacter(String studentCode, String qCode) throws RemoteException;
    public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
}

public class RMI_Character_7dOplAl2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // TODO code application logic here
        try
        {
            // Kết nối đến registry server tại localhost (hoặc IP của server)
            String host = "203.162.10.109";
            int port = 1099; // cổng mặc định của RMI Registry
            Registry registry = LocateRegistry.getRegistry(host, port);
            
            // Lấy đối tượng từ xa
            CharacterService service = (CharacterService) registry.lookup("RMICharacterService");
            
            // Thông tin sinh viên và mã câu hỏi
            String studentCode = "B22DCDT147";
            String qCode = "7dOplAl2";
            
            // (a) Gọi requestData để nhận dữ liệu nhị phân
            String data = service.requestCharacter(studentCode, qCode);
            System.out.println( data );
            HashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
            
            for( int i=0;i<data.length(); i++ )
            {
                String temp = "" + data.charAt(i);
                if ( ! map.containsKey(temp))
                {
                    map.put(temp, 1);
                }
                else
                {
                    map.put(temp, map.get(temp) +1 );
                }
            }
            String result = "{";
            boolean first = true;
            for(String i : map.keySet())
            {
                if(!first)
                {
                    result += ", ";
                }
                else
                {
                    first = false;
                }
                result += "\"" + i + "\": " + map.get(i).toString();
            }
            result += "}";
            System.out.println(result);
            service.submitCharacter(studentCode, qCode, result);
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
