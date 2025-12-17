package problems.miscellaneous;

/**
 * https://leetcode.com/problems/jump-game?envType=problem-list-v2&envId=greedy
 *
 */
public class JumpGame {
    public boolean canJump(int[] nums) {
        return canJumpRec(nums);
    }

    // 1ms solution
    private boolean canJumpRec(int[] nums) {
        return canJumpRec(nums, nums.length-1);
    }

    private boolean canJumpRec(int[] nums, int n) {
        if (n==0) return true;
        for (int i=n-1; i>=0; i--) {
            if (i+nums[i] >= n) return canJumpRec(nums, i);
        }
        return false;
    }

    // 2ms solution
    public boolean canJumpGreedy(int[] nums) {
        int n = nums.length;

        // boolean reach[] = new boolean[n];
        int maxval = n -1;
        int maxreach = 0;
        int curr;
        for(int i=0; i<n-1 && i <= maxreach; i++) {
            curr = i + nums[i];
            if(curr > maxreach) {
                maxreach = curr;
            }
            if(maxreach >= maxval) {
                return true;
            }
        }

        return maxreach >= maxval;
    }
}
