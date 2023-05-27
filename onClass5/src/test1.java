import javax.swing.*;
import java.awt.*;

public class test1 {
    public static void main(String[] args) {
        JFrame win = new JFrame("MouseDemo");
//        win.setLayout(null);
        win.setSize(400, 400);
//        win.add(new Panel0());
        win.add(new Canvas0());
        win.setVisible(true);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Canvas0 extends Canvas {
    private final Font[] fonts ={
            new Font("宋体",Font.BOLD,17)
    };
    @Override
    public void paint(Graphics g) {
        g.fillOval(0, 0, 50, 30);
        g.drawRect(100, 100, 100, 50);
        g.drawLine(200, 200, 300, 300);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(8.0f));
        g2d.drawLine(9, 99, 199, 299);
        g2d.drawString("安微太学AHU 211 AHU 211安微太学",300,300);
        g2d.setFont(fonts[0]);
        g2d.drawString("安微太学AHU 211 AHU 211安微太学",400,400);
    }
}

class Panel0 extends JPanel {
    @Override
    public void paint(Graphics g) {
        g.fillOval(0, 0, 50, 30);
        g.drawRect(100, 100, 100, 50);
        g.drawLine(200, 200, 300, 300);
    }
}