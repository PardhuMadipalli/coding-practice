package problems.miscellaneous;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

// https://leetcode.com/problems/find-the-most-competitive-subsequence/description/
//
public class MostCompetitive {
    public int[] mostCompetitiveUsingStack(int[] nums, int k) {
        if (k == nums.length) return nums;

        int n = nums.length;

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i=0; i<n; i++) {
            while(!stack.isEmpty() && stack.peekLast() > nums[i] && (stack.size() + n - i - 1 >= k)) {
                stack.removeLast();
            }
            stack.addLast(nums[i]);
        }
        int[] ans = new int[k];
        for (int i=0; i<k; i++) {
            ans[i] = stack.pollFirst();
        }
        return ans;
    }

    /**
     * Final answer
     */
    public int[] mostCompetitive(int[] nums, int k) {
        if (k == nums.length) return nums;
        int n = nums.length;
        int[] ans = new int[k];
        int stackSize = 0;
        int lhs = n-k-1;


        for (int i=0; i<n; i++) {
            // current stack size and the remaining unprocessed elements should be >= k
            while(stackSize !=0 && (lhs >= i - stackSize) && ans[stackSize-1] > nums[i]) {
                stackSize--;
            }
            // if the stack is already filled, no need to add new elements
            if (stackSize < k) {
                ans[stackSize++] = nums[i];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new MostCompetitive().mostCompetitive(new int[]{2,4,3,3,5,4,9,1}, 4)));
    }
}
