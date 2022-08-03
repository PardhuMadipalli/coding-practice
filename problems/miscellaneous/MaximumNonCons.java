package problems.miscellaneous;

import java.util.PriorityQueue;

// Find sum of non-consecutive maximum days, where you can pick the projects.
public class MaximumNonCons {

    // Using queue; TC : O(sum * log(n))

    public int getMax(int[] arr){

        // Max heap
        PriorityQueue<Town> queue = new PriorityQueue<>((a, b) -> b.count - a.count);
        for(int i=0; i<arr.length; i++){
            queue.add(new Town(i, arr[i]));
        }

        // Always choose the Town with highest number of counts left.
        // make sure you don't take peek() if it has same index as previous.

        if(queue.isEmpty() || queue.peek().count==0) return 0;
        int prev = -1;
        int count = 0;
        while(queue.peek().count!=0) {
            Town addlater = null;
            if(prev == queue.peek().index) {
                addlater = queue.poll();
            }
            if(queue.isEmpty() || queue.peek().count == 0)
                return count;

            count++;
            queue.peek().count--;
            prev = queue.peek().index;
            queue.add(queue.poll());

            if(addlater != null) queue.add(addlater);
        }

        return count;
    }


    private static class Town {
        int index;
        int count;

        Town(int i, int c){
            index=i;
            count=c;
        }
    }

    ////// other solution - using recursion

    private static int count2 = 0;


    private static void getMaxHelper(int[] arr, int prev){
        int n = arr.length, maxpos=0;
        int first = 0;
        for (int i = 0; i < n; i++) {
            if(i==prev) continue;

            if(first == 0) {
                first = 1;
                maxpos = i;
            } else if (arr[i]> arr[maxpos]) {
                maxpos = i;
            }
        }

        if(first != 0 && arr[maxpos] != 0) {
            count2++;
            arr[maxpos]--;
            prev = maxpos;
            getMaxHelper(arr, prev);
        }
    }

    // Using recursion; TC: O(sum * n)
    public static int getMaxUsingRecursion(int[] arr){
        getMaxHelper(arr, -1);
        return count2;
    }



    public static void main(String[] a){
        int[] input = new int[]{7, 2, 3};
        // If you first do index 0, then you must choose index 1 or 2.
        // proceed until there is nothing else left
        System.out.println(new MaximumNonCons().getMax(input));
        System.out.println(getMaxUsingRecursion(input));
    }
}
