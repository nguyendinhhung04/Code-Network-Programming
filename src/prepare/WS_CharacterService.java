/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package prepare;

import java.util.ArrayList;
import java.util.List;
import vn.medianews.CharacterService;
import vn.medianews.CharacterService_Service;

/**
 *
 * @author dinhh
 */
public class WS_CharacterService {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            CharacterService_Service service = new CharacterService_Service();
            CharacterService port = service.getCharacterServicePort();
            
            List<String> list = port.requestStringArray("B22DCDT147", "8AcYBFbf");
            
            List<String> result = new ArrayList<>();
            port.submitCharacterStringArray("B22DCDT147", "8AcYBFbf", result);
            
        } catch (Exception e) {
        }
    }
}
