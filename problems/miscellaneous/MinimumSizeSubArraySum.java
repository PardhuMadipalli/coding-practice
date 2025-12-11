package problems.miscellaneous;

import java.util.Arrays;

/**
 * <a href="https://leetcode.com/problems/minimum-size-subarray-sum/description/">Problem link</a>
 */
public class MinimumSizeSubArraySum {
    /**
     * O(n log(n)) solution
     */
    class Solution {
        public int minSubArrayLen(int s, int[] nums) {
            int total =0;
            for(int num: nums) {
                total+= num;
            }
            if (total < s) return 0;
            Arrays.sort(nums);
            int sum = 0;
            for(int i=nums.length-1;i>=0;i--){
                sum+=nums[i];
                if(sum>=s){
                    return nums.length-i;
                }
            }
            return 0;
        }
    }
}
