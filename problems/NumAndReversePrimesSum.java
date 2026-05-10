package problems;

public class NumAndReversePrimesSum {
    public int sumOfPrimesInRange(int n) {
        int res = 0;
        int rev = reverse(n);

        for (int i = Math.min(n, rev); i <= Math.max(n, rev); i++) {
            res += isPrime(i) ? i : 0;
        }
        return res;
    }

    /**
     * Return n when prime, else 0
     */
    private boolean isPrime(int n) {
        if (n == 1) return false;
        if (n == 2 || n == 3) return true;
        if (n % 2 == 0 || n % 3 == 0) { return false; }
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) { return false; }
        }
        return true;
    }

    public int reverse(int n) {
        int result = 0;
        while (n > 0) {
            result = result * 10 + (n % 10);
            n /= 10;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new NumAndReversePrimesSum().reverse(10));
        System.out.println(new NumAndReversePrimesSum().sumOfPrimesInRange(10));
    }
}
