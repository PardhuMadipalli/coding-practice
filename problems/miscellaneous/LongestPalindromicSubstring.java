package problems.miscellaneous;

public class LongestPalindromicSubstring {
    int len = 0;

    public String longestPalindrome(String s) {
        len = s.length();
        String longestEvenPalindrome = longEven(s);
        String longOdd = longOdd(s);
//        System.out.println("even: " + longestEvenPalindrome + " odd:" + longOdd);
        return (longestEvenPalindrome.length() > longOdd.length()) ? longestEvenPalindrome : longOdd;
    }

    private String longEven(String s){
        String longEven = "";
        for(int i=1; i<len; i++) {
            int m = i-1, n=i;
            for(; isValid(m,n); m--,n++) {
                    if(s.charAt(m) != s.charAt(n)) {
                        break;
                    }
            }
            if(n!=i && (n-i)*2 > longEven.length()) {
                longEven = s.substring(m+1, n);
            }
        }
        return longEven;
    }

    private String longOdd(String s){
        String longOdd = s.substring(s.length()-1);
        for(int i=0; i<len; i++) {
            int m = i-1, n=i+1;
            for(; isValid(m,n); m--,n++) {
                if(s.charAt(m) != s.charAt(n)) {
                    break;
                }
            }
            if(n!=i+1 && n-m+1 > longOdd.length()) {
                longOdd = s.substring(m+1, n);
            }
        }
        return longOdd;
    }

    private boolean isValid(int m, int n) {
        return m>=0 && n<len;
    }

    public static void main(String []args) {
        System.out.println(new LongestPalindromicSubstring().longestPalindrome("dcabcbacd"));
    }
}
