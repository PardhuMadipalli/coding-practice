package problems.miscellaneous;

import java.util.Arrays;

public class LongestIncreasingPath {
//    https://leetcode.com/problems/longest-increasing-path-in-a-matrix/submissions/
    int rows, cols;
    public int longestIncreasingPath(int[][] matrix) {
        rows = matrix.length;
        cols = matrix[0].length;
        int[][] ans = new int[rows][cols];
        int max=1;
        for(int i=0; i<rows; i++) {
            for(int j=0; j<cols; j++) {
                startFromHere(i,j, matrix, ans);
                if(ans[i][j] > max) {
                    max = ans[i][j];
                }
            }
        }
        return max;
    }

    private int startFromHere(int r, int c, int[][] matrix, int[][] ans) {
        if(ans[r][c] != 0) {
            return ans[r][c];
        }
        int currval = matrix[r][c];
        ans[r][c] = 1;
        if(isValid(r-1, c) && matrix[r-1][c] > currval) {
            ans[r][c] = 1 + startFromHere(r-1, c, matrix, ans);
        }

        if(isValid(r+1, c) && matrix[r+1][c] > currval) {
            int potValue = 1 + startFromHere(r+1, c, matrix, ans);
            if(ans[r][c] < potValue){
                ans[r][c] = potValue;
            }
        }

        if(isValid(r, c-1) && matrix[r][c-1] > currval) {
            int potValue = 1 + startFromHere(r, c-1, matrix, ans);
            if(ans[r][c] < potValue){
                ans[r][c] = potValue;
            }
        }

        if(isValid(r, c+1) && matrix[r][c+1] > currval) {
            int potValue = 1 + startFromHere(r, c+1, matrix, ans);
            if(ans[r][c] < potValue) {
                ans[r][c] = potValue;
            }
        }
        return ans[r][c];
    }

    private boolean isValid(int r, int c) {
        return r >= 0 && r < rows && c >=0 && c<cols;
    }
}
