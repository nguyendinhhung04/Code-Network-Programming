/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package WS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import vn.medianews.*;

/**
 *
 * @author dinhh
 */
public class CharacterService_WS_soluongnguyenam {

    /**
     * @param args the command line arguments
     */
    
    static int DemSoLuongNguyenAm(String s)
    {
        int soLuong = 0;
        for( int i = 0; i<s.length();i++ )
        {
            String temp =( s.charAt(i)+"").toLowerCase();
            if( temp.equals("u")
                    || temp.equals("e")
                    || temp.equals("o")
                    || temp.equals("a")
                    || temp.equals("i"))
            {
                soLuong++;
            }
        }
        return soLuong;
    }
    
    public static void main(String args[]) {
        try
        {
            CharacterService_Service service = new CharacterService_Service();
            CharacterService port = service.getCharacterServicePort();
            
            List<String> list = port.requestStringArray("B22DCDT147", "8AcYBFbf");
//            System.out.println(list);
            
            Map<Integer, List<String>> map = new TreeMap<>();
            for(String i: list)
            {
                Integer soLuongNguyenAm = DemSoLuongNguyenAm(i);
                if( map.containsKey(soLuongNguyenAm) )
                {
                    map.get(soLuongNguyenAm).add(i);
                }
                else
                {
                    map.put(soLuongNguyenAm, new ArrayList<String>());
                    map.get(soLuongNguyenAm).add(i);
                }
//                list.remove(i);
            }
            
            List<String> result = new ArrayList<>();
            
            for( Integer i:map.keySet())
            {
                Collections.sort( map.get(i));
//                System.out.print(i.toString()+" ");
//                System.out.println(map.get(i));
                result.addAll( map.get(i) );
            }
            
//            System.out.println(result);
            port.submitCharacterStringArray("B22DCDT147", "8AcYBFbf", result);
           
            
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
