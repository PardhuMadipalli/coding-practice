//import java.io.*;
//import java.math.*;
//import java.security.*;
//import java.text.*;
//import java.util.*;
//import java.util.concurrent.*;
//import java.util.function.*;
//import java.util.regex.*;
//import java.util.stream.*;
//import static java.util.stream.Collectors.joining;
//import static java.util.stream.Collectors.toList;
//
//class Result {
//
//    /*
//     * Complete the 'bfs' function below.
//     *
//     * The function is expected to return an INTEGER_ARRAY.
//     * The function accepts following parameters:
//     *  1. INTEGER n
//     *  2. INTEGER m
//     *  3. 2D_INTEGER_ARRAY edges
//     *  4. INTEGER s
//     */
//
//    public static List<Integer> bfs(int n, int m, List<List<Integer>> edges, int s) {
//        // Write your code here
//        s=s-1;
//        List<Set<Integer>> edgesFrom = new ArrayList<>();
//        for (int i=0; i<n; i++) {
//            edgesFrom.add(new HashSet<>());
//        }
//        for (List<Integer> edge: edges) {
//            edgesFrom.get(edge.get(0)-1).add(edge.get(1)-1);
//            edgesFrom.get(edge.get(1)-1).add(edge.get(0)-1);
//        }
//
////        for (Set<Integer> set : edgesFrom) {
////            System.out.println(set);
////        }
//
//        LinkedList<Integer> queue = new LinkedList<>();
//        boolean[] visited = new boolean[n];
//        List<Integer> dist = new ArrayList<>();
//        for(int i=0; i<n; i++) {
//            dist.add(-1);
//        }
//        dist.set(s, 0);
//        visited[s]=true;
//        queue.add(s);
//        while(!queue.isEmpty()) {
//            int source = queue.poll();
//            for(int connection : edgesFrom.get(source)) {
//                if(!visited[connection]) {
//                    queue.add(connection);
//                    visited[connection]=true;
//                    int finalDist = dist.get(source)+1;
//                    dist.set(connection, finalDist);
////                    System.out.println("setting dist of " + connection + " from " + s + " to " + finalDist);
//                }
//            }
//        }
//
//        // Remove the distance of the s element
//        dist.remove(s);
//
////         Multiply all distances by 6 except -1
//            for (int i=0; i<n-1; i++) {
//                int currdist = dist.get(i);
//                if(currdist!=-1) {
//                    dist.set(i, currdist*6);
//                }
//            }
//
//        return dist;
//
//    }
//
//}
//
//public class Solution {
//    public static void main(String[] args) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
//
//        int q = Integer.parseInt(bufferedReader.readLine().trim());
//
//        IntStream.range(0, q).forEach(qItr -> {
//            try {
//                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
//
//                int n = Integer.parseInt(firstMultipleInput[0]);
//
//                int m = Integer.parseInt(firstMultipleInput[1]);
//
//                List<List<Integer>> edges = new ArrayList<>();
//
//                IntStream.range(0, m).forEach(i -> {
//                    try {
//                        edges.add(
//                                Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
//                                        .map(Integer::parseInt)
//                                        .collect(toList())
//                        );
//                    } catch (IOException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                });
//
//                int s = Integer.parseInt(bufferedReader.readLine().trim());
//
//                List<Integer> result = Result.bfs(n, m, edges, s);
//
//                bufferedWriter.write(
//                        result.stream()
//                                .map(Object::toString)
//                                .collect(joining(" "))
//                                + "\n"
//                );
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        });
//
//        bufferedReader.close();
//        bufferedWriter.close();
//    }
//}
