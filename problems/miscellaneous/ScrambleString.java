package miscellaneous;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScrambleString {
    private static final Map<String, Boolean> scrambles = new HashMap<>();
    public boolean isScramble(String s1, String s2) {
//        System.out.printf("checking %s and %s\n", s1, s2);
        if(s1.equals(s2)) return true;
        final String combo = s1+"-"+s2;
        final String revCombo = s2 + "-" + s1;
        final int s1length = s1.length();
        if(scrambles.containsKey(combo)) return scrambles.get(combo);
        if(scrambles.containsKey(revCombo)) return scrambles.get(revCombo);
        if(!haveSameLetters(s1, s2)) {
            scrambles.put(combo, false);
            scrambles.put(revCombo, false);
            return false;
        }

        for(int i=1; i<=s1length-1; i++) {
            if((isScramble(s1.substring(0, i), s2.substring(0, i)) && isScramble(s1.substring(i), s2.substring(i))) ||
                    // swap case
                    (isScramble(s1.substring(0, s1length-i), s2.substring(i)) && isScramble(s1.substring(s1length-i), s2.substring(0, i)))) {
//                System.out.printf("Returning true for %s and %s\n", s1, s2);
                scrambles.put(combo, true);
                scrambles.put(revCombo, true);
                return true;
            }
        }
        scrambles.put(combo, false);
        scrambles.put(revCombo, false);
        return false;
    }

    private boolean haveSameLetters(String a, String b)
    {
        if (a.length() != b.length()) {
            return false;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < a.length(); i++) {
            if (map.containsKey(a.charAt(i))) {
                map.put(a.charAt(i),
                        map.get(a.charAt(i)) + 1);
            }
            else {
                map.put(a.charAt(i), 1);
            }
        }
        // Now loop over String b
        for (int i = 0; i < b.length(); i++) {
            if (map.containsKey(b.charAt(i))) {
                map.put(b.charAt(i),
                        map.get(b.charAt(i)) - 1);
            }
        }
        Set<Character> keys = map.keySet();
        for (Character key : keys) {
            if (map.get(key) != 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        System.out.println(new ScrambleString().isScramble("a", "a"));
    }
}
