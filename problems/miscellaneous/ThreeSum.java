package problems.miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int rm = nums.length - 1; rm >= 2; rm--) {
            int reqSum = -nums[rm], l=0, r= rm - 1;
            int comp = (nums[l]+nums[r])    ;
            while (l < r) {
                if (nums[l] + nums[r] == reqSum) {
                    result.add(Arrays.asList(nums[l], nums[r], nums[rm]));
                    while (nums[r] == nums[r-1] && l < r) {
                        r--;
                    }
                    r--;
                    while (nums[l] == nums[l+1] && l < r) {
                        l++;
                    }
                    l++;
                } else if (nums[l] + nums[r] < reqSum) {
                    l++;
                } else {
                    r--;
                }
            }
            while (rm >=2 && nums[rm] == nums[rm-1]) {
                rm--;
            }
        }
        return result;
    }
}
