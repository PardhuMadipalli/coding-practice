package problems.miscellaneous;

/**
 * JumpGame2
 */
public class JumpGame2 {
    public int jump(int[] nums) {
        int maxReachablePos=0, cur_max=0, ans=0;
        int jump_pos = 0;
        for(int i=0; i<nums.length-1; i++){
            if (i+nums[i] > cur_max){
                cur_max=i+nums[i];
                jump_pos=i;
            }
            cur_max = Math.max(cur_max, i+nums[i]);

            if(i==maxReachablePos){
                ans++; // You have made a jump somewhere between previous maxReachablePos and current maxReachablePos
                // We don't care where we jumped, but we know we jumped somewhere

                // next range is from i+1 to cur_max
                // in the new range, we again look for maxReachablePos, which is the max of all i+nums[i] of the range
                maxReachablePos=cur_max;

                // this is for additional info, if you want to find where to take the jump too
                System.out.println("Made a jump at " + jump_pos);
                if (maxReachablePos > nums.length - 1) return ans;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(new JumpGame2().jump(new int[]{2,3,1,0,5}));
    }
}
