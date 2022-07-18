package problems.miscellaneous;

import java.util.Comparator;
import java.util.PriorityQueue;

// https://leetcode.com/problems/ipo/

public class IPO {
        public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
            final int n=profits.length;
            PriorityQueue<Integer> q = new PriorityQueue<>((Integer a, Integer b) ->
                    capital[a]!=capital[b] ? Integer.compare(capital[a], capital[b]) : Integer.compare(profits[b], profits[a]));

            for(int i=0; i<n; i++){
                q.add(i);
            }

            PriorityQueue<Integer> rem = new PriorityQueue<>(Comparator.comparingInt((Integer a) -> profits[a]).reversed());

            while((!q.isEmpty() || !rem.isEmpty()) && k>0) {

                // Loop over q elements as long as capital can be afforded by w./
                // Each time add corresponding profit to "rem" queue
                while (!q.isEmpty() && w>=capital[q.peek()]) {
                    rem.add(q.remove());
                }

                // Out of the possible profits, choose maximum profit.
                // Do not run a loop here, in the next outer iterator, we may have a higher profitable project
                if(!rem.isEmpty()) {
                    w+=profits[rem.poll()];
                    k--;
                } else { // If no further profit can be earned, return.
                    return w;
                }
            }
            return w;
        }

        public static void main(String[] args){
            System.out.println(new IPO().findMaximizedCapital(2, 0, new int[]{1,2,3}, new int[]{0,1,1}));
        }
}
