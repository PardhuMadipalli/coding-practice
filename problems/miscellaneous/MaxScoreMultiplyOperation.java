package problems.miscellaneous;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MaxScoreMultiplyOperation {

    Map<String, Integer> dpResultMap = new HashMap<>();
    Map<String, Integer> prodMap = new HashMap<>();
    int[][] products;
    public int maximumScore(int[] nums, int[] multipliers) {
        final int n = nums.length;
        products = new int[2001][2001];
        for(int i=-1000; i<=1000; i++) {
            for(int j=0; j<=1000; j++) {

            }
        }
        return recurse(nums, 0, nums.length-1, 0, multipliers);
    }
//    int coutn = 0;
    private int recurse(int[] nums, int start, int end, int multiple, int[] multipliers) {

        if(start>=nums.length || end<0 || multiple>=multipliers.length || end-start+1 < multipliers.length-multiple) return 0;
        String dpResult = String.format("%d#%d", start, end);
        if(dpResultMap.containsKey(dpResult)) return dpResultMap.get(dpResult);

//        System.out.printf("checing %d and %d\n", start, end);
//        System.out.printf("%d and %d\n", multiple, start);
//        System.out.printf("%d and %d\n", multiple, end);

        int result = Math.max(getProduct(start, multiple, nums, multipliers) + recurse(nums, start+1, end, multiple+1, multipliers),
                getProduct(end, multiple, nums, multipliers) + recurse(nums, start, end-1, multiple+1, multipliers));
        dpResultMap.put(dpResult, result);
        return result;
    }

    private int getProduct(int i, int mult, int[] nums, int[] multiples) {
        String str = String.format("%d#%d", i, mult);
        Integer potAns = prodMap.get(str);
        if(potAns!=null) return potAns;

        potAns = nums[i]*multiples[mult];
        prodMap.put(str, potAns);
        return potAns;
    }

    private static class DpResult {
        int start;
        int end;

        DpResult(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DpResult dpResult = (DpResult) o;
            return start == dpResult.start && end == dpResult.end;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }

    public static void main(String... args) {
        System.out.println(new MaxScoreMultiplyOperation().maximumScore(new int[]{-5,-3,-3,-2,7,1}, new int[] {-10,-5,3,4,6}));
    }
}
