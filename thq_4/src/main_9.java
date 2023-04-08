public class main_9 {
    public static void main(String[] args) {
        int[] pool = new int[1000], num = new int[100];
        for (int i = 0; i < 100; i++) {
            num[i] = 0;
        }
        for (int i = 0; i < 1000; i++) {
            int randomized = (int) Math.floor(Math.random() * 100);
            pool[i] = randomized;
            num[randomized]++;
        }
        for (int i = 1; i <= 100; i++) {
            System.out.printf("[%2d] appeared %2d times%s", i, num[i - 1], i % 5 == 0 ? "\n" : ";\t");
        }
    }
}
