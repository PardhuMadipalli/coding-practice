package miscellaneous;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class TrainProblem {
    public static void main(String[] args) {
        /*

        10 12 21  110 112 121

        20 18 29  120 118 129

         */
        System.out.println(Solution.findPlatform2(
                new int[]{10, 12, 21, 110, 112, 121, 210, 212, 221},
                new int[]{20, 18, 29,  120, 118, 129, 220, 218, 229}, 9));
    }
}

class Solution
{
    //Function to find the minimum number of platforms required at the
    //railway station such that no train waits.
    static int findPlatform1(int[] arr, int[] dep, int n)
    {
        Arrays.sort(arr);
        Arrays.sort(dep);

        int j = 0;
        int max = 1;
        for(int i = 1; i < n; i++){
            if(arr[i] > dep[j]){
                j++;
            }else{
                max++;
            }
        }
        return max;

    }

    static int findPlatform2(int arr[], int dep[], int n)
    {
        // add your code here
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for(int i=0; i<n; i++) {
            map.put(arr[i], dep[i]);
        }

        PriorityQueue<Integer> q = new PriorityQueue<>();

        Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();

        q.add(it.next().getValue());
        while(it.hasNext()){
            Map.Entry<Integer, Integer> ent = it.next();
            if(ent.getKey() > q.peek()) {
                q.poll();
            }
            q.add(ent.getValue());
        }
        return q.size();
    }

    static int findPlatform3(int arr[], int dep[], int n)
    {
        // add your code here
        ArrayList<pair> ar=new ArrayList<>();
        for(int i=0;i<n;i++){
            ar.add(new pair(arr[i],dep[i]));
        }
        // First we sort all the timings on the basis of arraival time
        // as we would be using priority queue to get the minimum departure time
        // from all the platforms as that would be the most beneficial move
        //(think in terms of max utilisation , we can max utilise a platform by
        // letting it free for the minimal time possible , So we would send the earliest
        // arriving train to the platform which has the least departure time of
        // previous train , If this platform does not exist we have to create one by adding
        // our current train's ending time in priority queue.
        // i hope i was able to give some idea of what i have done

        Collections.sort(ar,(aa, bb)->aa.a-bb.a);
        PriorityQueue<Integer> pq=new PriorityQueue<>();
        pq.add(ar.get(0).b);
        for(int i=1;i<ar.size();i++){
            if(pq.peek()<ar.get(i).a){
                pq.poll();
                pq.add(ar.get(i).b);
            }
            else pq.add(ar.get(i).b);
        }
        return pq.size();

    }

    public static class pair{
        int a,b;
        public pair(int a,int b){
            this.a=a;
            this.b=b;
        }
    }

}