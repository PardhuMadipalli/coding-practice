package miscellaneous;

public class SimplifyFraction {

    private static final String FORMAT = "%d/%d";

    public static String simplify(int num, int denom) {
        if (num == 0) {
            return String.format(FORMAT, 0, 1);
        }
        if( num == denom) {
            return String.format(FORMAT, 1, 1);
        }
        int gcd = gcd(num, denom);
        System.out.println("gcd is "+ gcd);
        return String.format(FORMAT, num/gcd, denom/gcd);

    }

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static void main(String[] args){
        System.out.println(simplify(18, 15));

    }
}
