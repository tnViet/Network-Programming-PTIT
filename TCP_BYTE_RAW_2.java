/*
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2206 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng các luồng byte (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode.
Ví dụ: B16DCCN999;2B3A6510
b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách nhau bởi ký tự ,.
Ví dụ: 1,3,9,19,33,20
c. Tìm và gửi lên server giá trị lớn thứ hai cùng vị trí xuất hiện của nó trong chuỗi.Ví dụ: 20,5
d. Đóng kết nối và kết thúc chương trình.
*/
import java.net.*;
import java.util.*;
import java.io.*;

public class TCP_BYTE_RAW_2 {
    public static void main(String[] args) throws Exception {
        Socket sk = new Socket("36.50.135.242", 2206);
        InputStream in = sk.getInputStream();
        OutputStream out = sk.getOutputStream();

        out.write("B23DCCN924;owou3Wie\n".getBytes());
        out.flush();

        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        
        String respone = new String(buffer, 0 , len);
        String[] nums = respone.split(",");
        List<Integer> arr = new ArrayList<>();

        for(String c : nums){
            arr.add(Integer.parseInt(c.trim()));
        }
        int max = arr.get(0), ans = -1;
        for(int a : arr){
            if(a > max){
                ans = max;
                max = a;
            }
            if(a < max && a > ans)
                ans = a;
        }
        
        for(int i= 0; i<arr.size(); i++){
            if(arr.get(i) == ans){
                String result = ans + "," + i;
                out.write(result.getBytes());
                break;
            }
        }
        
        sk.close();
        in.close();
        out.close();
    }
}
