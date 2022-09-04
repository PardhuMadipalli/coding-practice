package problems.miscellaneous;

public class CopyListwithRandomPointer {

    private static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        Node newHead = clone(head);
        Node prev = head;
        Node newprev = newHead;

        while(prev.next!=null) {
            Node curr = prev.next;

            newprev.next = clone(prev.next);
            prev.next = newprev;

            newprev = newprev.next;
            prev = curr;
        }
        Node curr = newHead;
        while (curr!=null) {
            if(curr.random!=null) {
                curr.random = curr.random.next;
            }
            curr = curr.next;
        }
        return newHead;
    }

    private static Node clone(Node e) {
        Node node = new Node(e.val);
        node.next = e.next;
        node.random = e.random;
        return node;
    }
}
