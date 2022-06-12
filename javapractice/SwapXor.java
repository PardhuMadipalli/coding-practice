package javapractice;

public class SwapXor {
    public static void main(String[] args) {
        int x=2, y=3;
        x = x^y;
        y = x^y;
        x = x^y;
        System.out.println(x + " " + y);
    }
}
