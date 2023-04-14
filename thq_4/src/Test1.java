import java.util.Arrays;

class Solution1 {
    public static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;
        else if (s.length() == 1) return 1;

        int[] letters = new int[95];
        Arrays.fill(letters, -1);
        int nowIndex = 0, i = 0, longest = 0, lastrun = 0;
        while (s.length() - nowIndex != 0) {
            for (i = nowIndex; i < s.length(); i++) {
                int i_code = (char) (s.charAt(i) - ' ');
                if (letters[i_code] > -1) {
                    //Broke due to this duplicate letter
                    break;
                } else {
                    letters[i_code] = i;
                    lastrun++;
                }
            }
//            lastrun = i - nowIndex;
            longest = Math.max(longest, lastrun);
            if (i - s.length() == 0) return longest;
            int i_code_t = (char) (s.charAt(i) - ' ');
            lastrun -= (
                    (letters[i_code_t] - nowIndex >=0)?letters[i_code_t] - nowIndex:0
                    )+1;
            for (int j = letters[i_code_t]; j >= nowIndex; j--) letters[(s.charAt(j) - ' ')] = -1;
            nowIndex = i;
//            letters[i_code_t] = -1;
//            lastrun=0;
//            Arrays.fill(letters,false);
        }
        return longest;
//        if(i - s.length() == 0)return s.length();
//        int thisrun=i,nextrun=lengthOfLongestSubstring(s.substring(1));
//        return Math.max(nextrun, thisrun);
    }
}

public class Test1 {
    public static void main(String[] args) {
//        System.out.printf("%d=5\n", Solution1.lengthOfLongestSubstring("qrsvbspk"));
//        System.out.printf("%d=3\n", Solution1.lengthOfLongestSubstring("dvdf"));
//        System.out.printf("%d=5\n", Solution1.lengthOfLongestSubstring("tmmzuxt"));
//        System.out.printf("%d=3\n", Solution1.lengthOfLongestSubstring("abcabcbb"));
//        System.out.printf("%d=6\n", Solution1.lengthOfLongestSubstring("bbtablud"));
    }
}
