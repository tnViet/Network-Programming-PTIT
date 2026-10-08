package TCP;
/*
Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
a) Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: “lenovo thinkpad T520” bị chuyển thành “T520 thinkpad lenovo”
b) Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) để gửi/nhận và sửa các thông tin bị sai của sản phẩm. Chi tiết dưới đây:
a) Đối tượng trao đổi là thể hiện của lớp Laptop được mô tả như sau
Tên đầy đủ của lớp: TCP.Laptop
Các thuộc tính: id int, code String, name String, quantity int
Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
Trường dữ liệu: private static finallong serialVersionUID = 20150711L;
b) Tương tác với server theo kịch bản
1) Gửi đối tượng là chuỗi chứa mã sinh viên và mã câu hỏi với định dạng studentCode;qCode.
Ví dụ: B15DCCN999;5AD2B818
2) Nhận một đối tượng là thể hiện của lớp Laptop từ server
3) Sửa các thông tin sai của sản phẩm về tên và số lượng.
Gửi đối tượng vừa được sửa sai lên server
4) Đóng socket và kết thúc chương trình.
*/

import java.net.*;
import java.io.*;


public class TCP_OBJECT_1 {
    public static void main(String[] args) throws Exception {
        Socket sk = new Socket("36.50.135.242", 2209);
        ObjectInputStream in = new ObjectInputStream(sk.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(sk.getOutputStream());

        out.writeObject("B23DCCN924;yKPvrGO7");
        out.flush();

        Laptop respone = (Laptop) in.readObject();
        String[] words = respone.getName().split(" ");
        String tmp = words[0];
        words[0] = words[words.length - 1];
        words[words.length - 1] = tmp;
        String newName = String.join(" ",words);

        String quantity = "" + respone.getQuantity();
        StringBuilder temp = new StringBuilder(quantity).reverse();
        String temp2 = temp.toString();
        Integer newQuantity = Integer.parseInt(temp2);

        respone.setName(newName);
        respone.setQuantity(newQuantity);

        out.writeObject(respone);
        out.flush();

        in.close();
        out.close();
        sk.close();
    }
}
