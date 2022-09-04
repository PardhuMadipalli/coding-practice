package problems.miscellaneous;

public class XorSequence {
    // Complete the xorSequence function below.
    static long xorSequence(long l, long r) {
        return findcumxor(r) ^ findcumxor(l-1);
    }

    static long findcumxor(long n) {
        int nModEight = (int) (n % 8);
        if(nModEight==0 || nModEight == 1) {
            return n;
        }
        if(nModEight==2 || nModEight == 3) return 2L;
        if(nModEight==4 || nModEight == 5) return n+2;
        return 0L;
    }

    public static void main(String[] args) {
        System.out.println(XorSequence.xorSequence(680314533662474L, 979521774288029L));
    }
}
