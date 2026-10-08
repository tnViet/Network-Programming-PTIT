/*
Một chương trình máy chủ cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5s), yêu cầu xây dựng chương trình (tạm gọi là client) thực hiện kết nối tới server tại cổng 2207, sử dụng luồng byte dữ liệu (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode.
Ví dụ: B15DCCN999;1D25ED92
b. Nhận lần lượt hai số nguyên a và b từ server
c. Thực hiện tính toán tổng, tích và gửi lần lượt từng giá trị theo đúng thứ tự trên lên server
d. Đóng kết nối và kết thúc
*/
import java.net.*;
import java.io.*;

public class TCP_DATA_1 {
public static void main(String[] args) throws Exception {
    Socket sk = new Socket("36.50.135.242", 2207);
    DataInputStream in = new DataInputStream(sk.getInputStream());
    DataOutputStream out = new DataOutputStream(sk.getOutputStream());
    
    out.writeUTF("B23DCCN924;1Zrmq2Cb");
    out.flush();

    int a = in.readInt();
    int b = in.readInt();
    int sum = a + b;
    int mul = a * b;

    out.writeInt(sum);
    out.writeInt(mul);
    out.flush();

    in.close();
    out.close();
    sk.close();

}
}