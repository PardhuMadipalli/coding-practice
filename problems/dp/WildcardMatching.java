package problems.dp;

/**
 * DP solution. You can also use greedy solution which is faster.
 */
public class WildcardMatching {

    String s,p;
    Boolean[][] dp;

    public boolean isMatch(String s, String p) {
        this.s = s;
        this.p = p;
        dp = new Boolean[s.length() + 1][p.length() + 1];
        return isMatchWithDp(0, 0);
    }

    private boolean isMatchWithDp(int i, int j) {
        if (dp[i][j] == null) {
            dp[i][j] = isMatch(i, j);
        }
        return dp[i][j];
    }

    private boolean isMatch(int i, int j) {
        if (i == s.length() && j == p.length()) {
            return true;
        }
        if (i == s.length()) {
            for (int k = j; k < p.length(); k++) {
                if (p.charAt(k) != '*') {
                    return false;
                }
            }
            return true;
        }
        if (j == p.length()) {
            return false;
        }
        if (p.charAt(j) == '*') {
            return isMatchWithDp(i + 1, j) || isMatchWithDp(i, j + 1);
        } else if (p.charAt(j) == '?' || p.charAt(j) == s.charAt(i)) {
            return isMatchWithDp(i + 1, j + 1);
        }
        return false;
    }

    public static void main(String[] args) {
        WildcardMatching w = new WildcardMatching();
//        System.out.println(w.isMatch("aa", "a"));
        System.out.println(w.isMatch("aa", "*"));
//        System.out.println(w.isMatch("cb", "?a"));
//        System.out.println(w.isMatch("adceb", "*a*b"));
//        System.out.println(w.isMatch("acdcb", "a*c?b"));
    }
}
