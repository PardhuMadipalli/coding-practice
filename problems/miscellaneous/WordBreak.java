package problems.miscellaneous;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordBreak {
    public boolean wordBreak(String s, List<String> wordDict) {

        if (wordDict.size() > 20) wordDict.sort((a, b) -> Integer.compare(b.length(), a.length()));

        HashMap<String, Boolean> map = new HashMap<>();

        for(String word : wordDict) {
            map.put(word, true);
        }
        return solve(s, map, wordDict);
    }

    private boolean solve(String s, Map<String, Boolean> map, List<String> wordDict) {
        if (s.length() == 0) return true;
        Boolean isThere = map.get(s);
        if(isThere != null) return isThere;

        for(String word : wordDict) {
            if (s.startsWith(word) && solve(s.substring(word.length()), map, wordDict)) {
                map.put(s, Boolean.TRUE);
                return true;
            }
        }
        map.put(s, Boolean.FALSE);
        return false;
    }

    public static void main(String[] args) {
        String[] words = new String[] {"code", "leet"};

        System.out.println(new WordBreak().wordBreak("leetcode", Arrays.asList(words)));
    }
}
