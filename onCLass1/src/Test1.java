import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Test1 {
    public static JFrame win,win2;
    static JButton btn1;
    static int hitCount=0;
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

        win2=new MyWin2();


        ActionListener acL=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitCount++;
//                System.out.print("Hit.\n"+((JButton)e.getSource()).getText());
                btn1.setText("Hit me! x"+hitCount);
                btn1.setBounds(20,20,100,60);
            }
        };
        btn1=new JButton("Hit me!");
//        btn1.setFont(new Font());
        btn1.setBounds(20,20,100,60);
        btn1.addActionListener(acL);

        win2.add(btn1);
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

            int savedHitCount=Test1.hitCount;
            try {
                sleep(1000);
                if(savedHitCount==Test1.hitCount) {
                    Test1.hitCount = 0;
                    Test1.btn1.setText("Hit me!");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
class MyWin2 extends JFrame{
    public MyWin2(){
        super("Window Title is here");
        setSize(300,100);
        setVisible(true);
        setLocation(300,250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}