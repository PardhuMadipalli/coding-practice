package problems.miscellaneous;

public class LongestSubstringWithoutDupl {
    public int lengthOfLongestSubstring(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        // By using boolean array instead of hashset we can save a lot of time and memory
        boolean[] exists = new boolean[256];
        int max = 1;
        int uniques = 0;
        int start = 0;
        int end = 0;
        while (end < s.length()) {
            if (exists[s.charAt(end)]) {
                exists[s.charAt(start++)] = false;
                --uniques;
            }  else {
                exists[s.charAt(end++)] = true;
                max = Math.max(max, ++uniques);
            }
        }
        return max;
    }
}
