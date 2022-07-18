package problems.miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

//https://leetcode.com/problems/largest-multiple-of-three/

// Wrong answer //

public class LargestMultipleOfThree {
    public String largestMultipleOfThree(int[] digits) {
        final int n = digits.length;
        List<Integer> finalans = new ArrayList<>();
        Arrays.sort(digits);
        int curr, zeroCount = 0;
        Queue<Integer> modTwos = new LinkedList<>(), modOnes = new LinkedList<>();
        for (int i=n-1; i>=0; i--) {
            curr = digits[i];
            if (curr % 3 == 0) {
                finalans.add(curr);
            } else if (curr % 3 == 1) {
                processNonThreeMultiple(finalans, curr, modOnes, modTwos);
            } else {
                processNonThreeMultiple(finalans, curr, modTwos, modOnes);
            }
        }

        Collections.sort(finalans);
        if(!finalans.isEmpty() && finalans.get(finalans.size()-1) == 0) {
            // if only 0s are there and nothing else
            return "0";
        }
        int anslength = finalans.size();
        char[] chars = new char[anslength];
        for(int i=anslength-1,j=0; i>=0; i--,j++){
            chars[j] = (char)(finalans.get(i) + '0');
        }
        String answer = new String(chars);
        return answer;
    }

    private void processNonThreeMultiple(List<Integer> finalans, int curr, Queue<Integer> sameMods, Queue<Integer> diffMods) {
//        System.out.println("processing curr: " + curr);
        if (diffMods.size() > 0) {
//            System.out.println("Adding ans because we have diff: " + diffMods.peek());
            finalans.add(diffMods.poll());
            finalans.add(curr);
        } else if (sameMods.size() == 2) {
//            System.out.println("Adding ans because we have same ones twice: " + sameMods.peek());
            finalans.add(sameMods.poll());
            finalans.add(sameMods.poll());
            finalans.add(curr);
        } else {
//            System.out.println("just adding to list");
            sameMods.add(curr);
        }
    }

    public static void main(String[] args){
        System.out.println(new LargestMultipleOfThree().largestMultipleOfThree(new int[] {0}));
    }
}
