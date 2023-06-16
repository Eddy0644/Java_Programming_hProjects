import javax.swing.*;
import java.awt.*;

public class Dice extends JFrame {
    public JLabel imageLabel;
    public JProgressBar progressBar;
    public int diceIdx = 1, rollingLeft = 0, MAXROLLING = 12, ROLLINTERVAL = 150;

    public Dice() {
        super("Rolling Dice");
        setSize(1000, 1000);
        setVisible(true);
        setLocation(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        paintComponents();
    }

    public static void main(String[] args) {
        new Dice();
    }

    void paintComponents() {
        imageLabel = new JLabel();
        imageLabel.setBounds(135, 60, 120, 120);
        updateDiceImg(1);
        this.add(imageLabel);
        Timer timer = new Timer(ROLLINTERVAL, (x) -> {
            if (rollingLeft > 0) {
                rollingLeft--;
                diceIdx = ((int) (Math.random() * 100)) % 6 + 1;
                String progress = String.valueOf((MAXROLLING - rollingLeft) * 83);
                if (rollingLeft == 0) {
                    progressBar.setString("100%");
                    progressBar.setValue(MAXROLLING);
                }
                else {
                    if (progress.length() < 3) progress = " " + progress;
                    progressBar.setString(progress.substring(0, 2) + "." + progress.charAt(2) + "%");
                }
                progressBar.setValue(MAXROLLING - rollingLeft);
                updateDiceImg(diceIdx);
            }
        });
        timer.start();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(20, 200, 340, 150);
        topPanel.setBackground(Color.WHITE);
        this.add(topPanel);

        JButton doRollBtn = new JButton("Start Roll !");
        doRollBtn.setBounds(120, 20, 100, 40);
        doRollBtn.addActionListener((e) -> rollingLeft = MAXROLLING);
        topPanel.add(doRollBtn);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);
        progressBar.setMaximum(MAXROLLING);
        progressBar.setStringPainted(true);
        progressBar.setString("0%");
        progressBar.setBounds(20, 80, 300, 25);

        topPanel.add(progressBar);

        this.pack();
        this.setSize(400, 600);
    }

    void updateDiceImg(int diceIdx) {
        imageLabel.setIcon(new ImageIcon("./asset/a" + diceIdx + ".jpg"));
    }
}
