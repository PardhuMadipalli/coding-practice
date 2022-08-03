package problems.miscellaneous;

import java.util.Arrays;
import java.util.Comparator;

// https://leetcode.com/problems/largest-number
// Check Leetcode link for better solution

public class LargestNumber {
    public String largestNumber(int[] nums) {
        String[] input = new String[nums.length];
        for(int i=0; i<nums.length; i++){
            input[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(input, new StringIntegerComparator());
        if (input[0].equals("0")) return "0";
        StringBuilder sb = new StringBuilder();
        Arrays.stream(input).forEach(sb::append);
        return sb.toString();
    }

    static class StringIntegerComparator implements Comparator<String> {

        @Override
        public int compare(String a, String b) {
            if (a.equals("0") || b.equals("0") || a.length() == b.length())
                return b.compareTo(a);

            int adigis  = a.length();
            int bdigits = b.length();

            if(adigis > bdigits) {
                String acut = a.substring(0, bdigits);
                int comparedVal = b.compareTo(acut);
                return comparedVal != 0 ? comparedVal : compare(a.substring(bdigits), b);
            } else {
                String bcut = b.substring(0, adigis);
                int comparedVal = bcut.compareTo(a);
                return comparedVal != 0 ? comparedVal : compare(a, b.substring(adigis));
            }
        }
    }

    public static void main(String[] arhs){
        System.out.println(new LargestNumber().largestNumber(new int[]{432,43243}));
        System.out.println(new LargestNumber().largestNumber(new int[]{3,30,34,5,9}));
        System.out.println(new LargestNumber().largestNumber(new int[]{2060, 2}));
    }

}