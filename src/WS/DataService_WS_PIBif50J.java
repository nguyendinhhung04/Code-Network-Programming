/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WS;

/**
 *
 * @author dinhh
 */
import java.net.URL;
import java.util.*;
import javax.sound.sampled.Port;
import vn.medianews.*;

public class DataService_WS_PIBif50J {

    public static void main(String[] args) {
        try {
            DataService_Service service = new DataService_Service();
            DataService p = service.getDataServicePort();
            
            String studentCode = "B22DCDT147";
            String qCode = "wvhk9BFf";
            
            List<Integer> list = p.getData(studentCode, qCode);
            System.out.println(list);
            List<String> data = new ArrayList<>();
            for(Integer i:list)
            {
                String binary = Integer.toBinaryString(i);
                data.add(binary);
            }
            p.submitDataStringArray(studentCode, qCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
