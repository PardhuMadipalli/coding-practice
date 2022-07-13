package miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class MinCostToDestInTime {
    int[][] e;
    int[] p;
    int n;
//    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
//        e = edges;
//        p = passingFees;
//        for(int[] arr: edges)
//            Arrays.sort(arr);
//
//        return min(maxTime, n-1, p[n-1]);
//    };
//
//    int min(int remtime, int dest, int feeTillDest) {
//        int destIndex = Arrays.binarySearch(e[0], dest);
//        if(destIndex >= 0 && ) {
//
//        }
//    }

    public List<List<Integer>> edgesListToAdjacencyList(int n, List<List<Integer>> edgesList){
        final ArrayList<List<Integer>> adjList = new ArrayList<>();
        IntStream.range(0, n).forEach(i -> adjList.add(new ArrayList<>()));

        for(List<Integer> edge: edgesList) {
            adjList.get(edge.get(0)).add(edge.get(1));
            adjList.get(edge.get(1)).add(edge.get(0));
        }
        return adjList;
    }
}
