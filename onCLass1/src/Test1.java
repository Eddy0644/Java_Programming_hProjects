import javax.swing.*;
import java.util.Random;

public class Test1 {
    public static JFrame win;
    public static void main(String[] args) {
        win=new JFrame();
        win.setTitle("51522");
        win.setSize(600,300);
        win.setResizable(false);
        win.setVisible(true);
        win.setLocation(200,200);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ThUpdater th1=new ThUpdater();
        th1.start();

        new MyWin2();
    }
}
class ThUpdater extends Thread{
    public void run(){
        StringBuilder str=new StringBuilder("[51522]");
        System.out.print("Thread started, will update title\n");
        for (int i = 0; i < 100; i++) {
//            str.append('a');
            str=new StringBuilder("[51522] ");
            int rand= new Random().nextInt(20);
            for (int j = 0; j < rand; j++) {
                str.append('_');
            }
            str.append("^^");
            for (int j = rand; j < 20; j++) {
                str.append('_');
            }
            Test1.win.setTitle(str.toString());
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
class MyWin2 extends JFrame{
    public MyWin2(){
        super("Window Title is here");
        setSize(400,100);
        setVisible(true);
        setLocation(300,250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}