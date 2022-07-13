package problems.miscellaneous;

public class LargestMagicSquare {
    int[][][] gridSums;
    int rows, cols;
    public int largestMagicSquare(int[][] grid) {
        // Store 4 items:
        //
        // left cumulative sum,
        // top cum sum,
        // top-left to right-bottom diagonal cum sum
        // top-right to left-bottom diagonal cum sum
        rows = grid.length;
        cols = grid[0].length;

        // Add extra row at top and two extra cols one to the left and one to the right of existing grid
        gridSums = new int[rows+1][cols+2][4];

        // left cum sum
        for (int i = 0; i < rows; i++) {
            for(int j=0; j < cols; j++) {
                if(j==0) {
                    gridSums[i+1][j+1][0] = grid[i][j];
                }
                else
                    gridSums[i+1][j+1][0] = gridSums[i+1][j][0] + grid[i][j];

                if(i==0) {
                    gridSums[i+1][j+1][1] = grid[i][j];
                }
                else {
                    gridSums[i+1][j+1][1] = gridSums[i][j+1][1] + grid[i][j];
                }

                if(i==0 || j==0){
                    gridSums[i+1][j+1][2] = grid[i][j];
                } else {
                    gridSums[i+1][j+1][2] = gridSums[i][j][2] + grid[i][j];
                }

                if(i==0 || j== cols-1) {
                    gridSums[i+1][j+1][3] = grid[i][j];
                } else {
                    gridSums[i+1][j+1][3] = gridSums[i][j+2][3] +   grid[i][j];
                }
            }
        }

        for(int k = Math.min(rows, cols); k>1; k--) {
            for (int i = rows; i >= k; i--) {
                for (int j = cols; j >= k; j--) {
                    if(checkIfSquare(i,j,k))
                        return k;
                }
            }
        }

        return 1;
    }

    private boolean checkIfSquare(int bottom, int right, int k) {
//      System.out.printf("checking square for %d and %d with %d\n", bottom, right, k);
//      System.out.println(Arrays.toString(newGridSums[bottom][right]));
        int diff = gridSums[bottom][right][0] - gridSums[bottom][right-k][0];
        for (int i = bottom-1; i > bottom - k  ; i--) {
            if(diff != gridSums[i][right][0] - gridSums[i][right-k][0])
                return false;
        }
        for (int j = right; j > right - k  ; j--) {
            if(diff != gridSums[bottom][j][1] - gridSums[bottom-k][j][1])
                return false;
        }
        if(diff !=  gridSums[bottom][right][2] - gridSums[bottom-k][right-k][2])
            return false;

        return diff == gridSums[bottom][right-k+1][3] - gridSums[bottom-k][right+1][3];
    }

    public static void main(String[] args) {
        int[][] grid = {{7,1,4,5,6},{2,5,1,6,4},{1,5,4,3,2},{1,2,7,3,4}};
//        int[][] grid = {{5,1,3,1},{9,3,3,1},{1,3,3,8}};
        System.out.println(new LargestMagicSquare().largestMagicSquare(grid));
    }
}
