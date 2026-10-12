/*
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader) theo kịch bản sau:
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng studentCode;qCode.
Ví dụ: B15DCCN999;BAA62945
b. Nhận một chuỗi ngẫu nhiên từ server
Ví dụ: dgUOo ch2k22ldsOo
c. Liệt kê các ký tự (là chữ hoặc số) xuất hiện nhiều hơn một lần trong chuỗi và số lần xuất hiện của chúng và gửi lên server
Ví dụ: d:2,O:2,o:2,2:3,
d. Đóng kết nối và kết thúc chương trình.
=============
Note : kí tự trả về không bao gồm white space

*/

import java.net.*;
import java.io.*;
import java.util.*;

public class TCP_CHARACTER_2 {
        public static String host = "36.50.135.242";
        public static int port = 2208;
        public static String question = "wBJUJabC";
        public static String student = "B23DCCN924";
    public static void main(String[] args) throws Exception {

    
        Socket socket = new Socket(host, port);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader((in)));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));

        String request = student + ";" + question +"\n";
        bw.write(request);
        bw.flush();

        String tmp = br.readLine();
        char[] respone = tmp.toCharArray();
        Map <Character, Integer> m = new LinkedHashMap<>();
        for(Character c : respone){
            if(c.equals(' ')) continue;
            if(m.getOrDefault(c, 0) == 0){
                m.put(c, 1);
            } else {
                int temp = m.get(c) + 1;
                m.put(c, temp);
            }
        }
        for(Character c : m.keySet()){
            if(m.get(c) > 1){
                bw.write(c);
                bw.write(":");
                bw.write(m.get(c).toString());
                bw.write(",");
            }
        }

        bw.write("\n");
        bw.flush();

        br.close();
        bw.close();
        socket.close();
 
    }
}
