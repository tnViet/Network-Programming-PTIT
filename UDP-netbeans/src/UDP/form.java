package UDP;
import java.net.*;
import java.util.*;
import java.io.*;
/*
package UDP;
import java.io.*;

public class Customer implements Serializable {
    private static final long serialVersionUID = 20151107;
    public String id, code, name, dayOfBirth, userName;
    public Customer(String id, String code, String name, String dayOfBirth, String userName){}
    
}
*/

public class form {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress addr = InetAddress.getByName("36.50.135.242");
        
        byte[] stq = ";B23DCCN924;uWFvDP5n".getBytes();
        DatagramPacket stqq = new DatagramPacket(stq, stq.length, addr, 2208);
        socket.send(stqq);
        
        byte[] buffer = new byte[2048];
        DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
        socket.receive(pack);
        String respone = new String(pack.getData(), 0, pack.getLength());
        System.out.println(respone);
        String[] a = respone.split(";");
        
        Map<Character, Integer> mp = new LinkedHashMap<>();
        for(Character c : a[1].toCharArray()){
            if(mp.getOrDefault(c,0) == 0){
                mp.put(c, 1);
            } else {
                mp.put(c, mp.get(c) + 1);
            }
        }
        Character charx = 'a';
        Integer max = -1;
        for(Character c : mp.keySet()){
            if(mp.get(c) > max){
                charx = c;
                max = mp.get(c);
            }
        }
        StringBuilder ans = new StringBuilder();
        ans.append(a[0] + ";");
        ans.append(charx + ":");
        char[] ch = a[1].toCharArray();
        for(int i=0; i<ch.length; i++){
            if(ch[i] == charx)
                ans.append(i+1 + ",");
        }
        
        byte[] ansx = ans.toString().getBytes();
        DatagramPacket answer = new DatagramPacket (ansx, ansx.length, addr, 2208);
        socket.send(answer);
        socket.close();
    


/*
 public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress addr = InetAddress.getByName("36.50.135.242");
        
        byte[] qwe = ";B23DCCN924;6EFWianO".getBytes();
        DatagramPacket qw = new DatagramPacket(qwe, qwe.length, addr, 2209);
        socket.send(qw);
        
        byte[] buffer = new byte[2048];
        DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
        socket.receive(pack);
        String rq = new String(buffer, 0, 8);
        
        ObjectInputStream  ois = new ObjectInputStream(
               new ByteArrayInputStream(buffer, 8, pack.getLength() - 8)); 
        Customer cu = (Customer) ois.readObject();
        
        String[] days = cu.dayOfBirth.split("-");
        StringBuilder newday = new StringBuilder();
        newday.append(days[1] +"/" + days[0] +"/" + days[2]);
        cu.dayOfBirth = newday.toString();
        String[] words = cu.name.toLowerCase().split("\\s+");
        StringBuilder newname = new StringBuilder();
        StringBuilder newuser = new StringBuilder();
        newname.append(words[words.length-1].toUpperCase() + ",");
        for(int i=0; i<words.length; i++){
            if(i < words.length - 1){
                char[] tmp = words[i].toCharArray();
                newuser.append(tmp[0]);
                tmp[0] = Character.toUpperCase(tmp[0]);
                newname.append(" " + String.valueOf(tmp)); 
            }
        }
        newuser.append(words[words.length-1]);
        cu.name = newname.toString();
        cu.userName = newuser.toString();
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(rq.getBytes());
        ObjectOutputStream ous = new ObjectOutputStream(bos);
        ous.writeObject(cu);
        ous.flush();
        
        byte[] zxc = bos.toByteArray();
        DatagramPacket ans = new DatagramPacket(zxc, zxc.length, addr, 2209);
        socket.send(ans);
        socket.close();
    }
*/

/*
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
*/

    }
}