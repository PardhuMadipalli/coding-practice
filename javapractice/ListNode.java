package javapractice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ListNode {
     int val;
     ListNode next;
     ListNode() {}
     ListNode(int val) { this.val = val; }
     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 }

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode partition(ListNode head, int x) {
        if(head == null || head.next ==null)
            return head;

        ListNode maxValParentNode = null;
        ListNode maxValNode = null;
        List<Integer> smalls = new ArrayList<>();
        boolean firstnodebig = false;
        if(head.val >= x) {
            maxValParentNode = new ListNode(0);
            firstnodebig = true;
            maxValNode = head;
        }

        // We already processed head
        for(ListNode curr = head.next, prev = head; curr != null; prev = curr, curr = curr.next) {
            if(maxValParentNode != null && curr.val < x) {
                smalls.add(curr.val);
                prev.next = curr.next;
                curr = prev;
            } else {
                if (curr.val >= x) {
                    if (maxValParentNode == null) {
                        maxValParentNode = prev;
                        maxValNode = curr;
                    }
                }
            }
        }


        if(maxValParentNode != null && !smalls.isEmpty()){ // If there exists such a parent & some less than x nodes
            ListNode headsmall  = new ListNode(smalls.get(0));
            ListNode tail = headsmall;
            for(int i=1; i<smalls.size(); i++, tail = tail.next) {
                tail.next = new ListNode(smalls.get(i));
            }
            tail.next = maxValNode;
            if(firstnodebig) // If the input's head node itself is maxValNode, then make smalls linked list as the head
                return headsmall;
            maxValParentNode.next = headsmall;
        }
        return head;
    }
}