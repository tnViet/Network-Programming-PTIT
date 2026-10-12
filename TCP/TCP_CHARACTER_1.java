/* 
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 
Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader) 
theo kịch bản sau: 
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". 
Ví dụ: "B15DCCN999;EC4F899B" 
b. Nhận một chuỗi ngẫu nhiên là danh sách các một số tên miền từ server 
Ví dụ: giHgWHwkLf0Rd0.io, I7jpjuRw13D.io, wXf6GP3KP.vn, MdpIzhxDVtTFTF.edu, TUHuMfn25chmw.vn, HHjE9.com, 4hJld2m2yiweto.vn, y2L4SQwH.vn, s2aUrZGdzS.com, 4hXfJe9giAA.edu 
c. Tìm kiếm các tên miền .edu và gửi lên server 
Ví dụ: MdpIzhxDVtTFTF.edu, 4hXfJe9giAA.edu 
d. Đóng kết nối và kết thúc chương trình.

*/
import java.net.Socket;
import java.io.*;

public class TCP_CHARACTER_1{
    public static String svHost = "36.50.135.242";
    public static int svPort = 2208;
    public static String student = "B23DCCN924";
    public static String question = "h38NKfcy";
    public static void main(String agrs[]) throws Exception {
       try{
        Socket socket = new Socket(svHost, svPort);
       
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        
        String request = student + ";" + question;
        out.write(request);
        out.newLine();
        out.flush();

        String respone = in.readLine();

        String [] domains = respone.split(",");
        String result = "";
        int first = 0;
        for(String domain : domains){
            if(domain.endsWith(".edu")){
                if(first == 0){
                    first = 1;
                } else {
                    result = result.concat(", ");
                }

                result = result.concat(domain.trim());
            }
        }
        out.write(result);
        out.newLine();
        out.flush();
    
    in.close();
    out.close();
    socket.close();
       }
       catch (IOException e){
        e.printStackTrace();
       }
    }
}

