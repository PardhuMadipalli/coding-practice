package problems.miscellaneous;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MinimumDeleteOperations {
    public int minOperations(int[] nums, int[] numsDivide) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for(int num : nums)
            queue.add(num);

        int count = 0, prev =-1;
        while(!queue.isEmpty()) {
            int curr = queue.poll();
            if(prev != curr && canDivide(curr, numsDivide)) {
                return count;
            }
            count++;
            prev = curr;
        }
        return -1;
    }

    private boolean canDivide(int a, int[] nums) {
        return Arrays.stream(nums).allMatch(n -> n % a == 0);
    }
}
