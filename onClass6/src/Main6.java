import java.io.*;

public class Main6 {
    public static void main(String[] args) {
        try{
            FileInputStream fis=new FileInputStream("f1.txt");
            InputStreamReader isr=new InputStreamReader(fis);
            BufferedReader reader=new BufferedReader(isr);
            String s;
            while((s= reader.readLine())!= null){
                System.out.println("read:"+s);
            }
            isr.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
class T2 implements Serializable{
    public T2(){

    }
}