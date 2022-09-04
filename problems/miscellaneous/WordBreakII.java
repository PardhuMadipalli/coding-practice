package problems.miscellaneous;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreakII {
    Set<String> wordDictSet;
    List<String> result;
    char[] sarr;
    public List<String> wordBreak(String s1, List<String> wordDict) {
        wordDictSet = new HashSet<>(wordDict);
        result = new ArrayList<>();
        sarr = s1.toCharArray();
        backtrack("", 0, "");
        return result;
    }

    void backtrack(String curr,
                   int numSpaces,
                   String tillLastSpace) {
        if(curr.length()- numSpaces == sarr.length) {
            if(wordDictSet.contains(tillLastSpace))
                result.add(curr);
            return;
        }
        if(wordDictSet.contains(tillLastSpace)) {
            backtrack(curr + ' ', numSpaces+1, "");
        }
        char newChar = sarr[curr.length() - numSpaces];
        backtrack(curr + newChar, numSpaces, tillLastSpace + newChar);
    }

    public static void main(String... args) {
        List<String> words = new ArrayList<>();
        String word = "catsanddog";
        words.add("cat");
        words.add("cats");
        words.add("and");
        words.add("sand");
        words.add("dog");
        System.out.println(new WordBreakII().wordBreak(word, words));

        List<String> words2 = new ArrayList<>();
        String word2 = "pineapplepenapple";
        words2.add("apple");
        words2.add("pen");
        words2.add("applepen");
        words2.add("pine");
        words2.add("pineapple");
        System.out.println(new WordBreakII().wordBreak(word2, words2));
    }
}
