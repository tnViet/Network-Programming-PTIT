/*

*/
// import java.util.*;
// import java.util.zip.GZIPInputStream;
// import java.util.zip.GZIPOutputStream;
// import java.io.*;
// import java.net.*;


// public class TCP_GZIP_1{
//     public static void main(String[] args) throws Exception {
//         Socket sk = new Socket("36.50.135.242", 2210);
//         GZIPOutputStream gout = new GZIPOutputStream(sk.getOutputStream(), true);
//         BufferedWriter out = new BufferedWriter(new OutputStreamWriter(gout));
        
//         out.write("B23DCCN924;a3CEAJLk");
//         out.newLine();
//         out.flush();

//         GZIPInputStream gin = new GZIPInputStream(sk.getInputStream());
//         BufferedReader in = new BufferedReader(new InputStreamReader(gin));

//         String respone = in.readLine();

//         String reversed = new StringBuilder(respone).reverse().toString();
//         String encoded = Base64.getEncoder().encodeToString(reversed.getBytes());
//         String result = reversed + "|" + encoded;
//         out.write(result);
//         out.newLine();
//         out.flush();

//         in.close();
//         out.close();
//         sk.close();
        
//     }
// }



import java.util.*;
import java.util.zip.GZIPOutputStream;
import java.io.*;
import java.net.*;

public class TCP_GZIP_1 {
     public static void main(String[] args) throws Exception {
        Socket sk = new Socket("36.50.135.242", 2210);
        GZIPOutputStream gout = new GZIPOutputStream(sk.getOutputStream(), true);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(gout));

        out.write("B23DCCN924;a3CEAJLk");
        out.newLine();
        out.flush();

        InputStream raw = sk.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n;
        while ((n = raw.read(buf)) != -1) {
            baos.write(buf, 0, n);
        }
        byte[] allData = baos.toByteArray();
        System.out.println("So byte nhan duoc: " + allData.length);
        for (byte b : allData) System.out.printf("%02X ", b);
        System.out.println();

        sk.close();
    }
}