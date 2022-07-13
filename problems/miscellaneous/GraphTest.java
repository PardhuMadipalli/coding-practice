package miscellaneous;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    private static final String FORMAT = "%d/%d";
    public static String storyOfATree(int n, List<List<Integer>> edges, int k, List<List<Integer>> guesses) {
        if(k>n-1)
            simplify(0, n);

        for(List<Integer> edge: edges){
            edge.set(0, edge.get(0)-1);
            edge.set(1, edge.get(1)-1);
        }

        for(List<Integer> edge: guesses){
            edge.set(0, edge.get(0)-1);
            edge.set(1, edge.get(1)-1);
        }

        List<List<Integer>> adjList = edgesListToAdjacencyList(n, edges);


        int res = 0;
        int[] matches = new int[n];
        Arrays.fill(matches, -1);

        Map<Integer, List<Integer>> guessmap = new HashMap<>();
        for(List<Integer> g: guesses) {
            if(!guessmap.containsKey(g.get(1))) {
                List<Integer> list = new ArrayList<>();
                list.add(g.get(0));
                guessmap.put(g.get(1), list);
            } else {
                guessmap.get(g.get(1)).add(g.get(0));
            }
        }

        int nummatch = checkifkcorrect(0, adjList, guessmap, k);
        matches[0] = nummatch;
//        System.out.printf("n is %d. K is %d. First nummatch is %d.\n", n, k, nummatch);

        dfsmain(0, matches, adjList, guessmap, n, k);

        for(int a: matches) {
            if(a >= k){
                res++;
            }
        }

        String ans = simplify(res, n);
        return ans;

    }

    public static void dfsmain(int root, int[] matches, List<List<Integer>> adjList, Map<Integer, List<Integer>> guessmap, int n, int k){
        List<Integer> neighs = adjList.get(root);

        for(int neigh: neighs) {
            // consider each neigh as root. When you do it, only difference for this neigh is that
            // This will be 1 less than root value, if guesses has root(pa)->neigh(chi).
            // This is because it is the opposite relation now.

            // Similarly it is 1 more than root, if guesses have neigh(pa) -> root(chi)
            if(matches[neigh] == -1) {
                matches[neigh] = matches[root];
                if(guesscontain(root, neigh, guessmap)) {
                    matches[neigh]--;
                }
                if(guesscontain(neigh, root, guessmap)){
                    matches[neigh]++;
                }
                dfsmain(neigh, matches, adjList, guessmap, n, k);
            }
        }
    }

    public static boolean guesscontain(int parent, int child, Map<Integer, List<Integer>> guessmap){
        if(!guessmap.containsKey(child)) {
            return false;
        } else {
            return guessmap.get(child).contains(parent);
        }
    }

    public static String simplify(int num, int denom) {
        if (num == 0) {
            return String.format(FORMAT, 0, 1);
        }
        if( num == denom) {
            return String.format(FORMAT, 1, 1);
        }
        int gcd = gcd(num, denom);
        return String.format(FORMAT, num/gcd, denom/gcd);
    }

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static int checkifkcorrect(int root, List<List<Integer>> adjList, Map<Integer,List<Integer>> guessmap, int k) {
        boolean[] visited = new boolean[adjList.size()];
        List<List<Integer>> corrGuesses = new ArrayList<>();
        dfs(root, visited, adjList, guessmap, corrGuesses, k);
        return corrGuesses.size();
    }

    public static void dfs(int root, boolean[] visited, List<List<Integer>> adjList, Map<Integer,List<Integer>> guessmap, List<List<Integer>> corrGuesses, int k){

        visited[root] = true;
        List<Integer> neighs = adjList.get(root);
        for(int neigh: neighs){
            if(!visited[neigh]) {
                if(guesscontain(root, neigh, guessmap)) {
                    List<Integer> li = new ArrayList<>();
                    li.add(root);
                    li.add(neigh);
                    corrGuesses.add(li);
                }

                dfs(neigh, visited, adjList, guessmap, corrGuesses, k);
            }
        }
    }

    public static List<List<Integer>> edgesListToAdjacencyList(int n, List<List<Integer>> edgesList){
        final ArrayList<List<Integer>> adjList = new ArrayList<>();
        IntStream.range(0, n).forEach(i -> adjList.add(new ArrayList<>()));

        for(List<Integer> edge: edgesList) {
            adjList.get(edge.get(0)).add(edge.get(1));
            adjList.get(edge.get(1)).add(edge.get(0));
        }
        return adjList;
    }
}

public class GraphTest {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader("miscellaneous/GraphTestInput.txt"));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<List<Integer>> edges = new ArrayList<>();

                IntStream.range(0, n - 1).forEach(i -> {
                    try {
                        edges.add(
                                Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                        .map(Integer::parseInt)
                                        .collect(toList())
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int g = Integer.parseInt(firstMultipleInput[0]);

                int k = Integer.parseInt(firstMultipleInput[1]);

                List<List<Integer>> guesses = new ArrayList<>();

                IntStream.range(0, g).forEach(i -> {
                    try {
                        guesses.add(
                                Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                        .map(Integer::parseInt)
                                        .collect(toList())
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                String result = Result.storyOfATree(n, edges, k, guesses);

                System.out.println(result);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
    }
}
