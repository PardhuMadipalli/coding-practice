package problems.miscellaneous;

import java.util.Arrays;

// https://leetcode.com/problems/next-permutation/
public class NextPermutation {
    public void nextPermutation(int[] nums) {
        final int n = nums.length;
        int repvalindex = -1;
        for(int i=n-2; i>=0; i--) {
            if(nums[i] < nums[i+1]) {
                repvalindex = i;
                break;
            }
        }

        if(repvalindex != -1) {
            int repval = nums[repvalindex];
            for (int i=n-1; i>repvalindex; i--) {
                if(nums[i] > repval) {
                    nums[repvalindex] = nums[i];
                    nums[i] = repval;
                    reverse(nums, repvalindex+1);
                    break;
                }
            }
        } else {
            reverse(nums, 0);
        }
    }

    static void reverse(int[] a, int begin)
    {   final int n = a.length;
        int size = n - begin;
        int i, t;
        for (i = 0; i < size / 2; i++) {
            t = a[i+begin];
            a[i+begin] = a[n - i - 1];
            a[n - i - 1] = t;
        }
    }

    public static void main(String[] args) {
        int[] input = new int[]{9, 3, 7, 13, 6, 12};
        new NextPermutation().nextPermutation(input);
        System.out.println(Arrays.toString(input));
    }
}
