package problems;

import java.util.Arrays;
import java.util.List;

public class ArrayCursor {
    public int arrayCursor(List<Integer> path, int k){
        if(path.size()==1) return path.get(0);
        int maxsum = path.get(0);
        for(int i=0; i<path.size()-1;){
            int maxel = Integer.MIN_VALUE;
            int maxindex = 0;
//            System.out.printf("searcing from %d and %d\n", i+1, i+k);
            for(int j=i+1; j<=i+k && j<path.size(); j++){
               if(path.get(j) > maxel){
                   maxel = path.get(j);
                   maxindex = j;
               }
            }
            maxsum += maxel;
            i=maxindex;
//            System.out.println(i);
        }
        return maxsum;
    }

    public int arrayCursorQ(List<Integer> path, int k){
        if(path.size()==1) return path.get(0);
        int maxsum = path.get(0);
        for(int i=0; i<path.size()-1;){
            int maxel = Integer.MIN_VALUE;
            int maxindex = 0;
//            System.out.printf("searcing from %d and %d\n", i+1, i+k);
            for(int j=i+1; j<=i+k && j<path.size(); j++){
                if(path.get(j) > maxel){
                    maxel = path.get(j);
                    maxindex = j;
                }
            }
            maxsum += maxel;
            i=maxindex;
//            System.out.println(i);
        }
        return maxsum;
    }

    static class Pair{
        int index;
        int val;

        Pair(int i, int v){
            index = i;
            val = v;
        }
    }


    public static void main(String[] args){
        Integer[] input = new Integer[]{3, -4, -3, -5, 0};
        System.out.println(new ArrayCursor().arrayCursor(Arrays.asList(input), 2));
    }
}
