import javax.swing.*;
import java.awt.*;

public class TextFontDemo {
    public static void main(String[] args) {
        JFrame win = new JFrame("MouseDemo");
        win.setSize(450, 450);
        TextFont tf = new TextFont();
        win.add(tf);
        win.setVisible(true);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class TextFont extends Canvas {
    public static Color color = Color.CYAN;
    public static float strokeWidth = 1.0f;
    public static boolean willDispose = false;
    private Graphics gr;
    private Font[] fonts = {
            new Font("Arial", Font.PLAIN, 17),
            new Font("Arial", Font.BOLD, 20),
            new Font("Arial", Font.ITALIC, 23),
            new Font("ScoutCond Regular", Font.PLAIN, 30),
            new Font("ScoutCond Black", Font.ITALIC, 42),
            new Font("ScoutCond Light", Font.PLAIN, 56),
    };
    public TextFont() {

    }
    @Override
    public void paint(Graphics g) {
        g.fillRect(50, 50, 4, 4);
        Graphics2D g2d = (Graphics2D) g;
        String msg = "AHU is not 6!";
        for (int i = 0; i < fonts.length; i++) {
            g2d.setFont(fonts[i]);
            g2d.drawString(msg, 40, 80 + 50 * i);
        }
    }
}