package problems.miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PerfectSquares {
    private List<Integer> psqrs;
    private int[] dp;
    public int numSquares(int n) {
        if (n == 0 || n == 1) return n;
        psqrs = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            int sqr = i * i;
            if (sqr <= n) {
                psqrs.add(sqr);
            } else {
                break;
            }
        }
        Collections.reverse(psqrs);
        dp = new int[n+1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        dp[1] = 1;
        return findAns(n);
    }

    private int findAns(int n) {
        if(dp[n] != Integer.MAX_VALUE)
            return dp[n];

        for (int sqr : psqrs) {
            if (sqr <= n) {
                int numways = 1 + findAns(n-sqr);
                if (numways < dp[n]) {
                    dp[n] = numways;
                }
            }
        }
        return dp[n];
    }
}
