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

    int answer = 1;
    String answerString = "";
    int answerStart = 0;
    int answerEnd = 0;
    public String longestPalindromeDp(String s) {
        final int n = s.length();
        int[][] dp = new int[n][n];
        for(int i=0; i<n; i++) {
            dp[i][i] = 1;
        }
        answerString = s.substring(s.length()-1);
        for(int end=0; end<n; end++) {
            for(int start=0; start<end; start++) {
                getDpMemoize(end,start, dp, s);
            }
        }
        return s.substring(answerStart, answerEnd);
    }

    private int getDpMemoize(int end, int start, int[][] dp, String s) {
        if(start >= end) {
            return 1;
        }
        if(dp[end][start]!=0) {
            return dp[end][start];
        } else {
            if(s.charAt(start)==s.charAt(end)) {
                dp[end][start] = getDpMemoize(end-1, start+1, dp, s);
                if(dp[end][start]==1) {
                    if(end-start+1 > answer) {
                        answer = end-start+1;
                        answerStart = start;
                        answerEnd = end+1;
                    }
                }
            } else {
                dp[end][start]=-1;
            }
        }
        return dp[end][start];
    }

    public static void main(String []args) {
//        System.out.println(new LongestPalindromicSubstring().longestPalindrome("dcabcbacd"));
        System.out.println(new LongestPalindromicSubstring().longestPalindromeDp("cbbd"));
    }
}
