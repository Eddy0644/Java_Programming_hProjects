import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainEntry {
    public static void main(String[] args) {
//        PokerWin pwin = new PokerWin();
        new PokerMain();
    }
}


class PokerMain extends MouseAdapter implements KeyListener {
    public int[] cardOrder, cardState;
    public Dimension[] cardPos;
    public JLabel[] cardObj;
    public JFrame pwin;
    public int selectedCardId = 0;

    public PokerMain() {
        pwin = new JFrame("Poker Card Demo by Cy");
//        MultiHandler handler = new MultiHandler();
        pwin.setLayout(null);
        pwin.setVisible(true);
        pwin.setFocusable(true);
        pwin.addMouseListener(this);
        pwin.addKeyListener(this);
        pwin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initCardSet();
        cardState[20] = 1;
        spawnCard(false);
    }

    void initCardSet() {
        cardOrder = new int[54];
        cardState = new int[54];
        cardPos = new Dimension[54];
        int initX = 10, initY = 100;
        for (int i = 0; i < 54; i++) {
            cardOrder[i] = i + 1;
            cardPos[i] = new Dimension(initX += 20, initY += 0);
//            cardPos[i].
        }
    }

    void spawnCard(boolean isUpdate) {
        cardObj = new JLabel[54];
        for (int i = 53; i > 0; i--) {
//            cardObj[i] = new JLabel(new ImageIcon("./asset/puke" + cardOrder[i] + ".jpg"));
            if (!isUpdate) cardObj[i] = new JLabel(new ImageIcon("./asset2/x100/" + cardOrder[i] + ".jpg"));
            int height = ((int) cardPos[i].getHeight()) - (cardState[i] == 1 ? 30 : 0);
            cardObj[i].setBounds((int) cardPos[i].getWidth(), height, 100, 145);
            if (!isUpdate) pwin.add(cardObj[i]);
        }
        if (!isUpdate) {
            pwin.pack();
            pwin.setSize(1250, 450);
        }
    }

    void spawnCard(int i) {
        int height = ((int) cardPos[i].getHeight()) - (cardState[i] == 1 ? 30 : 0);
        cardObj[i].setBounds((int) cardPos[i].getWidth(), height, 100, 145);
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        if (x > cardPos[0].width && x < cardPos[53].width + 30 && y > cardPos[0].height && y < cardPos[0].height + 180) {
            System.out.println("This works!");
            int pos = (x - cardPos[0].width) / 20;
            cardState[pos] = (cardState[pos] == 1) ? 0 : 1;
            spawnCard(pos);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int shouldRefresh = 1;
        System.out.printf("%c-%d\n", e.getKeyChar(), e.getKeyCode());
        switch (e.getKeyChar()) {
            case 'w' -> cardState[selectedCardId] = 1;
            case 'a' -> selectedCardId--;
            case 's' -> cardState[selectedCardId] = 0;
            case 'd' -> selectedCardId++;
            case 'r' -> spawnCard(selectedCardId);
            default -> shouldRefresh = 0;
        }
        if (shouldRefresh == 1) spawnCard(selectedCardId);
        System.out.printf("%d-%d\n", selectedCardId, cardState[selectedCardId]);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
