/*
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206.
 Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream)
  để trao đổi thông tin theo thứ tự:   
  a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;FF49DC02"  
  b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách nhau bởi ký tự ","  Ex: 1,3,9,19,33,20  
  c. Thực hiện tìm giá trị khoảng cách nhỏ nhất của các phần tử nằm trong chuỗi và hai giá trị lớn nhất tạo nên khoảng cách đó. 
  Gửi lên server chuỗi gồm "khoảng cách nhỏ nhất, số thứ nhất, số thứ hai". 
  Ex: 1,19,20  
  d. Đóng kết nối và kết thúc
*/

import java.net.Socket;
import java.io.*;
import java.util.*;

public class TCP_BYTE_RAW_1{ 
        public static int port = 2206;
        public static String host = "36.50.135.242";
        public static String question = "Ga7fVX5N";
        public static String student = "B23DCCN924";
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(host, port);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        String request = student +";" + question;
        out.write(request.getBytes());
        out.flush();

        byte[] buffer = new byte[4096];
        int len = in.read(buffer);
        
        String respone = new String(buffer, 0, len);
        String[] numbers = respone.split(",");

        List<Integer> list = new ArrayList<>();
        for(String number : numbers){
            list.add((Integer.parseInt(number)));
        }
        Collections.sort(list);

        int num1 = 0, num2 = 0, dis = 10000;
        for(int i=1; i<list.size(); i++){
            if(list.get(i) - list.get(i-1) <= dis){
                dis = list.get(i) - list.get(i-1);
                num2 = list.get(i);
                num1 = list.get(i-1);
            }
        }
        String result = dis +"," + num1 +"," + num2;
        out.write(result.getBytes());
        out.flush();

        in.close();
        out.close();
        socket.close();
    }
}
