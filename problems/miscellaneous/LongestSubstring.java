package problems.miscellaneous;

import java.util.HashMap;
import java.util.Map;


/**
 * Not an efficient solution
 */
public class LongestSubstring {
    public int longestSubstring(String s, int k) {
        return longestSub(0, s.length()-1, s, k);
    }

    /**
     * Divide and conquer.
     * Split the string into 2 parts where the partition occurs at char whose count is less than k
     */
    private int longestSub(int l, int r, String s, int k) {
        if (r < l)
            return 0;
        if (l == r)
            return k==1 ? 1 : 0;

        int[] counts = getCountsArray(s, l, r);
        for (int i = l; i <= r; i++) {
            if (counts[s.charAt(i)-'a'] < k) {
                int left = longestSub(l, i-1, s, k);
                if (r-i > left) {
                    return Math.max(left, longestSub(i+1, r, s, k));
                }
                return left;
            }
        }
        return r-l+1;
    }

    private int[] getCountsArray(String s, int l, int r) {
        int[] counts = new int[26];
        for (int i = l; i <= r; i++) {
            counts[s.charAt(i) - 'a']++;
        }
        return counts;
    }

    private Map<Character, Integer> getCounts(String s, int l, int r) {
        Map<Character, Integer> map = new HashMap<>();
        for(int i=l; i<=r; i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        return map;
    }

    private int longestSub2(int l, int r, String s, int k, int[] counts) {
        if(r<l) {
            return 0;
        }

        if (substrExists(counts, k)) return r-l+1;

        char rchar = s.charAt(r);
        decChar(counts, rchar);
        int leftSide = longestSub2(l, r-1, s, k, counts);
        incChar(counts, rchar);

        char lchar = s.charAt(l);
        decChar(counts, lchar);
        int rightSide = longestSub2(l+1, r, s, k, counts);
        incChar(counts, lchar);
        return Math.max(rightSide, leftSide);
    }

    private boolean substrExists(int[] counts, int k) {
        for(int count : counts) {
            if (count > 0 && count < k) return false;
        }
        return true;
    }

    private void decChar(int[] counts, char c) {
        counts[c - 'a']--;
    }

    private void incChar(int[] counts, char c) {
        counts[c - 'a']++;
    }
}