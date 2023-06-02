import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class fileEditorDemo {
    public static void main(String[] args) {
        new fileEditor();
    }
}

class fileEditor {
    private final JFrame win;
    private final JTextArea[] area = new JTextArea[2];

    public fileEditor() {
        win = new JFrame("fileEditorDemo");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setLayout(new FlowLayout());
        win.setSize(800, 500);
        win.setVisible(true);

        ActionListener acL = e -> {
            String op = ((JButton) e.getSource()).getText();
            switch (op) {
                case "Read" -> open();
                case "Sort" -> sort();
                case "Save" -> save();
            }
        };

        JScrollPane[] pane = new JScrollPane[2];
        for (int i = 0; i < 2; i++) {
            area[i] = new JTextArea(32, 20);
            area[i].setLineWrap(true);
            pane[i] = new JScrollPane();
            pane[i].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            pane[i].setViewportView(area[i]);
        }

        JButton[] btns = new JButton[3];
        int a = 0;
        for (String str : new String[]{"Read", "Sort", "Save"}) {
            JButton tmp = new JButton(str);
            tmp.addActionListener(acL);
            btns[a++] = tmp;
        }
        win.add(btns[0]);
        win.add(pane[0]);
        win.add(btns[1]);
        win.add(pane[1]);
        win.add(btns[2]);
        win.pack();
        win.setSize(800, 666);


    }

    private void open() {
        try {
            FileInputStream fis = new FileInputStream("file.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
            String s;
            StringBuilder raw = new StringBuilder();
            while ((s = reader.readLine()) != null) {
                System.out.println("read:" + s);
                raw.append(s);
            }
            isr.close();
            area[0].setText(raw.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void sort() {
        String str = area[0].getText();
        str = str.replaceAll("DBa", "DBa\n");
        area[1].setText(str);
    }

    private void save() {
        FileWriter fw = null;
        try {
            fw = new FileWriter("result.txt");
            String str = area[1].getText();
            fw.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fw != null) try {
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}