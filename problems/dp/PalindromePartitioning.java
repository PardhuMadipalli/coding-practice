package problems.dp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://leetcode.com/problems/palindrome-partitioning/
public class PalindromePartitioning {
    List<List<String>> emptyListList;
    public List<List<String>> partition(String s) {
        HashMap<String, List<List<String>>> map = new HashMap<>();
        HashMap<String, Boolean> palMap = new HashMap<>();
        List<String> empty = Collections.emptyList();
        emptyListList = new ArrayList<>();
        emptyListList.add(empty);
        return solve(s, map, palMap);
    }

    private List<List<String>> solve(String s, HashMap<String, List<List<String>>> map, Map<String, Boolean> palMap) {
        if (s.length() == 0) return emptyListList;
        if (map.containsKey(s)) return map.get(s);

        List<List<String>> answer = new ArrayList<>();
        final int n = s.length();
        for(int i=1; i<=n; i++) {
            String firstPart = s.substring(0, i);
            if(isPalindrome(firstPart, palMap)) {
                solve(s.substring(i), map, palMap).forEach(firstPartList ->
                {
                    List<String> newlist = new ArrayList<>(firstPartList.size()+1);
                    newlist.add(firstPart);
                    newlist.addAll(firstPartList);
                    answer.add(newlist);
                });
            }
        }
        map.put(s, answer);
        return answer;
    }

    private boolean isPalindrome(String s, Map<String, Boolean> palMap) {
        if (s.length() < 2) return true;

        String stringToNum = getStringToNum(s);
        Boolean palMapVal = palMap.get(stringToNum);
        if(palMapVal != null) return palMapVal;

        final int n = s.length();
        for(int i=0; i<n/2; i++) {
            if(s.charAt(i) != s.charAt(n-1-i)) {
                palMap.put(stringToNum, false);
                return false;
            }
        }
        palMap.put(stringToNum, true);
        return true;
    }

    private String getStringToNum(String s) {
        char[] arr = s.toCharArray();
        char min = 'z';
        for(char c: arr){
            if(c < min) min=c;
        }
        int diff = min-48;
        for (int i = 0; i < arr.length; i++) {
            arr[i] -= diff ;
//            System.out.println("new char is " + arr[i]);
        }
        return new String(arr);
    }

    public static void main(String[] args) {
//        System.out.println('a'-'a' - 49);
        new PalindromePartitioning().partition("def").forEach(System.out::println);
    }
}
