package problems.miscellaneous;

import java.util.ArrayDeque;
import java.util.Deque;

// https://leetcode.com/problems/shortest-unsorted-continuous-subarray/

/**
 * For solution without using queue, {@link ShortestUnsortedContinuousSubarray2}
 */
public class ShortestUnsortedContinuousSubarray {
    public int findUnsortedSubarray(int[] nums) {
        // increasing monotonic queue
        Deque<Integer> q = new ArrayDeque<>();
        int i;
        for(i=0; i<nums.length; i++) {
            if(!q.isEmpty() && nums[i] < nums[q.getLast()]) {
                break;
            }
            q.addLast(i);
        }
        for(; i<nums.length; i++) {
            while (!q.isEmpty() && nums[i] < nums[q.getLast()]) {
                q.pollLast();
            }
        }
        if(q.size() == nums.length) return 0;
        int startCount=q.size();

        // Decreasing monotonic queue from end
        q.clear();
        for(i=nums.length-1; i>=0; i--){
            if(!q.isEmpty() && nums[i] > nums[q.getLast()]){
                break;
            }
            q.addLast(i);
        }
        for(; i>=0; i--){
            while (!q.isEmpty() && nums[i] > nums[q.getLast()]) {
                q.pollLast();
            }
        }
        return nums.length - startCount - q.size();
    }

    public static void main(String[] args) {
        System.out.println(new ShortestUnsortedContinuousSubarray().findUnsortedSubarray(new int[]{1,2,5,-1,4,8,10,16,11,12, 19, 20, 21}));
    }
}
