import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class Calculator {
    public static JFrame win, win2;
    public static int hitCount = 0;
    public static StringBuilder request;

    public static void main(String[] args) {
        request = new StringBuilder();
        win = new JFrame();
        win.setTitle("51522");
        win.setSize(600, 300);
        win.setResizable(false);
        win.setVisible(true);
        win.setLocation(200, 200);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ActionListener btnListener = e -> {
            hitCount++;
            request.append(((JButton) e.getSource()).getText());
//                System.out.print("Hit.\n"+((JButton)e.getSource()).getText());
//                btn1.setText("Hit me! x"+hitCount);
//                btn1.setBounds(20,20,100,60);
            System.out.println(request);
        };
        JButton btn1 = new JButton("+");
        btn1.setBounds(20, 20, 100, 60);
        btn1.addActionListener(btnListener);

        JPanel contentPane = new JPanel();

        JButton[] btnGroup = new JButton[20];
//        contentPane.setLayout(new BorderLayout());
//        contentPane.setLayout(new GridLayout());
        contentPane.setLayout(new FlowLayout());
        String template = ".+-*/=";
        for (int i = 0; i < 10; i++) {
            btnGroup[i] = new JButton(String.valueOf(i));
            btnGroup[i].addActionListener(btnListener);
            contentPane.add(btnGroup[i]);
        }
        for (int i = 10; i < 10 + template.length(); i++) {
            btnGroup[i] = new JButton(String.valueOf(template.charAt(i - 10)));
            btnGroup[i].addActionListener(btnListener);
            contentPane.add(btnGroup[i]);
        }
//        win.setContentPane(contentPane);
        JTextField tf1 = new JTextField();
        tf1.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                // Handle change events
                System.out.println(e.toString());
            }

            public void insertUpdate(DocumentEvent e) {
                // Handle insert events
                System.out.println(e.toString());
            }

            public void removeUpdate(DocumentEvent e) {
                // Handle remove events
                System.out.println(e.toString());
            }
        });

        tf1.setBounds(50, 50, 200, 30);
        tf1.setPreferredSize(new Dimension(200, 30));
        contentPane.add(tf1);

        win.getContentPane().add(contentPane);
        win.pack();

//        win.add(btn1);


    }
}
