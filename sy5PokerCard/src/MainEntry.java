import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class MainEntry {
    public static void main(String[] args) {
        new PokerMain();
    }
}

class PokerMain extends MouseAdapter implements KeyListener {
    public int[] cardState;
    public Dimension[] cardPos;
    public JLabel[] cardObj;
    public JFrame pwin;
    public int selectedCardId = 0;
    public ArrayList<Integer> cardOrder;

    public PokerMain() {
        pwin = new JFrame("Poker Card Demo by Cy");
        pwin.setLayout(null);
        pwin.setVisible(true);
        pwin.setFocusable(true);
        pwin.addMouseListener(this);
        pwin.addKeyListener(this);
        pwin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initCardSet();
        spawnCard(false);

        ActionListener btnListener = e -> {
            String eText = ((JButton) e.getSource()).getText();
            switch (eText) {
                case "Shuffle" -> shuffleCardSet();
                case "Reset" -> resetCardSet();

            }
        };

        JButton btn1 = new JButton("Shuffle");
        btn1.setBounds(20, 20, 80, 30);
        btn1.addActionListener(btnListener);
        pwin.add(btn1);
        JButton btn2 = new JButton("Reset");
        btn2.setBounds(120, 20, 80, 30);
        btn2.addActionListener(btnListener);
        pwin.add(btn2);
    }

    void initCardSet() {
        cardOrder = new ArrayList<>(54);
        cardState = new int[54];
        cardPos = new Dimension[54];
        int initX = 10, initY = 100;
        for (int i = 0; i < 54; i++) {
            cardOrder.add(i + 1);
            cardPos[i] = new Dimension(initX += 20, initY += 0);
        }
    }

    void spawnCard(boolean needRemove) {
        if (needRemove) for (int i = 0; i < 54; i++) {
            pwin.remove(cardObj[i]);
        }
        cardObj = new JLabel[54];
        for (int i = 53; i >= 0; i--) {
            cardObj[i] = new JLabel(new ImageIcon("./asset2/x100/" + cardOrder.get(i) + ".jpg"));
            int height = ((int) cardPos[i].getHeight()) - (cardState[i] == 1 ? 30 : 0);
            cardObj[i].setBounds((int) cardPos[i].getWidth(), height, 100, 145);
            pwin.add(cardObj[i]);
        }
        pwin.pack();
        pwin.setSize(1250, 400);
    }

    void spawnCard(int i) {
        int height = ((int) cardPos[i].getHeight()) - (cardState[i] == 1 ? 30 : 0);
        cardObj[i].setBounds((int) cardPos[i].getWidth(), height, 100, 145);
    }

    void resetCardSet() {
        cardState = new int[54];
        cardOrder.clear();
        for (int i = 0; i < 54; i++) {
            cardOrder.add(i + 1);
        }
        spawnCard(true);
    }

    void shuffleCardSet() {
        cardState = new int[54];

        Collections.shuffle(cardOrder);
        spawnCard(true);
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        System.out.printf("Mouse Clicked: (%d, %d)\t\t", x, y);
        if (x > cardPos[0].width && x < cardPos[53].width + 110 && y > cardPos[0].height && y < cardPos[0].height + 180) {
//            System.out.println("This works!");
            int pos = (x - cardPos[0].width) / 20;
            System.out.printf("Calculated position: %d\n", pos);
            if (pos > 53) pos = 53;
            cardState[pos] = (cardState[pos] == 1) ? 0 : 1;
            spawnCard(pos);
        } else System.out.print("Out of range, dropped.\n");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int shouldRefresh = 1;
        System.out.printf("Key pressed: %c-%d\t\t", e.getKeyChar(), e.getKeyCode());
        switch (e.getKeyChar()) {
            case 'w' -> cardState[selectedCardId] = 1;
            case 'a' -> selectedCardId--;
            case 's' -> cardState[selectedCardId] = 0;
            case 'd' -> selectedCardId++;
            case 'r' -> resetCardSet();
            case 'q' -> shuffleCardSet();
            default -> shouldRefresh = 0;
        }
        if (selectedCardId > 53) {
            selectedCardId = 53;
            System.err.println("selectedCardId out of bound!");
        } else if (selectedCardId < 0) {
            selectedCardId = 0;
            System.err.println("selectedCardId out of bound!");
        }
        if (shouldRefresh == 1) spawnCard(selectedCardId);
        System.out.printf("selectedCardId is %d, state is %d.\n", selectedCardId, cardState[selectedCardId]);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
