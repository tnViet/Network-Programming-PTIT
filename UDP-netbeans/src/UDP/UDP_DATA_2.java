/*
Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. 
Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ;studentCode;qCode.
Ví dụ: ;B15DCCN001;DC73CA2E
b. Nhận thông điệp là một chuỗi từ server theo định dạng requestId;a1,a2,...,a50
requestId là chuỗi ngẫu nhiên duy nhất
a1 -> a50 là 50 số nguyên ngẫu nhiên
c. Thực hiện tìm giá trị lớn nhất và giá trị nhỏ nhất thông điệp trong a1 -> a50 và gửi thông điệp lên lên server theo định dạng requestId;max,min
d. Đóng socket và kết thúc chương trình
*/

package udp;
import java.net.*;

public class UDP_DATA_2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress addr = InetAddress.getByName("36.50.135.242");
        
        byte[] st = ";B23DCCN924;6zSAysTo".getBytes();
        DatagramPacket stq =  new DatagramPacket(st, st.length, addr, 2207);
        socket.send(stq);
        
        byte[] buffer = new byte[2048];
        DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
        socket.receive(pack);
        
        String respone = new String(pack.getData(), 0, pack.getLength());
        System.out.println(respone);
        String[] a = respone.split(",|;");
        StringBuilder ans = new StringBuilder();
        int max = -1000000, min = 1000000;
        
        for(int i=1; i< a.length; i++){
            int x = Integer.parseInt(a[i]);
            if(x >= max) max = x;
            if(x <= min) min = x;
        }
        ans.append(a[0] + ";" + max + "," + min);
        
        byte[] tmp = ans.toString().getBytes();
        DatagramPacket answer = new DatagramPacket(tmp, tmp.length , addr, 2207);
        socket.send(answer);
        
        socket.close();
    }
}

