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
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));

        JTextField tf1 = new JTextField();
        tf1.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                // Handle change events
//                System.out.println(e.toString());
            }

            public void insertUpdate(DocumentEvent e) {
                // Handle insert events
                System.out.println(e.toString());
            }

            public void removeUpdate(DocumentEvent e) {
                // Handle remove events
//                System.out.println(e.toString());
            }
        });
        tf1.setBounds(50, 50, 200, 30);
        tf1.setPreferredSize(new Dimension(200, 30));
        contentPane.add(tf1);


        JButton[] btnGroup = new JButton[20];

        ActionListener btnListener = e -> {
            hitCount++;
            request.append(((JButton) e.getSource()).getText());
            tf1.setText(request.toString());
            System.out.println(request);
        };
        JButton btn1 = new JButton("+");
        btn1.setBounds(20, 20, 100, 60);
        btn1.addActionListener(btnListener);


        String template = "+-.*/=1234567890";
        for (int i = 0; i < template.length(); i++) {
            btnGroup[i] = new JButton(String.valueOf(template.charAt(i)));
            btnGroup[i].addActionListener(btnListener);
            btnGroup[i].setPreferredSize(new Dimension(63, 30));
            contentPane.add(btnGroup[i]);
        }

        win.getContentPane().add(contentPane);
        win.pack();
        win.setSize(250, 300);


    }
}
