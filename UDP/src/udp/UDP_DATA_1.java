/*
Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. 
Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ;studentCode;qCode.
Ví dụ: ;B15DCCN001;73457A17
b. Nhận thông điệp là một chuỗi từ server theo định dạng requestId;n;A1,A2,...An , với
requestId là chuỗi ngẫu nhiên duy nhất
n là một số ngẫu nhiên nhỏ hơn 100.
A1, A2 ... Am (m <= n) là các giá trị ngẫu nhiên nhỏ hơn hoặc bằng n và có thể trùng nhau.
Ví dụ: requestId;10;2,3,5,6,5
c. Tìm kiếm các giá trị còn thiếu và gửi lên server theo định dạng requestId;B1,B2,...,Bm
Ví dụ: requestId;1,4,7,8,9,10
d. Đóng socket và kết thúc chương trình.
*/
package udp;
import java.net.*;

public class UDP_DATA_1 {
    public static void main(String[] args) throws Exception {
           DatagramSocket sk = new DatagramSocket();
           InetAddress sv = InetAddress.getByName("36.50.135.242");
           
           byte[] send = ";B23DCCN924;BmQKhNhf".getBytes();
           DatagramPacket sendPa = new DatagramPacket(send, send.length, sv, 2207);
           sk.send(sendPa);
           
           byte[] buffer = new byte[4096];
           DatagramPacket receivePa = new DatagramPacket(buffer, buffer.length);
           sk.receive(receivePa); 
           String respone = new String(receivePa.getData(), 0 , receivePa.getLength());
           
           String[] a = respone.trim().split(",|;");
           String qcode = a[0];
           int n = Integer.parseInt(a[1].trim());
           int first = 0;

           int[] exist = new int[n+1];
           StringBuilder ans = new StringBuilder(qcode + ";");
           
           for(int i=2; i<a.length; i++){
               int tmp = Integer.parseInt(a[i]);
               exist[tmp] = 1;
           }
           for(int i=1 ;i <= n; i++){
               if(exist[i] != 1){
                   if(first == 0){
                       first = 1;
                       ans.append("" + i);
                       continue;
                   }
                   ans.append(",");
                   ans.append("" + i);
               }
           }
           String tmp2 = ans.toString();
           byte[] tmp = tmp2.getBytes();
           DatagramPacket b = new DatagramPacket(tmp, tmp.length, sv, 2207);
           sk.send(b);
           
           sk.close();
    }
}


