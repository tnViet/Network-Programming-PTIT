/*
Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. 
Yêu cầu là xây dựng một chương trình client tương tác với server kịch bản dưới đây:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ;studentCode;qCode.
Ví dụ: ;B15DCCN001;EE29C059
b. Nhận thông điệp từ server theo định dạng requestId; data
requestId là một chuỗi ngẫu nhiên duy nhất
data là chuỗi dữ liệu đầu vào cần xử lý
Ví dụ: requestId;Qnc8d5x78aldSGWWmaAAjyg3
c. Tìm kiếm ký tự xuất hiện nhiều nhất trong chuỗi và gửi lên server theo định dạng “requestId;ký tự xuất hiện nhiều nhất: các vị trí xuất hiện ký tự đó”
Ví dụ: requestId;8:4,9,
d. Đóng socket và kết thúc chương trình
 */
package udp;
import java.net.*;
import java.util.*;

public class UDP_STRING_1 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress addr = InetAddress.getByName("36.50.135.242");
        
        byte[] stq = ";B23DCCN924;uWFvDP5n".getBytes();
        DatagramPacket stqq = new DatagramPacket(stq, stq.length, addr, 2208);
        socket.send(stqq);
        
        byte[] buffer = new byte[2048];
        DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
        socket.receive(pack);
        String respone = new String(pack.getData(), 0, pack.getLength());
        System.out.println(respone);
        String[] a = respone.split(";");
        
        Map<Character, Integer> mp = new LinkedHashMap<>();
        for(Character c : a[1].toCharArray()){
            if(mp.getOrDefault(c,0) == 0){
                mp.put(c, 1);
            } else {
                mp.put(c, mp.get(c) + 1);
            }
        }
        Character charx = 'a';
        Integer max = -1;
        for(Character c : mp.keySet()){
            if(mp.get(c) > max){
                charx = c;
                max = mp.get(c);
            }
        }
        StringBuilder ans = new StringBuilder();
        ans.append(a[0] + ";");
        ans.append(charx + ":");
        char[] ch = a[1].toCharArray();
        for(int i=0; i<ch.length; i++){
            if(ch[i] == charx)
                ans.append(i+1 + ",");
        }
        
        byte[] ansx = ans.toString().getBytes();
        DatagramPacket answer = new DatagramPacket (ansx, ansx.length, addr, 2208);
        socket.send(answer);
        socket.close();
    }
}
