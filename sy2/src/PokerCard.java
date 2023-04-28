import java.util.Date;
import java.util.Vector;

public class PokerCard {
    private Vector<String> cardSet;

    public static void main(String[] args) {
        PokerCard c = new PokerCard();
        c.initCardSet();
        printCardSet(c);
        c.shuffle();
        printCardSet(c);
        Vector<Vector<String>> users;
        try {
            users = c.dispatchCard();
            printCardSet(users);
        } catch (Exception e) {
            System.out.println("Some error occurred: " + e);
        }
    }

    public static void printCardSet(PokerCard c) {
        System.out.println("\nCardSet at " + new Date());
        for (int i = 1; i <= c.cardSet.size(); i++) {
            if (i % 5 == 1) System.out.printf("%2d-%2d: ", i, i + 4);
            System.out.printf("[%s] %s", c.cardSet.elementAt(i - 1), (i % 5 == 0) ? "\n" : "\t");
        }
    }

    public static void printCardSet(Vector<Vector<String>> users) {
        for (int i = 0; i < users.size(); i++) {
            System.out.println("\nCardSet for User " + (i + 1) + " at " + new Date());
            for (int j = 1; j <= users.get(i).size(); j++) {
                if (j % 9 == 1) System.out.printf("%2d-%2d: ", j, j + 8);
                System.out.printf("[%s] %s", users.get(i).elementAt(j - 1), (j % 9 == 0) ? "\n" : "\t");
            }
        }
    }

    public void initCardSet() {
        this.cardSet = new Vector<>();
        String[] flower = new String[]{"红桃", "黑桃", "方片", "梅花"};
        String value = "234567890JQKA";
        for (String s : flower) {
            for (int j = 0; j < value.length(); j++) {
                cardSet.addElement(s + value.charAt(j));
            }
        }
        cardSet.addElement("小王X");
        cardSet.addElement("大王X");
    }

    public void shuffle() {
        for (int i = 0; i < 150; i++) {
            int IndexA = (int) (Math.random() * 54 - 1);
            int IndexB = (int) (Math.random() * 54 - 1);
            String tempObj = cardSet.elementAt(IndexA);
            cardSet.removeElementAt(IndexA);
            cardSet.add(IndexA, cardSet.elementAt(IndexB));
            cardSet.removeElementAt(IndexB);
            cardSet.add(IndexB, tempObj);
        }
    }

    public Vector<Vector<String>> dispatchCard() throws Exception {
        if (cardSet.size() != 54) {
            throw new Exception("Wrong cardSet size");
        }
        Vector<Vector<String>> ans = new Vector<>();
        ans.addElement(new Vector<>());
        for (int i = 0, u = 0; u < 3; ) {
            ans.elementAt(u).addElement(cardSet.elementAt(i));
            if (i++ == 17) {
                i = 0;
                u++;
                ans.addElement(new Vector<>());
            }
        }
        ans.removeElementAt(3);
        return ans;
    }

    public void organize() {

    }
}
