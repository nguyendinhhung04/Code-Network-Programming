/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package WS;

import java.util.ArrayList;
import java.util.List;
import vn.medianews.*;

/**
 *
 * @author dinhh
 */
public class DataService_WS_phantichSNT {

    /**
     * @param args the command line arguments
     */
    
    static ArrayList<Integer> ptSNT(Integer a)
    {
        ArrayList<Integer> list = new ArrayList();
        if(a%2==0)
        {
            while(a%2==0)
            {
                a/= 2;
                list.add(2);
            }
        }
        for( int i=3; i<= Math.sqrt(a);i+=2 )
        {
            if(a%i==0)
            {
                list.add(i);
                while(a%i==0)
                {
                    a/=i;
                }
            }
        }
        if(a!=1)
        {
            list.add(a);
        }
        return list;
    }
    
    public static void main(String args[]) {
        
        System.out.println(ptSNT(26));
    }
}
