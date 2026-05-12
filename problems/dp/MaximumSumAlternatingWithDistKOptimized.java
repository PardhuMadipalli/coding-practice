package problems.dp;

/**
 * O(n log n) solution using two BITs (Fenwick trees) indexed by value.
 *
 * State:
 *   dpSmall[i] = best sum of alternating subsequence ending at i, where nums[i]
 *                is a "valley" (previous  element must be bigger)
 *   dpBig[i]   = best sum of alternating subsequence ending at i, where nums[i]
 *                is a "peak" (previous element must be smaller)
 *
 * Transition for index i (processing left to right):
 *   dpSmall[i] = nums[i] + max{ dpBig[j] : j <= i-k, nums[j] > nums[i] }
 *              = nums[i] + suffixMax of dpBig over values > nums[i], among j <= i-k
 *   dpBig[i]   = nums[i] + max{ dpSmall[j] : j <= i-k, nums[j] < nums[i] }
 *              = nums[i] + prefixMax of dpSmall over values < nums[i], among j <= i-k
 *
 * We maintain two BITs indexed by value (1..maxVal):
 *   bitSmall: prefixMax BIT — query gives max dpSmall for values in [1..v]
 *   bitBig:   suffixMax BIT — query gives max dpBig for values in [v..maxVal]
 *
 * We lag updates by k: when processing index i, we insert index i-k into the BITs.
 * This ensures only j <= i-k are in the BITs when we query for i.
 */
public class MaximumSumAlternatingWithDistKOptimized {

    private static final int MAX_VAL = 100_001;

    private long[] bitSmall;
    private long[] bitBig;

    // Prefix max BIT: update position v with value x
    private void updateSmall(int v, long x) {
        for (; v <= MAX_VAL; v += v & -v)
            bitSmall[v] = Math.max(bitSmall[v], x);
    }

    // Prefix max BIT: query max over [1..v]
    private long querySmall(int v) {
        long res = 0;
        for (; v > 0; v -= v & -v)
            res = Math.max(res, bitSmall[v]);
        return res;
    }

    // Suffix max BIT: update position v with value x (store at MAX_VAL+1-v)
    private void updateBig(int v, long x) {
        v = MAX_VAL + 1 - v;
        for (; v <= MAX_VAL; v += v & -v)
            bitBig[v] = Math.max(bitBig[v], x);
    }

    // Suffix max BIT: query max over [v..MAX_VAL]
    private long queryBig(int v) {
        v = MAX_VAL + 1 - v;
        long res = 0;
        for (; v > 0; v -= v & -v)
            res = Math.max(res, bitBig[v]);
        return res;
    }

    public long maxAlternatingSum(int[] nums, int k) {
        int n = nums.length;
        // Reset BITs for each call
        bitSmall = new long[MAX_VAL + 1];
        bitBig = new long[MAX_VAL + 1];
        long[] dpSmall = new long[n]; // ending at i as valley
        long[] dpBig = new long[n];   // ending at i as peak

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int v = nums[i];

            // Insert index i-k into BITs (lag by k)
            int lag = i - k;
            if (lag >= 0) {
                updateSmall(nums[lag], dpSmall[lag]);
                updateBig(nums[lag], dpBig[lag]);
            }

            // dpBig[i]: nums[i] is peak, need predecessor < nums[i]
            // query prefixMax of dpSmall for values in [1..v-1]
            long bestFromSmall = v > 1 ? querySmall(v - 1) : 0;
            dpBig[i] = v + bestFromSmall;

            // dpSmall[i]: nums[i] is valley, need predecessor > nums[i]
            // query suffixMax of dpBig for values in [v+1..MAX_VAL]
            long bestFromBig = v < MAX_VAL ? queryBig(v + 1) : 0;
            dpSmall[i] = v + bestFromBig;

            // A single element is always valid
            ans = Math.max(ans, Math.max(dpBig[i], dpSmall[i]));
        }

        return ans;
    }

    public static void main(String[] args) {
        MaximumSumAlternatingWithDistKOptimized sol = new MaximumSumAlternatingWithDistKOptimized();
        System.out.println(sol.maxAlternatingSum(new int[]{5, 4, 2}, 2));          // 7
        System.out.println(sol.maxAlternatingSum(new int[]{3, 5, 4, 2, 4}, 1));    // 14
        System.out.println(sol.maxAlternatingSum(new int[]{5}, 1));                 // 5
        System.out.println(sol.maxAlternatingSum(new int[]{1, 5, 7, 4}, 1));       // 17
    }
}
