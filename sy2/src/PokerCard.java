import java.util.Date;
import java.util.Vector;

public class PokerCard {
    public static boolean verbose = true;
    private Vector<String> cardSet;

    public static void main(String[] args) {
        PokerCard c = new PokerCard();
        c.initCardSet();
        if (verbose) printCardSet(c, "initial");
        c.shuffle();
        printCardSet(c, "after Shuffle");
        Vector<Vector<String>> users;
        try {
            // dispatch cardSet to three user vectors, then sum up in a Vector<Vector<String>>.
            users = c.dispatchCard();
            c.organize(users.get(0)).organize(users.get(1)).organize(users.get(2));
            printCardSet(users, "after organize");
        } catch (Exception e) {
            System.out.println("Some error occurred: " + e);
        }
    }

    //Two reloads of printCardSet to handle different arguments
    public static void printCardSet(PokerCard c, String msg) {
        System.out.printf("\nCardSet [Whole] at %s {%s}\n", new Date(), msg);
//        System.out.println("\nCardSet at " + new Date());
        for (int i = 1; i <= c.cardSet.size(); i++) {
            if (i % 9 == 1) System.out.printf("%2d-%2d: ", i, i + 8);
            System.out.printf("[%4s] %s", c.cardSet.elementAt(i - 1).replaceAll("0", "10").replaceAll("X", ""), (i % 9 == 0) ? "\n" : " ");
        }
    }

    public static void printCardSet(Vector<Vector<String>> users, String msg) {
        for (int i = 0; i < users.size(); i++) {
            System.out.printf("\nCardSet [User%d]  {%s}\n", i + 1, msg);
            for (int j = 1; j <= users.get(i).size(); j++) {
                if (j % 9 == 1) System.out.printf("%2d-%2d: ", j, j + 8);
                System.out.printf("[%4s] %s", users.get(i).elementAt(j - 1).replaceAll("0", "10").replaceAll("X", ""), (j % 9 == 0) ? "\n" : " ");
            }
        }
    }

    public void initCardSet() {
        //Generate 1-52 cards then manually add 小王 and 大王.
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
        // 150 is shuffle rounds, the more it is, more randomness the cardSet is
        for (int i = 0; i < 150; i++) {
            int IndexA = (int) (Math.random() * 54 );
            int IndexB = (int) (Math.random() * 54 );
            // use set() and get() to swap to avoid errors.
            String tempObj = cardSet.elementAt(IndexA);
            cardSet.set(IndexA, cardSet.get(IndexB));
            cardSet.set(IndexB, tempObj);
        }
    }

    public Vector<Vector<String>> dispatchCard() throws Exception {
        if (cardSet.size() != 54) {
            throw new Exception("Wrong cardSet size");
        }
        // store three user Vector<String>
        Vector<Vector<String>> ans = new Vector<>();
        ans.addElement(new Vector<>());
        for (int i = 0, u = 0; u < 3; ) {
            // mix 'move card to user' and 'switch between users' into one circulation
            ans.elementAt(u).addElement(cardSet.elementAt(u * 18 + i));
            if (i++ == 17) {
                //moved 17th card, that means 0-17 are moved so next step is switch user.
                i = 0;
                u++;
                ans.addElement(new Vector<>());
            }
        }
        // remove the suffix user
        ans.removeElementAt(3);
        return ans;
    }

    public PokerCard organize(Vector<String> user) {
        // Done a little trick on this method to enable
        // recursive call like a.b().b() and not let it be moved upward when Ctrl+Alt+L.
        // that is to remove static prop. and return the instance itself.
        String value = "234567890JQKAX";
        // Using bubble sort, using the position of 'the third letter in card String'
        // in 'value' String to make sure the smallest card appears first, and so on.
        for (int i = 0; i < user.size(); i++) {
            for (int j = i + 1; j < user.size(); j++) {
                if (value.indexOf(user.elementAt(i).charAt(2)) > value.indexOf(user.elementAt(j).charAt(2))) {
                    String tempObj = user.get(i);
                    user.set(i, user.get(j));
                    user.set(j, tempObj);
                }
            }
        }
        return this;
    }
}
