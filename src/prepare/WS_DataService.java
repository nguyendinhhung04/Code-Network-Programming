/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package prepare;

import java.util.ArrayList;
import java.util.List;
import vn.medianews.DataService;
import vn.medianews.DataService_Service;

/**
 *
 * @author dinhh
 */
public class WS_DataService {

    public static void main(String[] args) {
        try{
            DataService_Service service = new DataService_Service();
            DataService p = service.getDataServicePort();
            
            String studentCode = "B22DCDT147";
            String qCode = "wvhk9BFf";
            
            List<Integer> list = p.getData(studentCode, qCode);
            System.out.println(list);
            List<String> data = new ArrayList<>();
            
        
            p.submitDataStringArray(studentCode, qCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
