package problems.miscellaneous;

//https://leetcode.com/problems/maximum-product-subarray

public class MaxProductSubArray {
    public int maxProduct(int[] nums) {
        int prevmin = nums[0];
        int prevmax = nums[0];
        int curr = nums[0], max;
        for(int i=1; i<nums.length; i++){
            if(nums[i] > 0) {
                prevmax = prevmax > 0 ? prevmax * nums[i] : nums[i];
                prevmin = prevmin <= 0 ? prevmin * nums[i] : nums[i];
            } else if (nums[i] < 0) {
                max = prevmin <= 0 ? prevmin * nums[i] : nums[i];
                prevmin = prevmax > 0 ? prevmax * nums[i] : nums[i];
                prevmax = max;
            } else {
                prevmax = 0;
                prevmin = 0;
            }
            curr = Math.max(curr, prevmax);
        }
        return curr;
    }
}
