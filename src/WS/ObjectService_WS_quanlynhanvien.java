/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package WS;

import java.util.Date;
import java.util.List;
import vn.medianews.EmployeeY;
import vn.medianews.ObjectService;
import vn.medianews.ObjectService_Service;

/**
 *
 * @author dinhh
 */



public class ObjectService_WS_quanlynhanvien {
        
    public static void main(String[] args) {
        try {
            
            ObjectService_Service service = new ObjectService_Service();
            ObjectService port = service.getObjectServicePort(); 
            
            String studentCode = "B22DCDT147";
            String qCode = "395U9a30";
            
            List<EmployeeY> list = port.requestListEmployeeY(studentCode, qCode);
            System.out.println(list);
            
            list.sort( (s1,s2) -> s1.getStartDate().compare(s2.getStartDate()) );
            
            port.submitListEmployeeY(studentCode, qCode, list);
            System.out.println(list);
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
}
