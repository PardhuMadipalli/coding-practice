package miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class IPO {
        public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
            final int n=profits.length;
            PriorityQueue<Integer> q = new PriorityQueue<>((Integer a, Integer b) ->
                    capital[a]!=capital[b] ? Integer.compare(capital[a], capital[b]) : Integer.compare(profits[b], profits[a]));

            for(int i=0; i<n; i++){
                q.add(i);
            }

            PriorityQueue<Integer> rem = new PriorityQueue<>(Comparator.comparingInt((Integer a) -> profits[a]).reversed());

            Integer curr;
            while(!q.isEmpty() && k>0) {
                for(curr = q.peek(); !q.isEmpty() && w>=capital[curr];) {
                    rem.add(curr);
                    q.remove();
                    curr = q.peek();
                }
                if(!rem.isEmpty()) {
                    w+=profits[rem.poll()];
                    k--;
                } else {
                    return w;
                }
            }

            while(!rem.isEmpty() && k>0) {
                w+=profits[rem.poll()];
                k--;
            }
            return w;
        }

        public static void main(String[] args){
            new IPO().findMaximizedCapital(2, 0, new int[]{1,2,3}, new int[]{0,1,1});
        }
}
