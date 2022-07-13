package miscellaneous;

import java.time.Duration;
import java.time.Instant;

public class WordSearch {

    char[] wc;
    boolean[][] visi;
    char[][] b;
    int rows;
    int cols;
    int n;
    boolean result;
    public boolean exist(char[][] board, String word) {
        b=board;
        wc = word.toCharArray();
        rows = board.length;
        cols = board[0].length;
        visi = new boolean[rows][cols];
        n = wc.length;

        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++) {
                if(board[i][j] == wc[0]) {
                    visi[i][j] = true;
                    if(find(1, i, j))
                        return true;
                    visi[i][j] = false;
                }
            }
        }
        return false;
    }

    private boolean find(int k, int starti, int startj) {
        if(k >= n) return true;
        visi[starti][startj] = true;
        result = runnext(k, starti+1, startj) ||
                runnext(k, starti-1, startj) ||
                runnext(k, starti, startj+1) ||
                runnext(k, starti, startj-1);
        if(!result)
            visi[starti][startj] = false;
        return result;
    }

    private boolean runnext(int k, int i, int j) {
        if(isvalid(i, j) && b[i][j] == wc[k]) {
            return find(k + 1, i, j);
        }
        return false;
    }

    private boolean isvalid(int i, int j) {
        return i>=0 && i<rows && j>=0 && j<cols && !visi[i][j];
    }

    public static void main(String[] args){
        Instant start = Instant.now();
        for(int i=0; i<20000; i++){
            new WordSearch().exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}},
                    "ABCCED");
        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
    }
}