import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;

public class ScratchPadDemo {
    public static void main(String[] args) {
        JFrame win = new JFrame("MouseDemo");
        win.setSize(700, 700);
        win.add(new ScratchPad());
        win.setVisible(true);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class ScratchPad extends Canvas {
    private Graphics gr;
    private int OldPointX = 0, OldPointY;

    public ScratchPad() {
        MouseMotionListener a = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (OldPointX == 0) {
                    OldPointX = e.getX();
                    OldPointY = e.getY();
                    gr = getGraphics();
                } else {
                    gr.drawLine(OldPointX,OldPointY,e.getX(),e.getY());
                    OldPointX = e.getX();
                    OldPointY = e.getY();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
//                gr.fillRect(e.getX(), e.getY(), 4, 4);
            }
        };
        this.addMouseMotionListener(a);
    }

    @Override
    public void paint(Graphics g) {
        gr = g;
        g.fillRect(50, 50, 4, 4);
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

            try {
                sleep(1000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}