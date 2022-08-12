package problems.miscellaneous;


class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }
 

public class SwapNodesInPairs {
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next==null) return head;
        ListNode first = head, nextFirst=first.next.next;
        head = head.next;

        first.next.next = first;
        first.next = swapPairs(nextFirst);

        return head;
    }
}
