package problems.backtracking;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/n-queens-ii/?envType=problem-list-v2&envId=backtracking
 */
public class NQueensII {
    int res = 0;
    int[] pos;
    public int totalNQueens(int n) {
        if (n==1) return 1;
        if (n <= 3) return 0;
        pos = new int[n];
        Arrays.fill(pos, -1);
        backtrack(n, 0);
        return res;
    }

    private void backtrack(int n, int currLevel) {
        if (currLevel == n) {
            res++;
            return;
        }

        for (int i=0; i<n; i++) {
            if (verticallySafe(i) && diagonalSafe(currLevel, i)) {
                pos[currLevel] = i;
                backtrack(n, currLevel + 1);
                pos[currLevel] = -1;
            }
        }
    }

    private boolean verticallySafe(int i) {
        for (int po : pos) {
            if (po == i) return false;
        }
        return true;
    }

    private boolean diagonalSafe(int currentLevel, int checkPosition) {
        for (int i=0; i<currentLevel; i++) {
            if (currentLevel - i == Math.abs(checkPosition - pos[i])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new NQueensII().totalNQueens(9));
    }

}
