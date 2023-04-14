import java.util.Vector;
class Solution2 {
    public static int reverseBits(int num) {
        String src = Integer.toBinaryString(num);
        Vector<Integer> record = new Vector<>();
        for (byte i = 0, p = -1; i < src.length(); i++) {
            int binary_at_i = src.charAt(i) == '1' ? 1 : 0;
            if (binary_at_i == 1) {
                if (p == -1) {
                    record.addElement(0);
                    p++;
                }
                record.set(p, record.elementAt(p) + 1);
            } else {
                record.addElement(0);
                p++;
            }
        }
        byte size = (byte) record.size();
        // Count on Record is 0
        if (size == 0) return 1;
        // Count on Record is 1
        if (size == 1) {
            return record.elementAt(0) + (src.contains("0")||num>0 ? 1 : 0);
        }
        int longest = 0;
        for (byte i = 0; i < size - 1; i++)
            longest = Math.max(record.elementAt(i) + record.elementAt(i + 1) + 1, longest);
        return longest;
    }
}

public class Test2 {
    public static void main(String[] args) {
        System.out.println(Solution.reverseBits(0));
        System.out.println(Solution.reverseBits(30));
        System.out.println(Solution.reverseBits(1775));
        System.out.println(Solution.reverseBits(2147483647));
        System.out.println(Solution.reverseBits(-1));
        return;
    }
}
