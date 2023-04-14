public class Fract {
    private int num;
    private int den;

    public Fract(int a, int b) {
        this.num = a;
        this.den = b;
    }

    public static void main(String[] args) {
//        System.out.println(getMinMultiple(5, 6));
//        System.out.println(getMaxDivisor(15, 25));
        Fract f1 = new Fract(1, 5), f2 = new Fract(7, 20), f3;
        f3 = f1.add(f2);
        f3.show();
    }

    public static int getMaxDivisor(int m, int n) {
        for (int i = (int) (m / 2); i > 1; i--) {
            if (m % i == 0 && n % i == 0) return i;
        }
        return 1;
    }

    public static int getMinMultiple(int m, int n) {
        int k = m;
        while (k % n != 0) k += m;
        return k;
    }

    public Fract add(Fract f) {
        // 为两个分母找到最小公倍数
        int mm = getMinMultiple(this.den, f.den);
        // 以mm为分母的和分数的分子
        int new_num = (mm / this.den) * this.num + (mm / f.den) * f.num;
        // 和分数的分子分母可同整除的最大整数
        int r = getMaxDivisor(new_num, mm);
        // 约分之后
        return new Fract(new_num / r, mm / r);
    }

    public void show() {
        System.out.printf("%d/%d", this.num, this.den);
    }
}
