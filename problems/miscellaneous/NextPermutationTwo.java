package problems.miscellaneous;

import java.util.Arrays;

public class NextPermutationTwo {
    public void nextPermutation(int[] nums) {
        int len =  nums.length;
        for (int r = len - 1; r > 0; r--) {
            if (nums[r] > nums[r-1]) {
                // We have to replace num[r-1] with the number just bigger than this starting from right most
                for (int target = len - 1; target > 0; target--) {
                    if (nums[target] > nums[r-1]) {
                        int tmp = nums[target];
                        nums[target] = nums[r-1];
                        nums[r-1] = tmp;
                        break;
                    }
                }
                reverse(nums, r, len-1);
                return;
            }
        }
        reverse(nums,  0, len-1);
    }

    private static void reverse(int[] a, int l, int r) {
        while (l < r) {
            int tmp = a[l];
            a[l] = a[r];
            a[r] = tmp;
            l++;
            r--;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,3,2};
        new NextPermutationTwo().nextPermutation(nums);
        System.out.println(Arrays.toString(nums));
    }
}
