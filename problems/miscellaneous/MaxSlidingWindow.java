package problems.miscellaneous;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

// https://leetcode.com/problems/sliding-window-maximum/
public class MaxSlidingWindow {

    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        Deque<Integer> q = new ArrayDeque<>();

        // first k elements to initialise the queue
        // elements should be stored in the descending order.
        // If we find a new higher element, since this is the latest and highest element,
        // we don't care about other smaller elements. So remove them from the queue.
        for(int i=0; i<k; i++) {
            while(!q.isEmpty() && nums[q.getLast()] < nums[i])
                q.pollLast();
            q.add(i);
        }

        // process remaining elements
        for(int i=k; i<nums.length; i++) {
            result[i-k] = nums[q.getFirst()];
            while(!q.isEmpty() && nums[q.getLast()] < nums[i])
                q.pollLast();
            q.add(i);
            if(q.getFirst() == i-k){
                q.pollFirst();
            }
        }

        result[nums.length - k] = nums[q.getFirst()];
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new MaxSlidingWindow().maxSlidingWindow(new int[]{1,-1}, 1)));
    }
}
