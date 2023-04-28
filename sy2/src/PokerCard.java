import java.util.Date;
import java.util.Vector;

public class PokerCard {
    public static boolean verbose = false;
    private Vector<String> cardSet;

    public static void main(String[] args) {
        PokerCard c = new PokerCard();
        c.initCardSet();
        if (verbose) printCardSet(c,"initial");
        c.shuffle();
        printCardSet(c,"after Shuffle");
        Vector<Vector<String>> users;
        try {
            users = c.dispatchCard();
            organize(users.get(2));
            printCardSet(users,"after organize");
        } catch (Exception e) {
            System.out.println("Some error occurred: " + e);
        }
    }

    public static void printCardSet(PokerCard c, String msg) {
        System.out.printf("\nCardSet [Whole] at %s {%s}\n", new Date(), msg);
//        System.out.println("\nCardSet at " + new Date());
        for (int i = 1; i <= c.cardSet.size(); i++) {
            if (i % 9 == 1) System.out.printf("%2d-%2d: ", i, i + 8);
            System.out.printf("[%4s] %s", c.cardSet.elementAt(i - 1).replaceAll("0","10").replaceAll("X",""), (i % 9 == 0) ? "\n" : " ");
        }
    }

    public static void printCardSet(Vector<Vector<String>> users, String msg) {
        for (int i = 0; i < users.size(); i++) {
            System.out.printf("\nCardSet [User%d]  {%s}\n", i + 1, new Date(), msg);
            for (int j = 1; j <= users.get(i).size(); j++) {
                if (j % 9 == 1) System.out.printf("%2d-%2d: ", j, j + 8);
                System.out.printf("[%4s] %s", users.get(i).elementAt(j - 1).replaceAll("0","10").replaceAll("X",""), (j % 9 == 0) ? "\n" : " ");
            }
        }
    }

    public static void organize(Vector<String> user) {
        String value = "234567890JQKAX";
        for (int i = 0; i < user.size(); i++) {
            for (int j = i + 1; j < user.size(); j++) {
                if (value.indexOf(user.elementAt(i).charAt(2)) > value.indexOf(user.elementAt(j).charAt(2))) {
                    String tempObj = user.get(i);
                    user.set(i, user.get(j));
                    user.set(j, tempObj);
                }
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
            //use set() and get() to avoid errors.
            String tempObj = cardSet.elementAt(IndexA);
            cardSet.set(IndexA, cardSet.get(IndexB));
            cardSet.set(IndexB, tempObj);
        }
    }

    public Vector<Vector<String>> dispatchCard() throws Exception {
        if (cardSet.size() != 54) {
            throw new Exception("Wrong cardSet size");
        }
        Vector<Vector<String>> ans = new Vector<>();
        ans.addElement(new Vector<>());
        for (int i = 0, u = 0; u < 3; ) {
            ans.elementAt(u).addElement(cardSet.elementAt(u * 18 + i));
            if (i++ == 17) {
                i = 0;
                u++;
                ans.addElement(new Vector<>());
            }
        }
        ans.removeElementAt(3);
        return ans;
    }
}
