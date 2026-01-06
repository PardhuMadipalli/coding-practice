package problems.dp;

import java.util.Arrays;
import java.util.Comparator;

public class MaximumProfitInJobSchedulingDp {
    
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][3];

        for(int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        
        // Sort by end time
        Arrays.sort(jobs, Comparator.comparingInt(a -> a[1]));

        int[] dp = new int[n];
        dp[0] = jobs[0][2];
        
        for(int i = 1; i < n; i++) {
            // Option 1: Skip current job
            int skip = dp[i-1];
            
            // Option 2: Take current job
            int take = jobs[i][2];
            
            // Find the latest job that ends before current job starts
            int latestCompatible = binarySearch(jobs, i, jobs[i][0]);
            if(latestCompatible != -1) {
                take += dp[latestCompatible];
            }
            
            dp[i] = Math.max(skip, take);
        }
        
        return dp[n-1];
    }
    
    // Binary search to find the latest job that ends before or at startTime
    private int binarySearch(int[][] jobs, int index, int startTime) {
        int left = 0, right = index - 1;
        int result = -1;
        
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if(jobs[mid][1] <= startTime) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }

    public static void main(String[] args) {
        MaximumProfitInJobSchedulingDp problem = new MaximumProfitInJobSchedulingDp();
        System.out.println(problem.jobScheduling(new int[]{1,2,3,4,6}, new int[]{3,5,10,6,9}, new int[]{20,20,100,70,60}));
    }
}
