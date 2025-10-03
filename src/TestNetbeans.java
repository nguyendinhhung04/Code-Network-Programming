
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
public class TestNetbeans {
    public static void main(String[] args) {

        String maSV = "B22DCDT147";
        String questionCode = "MF4JniOv";
        int port = 2207;

        try(Socket client = new Socket("203.162.10.109", port))
        {
            client.setSoTimeout(5000);
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();

            DataInputStream dataIn = new DataInputStream(in);
            DataOutputStream dataOut = new DataOutputStream(out);


            dataOut.writeUTF( (maSV + ";" + questionCode));
            dataOut.flush();
            System.out.println("Connect with:" + maSV + ";" + questionCode);

            int a = dataIn.readInt();
            int b = dataIn.readInt();
            System.out.println("Received:" + a + "," + b);

            dataOut.writeInt(a+b);
            dataOut.flush();
            System.out.println(a+b);
            dataOut.writeInt(a*b);
            dataOut.flush();
            System.out.println(a*b);


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
