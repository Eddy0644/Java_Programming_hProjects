import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
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
//        win.setLayout(new GridLayout());
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
        for (int i = 0; i < 3; i++) {
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

        Border etched = BorderFactory.createEtchedBorder();

        JPanel loginPanel = new JPanel();
        loginPanel.setBorder(BorderFactory.createTitledBorder(etched, "Login"));

        // Create the labels, text fields, and password field
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        JButton loginButton = new JButton("Login");
        loginPanel.add(loginButton);

        JPanel MathPanel = new JPanel();
        MathPanel.setBorder(BorderFactory.createTitledBorder(etched, "Math"));
        MathPanel.setVisible(false);
        MathPanel.setLayout(new GridLayout(2, 1));
        JPanel[] mathQA = new JPanel[3];
        ButtonGroup[] btnGrp = new ButtonGroup[5];
        for (int i = 0; i < 3; i++) {
            mathQA[i] = new JPanel();
            btnGrp[i] = new ButtonGroup();
            mathQA[i].setBorder(BorderFactory.createTitledBorder(etched, "ProblemSet " + i));
            MathPanel.add(mathQA[i]);
        }
        mathQA[0].add(new JLabel("Exclusive RadioBtn Example"));
        mathQA[1].add(new JLabel("Exclusive CheckBox Example"));
        mathQA[2].add(new JLabel("Non-Exclusive CheckBox Example"));
        for (String str : new String[]{"A. 114514", "B. 1919810", "C. AHU is 211"}) {
            JRadioButton tmp = new JRadioButton(str);
            btnGrp[0].add(tmp);
            mathQA[0].add(tmp);
        }

        for (String str : new String[]{"A. 114514", "B. 1919810", "C. AHU is 211"}) {
            JCheckBox tmp = new JCheckBox(str);
            btnGrp[1].add(tmp);
            mathQA[1].add(tmp);
        }
        for (String str : new String[]{"A. 114514", "B. 1919810", "C. AHU is 211"}) {
            JCheckBox tmp = new JCheckBox(str);
//            btnGrp[2].add(tmp);
            mathQA[2].add(tmp);
        }

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            loginPanel.setVisible(false);
            MathPanel.setVisible(true);
        });


        loginPanel.setBounds(0, 0, 300, 400);
        MathPanel.setBounds(0, 0, 500, 400);
        win.add(loginPanel);
        win.add(MathPanel);
        win.pack();
        win.setSize(500, 400);
        win.setLocation(300, 300);
    }
}