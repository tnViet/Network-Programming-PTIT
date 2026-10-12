/*
Thông tin khách hàng được yêu cầu thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
a. Tên khách hàng cần được chuẩn hóa theo định dạng mới.
Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
b. Ngày sinh của khách hàng đang ở dạng mm-dd-yyyy, 
cần được chuyển thành định dạng dd/mm/yyyy.
Ví dụ: 10-11-2012 -> 11/10/2012
c. Tài khoản khách hàng được tạo từ các chữ cái in thường được sinh tự động từ họ tên khách hàng.
Ví dụ: nguyen van hai duong -> nvhduong
Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client giao tiếp với server theo mô tả sau:
a. Đối tượng trao đổi là thể hiện của lớp UDP.Customer được mô tả như sau
Tên đầy đủ của lớp: UDP.Customer
Các thuộc tính: id String, code String, name String, , dayOfBirth String, userName String
Một Hàm khởi tạo với đầy đủ các thuộc tính được liệt kê ở trên
Trường dữ liệu: private static final long serialVersionUID = 20151107;
b. Client giao tiếp với server theo các bước
Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ;studentCode;qCode.
Ví dụ: ;B15DCCN001;EE29C059
Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Customer từ server. Trong đó, các thuộc tính id, code, name,dayOfBirth đã được thiết lập sẵn.
Yêu cầu thay đổi thông tin các thuộc tính như yêu cầu ở trên và gửi lại đối tượng khách hàng đã được sửa đổi lên server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Customer đã được sửa đổi.
Đóng socket và kết thúc chương trình.
 */
package UDP;

import java.net.*;
import java.io.*;

public class UDP_OBJECT_2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress addr = InetAddress.getByName("36.50.135.242");
        
        byte[] qwe = ";B23DCCN924;6EFWianO".getBytes();
        DatagramPacket qw = new DatagramPacket(qwe, qwe.length, addr, 2209);
        socket.send(qw);
        
        byte[] buffer = new byte[2048];
        DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
        socket.receive(pack);
        String rq = new String(buffer, 0, 8);
        
        ObjectInputStream  ois = new ObjectInputStream(
               new ByteArrayInputStream(buffer, 8, pack.getLength() - 8)); 
        Customer cu = (Customer) ois.readObject();
        
        String[] days = cu.dayOfBirth.split("-");
        StringBuilder newday = new StringBuilder();
        newday.append(days[1] +"/" + days[0] +"/" + days[2]);
        cu.dayOfBirth = newday.toString();
        String[] words = cu.name.toLowerCase().split("\\s+");
        StringBuilder newname = new StringBuilder();
        StringBuilder newuser = new StringBuilder();
        newname.append(words[words.length-1].toUpperCase() + ",");
        for(int i=0; i<words.length; i++){
            if(i < words.length - 1){
                char[] tmp = words[i].toCharArray();
                newuser.append(tmp[0]);
                tmp[0] = Character.toUpperCase(tmp[0]);
                newname.append(" " + String.valueOf(tmp)); 
            }
        }
        newuser.append(words[words.length-1]);
        cu.name = newname.toString();
        cu.userName = newuser.toString();
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(rq.getBytes());
        ObjectOutputStream ous = new ObjectOutputStream(bos);
        ous.writeObject(cu);
        ous.flush();
        
        byte[] zxc = bos.toByteArray();
        DatagramPacket ans = new DatagramPacket(zxc, zxc.length, addr, 2209);
        socket.send(ans);
        socket.close();
    }
}
