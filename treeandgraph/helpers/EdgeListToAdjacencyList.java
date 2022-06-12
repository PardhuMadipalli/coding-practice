package treeandgraph.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class EdgeListToAdjacencyList {

    /** For undirected edges
     *
     * @param n number of nodes
     * @param edgesList List of lists each containing 2 elements.
     */
    public List<List<Integer>> edgesListToAdjacencyList(int n, List<List<Integer>> edgesList){
        final ArrayList<List<Integer>> adjList = new ArrayList<>();
        IntStream.range(0, n).forEach(i -> adjList.add(new ArrayList<>()));

        for(List<Integer> edge: edgesList) {
            adjList.get(edge.get(0)).add(edge.get(1));
            adjList.get(edge.get(1)).add(edge.get(0));
        }
        return adjList;
    }

    public static void main(String[] args){
        Count a = new Count(5);
        int b = 10;
        incboth(a, b);
        System.out.println(a.c + " " + b);

    }

    public static void incboth(Count a, int b){
        a.c++;
        b++;
    }

    static class Count {
        Count(int c) {
            this.c = c;
        }
        int c;
    }
}
