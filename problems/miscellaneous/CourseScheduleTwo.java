package problems.miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseScheduleTwo {
    private int done = 0;
    public int[] findOrder(int numCourses, int[][] prereqs) {
        List<List<Integer>> adjacency = getAdjacency(numCourses, prereqs);
        Set<Integer> visi = new HashSet<>(), comple = new HashSet<>();
        int[] arr = new int[numCourses];
        for (int i=0; i<numCourses; i++){
            if(!solve(i, adjacency, arr, visi, comple)) return new int[0];
        }
        return arr;
    }

    private boolean solve(int i, List<List<Integer>> adjacency, int[] answer, Set<Integer> visi, Set<Integer> comple){
        if(visi.contains(i)) return comple.contains(i);
        visi.add(i);
        for(int dependency: adjacency.get(i)) {
            if(!solve(dependency, adjacency, answer, visi, comple))
                return false;
        }
        answer[done++] = i;
        return comple.add(i);
    }

    private List<List<Integer>> getAdjacency(int n, int[][] edges) {
        List<List<Integer>> lists = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            lists.add(new ArrayList<>());
        }
        for(int[] edge: edges){
            lists.get(edge[0]).add(edge[1]);
        }
        return lists;
    }

    public static void main(String[] args) {
        int[][] input = new int[1][];
        input[0] = new int[]{1,0};
        System.out.println(Arrays.toString(new CourseScheduleTwo().findOrder(2, input)));
    }
}
