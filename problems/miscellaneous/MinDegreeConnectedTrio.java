package problems.miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.IntStream;

public class MinDegreeConnectedTrio {
    public int minTrioDegree(int n, int[][] edges) {
        List<Set<Integer>> edgesSet = edgesListToAdjacencyList(n, edges);
        int min = Integer.MAX_VALUE;
        int ilen, jlen, klen;
        for(int i=0; i<n; i++) {
            ilen = edgesSet.get(i).size();
            if(ilen>=2 && ilen<min) {
                for(int j=i+1; j<n; j++) {
                    jlen = edgesSet.get(j).size() + ilen;
                    if(jlen>=4 && jlen<min && edgesSet.get(i).contains(j)) {
                        for(int k=j+1; k<n; k++) {
                            klen = jlen+edgesSet.get(k).size();
                            Set<Integer> kset = edgesSet.get(k);
                            if(klen>=6 && klen<min && kset.contains(i) && kset.contains(j)) {
                                min=klen;
                            }
                        }
                    }
                }
            }
        }

        if(min==Integer.MAX_VALUE)
            return -1;
        return min-6;
    }

    public List<Set<Integer>> edgesListToAdjacencyList(int n, int[][] edgesList){
        final ArrayList<Set<Integer>> adjList = new ArrayList<>();
        IntStream.range(0, n).forEach(i -> adjList.add(new HashSet<>()));

        for(int[] edge: edgesList) {
            adjList.get(edge[0]-1).add(edge[1]-1);
            adjList.get(edge[1]-1).add(edge[0]-1);
        }
        return adjList;
    }

    public static void main(String... args) {
        int n = 6;
        int[][] edges = {{1,2},{1,3},{3,2},{4,1},{5,2},{3,6}};

        int n2 = 7;
        int[][] edges2 = {{1,3},{4,1},{4,3},{2,5},{5,6},{6,7},{7,5},{2,6}};
        System.out.println(new MinDegreeConnectedTrio().minTrioDegree(n, edges));
        System.out.println(new MinDegreeConnectedTrio().minTrioDegree(n2, edges2));
    }
}
