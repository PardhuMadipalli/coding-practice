package problems.backtracking;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * https://leetcode.com/problems/word-ladder-ii/?envType=problem-list-v2
 */
public class WordLadderII {

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        HashSet<String> words = new HashSet<>(wordList);
        Map<String, Set<String>> neighbours = new HashMap<>();

        // start bfs
        // process so that we find all shortest possible parent map until end word
        // In normal BFS, we come out of loop immediately after finding the desired word
        // Here we will exit loop only after processing all elements in level before the endword, so that we note down all parents reaching endWord.
        Queue<String> queue = new ArrayDeque<>();
        queue.add(beginWord);
        words.remove(beginWord);
        boolean foundEndWord = false;
        Set<String> nextLevel = new HashSet<>();
        while(!queue.isEmpty()) {
            String curr = queue.remove();
            for (String next: words) {
                if (isNeighbour(next, curr)) {
                    neighbours.computeIfAbsent(next,  a -> new HashSet<>()).add(curr);
                    if (next.equals(endWord)) {
                        foundEndWord = true;
                        break; // if we found the endWord, we don't care about other words in this last level
                    }
                    nextLevel.add(next);
                }
            }

            if (queue.isEmpty()) {
                // if we finish processing current level. Queue will be empty only when one level is processed.
                if (foundEndWord) break; // break out of while loop

                queue.addAll(nextLevel);
                words.removeAll(nextLevel);
                nextLevel.clear();
            }
        }

        List<List<String>> ans = new ArrayList<>();
        if (!foundEndWord) return ans;

        List<String> path = new ArrayList<>();
        path.add(endWord);
        findPath(endWord, beginWord, neighbours, ans, path);
        return ans;
    }

    /**
     * Using DFS and backtracking
     */
    private void findPath(String endWord, String beginWord, Map<String, Set<String>> graph,
                          List<List<String>> ans, List<String> path) {
        Set<String> nextStrings = graph.get(endWord);
        if (nextStrings == null) return;
        for (String word : nextStrings) {
            path.add(word);
            if (beginWord.equals(word)) {
                List<String> shortestPath = new ArrayList<>(path);
                Collections.reverse(shortestPath); // reverse words in shortest path
                ans.add(shortestPath); // add the shortest path to ans.
            } else {
                findPath(word, beginWord, graph, ans, path);
            }
            path.remove(word);
        }
    }

    private boolean isNeighbour(String a, String b) {
        if (a.length() != b.length()) return false;
        int diff = 0;
        for (int i=0; i<a.length(); i++) {
            if (a.charAt(i) != b.charAt(i))  {
                if (++diff > 1) return false;
            };
        }
        return diff == 1;
    }

    public static void main(String[] args) {
        String[] words = new String[]{"hot","dot","dog","lot","log","cog"};
        System.out.println(new WordLadderII().findLadders("hit", "cog", Arrays.asList(words)));
    }
}
