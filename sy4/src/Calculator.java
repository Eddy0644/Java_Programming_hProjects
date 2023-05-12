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
        Font emojiFont = new Font("Noto Color Emoji", Font.PLAIN, 13);
        spliced = new StringBuilder();
        prompt = new StringBuilder("0");
        win = new JFrame();
        win.setTitle("51522 Calc.");
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
        tf1.setText("0");
        tf1.setPreferredSize(new Dimension(200, 30));
        tf1.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        contentPane.add(tf1);
        JTextArea tf2 = new JTextArea(2, 20);
        tf2.setLineWrap(true);
        tf2.setWrapStyleWord(true);
        tf2.setFont(emojiFont);
        tf1.setPreferredSize(new Dimension(200, 60));
//        tf2.setPreferredSize(new Dimension(200, 60));

        JButton[] btnGroup = new JButton[20];
        String operationTemplate = "+-*/=";// operationTemplate.contains(eText)  Objects.equals(eText, "=")
        ActionListener btnListener = e -> {
            hitCount++;
            String eText = ((JButton) e.getSource()).getText();
            tf1.setForeground(Color.BLACK);
            if (eText.equals("<")) {
                prompt.deleteCharAt(prompt.length() - 1);
                if (prompt.toString().equals("")) prompt.append("0");
                tf1.setText(prompt.toString());
                tf2.setText("Just sent backspace.");
                if (prompt.length() > 8) tf1.setForeground(Color.RED);
            } else if (operationTemplate.contains(eText)) {
                int opNum = Integer.parseInt(prompt.toString()), newNum = 0;
                spliced.append(prompt);
                spliced.append(eText);
                prompt = new StringBuilder();
                if (lastOp.equals("") || lastOp.equals("=")) {
                    lastOp = eText;
                    lastNum = opNum;
                    tf1.setText(prompt.toString());
                    tf2.setText("Waiting for next operation number.");
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
                    tf2.setText("Done calculating, and stored last operation " + lastOp);
                }
            } else {
                prompt.append(eText);
                tf1.setText(prompt.toString());
                if (prompt.length() > 8) {
                    tf2.setText("\u26A0\uFE0FYou are running into the limit of Integer. Stop now!");
                    tf1.setForeground(Color.RED);
                } else {
                    tf2.setText("Just entered number " + eText);
                }
            }

            System.out.printf("Current:%8s, [%8d, %2s], Total: %s\n", prompt, lastNum, lastOp, spliced);
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

        contentPane.add(tf2);
        win.getContentPane().add(contentPane);
        win.pack();
        win.setSize(250, 400);

    }
}
