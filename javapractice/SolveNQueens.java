package javapractice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolveNQueens {
    private List<List<String>> answer;
    public List<List<String>> solveNQueens(int n) {
        List<int[]> existing = new ArrayList<>();
        answer = new ArrayList<>();
        solve(0, n, n, n, existing);
        return answer;
    }

    private void solve(int start, int end, int rem, final int n, List<int[]> existing) {
        if(rem==0) {
            answer.add(process(existing, n));
//            partAns.forEach(System.out::println);
//            existing.forEach(ints -> System.out.println(Arrays.toString(ints)));
//            System.out.println("-------");
            return;
        }

        for (int i = start; i < end; i++) {
            int row = i/n;
            int col = i%n;
            if(isvalid(row, col, existing)) {
                existing.add(new int[]{row, col});
                // next Q must be only in the next row. So start and end are calculated as follows.
                solve(((row+1)*n), (row+2)*n, rem-1, n, existing);
                existing.remove(existing.size()-1);
            }
        }
    }

    private boolean isvalid(int row, int col, List<int[]> existing) {
        for(int[] existElem : existing) {
            int exiRow = existElem[0];
            int exiCol = existElem[1];
            if(col == exiCol || row-exiRow == Math.abs(col-exiCol)) {
                return false;
            }
        }
        return true;
    }

    /* =================================================================================

    Interestingly stream based method is slower than for loop based isValid method. So commented this code.

    private boolean isvalid(int row, int col, List<int[]> existing) {
        return existing.stream().noneMatch(exis -> col == exis[1] || row - exis[0] == Math.abs(col - exis[1]));
    }

    ====================================================================================== */

    private List<String> process(List<int[]> existing, final int n) {
        List<String> list = new ArrayList<>();
        for(int[] nums : existing) {
            char[] carray = new char[n];
            Arrays.fill(carray, '.');
            carray[nums[1]] = 'Q';
            list.add(new String(carray));
        }
        return list;
    }

    public static void main(String[] args){
        System.out.println(new SolveNQueens().solveNQueens(7).size());
    }
}
