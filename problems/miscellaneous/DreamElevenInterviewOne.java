package problems.miscellaneous;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DreamElevenInterviewOne {

    int[] findMax(int[] arr, final int k) {
        int[] answer = new int[arr.length-k+1];

        if(k==1) return arr;

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());

        for(int i=0; i<k; i++) {
            queue.add(arr[i]);
        }

        answer[0] = queue.peek();

        for(int i=1; i<answer.length; i++) {
            queue.remove(arr[i-1]);
            queue.add(arr[i+k-1]);
            answer[i] = queue.peek();
            queue.remove();
        }
        return answer;
    }

    public static void main(String... args) {
        int[] inputs = {1, 4, 7, 2, 6, 1, 9, 0};
        System.out.println(Arrays.toString(new DreamElevenInterviewOne().findMax(inputs, 3)));
    }

}
