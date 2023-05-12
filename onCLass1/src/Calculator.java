import javax.swing.*;
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

        JButton[] btnGroup = new JButton[10];
        contentPane.setLayout(new BorderLayout());
        for (int i = 0; i < 10; i++) {
            btnGroup[i] = new JButton(String.valueOf(i));
            btnGroup[i].addActionListener(btnListener);
            contentPane.add(btnGroup[i], BorderLayout.AFTER_LAST_LINE);
        }
//        contentPane.add(btn1, BorderLayout.CENTER);
        win.setContentPane(contentPane);
        win.pack();

//        win.add(btn1);

        JTextField tf1 = new JTextField();

    }
}
