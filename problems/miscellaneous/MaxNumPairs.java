package problems.miscellaneous;

public class MaxNumPairs {
    public int[] numberOfPairs(int[] nums) {
        int[] counts = new int[101];
        for(int num: nums) {
            counts[num] ++;
        }
        int pairs = 0, rem=0;
        for(int count : counts) {
            pairs += count/2;
            rem += count%2;
        }
        return new int[]{pairs, rem};
    }
}
