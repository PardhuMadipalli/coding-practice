package problems.miscellaneous;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// https://leetcode.com/problems/longest-consecutive-sequence/
public class LongestConsecutive {
    public int longestConsecutive(int[] nums) {

        // Two maps for maintaining ranges. One start->end and the other for end -> start
        // For every range we have entries in both maps.
        // For example if a range is [4,7], startEnds will contain 4->7 and endStart will contain 7->4
        Map<Integer, Integer> startEnds = new HashMap<>(), endStarts = new HashMap<>();
        Set<Integer> covered = new HashSet<>();
        int answer = 0;

        for(int num: nums) {
            if(covered.contains(num)) continue; // Avoid duplicates

            Integer newStart = endStarts.get(num-1);
            Integer newEnd = startEnds.get(num+1);
            if(newStart != null && newEnd != null){
                // Example: num=4, existing ranges are {1,3} and {5, 8}
                // We merge these 3 ranges to a single range {1, 8}

                endStarts.put(newEnd, newStart);
                startEnds.remove(num+1);

                startEnds.put(newStart, newEnd);
                endStarts.remove(num-1);
                if(newEnd - newStart +1 > answer) {
                    answer = newEnd - newStart +1;
                }
            }
            else if(newStart != null) {
                // {1,3} and 4 will become {1, 4}
                startEnds.put(newStart, num);
                endStarts.put(num, newStart);
                endStarts.remove(num-1);
                answer = Math.max(num-newStart+1, answer);
            }
            else if(newEnd != null) {
                // 4, {5,7} will become {4,8}
                startEnds.put(num, newEnd);
                endStarts.put(newEnd, num);
                startEnds.remove(num+1);
                answer = Math.max(newEnd-num+1, answer);
            } else {
                // Just put {4}
                startEnds.put(num, num);
                endStarts.put(num, num);
                if(1 > answer) answer=1;
            }
            covered.add(num);
        }
        return answer;
    }

    public static void main(String []args) {
        System.out.println(new LongestConsecutive().longestConsecutive(new int[]{-7,-1,3,-9,-4,7,-3,2,4,9,4,-9,8,-7,5,-1,-7}));
    }

}
