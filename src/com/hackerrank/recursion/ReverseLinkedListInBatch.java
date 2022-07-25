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


    /**
     * REverses list from head to tail, return new head.
     * It delinks this list from the original list if it was previously linked. i.e. tail.next points to next within the returned list.
     * @param head
     * @param tail
     * @return
     */
    ListNode reverse1(ListNode head, ListNode tail) {
        ListNode curr = head;
        ListNode prev = null;

        while(curr != tail){
            ListNode tmp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = tmp;
        }
        if(tail != null){
            tail.next = prev;
        }
        return tail;
    }

    ListNode reverse1(ListNode head, int k)
    {
        ListNode curr = head;
        int count =1;
        while(curr!= null){
            curr = curr.next;
            ++count;
            if(count == k) break;
        }

        if (count == k){
            ListNode next = curr.next;
            ListNode newHead = reverse1(head, curr);
            head.next = reverse(next, k);
            return newHead;
        }else{
            return head;
        }
    }





    ListNode reverse(ListNode head, ListNode tail) {
        ListNode curr = head;
        ListNode prev = null;
        while (curr != tail) {
            ListNode next = curr.next;
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
        //assuming head is 0-th node, curr now points to (k)-th node, prev points to (k-1)-th node
        ListNode newHead;
        if (count == k) { // Reverse first k nodes of linked list[0, k-1]
            //curr points to k-th node, prev points to (k-1)-th node.
            ListNode reverse = reverse(head, prev);
            //now head points to is tail of this k-th batch and prev is head of this k-th batch.
            head.next = reverse(current, k);
            newHead = prev;
        } else {
            return head;
        }

       /* next is now a pointer to (k+1)th node
          Recursively call for the list starting from current.
          And make rest of the list as next of first node */
        if (current != null)
            head.next = reverse(current, k);

        // prev is now head of input list
        return newHead ;
    }

//    ListNode reverse(ListNode head, ListNode tail){
//
//        ListNode curr = head;
//        ListNode prev = null;
//        while (curr != tail){
//            ListNode next = curr.next;
//            curr.next = prev;
//            prev = curr;
//            curr = next;
//        }
//
//        if (curr != null) {
//            curr.next = prev;
//        }
//        return curr;
//    }
//
//    ListNode reverse(ListNode head, int k){
//
//        ListNode curr = head;
//        ListNode prev = null;
//
//        int count=0;
//        while(curr!= null && count<k){
//            prev = curr;
//            curr = curr.next;
//            ++count;
//        }
//        //now curr points to (k)-th node, prev points to (k-1)-th node, head points to 0-th node
//        if(count == k) {
//            ListNode reverse = reverse(head, prev);
//            head.next = reverse(curr, k);
//            return reverse;
//        }else{
//            return head;
//        }
//    }

    public static void main(String[] args) {
        ReverseLinkedListInBatch reverseLinkedListInBatch = new ReverseLinkedListInBatch();
        //1->2->3->4->5
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println(head);
        ListNode reverse = reverseLinkedListInBatch.reverse1(head, 2);
        System.out.println(reverse);
    }
}
