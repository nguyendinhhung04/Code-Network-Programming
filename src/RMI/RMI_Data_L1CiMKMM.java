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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

interface DataService extends Remote {
    public Object requestData(String studentCode, String qCode) throws RemoteException;
    public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
}


public class RMI_Data_L1CiMKMM {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try
        {
            // (a) Kết nối đến RMI registry
            String host = "203.162.10.109"; // IP server
            int port = 1099;
            Registry registry = LocateRegistry.getRegistry(host, port);

            DataService service = (DataService) registry.lookup("RMIDataService");

            String studentCode = "B22DCDT147";
            String qCode = "L1CiMKMM";

            // Nhận dữ liệu chuỗi từ server, ví dụ: "4, 2, 4, 5, 5"
            Object obj = service.requestData(studentCode, qCode);
            if (!(obj instanceof String)) {
                System.out.println("Dữ liệu nhận không phải là chuỗi!");
                return;
            }

            String dataStr = ((String) obj).trim();
            System.out.println(dataStr);
            String[] parts = dataStr.split(";");
            String[] strArr = parts[0].trim().split(",");
            int k = Integer.parseInt(parts[1].trim());
            ArrayList<Integer> arr = new ArrayList<Integer>();
            
            for (String i : strArr)
            {
                arr.add( Integer.parseInt(i.trim() ) );
            }
            System.out.println(arr.toString());
            Collections.sort(arr, Collections.reverseOrder());
            System.out.println(arr.toString());
            service.submitData(studentCode, qCode, arr.get(k-1));
            System.out.println(arr.get(k-1).toString());
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
