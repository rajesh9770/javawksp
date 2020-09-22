package com.hackerrank.List;

import java.util.List;

public class DeleteNthNode {

    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1, head);
        ListNode first = dummy;
        ListNode second = dummy;

        for(int i=0; i<n+1; ++i) {
            if(second!= null){
                second = second.next;
            }else{
                return head;
            }
        }

        while (second!= null){
            second = second.next;
            first= first.next;
        }

        first.next = first.next.next;
        return dummy.next;
    }

}
