/*
Mật mã caesar, còn gọi là mật mã dịch chuyển, để giải mã thì mỗi ký tự nhận được sẽ được thay thế bằng một ký tự cách nó một đoạn s.
Ví dụ: với s = 3 thì ký tự A sẽ được thay thế bằng ký tự D
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng chương trình client tương tác với server trên, sử dụng các luồng byte (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode.
Ví dụ: B15DCCN999;D68C93F7
b. Nhận lần lượt chuỗi đã bị mã hóa caesar và giá trị dịch chuyển s nguyên
c. Thực hiện giải mã ra thông điệp ban đầu và gửi lên Server
d. Đóng kết nối và kết thúc chương trình.
*/

import java.net.*;
import java.io.*;
public class TCP_DATA_2 {
public static void main(String[] args) throws Exception {
    Socket sk = new Socket("36.50.135.242", 2207);
    DataInputStream in = new DataInputStream(sk.getInputStream());
    DataOutputStream out = new DataOutputStream(sk.getOutputStream());

    out.writeUTF("B23DCCN924;jnfD6Xk4");
    out.flush();
    
    String respone = in.readUTF();
    int s = in.readInt();

    char[] a = respone.toCharArray();
    StringBuilder result = new StringBuilder(); 
    for(char c : a){
        char tmp;
        if(c <= 'z' && c >= 'a'){
            tmp = (char)((c - 'a' - s + 26) % 26 + 'a');
        } else if(c <= 'Z' && c >= 'A'){
            tmp = (char)((c - 'A' - s + 26) % 26 + 'A');
        } else {
            tmp = c;
        }
        result.append(tmp);
    }
    out.writeUTF(result.toString());
    in.close();
    out.close();
    sk.close();

}
}
