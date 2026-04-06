package problems;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

public class Alien_Dictionary {

    public static void main(String[] args) {
        Alien_Dictionary out = new Alien_Dictionary();
        Solution s = out.new Solution();
        Solution_bfs s2 = out.new Solution_bfs();

        String[] words = new String[] {
                "wrt",
                "wrf",
                "t",
                "e",
                "f"
        };

        System.out.println(s.alienOrder(words));
        System.out.println(s2.alienOrder(words));
    }


    // ref: http://buttercola.blogspot.com/2015/09/leetcode-alien-dictionary.html
    public class Solution_bfs {
        /**
         * @param words: a list of words
         * @return: a string which is correct order
         */
        public String alienOrder(String[] words) {
            // Write your code here
            if (words == null || words.length == 0) {
                return "";
            }

            // step 1: construct the graph
            //
            Map<Character, List<Character>> adjMap = new HashMap<>();
            constructGraph(words, adjMap);

            int numNodes = adjMap.size();

            StringBuilder result = new StringBuilder();

            // toplogical sorting
            //
            Map<Character, Integer> indegreeMap = new HashMap<>();
            for (Character node : adjMap.keySet()) {
                indegreeMap.put(node, 0);
            }

            for (Character node : adjMap.keySet()) {
                for (Character neighbor : adjMap.get(node)) {
                    int indegree = indegreeMap.get(neighbor);
                    indegree += 1;
                    indegreeMap.put(neighbor, indegree);
                }
            }

            // start from indegree=0
            Queue<Character> queue = new PriorityQueue<>();
            for (Character node : indegreeMap.keySet()) {
                if (indegreeMap.get(node) == 0) {
                    // starting node, can only be one, cannot be 2 starting with 0 indegree
                    queue.offer(node);
                }
            }

            while (!queue.isEmpty()) {
                char curNode = queue.poll();
                result.append(curNode);

                for (char neighbor : adjMap.get(curNode)) {
                    int indegree = indegreeMap.get(neighbor);
                    indegree -= 1;
                    indegreeMap.put(neighbor, indegree);

                    // @note: key is here.
                    // for A->B, B->C, A-C: C will not be counted until its indgree is 0

                    if (indegree == 0) {
                        queue.offer(neighbor);
                    }
                }
            }

            if (result.length() < numNodes) {
                return "";
            }

            return result.toString();
        }

        private void constructGraph(String[] words, Map<Character, List<Character>> adjMap) {
            // construct nodes
            for (String word : words) {
                for (Character c : word.toCharArray()) {
                    adjMap.put(c, new ArrayList<>()); // c to all its next
                }
            }

            // construct edges
            for (int i = 1; i < words.length; i++) {
                String prev = words[i - 1];
                String curr = words[i];

                for (int j = 0; j < prev.length() && j < curr.length(); j++) {
                    if (prev.charAt(j) != curr.charAt(j)) {
                        adjMap.get(prev.charAt(j)).add(curr.charAt(j));
                        break;
                    }
                }
            }
        }
    }


    public class Solution {
        // dfs
        public String alienOrder(String[] words) {

            // Step 1: build the graph
            Map<Character, Set<Character>> graph = new HashMap<>();
            for (int i = 0; i < words.length; i++) {
                String currentWord = words[i];
                for (int j = 0; j < currentWord.length(); j++) {
                    if (!graph.containsKey(currentWord.charAt(j))) {
                        graph.put(currentWord.charAt(j), new HashSet<>());
                    }
                }

                if (i > 0) {
                    connectGraph(graph, words[i - 1], currentWord);
                }
            }

            // Step 2: topological sorting
            StringBuffer sb = new StringBuffer();
            Map<Character, Integer> visited = new HashMap<Character, Integer>(); // mark as visited: visited.put(vertexId, -1);

            for (Map.Entry<Character, Set<Character>> entry: graph.entrySet()) {
                char vertexId = entry.getKey();
                if (!topologicalSort(vertexId, graph, sb, visited)) {
                    return "";
                }
            }

            return sb.toString();
        }

        private void connectGraph(Map<Character, Set<Character>> graph, String prev, String curr) {
            if (prev == null || curr == null) {
                return;
            }

            int len = Math.min(prev.length(), curr.length());

            for (int i = 0; i < len; i++) {
                char p = prev.charAt(i);
                char q = curr.charAt(i);
                if (p != q) { // so if same duplicated work, will not reach here and not update graph
                    if (!graph.get(p).contains(q)) {
                        graph.get(p).add(q);
                        System.out.printf("Added edge between %c -> %c\n", p, q);
                    }
                    break;
                }
            }
        }

        private boolean topologicalSort(
                char vertexId,
                Map<Character, Set<Character>> graph,
                StringBuffer sb,
                Map<Character, Integer> visited
        ) {

            if (visited.containsKey(vertexId)) {
                // visited
                if (visited.get(vertexId) == -1) { // -1 meaning visited, cycle found
                    return false;
                }

                // already in the list
                if (visited.get(vertexId) == 1) {
                    return true;
                }
            }

            visited.put(vertexId, -1); // mark as visited


            Set<Character> neighbors = graph.get(vertexId);
            for (char neighbor : neighbors) {
                if (!topologicalSort(neighbor, graph, sb, visited)) {
                    return false;
                }
            }

            sb.insert(0, vertexId);
            visited.put(vertexId, 1); // restore visited

            return true;
        }
    }
}

//////

class Solution {
    public String alienOrder(String[] words) {
        boolean[][] g = new boolean[26][26];
        boolean[] s = new boolean[26];
        int cnt = 0;
        int n = words.length;
        for (int i = 0; i < n - 1; ++i) {
            for (char c : words[i].toCharArray()) {
                if (cnt == 26) {
                    break;
                }
                c -= 'a';
                if (!s[c]) {
                    ++cnt;
                    s[c] = true;
                }
            }
            int m = words[i].length();
            for (int j = 0; j < m; ++j) {
                if (j >= words[i + 1].length()) {
                    return "";
                }
                char c1 = words[i].charAt(j), c2 = words[i + 1].charAt(j);
                if (c1 == c2) {
                    continue;
                }
                if (g[c2 - 'a'][c1 - 'a']) {
                    return "";
                }
                g[c1 - 'a'][c2 - 'a'] = true;
                break;
            }
        }
        for (char c : words[n - 1].toCharArray()) {
            if (cnt == 26) {
                break;
            }
            c -= 'a';
            if (!s[c]) {
                ++cnt;
                s[c] = true;
            }
        }

        int[] indegree = new int[26];
        for (int i = 0; i < 26; ++i) {
            for (int j = 0; j < 26; ++j) {
                if (i != j && s[i] && s[j] && g[i][j]) {
                    ++indegree[j];
                }
            }
        }
        Deque<Integer> q = new LinkedList<>();
        for (int i = 0; i < 26; ++i) {
            if (s[i] && indegree[i] == 0) {
                q.offerLast(i);
            }
        }
        StringBuilder ans = new StringBuilder();
        while (!q.isEmpty()) {
            int t = q.pollFirst();
            ans.append((char) (t + 'a'));
            for (int i = 0; i < 26; ++i) {
                if (i != t && s[i] && g[t][i]) {
                    if (--indegree[i] == 0) {
                        q.offerLast(i);
                    }
                }
            }
        }
        return ans.length() < cnt ? "" : ans.toString();
    }
}