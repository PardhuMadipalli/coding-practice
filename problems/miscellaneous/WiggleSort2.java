package problems.miscellaneous;

//https://leetcode.com/problems/wiggle-sort-ii/

public class WiggleSort2 {
    public void wiggleSort(int[] nums) {
        // Input constraints are 0 to 5000
        int[] counts = new int[5001];
        for(int num: nums){
            counts[num]++;
        }

        int j=0;
        for(int i= nums.length - (nums.length%2==0 ? 2 :1); i>=0; i-=2) {
            while(counts[j]==0){
                j++;
            }
            nums[i] = j;
            counts[j]--;
        }

        for(int i=nums.length - (nums.length%2==0 ? 1 : 2); i>=0; i-=2) {
            while(counts[j]==0){
                j++;
            }
            nums[i] = j;
            counts[j]--;
        }
    }
}
