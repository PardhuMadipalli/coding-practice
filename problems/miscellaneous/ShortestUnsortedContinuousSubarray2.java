package problems.miscellaneous;

// https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
public class ShortestUnsortedContinuousSubarray2 {
    static class Solution {

        int minIndex = Integer.MAX_VALUE;
        int maxIndex = Integer.MIN_VALUE;
        public int findUnsortedSubarray(int[] nums) {
            int prev = Integer.MIN_VALUE;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] < prev) {
                    minIndex = i-1;
                    break;
                }
                prev = nums[i];
            }

            if (minIndex == Integer.MAX_VALUE) {
                return 0;
            }

            prev = nums[nums.length-1];
            for(int i = nums.length - 2; i >= 0; i--) {
                if (nums[i] > prev) {
                    maxIndex = i+1;
                    break;
                }
                prev = nums[i];
            }

            int minValue = Integer.MAX_VALUE, maxValue = Integer.MIN_VALUE;

            for(int i = minIndex; i <= maxIndex; i++) {
                if (nums[i] < minValue) {
                    minValue = nums[i];
                }
                if (nums[i] > maxValue) {
                    maxValue = nums[i];
                }
            }

            for (int i = minIndex-1; i >= 0; i--) {
                if (minValue < nums[i]) {
                    minIndex = i;
                } else {
                    break;
                }
            }

            for (int i = maxIndex+1; i < nums.length; i++) {
                if (maxValue >  nums[i]) {
                    maxIndex = i;
                } else {
                    break;
                }
            }

            return maxIndex - minIndex + 1;
        }
    }

    public static void main(String[] args) {
        int[] input = new int[] {2,6,4,8,10,9,15};
        System.out.println(new Solution().findUnsortedSubarray(input));
    }

}
