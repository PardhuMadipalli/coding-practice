package problems.dp;

/**
 * https://leetcode.com/problems/maximal-square/
 */
public class MaximalSquare {
    public int maximalSquare(char[][] mat) {
        int n=mat.length,m=mat[0].length;
        int[][] arr = new int[n][m];
        for(int i=0;i<n;i++) for(int j=0;j<m;j++) if(mat[i][j]=='1') arr[i][j] = 1;
        for(int i=1;i<n;i++){
            for(int j=1;j<m;j++) {
                if(arr[i][j]==1)
                    arr[i][j] += Math.min(arr[i-1][j-1],Math.min(arr[i-1][j],arr[i][j-1]));
            }
        }
        int ans = 0;
        for(int[] ar : arr) for(int i: ar) ans = Math.max(ans,i);
        return ans*ans;
    }
}
