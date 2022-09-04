package problems.miscellaneous;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SmallestTrimmedNumbers {
    public int[] smallestTrimmedNumbers(String[] nums, int[][] queries) {
        final int origsize = nums[0].length();
        int[] answer = new int[queries.length];
        int count = 0;
        for(int[] quer : queries) {
            int trim = quer[1];
            int k = quer[0];
            Node[] modinums = new Node[nums.length];
            for(int i=0; i<nums.length; i++) {
                modinums[i] = new Node(nums[i].substring(origsize - trim), i);

            }
            answer[count] = kthLargestElement(modinums, k).origIndex;
            count++;
        }
        return answer;
    }
    // https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/2484384/Best-java-solution-oror-best-time-complexity-oror-priority-queue
    // O (n log k) time complexity
    public Node kthLargestElement(Node[] nodes, int k) {

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.addAll(Arrays.asList(nodes));
        while (k > 1){
            queue.poll();
            k-=1;
        }
        return queue.poll();
    }

    private static class Node implements Comparable<Node>{
        String str;
        int origIndex;

        Node(String str, int i) {
            this.str = str;
            origIndex = i;
        }
        @Override
        public int compareTo(Node o) {
            return this.str.compareTo(o.str);
        }
    }
}
