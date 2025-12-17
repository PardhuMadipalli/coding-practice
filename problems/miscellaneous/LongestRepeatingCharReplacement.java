package problems.miscellaneous;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LongestRepeatingCharReplacement {
    int k;
    Map<Character, Integer> map;
    public int characterReplacement(String s, int k) {
        char[] chars = s.toCharArray();
        this.map = new HashMap<>();
        this.k=k;
        int maxLength = 0;
        int l=0, r=0;
        while(r < chars.length){
            char c = chars[r++];
            System.out.println("Adding char at " + (r-1));
            if(map.containsKey(c)){
                map.put(c, map.get(c)+1);
            } else map.put(c, 1);

            // if map already contains more than 2 chars or diff is more, then remove the l char
            while (map.size() > 2 || diffGreaterThanK()) {
                // remove left char
                System.out.println("Removing char at " + l);
                char lchar = chars[l++];
                if(map.get(lchar) > 1){
                    map.put(lchar, map.get(lchar)-1);
                } else {
                    map.remove(lchar);
                }
            }
            maxLength = Math.max(maxLength, r-l);
        }

        return  maxLength;
    }

    private int getMapTotalValue() {
        int sum=0;
        for (int val: map.values()) {
            sum+=val;
        }
        return sum;
    }

    private boolean diffGreaterThanK() {
        if (map.size() == 2) {
            List<Map.Entry<Character, Integer>> list = new ArrayList<>(map.entrySet());
            list.sort(Map.Entry.comparingByValue());
            return list.get(0).getValue() > k;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new LongestRepeatingCharReplacement().characterReplacement("KRSCDCSONAJNHLBMDQGIFCPEKPOHQIHLTDIQGEKLRLCQNBOHNDQGHJPNDQPERNFSSSRDEQLFPCCCARFMDLHADJADAGNNSBNCJQOF", 4));
    }
}
