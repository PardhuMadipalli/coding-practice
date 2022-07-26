package problems.dp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

//https://leetcode.com/problems/maximum-profit-in-job-scheduling/
public class MaximumProfitInJobScheduling {
    TreeMap<Integer, Integer> endTimeTotalProfits;
    int endTimeIndex=0, answer=0;
    int n;
    int[] startTimes, endTimes, profits;
    Integer[] sortedIndexes;
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        n = startTime.length;
        sortedIndexes = new Integer[n];
        this.startTimes = startTime;
        this.endTimes = endTime;
        this.profits = profit;
        for(int i=0; i<n; i++) {
            sortedIndexes[i] = i;
        }
        Arrays.sort(sortedIndexes, Comparator.comparingInt(a -> endTimes[a]));
        endTimeTotalProfits = new TreeMap<>();

        for (Integer i : sortedIndexes) {
            answer = solve(endTimes[i], answer);
        }
        return answer;
    }

    private int solve(int endTime, int max){
        if(endTimeTotalProfits.containsKey(endTime)) return endTimeTotalProfits.get(endTime);
        for(;endTimeIndex < n && endTimes[sortedIndexes[endTimeIndex]] == endTime; endTimeIndex++) {
            int origIndex = sortedIndexes[endTimeIndex];
            Map.Entry<Integer, Integer> entryTillStartTime = endTimeTotalProfits.floorEntry(startTimes[origIndex]);
            max = Math.max(profits[origIndex] + (entryTillStartTime != null ? entryTillStartTime.getValue() : 0), max);
        }
        endTimeTotalProfits.put(endTime, max);
        return max;
    }

    public static void main(String[] args){
        int[][] input = new int[][] {
                {1,2,3,3},
                {3,4,5,1000000000},
                {50,10,40,70}};
//        int[][] input = new int[][] {
//                {1,2,3,3},
//                {3,4,5,6},
//                {50,10,40,70}
//        };
//        int[] startTimes = new int[]{1,2,3,4,6};
//        int[] endTimes = new int[] {3, 5, 10, 6, 9};
//        int[] profits = new int[] {20, 20, 100, 70, 60};
        System.out.println(new MaximumProfitInJobScheduling().jobScheduling(input[0], input[1], input[2]));
    }
}
