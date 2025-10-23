/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;

/**
 *
 * @author dinhh
 */

interface CharacterService extends Remote {
    public String requestCharacter(String studentCode, String qCode) throws RemoteException;
    public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
}


public class RMI_Character_ZwVnRj6P {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            String qCode = "ZwVnRj6P";
            
            // (a) Gọi requestData để nhận dữ liệu nhị phân
            String data = service.requestCharacter(studentCode, qCode);
            System.out.println(data);
            LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
            for (int i = 0; i<data.length(); i++)
            {
                String temp = "" + data.charAt(i);
                if (map.containsKey(temp))
                {
                    map.put(temp, map.get(temp) + 1);
                   
                }
                else
                {
                    map.put(temp, 1);
                }
            }
            
            String result = "";
            for(String i : map.keySet())
            {
                result += i + map.get(i).toString();
            }
            service.submitCharacter(studentCode, qCode, result);
            System.out.println(result);
            
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
