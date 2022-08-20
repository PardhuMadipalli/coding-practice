package problems.backtracking;

// https://leetcode.com/contest/weekly-contest-306/problems/construct-smallest-number-from-di-string/

public class SmallestNumberFromDIString {
    public String smallestNumber(String pattern) {
        StringBuilder sb = new StringBuilder();
        backtrack(sb, new boolean[10], pattern.length()+1, pattern);
        return sb.toString();
    }

    boolean backtrack(StringBuilder sb, boolean[] used, int totLength, String pattern) {
        if (sb.length() == totLength) return true;

        for (int i=1; i < used.length; i++) {
            if(!used[i]) {
                used[i] = true;
                sb.append(i);

                if (conditionMatches(sb, pattern) &&
                        backtrack(sb, used, totLength, pattern)) {
                    return true;
                }
                sb.deleteCharAt(sb.length()-1);
                used[i] = false;
            }
        }
        return false;
    }

    boolean conditionMatches(StringBuilder sb, String pattern) {
        if(sb.length()==1) return true;
        int i = sb.length()-2;
        if(pattern.charAt(i)=='I')
            return sb.charAt(i+1) > sb.charAt(i);
        else
            return sb.charAt(i+1) < sb.charAt(i);
    }

    public static void main(String... args) {
        System.out.println(new SmallestNumberFromDIString().smallestNumber("IIIDIDDD"));
    }
}
