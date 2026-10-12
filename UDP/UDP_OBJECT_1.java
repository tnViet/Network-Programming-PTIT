/*
Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. 
Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau: Đối tượng trao đổi là thể hiện của lớp UDP.Student được mô tả:
Tên đầy đủ lớp: UDP.Student
Các thuộc tính: id String,code String, name String, email String
02 Hàm khởi tạo:
public Student(String id, String code, String name, String email)
public Student(String code)
Trường dữ liệu: private static final long serialVersionUID = 20171107
Thực hiện:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ;studentCode;qCode.
Ví dụ: ;B15DCCN001;EE29C059
b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Student từ server. Trong đó, các thông tin được thiết lập gồm id và name.
c. Yêu cầu:
Chuẩn hóa tên theo quy tắc: Chữ cái đầu tiên in hoa, các chữ cái còn lại in thường và gán lại thuộc tính name của đối tượng
Tạo email ptit.edu.vn từ tên người dùng bằng cách lấy tên và các chữ cái bắt đầu của họ và tên đệm.
Ví dụ: nguyen van tuan nam -> namnvt@ptit.edu.vn. Gán giá trị này cho thuộc tính email của đối tượng nhận được
Gửi thông điệp chứa đối tượng xử lý ở bước c lên Server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Student đã được sửa đổi.
d. Đóng socket và kết thúc chương trình.
*/
package UDP;

import java.net.*;
import java.io.*;

public class UDP_OBJECT_1 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress addr = InetAddress.getByName("36.50.135.242");
        
        byte[] stx = ";B23DCCN924;BpblH1G1".getBytes();
        DatagramPacket stq = new DatagramPacket(stx, stx.length, addr, 2209);
        socket.send(stq);
        
        byte[] buffer = new byte[2048];
        DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
        socket.receive(pack);
        
        String rq = new String(buffer, 0, 8);
        ObjectInputStream os = new ObjectInputStream(
                new ByteArrayInputStream(
                        buffer, 8, pack.getLength() - 8));
        Student s = (Student) os.readObject();
        
        String[] words = s.getName().toLowerCase().split("\\s+");
        StringBuilder newname = new StringBuilder();
        StringBuilder newemail = new StringBuilder();
        newemail.append(words[words.length-1].toLowerCase());
        for(int i = 0 ; i < words.length; i++){
            char[] oldname = words[i].toCharArray();
            if(i < words.length - 1){
                newemail.append(oldname[0]);
            } 
            oldname[0] = Character.toUpperCase(oldname[0]);
            String tmp = String.valueOf(oldname);
            newname.append(tmp);
            if(i != words.length - 1) newname.append(" ");
        }
        newemail.append("@ptit.edu.vn");
        String name = newname.toString();
        String email = newemail.toString();
        s.setName(name);
        s.setEmail(email);
        
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        bo.write(rq.getBytes());
        ObjectOutputStream oos = new ObjectOutputStream(bo);
        oos.writeObject(s);
        oos.flush();
        
        byte[] tmp = bo.toByteArray();
        DatagramPacket ans = new DatagramPacket(tmp, tmp.length, addr, 2209);
        socket.send(ans);
        
        socket.close();
    }
}
