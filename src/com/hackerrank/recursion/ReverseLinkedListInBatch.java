package com.hackerrank.recursion;

import java.util.List;

/**
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 *
 * k is a positive integer and is less than or equal to the length of the linked list.
 * If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
 */
public class ReverseLinkedListInBatch {


    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }

        @Override
        public String toString() {
            return val + (next != null ? "->" + next.toString() : "");
        }
    }

    ListNode reverse(ListNode head, ListNode tail) {
        ListNode curr = head;
        ListNode prev = null;
        ListNode next = null;
        while (curr != tail) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        if (tail != null) {
            tail.next = prev;
        }
        return tail; //new head and original tail now points to new head and original head pointer now points to tail. caller needs to track original tails next.
    }

    ListNode reverse(ListNode head, int k)
    {
        ListNode current = head;
        ListNode prev = null;

        int count = 0;

        while (current != null && count != k) {
            prev = current;
            current = current.next;
            count++;
        }

        ListNode newHead;
        if (count == k) { // Reverse first k nodes of linked list
            //curr points to k+1, prev points to k-th node.
            reverse(head, prev);
            //now head points to is tail of this k-th batch and prev is head of this k-th batch.
            newHead = prev;
        } else {
            newHead = head;
        }

       /* next is now a pointer to (k+1)th node
          Recursively call for the list starting from current.
          And make rest of the list as next of first node */
        if (current != null)
            head.next = reverse(current, k);

        // prev is now head of input list
        return newHead ;
    }

    public static void main(String[] args) {
        ReverseLinkedListInBatch reverseLinkedListInBatch = new ReverseLinkedListInBatch();
        //1->2->3->4->5
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println(head);
        ListNode reverse = reverseLinkedListInBatch.reverse(head, 4);
        System.out.println(reverse);
    }
}
