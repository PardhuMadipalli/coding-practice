package problems;

import java.util.Arrays;

public class CountIndicesWithOppositeParity {
    public int[] countOppositeParity(int[] nums) {
        int n = nums.length;
        int[] results = new int[nums.length];

        int[] revOdds = new int[n];
        int[] revEvens = new int[n];

        int prevOdd = 0, prevEven = 0;

        for (int i = n-1; i >= 0; i--) {
            if (nums[i] % 2 == 0) {
                revEvens[i] = prevEven + 1;
                revOdds[i] = prevOdd;
            } else {
                revOdds[i] = prevOdd + 1;
                revEvens[i] = prevEven;
            }
            prevEven = revEvens[i];
            prevOdd = revOdds[i];
        }
        for (int i = 0; i < n-1; i++) {
            results[i] = nums[i]%2 == 0 ? revOdds[i+1] : revEvens[i+1];
        }
        return results;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new CountIndicesWithOppositeParity().countOppositeParity(new int[]{1})));
    }
}
