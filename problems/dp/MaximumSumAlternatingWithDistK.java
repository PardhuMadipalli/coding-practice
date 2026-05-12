package problems.dp;

import java.util.Arrays;

public class MaximumSumAlternatingWithDistK {
    long result = 0;
    int n;
    int k;
    int[] nums;
    long[][] dp;
    public long maxAlternatingSum(int[] nums, int k) {
        n = nums.length;
        this.k = k;
        this.nums = nums;
        dp = new long[n][2];
        if (nums.length == 1) return nums[0];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }
        for (int i = 0; i < n; i++) {
            long prevSmallAnswer = maxAlternatingSumEndingAtIWithPrevSmall(nums, k, i, true);
            result = Math.max(result, prevSmallAnswer);
            long prevBigAnswer = maxAlternatingSumEndingAtIWithPrevSmall(nums, k, i, false);
            result = Math.max(result, prevBigAnswer);
        }
        return result;
    }

    private long maxAlternatingSumEndingAtIWithPrevSmall(int[] nums, int k, int i, boolean prevSmall) {
        if (dp[i][prevSmall ? 0 : 1] != -1) {
            return dp[i][prevSmall ? 0 : 1];
        }
        long currResult = nums[i];
        for(int j = i-k; j >= 0; j--){
            if ((prevSmall && nums[j] < nums[i]) || (!prevSmall && nums[j] > nums[i])) {
                currResult = Math.max(currResult, nums[i] + maxAlternatingSumEndingAtIWithPrevSmall(nums, k, j, !prevSmall));
            }
        }
        return dp[i][prevSmall ? 0 : 1] = currResult;
    }

    public static void main(String[] args) {
        System.out.println(new MaximumSumAlternatingWithDistK().maxAlternatingSum(new int[]{1,5,7,4}, 1));
    }
}
