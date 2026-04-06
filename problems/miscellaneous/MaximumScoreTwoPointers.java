package problems.miscellaneous;

import java.util.ArrayDeque;
import java.util.Deque;

// https://leetcode.com/problems/maximum-score-of-a-good-subarray/
// Same solution as largest histogram but with additional check if it includes k
public class MaximumScoreTwoPointers {
    public int maximumScore(int[] nums, int k) {
        int left = k, right = k;
        int minVal = nums[k];
        int maxScore = minVal;

        while (left > 0 || right < nums.length - 1) {

            if (left == 0) {
                right++;
            } else if (right == nums.length-1) {
                left--;
            } else if (nums[right + 1] >= minVal &&  nums[left-1] >= minVal) {
                // doesn't matter
                right++;
            } else if (nums[right + 1] > nums[left-1]) {
                right++;
            } else {
                left--;
            }

            minVal = Math.min(minVal, Math.min(nums[left], nums[right]));
            maxScore = Math.max(maxScore, minVal * (right - left + 1));
        }

        return maxScore;
    }

    public static void main(String[] args) {
        System.out.println(new MaximumScoreTwoPointers().maximumScore(new int[]{10,3,7,}, 1));
    }
}
