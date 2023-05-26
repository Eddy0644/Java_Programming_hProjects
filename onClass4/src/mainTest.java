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
    private final CardLayout card;

    public menuWin() {
        win = new JFrame("MouseDemo");
//        win.setLayout(new GridLayout());
        win.setSize(400, 400);
        win.setVisible(true);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        card = new CardLayout();
        win.setLayout(card);

        JMenuBar mb = new JMenuBar();
        Vector<JMenu> mbs = new Vector<>();
        ActionListener defAL = e -> {
            JMenuItem source = (JMenuItem) e.getSource();
            String txt = source.getText();
            System.out.println(txt);
            if (txt.equals("Math Exam")) card.show(win.getContentPane(), "math");
            if (txt.equals("CS Exam")) card.show(win.getContentPane(), "cs");
            if (txt.equals("Login")) card.show(win.getContentPane(), "login");
        };
        for (String str : new String[]{"File", "Edit", "Exam System", "Help"}) {
            mbs.add(new JMenu(str));
            mb.add(mbs.lastElement());
        }
        for (int i = 0; i < 4; i++) {
            for (String str : new String[][]{
                    {"Open...", "Preferences", "Exit..."},
                    {"Cut", "Copy", "Paste"},
                    {"Login", "Math Exam", "CS Exam"},
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
        loginPanel.setLayout(new GridLayout(3, 3));
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        JButton loginButton = new JButton("Login");
        loginPanel.add(loginButton);
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
//            JDialog diag;
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            if(username.equals("c")&&password.equals("y")) card.show(win.getContentPane(), "cs");
            else{
//                diag=new JDialog(win,"Info",true);
                JOptionPane.showMessageDialog(win,"Username or password Wrong!!!!!!","WARN",JOptionPane.WARNING_MESSAGE);
            }
        });


        JPanel MathPanel = new JPanel();
        MathPanel.setBorder(BorderFactory.createTitledBorder(etched, "Math"));
        MathPanel.setLayout(new GridLayout(3, 1));
        JPanel[] mathQA = new JPanel[3];
        ButtonGroup[] btnGrp = new ButtonGroup[5];
        for (int i = 0; i < 3; i++) {
            mathQA[i] = new JPanel();
            btnGrp[i] = new ButtonGroup();
            mathQA[i].setBorder(BorderFactory.createTitledBorder(etched, "ProblemSet " + (i + 1)));
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
            mathQA[2].add(tmp);
        }
        JPanel csPanel = new JPanel();
        csPanel.setBorder(BorderFactory.createTitledBorder(etched, "CS"));
        csPanel.setLayout(new GridLayout(3, 1));
        JPanel[] csQA = new JPanel[3];
        ButtonGroup[] btnGrp2 = new ButtonGroup[5];
        for (int i = 0; i < 3; i++) {
            csQA[i] = new JPanel();
            btnGrp2[i] = new ButtonGroup();
            csQA[i].setBorder(BorderFactory.createTitledBorder(etched, "ProblemSet " + (i + 1)));
            csPanel.add(csQA[i]);
        }
        csQA[0].add(new JLabel("Which of the following is essential for us?",JLabel.CENTER));
        for (String str : new String[]{"A. Honkai: Star Rail", "B. Genshin Impact", "C. Python", "D. None of above"}) {
            JRadioButton tmp = new JRadioButton(str);
            btnGrp2[0].add(tmp);
            csQA[0].add(tmp);
        }
        csQA[1].add(new JLabel("Which is your favorite company?"));
        for (String str : new String[]{"A. Microsoft", "B. Apple", "C. Mihoyo", "D. Tencent"}) {
            JCheckBox tmp = new JCheckBox(str);
            btnGrp2[1].add(tmp);
            csQA[1].add(tmp);
        }
        csQA[2].add(new JLabel("Which one is correct?"));
        for (String str : new String[]{"A. 114514", "B. 1919810", "C. AHU is 211"}) {
            JCheckBox tmp = new JCheckBox(str);
            csQA[2].add(tmp);
        }


        win.add(loginPanel, "login");
        win.add(MathPanel, "math");
        win.add(csPanel, "cs");
        loginPanel.setBounds(0, 0, 300, 400);
        MathPanel.setBounds(0, 0, 500, 400);
        csPanel.setBounds(0, 0, 500, 400);

        card.show(win.getContentPane(), "login");
        win.pack();
        win.setSize(500, 400);
        win.setLocation(300, 300);
    }
}