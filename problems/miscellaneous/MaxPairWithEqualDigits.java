package problems.miscellaneous;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://leetcode.com/contest/weekly-contest-302/problems/max-sum-of-a-pair-with-equal-sum-of-digits/
public class MaxPairWithEqualDigits {
    public int maximumSum(int[] nums) {
        Map<Integer, List<Integer>> map= new HashMap<>();
        for(int num: nums) {
            int sumdig = getsumdig(num);
            List<Integer> list = map.get(sumdig);
            if(list == null) {
                list = new ArrayList<>();
                list.add(num);
                map.put(sumdig, list);
            } else {
                list.add(num);
            }
        }
        int ans = -1;
        for(Map.Entry<Integer, List<Integer>> entry: map.entrySet()) {
            int size = entry.getValue().size();
            if(size >= 2) {
                Collections.sort(entry.getValue());
                int potval = entry.getValue().get(size-1) + entry.getValue().get(size -2);
                if(potval > ans) {
                    ans = potval;
                }
            }
        }
        return ans;
    }

    private int getsumdig(int n) {
        int sum = 0;
        while(n != 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }
}
