package problems.miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Not completed

public class LarrysArray {
    public static String larrysArray(List<Integer> a) {
        // Write your code here
        final int n= a.size();
        int[] nums = new int[n];
        int[] indexes = new int[n];
        for(int i=0; i<n; i++) {
            nums[i] = a.get(i)-1;
            indexes[a.get(i)-1] = i;
        }
        for(int i=0; i<n; i++) {
            if(!setpos(i, nums, indexes)){
                return "NO";
            }
        }
        return "YES";
    }

    public static boolean setpos(int k, int[] nums, int[] indexes) {
        int diff = indexes[k] - k;
        return true;
    }

    public static void main(String[] a){
        Integer[] arr = new Integer[] {1, 6, 5, 2, 3, 4};
        System.out.println(LarrysArray.larrysArray(Arrays.asList(arr)));
    }

}
