package problems.dp;

import java.util.Arrays;

public class EqualPartition {
    int[][] memo;
    int reqSum;
    int[] nums;
    public boolean canPartition(int[] nums) {
        this.nums = nums;
        int n = nums.length;
        int fullSum = 0;
        for (var num: nums) {
            fullSum += num;
        }
        if (fullSum % 2 != 0) return false;
        reqSum = fullSum/2;
        memo = new int[n+1][reqSum + 1];
        for (int[] row: memo) {
            Arrays.fill(row, -1);
        }
        int ans = recur(0, n);
        return ans != 0;
    }

    private int recur(final int currSum, int n) {
        if (reqSum == currSum) return 1;
        if (currSum >  reqSum) return 0;
        if (n == 0) return 0;

        if(memo[n][currSum] != -1) return memo[n][currSum];

        int take = recur(currSum + nums[n-1], n-1);
        if (take == 1) {
            return memo[n][currSum] = 1;
        }
        return memo[n][currSum] = recur(currSum, n-1);
    }

    public static void main(String[] args) {
        System.out.println(new EqualPartition().canPartition(new int[]{1,2,2,1}));
    }
}
