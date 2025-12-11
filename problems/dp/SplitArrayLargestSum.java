package problems.dp;

import java.util.Arrays;

public class SplitArrayLargestSum {

    int[][] dp;
    int[] prefixSum;

    public int splitArray(int[] nums, int k) {
        k--;
        dp = new int[nums.length][k + 1];
        prefixSum = new int[nums.length];
        prefixSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            prefixSum[i] = prefixSum[i-1] + nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = -1;
            }
            dp[i][0] = prefixSum[i];
        }
        int max = nums[0];
        for (int i = 1; i <= k; i++) {
            max  = Math.max(max, nums[i]);
            dp[i][k] = max;
        }
//        return answer(nums.length - 1, k);
        return answerTwo(nums.length - 1, k);
    }

    int answerTwo(int r, int k) {
        System.out.println("finding " + r + " k: " + k);
        if (dp[r][k] != -1) {
            return dp[r][k];
        }
        int ans = Integer.MAX_VALUE;
        for (int cut = r-1; cut > 0; cut--) {
            int first = answerTwo(cut, k-1);
            int second = prefixSum[r] - prefixSum[cut];
            if (first != Integer.MAX_VALUE && first > second) {
                ans = Math.min(ans, first);
            }
            if (second >= first) {
                ans = Math.min(ans, second);
                break;
            }
        }
        return dp[r][k] = ans;
    }

    int answer(int r, int k) {
        if (dp[r][k] != -1) {
            return dp[r][k];
        }
//        if (r < k) return Integer.MAX_VALUE;

        int left = 0, right = r - 1;
        int ans = Integer.MAX_VALUE;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int first = answer(mid, k-1);
            int second = prefixSum[r] - prefixSum[mid];
            if (first > second && first != Integer.MAX_VALUE) {
                ans = Math.min(ans, first);
                right = mid - 1;
            } else {
                ans = Math.min(ans, second);
                left = mid + 1;
            }
//            System.out.printf("for r:%d and k:%d, mid is %d, first:%d, second:%d, ans:%d\n",
//                    r, k, mid, first, second, ans);
        }
//        System.out.printf("r:%d and k:%d, ans:%d\n", r, k, ans);
        return dp[r][k] = ans;
    }

    public static void main(String[] args) {
        SplitArrayLargestSum s = new SplitArrayLargestSum();
//        System.out.println(s.splitArray(new int[]{7,2,5,10,8}, 2));
        System.out.println(s.splitArray(new int[]{2,3,1,2,4,3}, 5));
//        System.out.println(s.splitArray(new int[]{1,2,3,4,5}, 1));
        System.out.println(Arrays.deepToString(s.dp));
    }
}
