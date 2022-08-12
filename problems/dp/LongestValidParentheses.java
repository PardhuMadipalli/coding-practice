package problems.dp;

import java.util.ArrayDeque;
import java.util.Deque;

// https://leetcode.com/problems/longest-valid-parentheses

public class LongestValidParentheses {
    public int longestValidParentheses(String s) {
        final int n = s.length();
        Deque<Integer> stack = new ArrayDeque<>();
        int[] dp = new int[n];
        int max=0;
        for (int i = 0; i < n; i++) {
            if(s.charAt(i)=='(') {
                stack.push(i);
            } else if(!stack.isEmpty()) {
                int prevopen = stack.pop();
                dp[i] = i-prevopen+1 + (prevopen > 0 ? dp[prevopen-1] : 0);
                if(dp[i] > max) max = dp[i];
            }
        }
        return max;
    }
}
