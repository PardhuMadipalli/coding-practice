package problems.miscellaneous;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequentElements {
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map= new HashMap<>();

        for(int num: nums){
            map.merge(num, 1, Integer::sum);
        }

        PriorityQueue<ElemFreq> q = new PriorityQueue<>(Comparator.comparing(ElemFreq::getFreq, Comparator.reverseOrder()));
        for(Map.Entry<Integer, Integer> entry: map.entrySet()) {
            q.add(new ElemFreq(entry.getKey(), entry.getValue()));
        }
        int[] res = new int[k];
        for(int i=0; i<k; i++){
            res[i] = q.poll().num;
        }
        return res;
    }

    private static class ElemFreq {
        int num;
        int freq;

        ElemFreq(int num, int freq){
            this.num = num;
            this.freq = freq;
        }

        int getFreq() {return freq;}
    }
}
