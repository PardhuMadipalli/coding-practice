package miscellaneous;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class RotateList {
    public ListNode rotateRight(ListNode head, int k) {
        Pair pair = findsize(head);
        int n = pair.size;
        if(n==0) return head;
        k = k%n;
        if(k ==0) return head;
        ListNode fin = pair.fin;
        ListNode newhead = null, prev = null, curr;
        int i=n;
        curr = head;
        while(i > k){
            i--;
            prev = curr;
            curr = curr.next;
        }
        fin.next = head;
        prev.next = null;
        return curr;
    }

    private Pair findsize(ListNode head) {
        ListNode curr = head, prev = null;;
        int count = 0;
        while(curr != null){
            count++;
            prev = curr;
            curr = curr.next;

        }
        return new Pair(count, prev);
    }

    private static class Pair {
        int size;
        ListNode fin;
        Pair(int count, ListNode prev) {
            this.size = count;
            this.fin = prev;
        }
    }
}