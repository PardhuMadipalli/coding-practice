package problems.miscellaneous;

import java.util.ArrayDeque;
import java.util.Deque;


// https://leetcode.com/problems/largest-rectangle-in-histogram
public class LargestHistogram {
    // Always implement stack with ArrayDeque, not with Stack
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0); // We know that at least one item is there in heights array
        int answer = heights[0];
        final int n = heights.length;
        for(int i=1; i <= n; i++) {
            // i==n : when all the in the array are covered, there can still be a few elements left in the stack.
            while(!stack.isEmpty() && (i==n || heights[i] < heights[stack.getLast()])) {
                int droppedIndex = stack.removeLast();
                int nextSmallerIndex = stack.isEmpty() ? - 1 : stack.getLast();
                answer =  Math.max(heights[droppedIndex] * (i - nextSmallerIndex - 1), answer);
            }
            stack.addLast(i);
        }
        return answer;
    }
}
