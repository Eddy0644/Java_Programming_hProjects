import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class Calculator {
    public static JFrame win, win2;
    public static int hitCount = 0, lastNum = 0;
    public static StringBuilder spliced, prompt;
    public static String lastOp = "";

    public static void main(String[] args) {
        spliced = new StringBuilder();
        prompt = new StringBuilder();
        win = new JFrame();
        win.setTitle("51522");
        win.setSize(600, 300);
        win.setResizable(true);
        win.setVisible(true);
        win.setLocation(200, 200);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER));

        JTextField tf1 = new JTextField();
        tf1.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
//                System.out.println(e.toString());
            }

            public void insertUpdate(DocumentEvent e) {
//                System.out.println(e.toString());
            }

            public void removeUpdate(DocumentEvent e) {
//                System.out.println(e.toString());
            }
        });
        tf1.setBounds(50, 50, 200, 30);
        tf1.setPreferredSize(new Dimension(200, 30));
        contentPane.add(tf1);


        JButton[] btnGroup = new JButton[20];
        String operationTemplate = "+-*/=";// operationTemplate.contains(eText)  Objects.equals(eText, "=")
        ActionListener btnListener = e -> {
            hitCount++;
            String eText = ((JButton) e.getSource()).getText();
            if (eText.equals("<")) {
                prompt.deleteCharAt(prompt.length() - 1);
                if(prompt.equals(""))prompt.append("0");
                tf1.setText(prompt.toString());
            } else if (operationTemplate.contains(eText)) {
                int opNum = Integer.parseInt(prompt.toString()), newNum = 0;
                spliced.append(prompt);
                spliced.append(eText);
                prompt = new StringBuilder();
                if (lastOp.equals("") || lastOp.equals("=")) {
                    // output original opNum
                    lastOp = eText;
                    lastNum = opNum;
                    tf1.setText(prompt.toString());
                } else {
                    switch (lastOp) {
                        case "+" -> newNum = lastNum + opNum;
                        case "-" -> newNum = lastNum - opNum;
                        case "*" -> newNum = lastNum * opNum;
                        case "/" -> newNum = lastNum / opNum;
                    }
                    lastOp = eText;
                    lastNum = newNum;
                    tf1.setText(String.valueOf(newNum));
                    prompt = new StringBuilder(String.valueOf(newNum));
                }
            } else {
                prompt.append(eText);
                tf1.setText(prompt.toString());
            }
            System.out.printf("Current:%6s, Total: %8s,[%6d, %2s]\n", prompt, spliced, lastNum, lastOp);
        };
        JButton btn1 = new JButton("+");
        btn1.setBounds(20, 20, 100, 60);
        btn1.addActionListener(btnListener);


        String template = "+-<*/=1234567890";
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
