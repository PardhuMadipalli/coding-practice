package problems.miscellaneous;

import java.util.HashMap;
import java.util.Map;

public class MinimumConsecutiveCardsToPickUp {
    public int minimumCardPickup(int[] cards) {
        Map<Integer, Integer> map = new HashMap<>(cards.length);
        int answer = Integer.MAX_VALUE;
        for(int i=0; i<cards.length; i++) {
            Integer prevLoc;
            if((prevLoc = map.get(cards[i])) != null) {
                answer = Math.min(i-prevLoc, answer);
            }
            map.put(cards[i], i);
        }
        return answer == Integer.MAX_VALUE ? -1 : answer+1;
    }

    public static void main(String... args) {
        int[] input = {3,4,5,3,2,9,2}; // answer should be 3
        System.out.println(new MinimumConsecutiveCardsToPickUp().minimumCardPickup(input));
    }
}
