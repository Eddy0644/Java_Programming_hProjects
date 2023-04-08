public class main_8 {
    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = (int) Math.round(Math.random() * 100);
        }
        for (int i = 0; i < 100; i++) {
            for (int j = i + 1; j < 100; j++) {
                if (arr[i] > arr[j]) {
                    int t = arr[i];
                    arr[i] = arr[j];
                    arr[j] = t;
                }
            }
        }
        for (int i = 1; i <= 100; i++) {
            System.out.printf("#%2d: < %2d > %s", i, arr[i - 1], i % 5 == 0 ? "\n" : "\t");
        }
    }
}
