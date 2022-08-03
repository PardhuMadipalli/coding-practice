package problems.miscellaneous;

import java.util.Arrays;

public class GasStation {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int[] fromZero = new int[gas.length];

//        fromZero[0] = gas[cost.length-1] - cost[cost.length-1];
        int maxindex = 0, maxval = -1, sum = 0;
        for (int i = 0; i < fromZero.length; i++) {
            fromZero[i] = gas[i] -  cost[i];
            sum+=fromZero[i];
            if(fromZero[i] > maxval) {
                maxindex = i;
                maxval = fromZero[i];
            }
        }
        if(sum <0) return -1;

        System.out.println(Arrays.toString(fromZero));
        System.out.println(Arrays.toString(gas));
        System.out.println(Arrays.toString(cost));
        for(int i=0; i< fromZero.length; i++) {
            if(fromZero[i] >=0){
                int currsum = fromZero[i];
                int j=i;
                boolean answerfound = true;
                int nextval = nextval(j, fromZero.length);
                while(nextval != i) {
//                    System.out.println(currsum);
                    currsum += fromZero[nextval];
//                    System.out.println("after inc " + currsum);
                    if(currsum < 0) {
                        answerfound = false;
                        break;
                    }
                    j=nextval;
                    nextval = nextval(j, fromZero.length);
                }
                if(answerfound) return i;
            }
        }
        return maxindex;
    }

    private int nextval(int j, int len) {
        if(j==len-1) return 0;
        else return j+1;
    }

    public static void main(String[] args) {
        // 4, -5, 2, -1, 1, -1,
        System.out.println(new GasStation().canCompleteCircuit(new int[]{6, 2, 3, 2, 4, 3}, new int[]{2, 7, 1, 3, 3, 4}));
        System.out.println(new GasStation().canCompleteCircuit(new int[]{5,8,2,8}, new int[]{6,5,6,6}));
        System.out.println(new GasStation().canCompleteCircuit(new int[]{1,2,4,3,5}, new int[]{3,4,2,5,1}));
        System.out.println(new GasStation().canCompleteCircuit(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2}));
    }
}
