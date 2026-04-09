package problems.dp;

import java.util.Arrays;

public class MinScoreTriangulation {

    int[] values;
    int[][] memo;

    // dp(i, j) = min score to triangulate the sub-polygon from vertex i to vertex j
    // vertices between i and j (exclusive) are i+1 .. j-1
    public int dp(int i, int j) {
        // base case: fewer than 3 vertices between i and j means no triangle possible
        if (j - i < 2) return 0;

        if (memo[i][j] != -1) return memo[i][j];

        int answer = Integer.MAX_VALUE;

        // try every vertex k between i and j as the third point of a triangle
        for (int k = i + 1; k < j; k++) {
            int score = values[i] * values[k] * values[j]  // triangle (i, k, j)
                      + dp(i, k)                            // left sub-polygon
                      + dp(k, j);                           // right sub-polygon
            answer = Math.min(answer, score);
        }

        return memo[i][j] = answer;
    }

    public int minScoreTriangulation(int[] values) {
        this.values = values;
        this.memo = new int[values.length][values.length];
        for (int[] row : memo) Arrays.fill(row, -1);
        return dp(0, values.length - 1);
    }

    public static void main(String[] args) {
        System.out.println(new MinScoreTriangulation().minScoreTriangulation(new int[]{1, 3, 1, 4, 1, 5}));
    }
}
