package problems.miscellaneous;

// https://leetcode.com/problems/first-missing-positive/

public class FirstMissingPositive {
    public int firstMissingPositive(int[] nums) {
        final int n = nums.length;
        for(int i=0; i<n; i++) {
            if(nums[i]<=0) {
                nums[i]=Integer.MAX_VALUE;
            }
        }

        for(int i=0; i<n; i++) {
            if(nums[i]!=Integer.MIN_VALUE && nums[i]!=Integer.MAX_VALUE && nums[i]<=n) {
                int index = Math.abs(nums[i])-1;
                if(nums[index]>0) {
                    if(nums[index]==Integer.MAX_VALUE) {
                        nums[index]=Integer.MIN_VALUE;
                    } else {
                        nums[index] *= -1;
                    }
                }
            }
        }

        for(int i=0; i<n; i++) {
            if(nums[i]>=0) return i+1;
        }
        return n+1;
    }

    public static void main(String... args) {
        int[] input = {3,4,-1,1};
        int[] input2 = {7,8,9,11,12};
        int[] input3 = {1,1};
        System.out.println(new FirstMissingPositive().firstMissingPositive(input3));
    }
}
