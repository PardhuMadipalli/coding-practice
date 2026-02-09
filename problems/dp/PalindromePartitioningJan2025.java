package problems.dp;

import java.util.ArrayList;
import java.util.List;

public class PalindromePartitioningJan2025 {
    Boolean[][] dp;
    List<List<String>>[] results;

    public List<List<String>> partition(String s) {
        dp = new Boolean[s.length()][s.length()];
        results = (List<List<String>>[]) new List[s.length()];
        return partition(s, 0);
    }

    private List<List<String>> partition(String s, int startIndex) {
//        System.out.println("Invoking partition for index: " + startIndex);
        List<List<String>> localResults = new ArrayList<>();
        if (startIndex >= s.length()) {
            localResults.add(new ArrayList<>());
            return localResults;
        }
        if (this.results[startIndex] != null) {
            for (List<String> list: this.results[startIndex]) {
                localResults.add(new ArrayList<>(list));
            }
            return localResults;
        }


        for (int i = s.length()-1; i >= startIndex; i--) {
//            System.out.printf("checking palindrome for %d and %d\n", startIndex, i);
            if (isPalindrome(s, startIndex, i)) {
                String currSubstring = s.substring(startIndex, i+1);
//                System.out.printf("This is palindrome: %s with start and ends: %d and %d\n", currSubstring, startIndex, i);
                partition(s, i+1).forEach(list -> {
                    List<String> newList = new ArrayList<>(list);
                    newList.add(0, currSubstring);
                    localResults.add(newList);
                });
            }
        }

        results[startIndex] = localResults;
        return localResults;
    }

    boolean isPalindrome(String s, final int l, final int r) {
        if (l>=r) return true;
        if (dp[l][r] != null) {
            return dp[l][r];
        }
        dp[l][r] = (s.charAt(l) == s.charAt(r)) && isPalindrome(s, l+1, r-1);
        return dp[l][r];
    }

    public static void main(String[] args) {
//        String s = "abcba";
//        System.out.println(new PalindromePartitioningJan2025().isPalindrome(s, 0, s.length()-1));
        System.out.println(new PalindromePartitioningJan2025().partition("aaab"));
    }
}
