package problems.miscellaneous;

import java.util.ArrayList;
import java.util.List;

// Asked in Byju's interview by InterviewVector (taken from Leetcode).

// Here we are checking if there is a cycle or not by seeing if the inStack is true 
// for any node while the node is being visted.
public class CanFinishCourses {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adjacencyLists = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adjacencyLists.add(new ArrayList<>());
        }

        for(int[] prereq: prerequisites) {
            adjacencyLists.get(prereq[0]).add(prereq[1]);
        }

        boolean[] visited = new boolean[numCourses], inStack = new boolean[numCourses];

        for(int i=0; i<numCourses; i++)
            if(!visited[i])
                if(!dfs(adjacencyLists, visited, inStack, i))
                    return false;

        return true;
    }

    private boolean dfs(List<List<Integer>> adjacencyLists,  boolean[] visited, boolean[] inStack, int startPoint) {
        if(inStack[startPoint])
            return false;
        if(visited[startPoint])
            return true;

        inStack[startPoint] = true;

        for(int dependency: adjacencyLists.get(startPoint)) {
           if(!dfs(adjacencyLists, visited, inStack, dependency))
               return false;
        }

        inStack[startPoint] = false;
        visited[startPoint] = true;

        return true;
    }


}
