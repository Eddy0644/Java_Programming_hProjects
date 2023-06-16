import javax.swing.*;
import java.awt.*;

public class Dice extends JFrame {
    public JLabel imageLabel, scoreLabel;
    public JProgressBar progressBar;
    public int diceIdx = 1, rollingLeft = 0, curScore = 100, betStat = 0, MAXROLLING = 12, ROLLINTERVAL = 150;

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
                updateDiceImg(diceIdx);
                if (rollingLeft == 0) {
                    progressBar.setString("Completed.");
                    progressBar.setValue(MAXROLLING);
                    // start evaluating score
                    if ((diceIdx <= 3 && betStat == 1) || (diceIdx > 3 && betStat == 2)) {
                        curScore += 10;
                        scoreLabel.setText("Well done! New score: " + curScore);
                        System.out.println("User bet correct, score updated to " + curScore);
                    } else {
                        if (betStat != 0) {
                            curScore -= 10;
                            scoreLabel.setText("Let's went on! New score: " + curScore);
                            System.out.println("User bet failed, score updated to " + curScore);
                        } else {
                            scoreLabel.setText("Not-ranked Round; Current score: " + curScore);
                            System.out.println("Rolled " + diceIdx + " but not ranked, score remains " + curScore);
                        }
                    }
                    betStat = 0;

                } else {
                    if (progress.length() < 3) progress = " " + progress;
                    progressBar.setString(progress.substring(0, 2) + "." + progress.charAt(2) + "%");
                    progressBar.setValue(MAXROLLING - rollingLeft - 1);
                }
            }
        });
        timer.start();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(20, 200, 340, 250);
        topPanel.setBackground(Color.WHITE);
        this.add(topPanel);

        JButton doRollBtn = new JButton("Start Roll !");
        doRollBtn.setBounds(120, 20, 100, 40);
        doRollBtn.addActionListener((e) -> {
            rollingLeft = MAXROLLING;
            System.out.println("User started rolling.");
            if (betStat == 0) {
                scoreLabel.setText("If you don't bet now, this result won't be ranked.");
                System.out.println("User didn't select bet initially.");
            }
        });
        topPanel.add(doRollBtn);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(false);
        progressBar.setMaximum(MAXROLLING);
        progressBar.setStringPainted(true);
        progressBar.setString("0%");
        progressBar.setBounds(20, 80, 300, 25);
        topPanel.add(progressBar);

        scoreLabel = new JLabel("Your current score: 100");
        System.out.println("Initial score: 100");
        scoreLabel.setBounds(20, 120, 300, 30);
        topPanel.add(scoreLabel);

        String[] btnTitle = new String[]{"Bet big", "Bet small"};
        Integer[][] dim = new Integer[][]{
                {59, 170, 100, 30},
                {189, 170, 100, 30},
        };
        for (int i = 0; i < 2; i++) {
            JButton tmp = new JButton(btnTitle[i]);
            tmp.setBounds(dim[i][0], dim[i][1], dim[i][2], dim[i][3]);
            tmp.addActionListener((e) -> {
                String eText = ((JButton) e.getSource()).getText();
                switch (eText) {
                    case "Bet big" -> betStat = 2;
                    case "Bet small" -> betStat = 1;
                    default -> System.out.println("Error Occurred with Btn Input!");
                }
                if (betStat != 0) {
                    scoreLabel.setText("You just bet " + (betStat == 2 ? "big." : "small."));
                    System.out.println("User just bet " + (betStat == 2 ? "big." : "small."));
                    progressBar.setString("Ready.");
                    progressBar.setValue(0);
                }
            });
            topPanel.add(tmp);
        }

        this.pack();
        this.setSize(400, 600);
    }

    void updateDiceImg(int diceIdx) {
        imageLabel.setIcon(new ImageIcon("./asset/a" + diceIdx + ".jpg"));
    }
}
