package problems.miscellaneous;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Asked in Nutanix online assessment

/*

 Imagine a network where nodes have strength values from 1 to n.
 Each of them belong to their own networks initially.
 After each time instant we join node from[i] to to[i].
 After we join network with the highest value will take the value of the entire network.

 Imagine networks with initial nodes: {1}, {2}, {3}, {4}, {5}, {6}, {7}
 from = {1, 4, 3, 6, 2, 7}
 to =   {2, 5, 2, 4, 5, 1}

 Answer[0] = 1+2+3+4+5+6+7 = 28

 After 0th second: we have {1, 2}, {3}, {4}, {5}, {6}, {7} ; Answer[1] = 2+3+4+5+6+7 = 27
 After 1st second we have  {1, 2}, {3}, {4, 5}, {6}, {7} ; Answer[2] = 2+3+5+6+7 = 23

 After 4th second we have {1,2,3,4,5,6}, {7} ; Answer[5] = 6 + 7 = 13
 After 5th second we have {1,2,3,4,5,6,7} ; Answer[6] = 7

 */
public class NetworkSumUnionFind {
    public static List<Long> networkSums(int nodeCount, List<Integer> from, List<Integer> to) {
        Node[] nodes = new Node[nodeCount+1];
        long sum = 0L;
        for(int i=1; i<=nodeCount; i++) {
            nodes[i] = new Node(i);
            sum+=i;
        }
        List<Long> ans = new ArrayList<>();
        ans.add(sum);

        for(int i=0; i<from.size(); i++) {

            Node firstTopNode = findroot(nodes[from.get(i)]);
            Node secondTopNode = findroot(nodes[to.get(i)]);

            if(firstTopNode.val > secondTopNode.val) {
                secondTopNode.parent = firstTopNode;
                sum-=secondTopNode.val;
            } else {
                firstTopNode.parent = secondTopNode;
                sum-=firstTopNode.val;
            }
            ans.add(sum);
        }
        return ans;
    }

    // This function may take forever to run if there are a lot of elements in the list.
    private static Node findroot(Node node) {
        while (node.parent != null) {
            node = node.parent;
        }
        return node;
    }

    private static class Node {
        Node parent;
        int val;

        Node(int val) {
            this.val = val;
        }

    }

    public static void main(String[] a) {
        int num = 100000;
//        Integer[] from = new Integer[num];
//        for(int i=1; i<=num; i++) {
//            from[i-1] = i+1;
//        }
//        Integer[] to = new Integer[num];
//        for(int i=1; i<=num; i++) {
//            to[i-1] = 1;
//        }
        Integer[] from = new Integer[]{1, 4, 3, 6, 2, 7};
        Integer[] to =   new Integer[]{2, 5, 2, 4, 5, 1};
        Instant initial = Instant.now();
        System.out.println(networkSums(7, Arrays.asList(from), Arrays.asList(to)));
//        networkSums(num+1, Arrays.asList(from), Arrays.asList(to));
        System.out.println("duration seconds: " + Duration.between(initial, Instant.now()).getSeconds());
    }
}
