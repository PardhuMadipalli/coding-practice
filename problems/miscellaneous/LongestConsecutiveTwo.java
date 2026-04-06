package problems.miscellaneous;

import java.util.HashSet;
import java.util.Set;

// https://leetcode.com/problems/longest-consecutive-sequence/
public class LongestConsecutiveTwo {
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0 || nums.length == 1) return nums.length;

        int answer = 1;
        Set<Integer> numSet = new HashSet<>();
        for (int num: nums) {
            numSet.add(num);
        }
        // Process each element, check in left and right neighbors
        // and remove them from set to avoid duplicate processing
        for(final int num: nums) {
            if(!numSet.contains(num)) continue; // Already processed

            int count = 1;

            for(int l=num-1; numSet.contains(l); l--) {
                count++;
                numSet.remove(l);
            }

            for(int r=num+1; numSet.contains(r); r++) {
                count++;
                numSet.remove(r);
            }
            answer = Math.max(answer, count);
            if (answer == nums.length) {
                return answer;
            }
        }
        return answer;
    }

    public static void main(String []args) {
        System.out.println(new LongestConsecutiveTwo().longestConsecutive(new int[]{-7,-1,3,-9,-4,7,-3,2,4,9,4,-9,8,-7,5,-1,-7}));
    }

}
