
package udp;
import java.io.*;
import java.net.*;

public class base_form {
    public static void main(String[] args) throws Exception {

        // ================= PHẦN CHUNG =================
        String host = "localhost";          // IP server
        int port = 2207;                    // cổng server
        String studentCode = "B15DCCN001";
        String qCode = "73457A17";

        DatagramSocket socket = new DatagramSocket();
        InetAddress addr = InetAddress.getByName(host);

        // a. Gửi chuỗi yêu cầu ;studentCode;qCode
        String message = ";" + studentCode + ";" + qCode;
        byte[] sendData = message.getBytes();
        socket.send(new DatagramPacket(sendData, sendData.length, addr, port));

        // Nhận gói tin về (dùng chung cho cả 3 dạng)
        byte[] buf = new byte[8192];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);


        // ================= DẠNG 1: UDP_STRING =================
        // Bắt đầu khối STRING
        String received = new String(packet.getData(), 0, packet.getLength()).trim();
        System.out.println("Nhan duoc: " + received);

        // c. Xử lý (tự viết)
        String result = "";

        // Gửi lại bằng chuỗi
        byte[] strData = result.getBytes();
        socket.send(new DatagramPacket(strData, strData.length, addr, port));
        // Kết thúc khối STRING

        
        // ================= DẠNG 3: UDP_OBJECT =================
        // Bắt đầu khối OBJECT
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(packet.getData(), 0, packet.getLength()));
        Student st = (Student) ois.readObject();   // ép kiểu về class của đề

        // c. Xử lý (tự viết)
        st.setName(st.getName().toUpperCase());

        // Gửi lại bằng ObjectOutputStream
        ByteArrayOutputStream bosObj = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bosObj);
        oos.writeObject(st);
        oos.flush();
        byte[] objBytes = bosObj.toByteArray();
        socket.send(new DatagramPacket(objBytes, objBytes.length, addr, port));
        // Kết thúc khối OBJECT


        // ================= DẠNG 2: UDP_DATA =================
        // Bắt đầu khối DATA
        DataInputStream dis = new DataInputStream(
                new ByteArrayInputStream(packet.getData(), 0, packet.getLength()));
        String requestId = dis.readUTF();   // đọc đúng thứ tự server đã ghi
        int n = dis.readInt();
        double x = dis.readDouble();

        // c. Xử lý (tự viết)
        int ketQua = n * 2;

        // Gửi lại bằng DataOutputStream
        ByteArrayOutputStream bosData = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bosData);
        dos.writeUTF(requestId);
        dos.writeInt(ketQua);
        dos.flush();
        byte[] dataBytes = bosData.toByteArray();
        socket.send(new DatagramPacket(dataBytes, dataBytes.length, addr, port));
        // Kết thúc khối DATA
    }
}

// Class đối tượng cho dạng OBJECT (xóa nếu không dùng, hoặc dùng class của đề)
class Student implements Serializable {
    private static final long serialVersionUID = 20151107L;   // theo đề
    private String id;
    private String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

}
