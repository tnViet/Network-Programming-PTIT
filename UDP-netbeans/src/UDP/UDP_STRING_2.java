/*
Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. 
Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ;studentCode;qCode.
Ví dụ: ;B15DCCN001;5B35BCC1
b. Nhận thông điệp từ server theo định dạng requestId;data
requestId là một chuỗi ngẫu nhiên duy nhất
data là chuỗi dữ liệu cần xử lý
c. Xử lý chuẩn hóa chuỗi đã nhận thành theo nguyên tắc
Ký tự đầu tiên của từng từ trong chuỗi là in hoa
Các ký tự còn lại của chuỗi là in thường Gửi thông điệp chứa chuỗi đã được chuẩn hóa lên server theo định dạng requestId;data
d. Đóng socket và kết thúc chương trình
 */
package udp;
import java.net.*;
import java.util.*;

public class UDP_STRING_2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress addr = InetAddress.getByName("36.50.135.242");
        
        byte[] st = ";B23DCCN924;sEEIUB59".getBytes();
        DatagramPacket stq = new DatagramPacket(st, st.length, addr, 2208);
        socket.send(stq);
        
        byte[] buffer = new byte[2048];
        DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
        socket.receive(pack);
        String respone = new String(pack.getData(), 0, pack.getLength());
        System.out.println(respone);
        String[] a = respone.split("[;\\s]+");
        StringBuilder ans = new StringBuilder();
        ans.append(a[0] + ";");
        
        for(int i=1; i<a.length; i++){
            char[] tmp = a[i].toLowerCase().toCharArray();
            tmp[0] = Character.toUpperCase(tmp[0]);
            String str = String.valueOf(tmp);
            ans.append(str +" ");
        }
            
        byte[] answer = ans.toString().getBytes();
        DatagramPacket last = new DatagramPacket(answer, answer.length, addr, 2208);
        socket.send(last);
        socket.close();
    }
}
