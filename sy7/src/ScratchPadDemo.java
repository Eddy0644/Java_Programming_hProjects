import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ScratchPadDemo {
    public static void main(String[] args) {
        JFrame win = new JFrame("ScratchPadDemo");
        win.setSize(700, 700);
        win.add(new ScratchPad());
        win.setVisible(true);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ThUpdater th1 = new ThUpdater();
        th1.start();
    }
}

class ScratchPad extends Canvas {
    public static Color color = Color.CYAN;
    public static float strokeWidth = 1.0f;
    public static boolean willDispose = false, shouldClear = false;
    private Graphics gr;
    private int OldPointX = 0, OldPointY;

    public ScratchPad() {
        MouseMotionListener a = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (OldPointX == 0 || willDispose) {
                    willDispose = false;
                    OldPointX = e.getX();
                    OldPointY = e.getY();
                    gr = getGraphics();
                } else {
                    gr.setColor(color);
                    Graphics2D g2d = (Graphics2D) gr;
                    g2d.setStroke(new BasicStroke(strokeWidth));
                    g2d.drawLine(OldPointX, OldPointY, e.getX(), e.getY());
                    OldPointX = e.getX();
                    OldPointY = e.getY();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        };
        MouseAdapter b = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                gr.setColor(new Color(238, 238, 238));
                gr.drawRect(0, 0, 700, 700);
                if (e.getButton() == MouseEvent.BUTTON3) repaint();
            }

            public void mouseReleased(MouseEvent e) {
                willDispose = true;
            }
        };
        this.addMouseMotionListener(a);
        this.addMouseListener(b);
    }

    @Override
    public void paint(Graphics g) {
        if (shouldClear) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            shouldClear = false;
        } else g.fillRect(50, 50, 4, 4);
    }
}

class ThUpdater extends Thread {
    public void run() {
        System.out.print("Thread started, will update color.\n");
        for (int i = 1; i < 100; i++) {
            try {
                sleep(500 + 437 + 2);
                ScratchPad.color = (new Color[]{Color.CYAN, Color.GRAY, Color.RED, Color.PINK, Color.GREEN})[i % 5];
                ScratchPad.strokeWidth = (float) (i % 7 + 3);
                System.out.println("Updated Color: " + ScratchPad.color);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}