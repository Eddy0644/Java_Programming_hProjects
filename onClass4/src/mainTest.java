import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Vector;

public class mainTest {
    public static void main(String[] args) {
        new menuWin();
    }
}

class menuWin {
    private final JFrame win;

    public menuWin() {
        win = new JFrame("MouseDemo");
        win.setLayout(null);
        win.setSize(400, 400);
        win.setVisible(true);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar mb = new JMenuBar();
        Vector<JMenu> mbs = new Vector<>();
        ActionListener defAL = e -> {
            JMenuItem source = (JMenuItem) e.getSource();
            System.out.println(source.getText());
        };
        for (String str : new String[]{"File", "Edit", "Help"}) {
            mbs.add(new JMenu(str));
            mb.add(mbs.lastElement());
        }
        for (int i = 0; i < 2; i++) {
            for (String str : new String[][]{
                    {"Open...", "Preferences", "Exit..."},
                    {"Cut", "Copy", "Paste"},
                    {"Help...", "Register", "About"}
            }[i]) {
                JMenuItem tmp = new JMenuItem(str);
                tmp.addActionListener(defAL);
                mbs.get(i).add(tmp);
            }
        }
        win.setJMenuBar(mb);
        win.pack();
        win.setSize(400, 400);
    }
}