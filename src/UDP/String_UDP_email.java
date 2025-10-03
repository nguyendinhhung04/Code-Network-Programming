;

public class String_UDP_email {
    
    // Hàm chuẩn hóa tên: mỗi từ chữ cái đầu in hoa, còn lại in thường
    public static String normalizeName(String name) {
        if (name == null || name.isEmpty()) return name;
        String[] words = name.trim().toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String w : words) {
            result.append(Character.toUpperCase(w.charAt(0)))
                  .append(w.substring(1)).append(" ");
        }
        return result.toString().trim();
    }

    // Hàm tạo email: lastname + chữ cái đầu của các từ còn lại
    public static String generateEmail(String name) {
        String[] words = name.trim().toLowerCase().split("\\s+");
        if (words.length == 0) return "";
        String lastName = words[words.length - 1]; // tên
        StringBuilder initials = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) {
            initials.append(words[i].charAt(0));
        }
        return lastName + initials.toString() + "@ptit.edu.vn";
    }

    public static void main(String[] args) {
        String studentCode = "B15DCCN001"; // sửa thành MSSV của bạn
        String qCode = "EE29C059";
        String serverHost = "localhost"; // hoặc IP server
        int serverPort = 2209;

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(serverHost);

            // a. Gửi chuỗi ";studentCode;qCode"
            String message = ";" + studentCode + ";" + qCode;
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);
            System.out.println("Đã gửi: " + message);

            // b. Nhận thông điệp: 8 byte requestId + object Student
            byte[] receiveBuffer = new byte[4096];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            byte[] data = receivePacket.getData();
            int length = receivePacket.getLength();

            // Lấy requestId (8 byte đầu)
            String requestId = new String(data, 0, 8, "UTF-8");

            // Deserialize Student
            ByteArrayInputStream bais = new ByteArrayInputStream(data, 8, length - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Student student = (Student) ois.readObject();
            ois.close();

            System.out.println("Nhận Student từ server: " + student);

            // c. Chuẩn hóa name và tạo email
            String normalizedName = normalizeName(student.getName());
            student.setName(normalizedName);
            student.setEmail(generateEmail(normalizedName));

            System.out.println("Sau xử lý: " + student);

            // Serialize lại object và gửi lên server
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(requestId.getBytes("UTF-8"));
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(student);
            oos.flush();

            byte[] sendBack = baos.toByteArray();
            DatagramPacket responsePacket = new DatagramPacket(sendBack, sendBack.length, serverAddress, serverPort);
            socket.send(responsePacket);
            System.out.println("Đã gửi lại đối tượng Student sau khi xử lý.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
