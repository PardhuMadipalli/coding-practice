package problems.miscellaneous;

public class BiPartiteGraph {
    int[][] graph;
    int[] colours;
    public boolean isBipartite(int[][] gr) {
        graph = gr;
        colours = new int[graph.length];
        for(int i=0; i< graph.length; i++) {
            if(colours[i] == 0) {
                colours[i] = 1;
                if(notBiPartite(i)) return false;
            }
        }
        return true;
    }

    private boolean notBiPartite(int i) {
        for(int neigh: graph[i]){
            if(colours[neigh] == colours[i])
                return true;

            if(colours[neigh] == 0) {
                colours[neigh] = -colours[i];
                if(notBiPartite(neigh))
                    return true;
            }
        }
        return false;
    }
}
