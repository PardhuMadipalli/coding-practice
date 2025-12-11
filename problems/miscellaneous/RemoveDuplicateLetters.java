package problems.miscellaneous;


import java.util.ArrayDeque;
import java.util.Deque;

// https://leetcode.com/problems/remove-duplicate-letters
// Solution is an imporvement over https://leetcode.com/problems/remove-duplicate-letters/discuss/2529665/Java-or-Using-Monotonic-Stack
public class RemoveDuplicateLetters {
    public String removeDuplicateLetters(String s) {
        //Find Frequency
        int[] frequency = new int[26];

        char[] arr = s.toCharArray();

        for (char c : arr) {
            frequency[c - 'a']++;
        }

        //Maintain seen array.
        //While top of the stack is greater && FREQUENCY IS NOT 0 than current element
        //-  remove the element and set seen as false
        //Add the new element to array, seen as true, reduce frequency by 1.
        boolean[] seen = new boolean[26];//0 false, 1 true

        //Montonic stack - Keep it increasing order
        Deque<Character> stack = new ArrayDeque<>();

        for (char currentCharacter : arr) {
            if (seen[currentCharacter - 'a']) {
                frequency[currentCharacter - 'a']--;
                // no need to add it as it is already added
                continue;
            }

            while (!stack.isEmpty()
                    && stack.getLast() > currentCharacter
                    && frequency[stack.getLast() - 'a'] > 0) {
                seen[stack.removeLast() - 'a'] = false; // pop it, we will add this to the queue later
            }

            stack.addLast(currentCharacter);
            frequency[currentCharacter - 'a']--;
            seen[currentCharacter - 'a'] = true;
        }

        StringBuilder answer = new StringBuilder();

        while (!stack.isEmpty()){
            answer.append(stack.removeFirst());
        }
        return answer.toString();
    }

    public static void main(String[] args) {
        System.out.println(new RemoveDuplicateLetters().removeDuplicateLetters("bcabc"));
    }
}
