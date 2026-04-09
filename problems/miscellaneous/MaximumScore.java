package problems.miscellaneous;

import java.util.ArrayDeque;
import java.util.Deque;

// https://leetcode.com/problems/maximum-score-of-a-good-subarray/
// Same solution as largest histogram but with additional check if it includes k
public class MaximumScore {
    public int maximumScore(int[] nums, int k) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0); // We know that at least one item is there in nums array
        int answer = nums[k]; // The answer is at least nums[k] if you don't include any other elements
        final int n = nums.length;
        for(int i=1; i <= n; i++) {
            // i==n : when all the in the array are covered, there can still be a few elements left in the stack.
            while(!stack.isEmpty() && nums[i] < nums[stack.getLast()]) {
                int droppedIndex = stack.removeLast();
                int previousSmallerIndex = stack.isEmpty() ? - 1 : stack.getLast();
                if (i > k && previousSmallerIndex < k) {
                    // index k must be in the boundary
                    answer =  Math.max(nums[droppedIndex] * (i - previousSmallerIndex - 1), answer);
                }
            }
            stack.addLast(i);
        }

        while(stack.isEmpty()) {
            int droppedIndex = stack.removeLast();
            int previousSmallerIndex = stack.isEmpty() ? - 1 : stack.getLast();
            if (previousSmallerIndex < k) {
                // index k must be in the boundary
                answer =  Math.max(nums[droppedIndex] * (n - previousSmallerIndex - 1), answer);
            }
        }
        return answer;
    }
}
