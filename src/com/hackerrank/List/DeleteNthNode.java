package com.hackerrank.List;

import java.util.List;

public class DeleteNthNode {

    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
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

    public static ListNode removeNthFromEndPractice(ListNode head, int n) {
        ListNode nodeBeforeNthNode = head;
        for(int i=0; i<n; ++i){
            nodeBeforeNthNode = nodeBeforeNthNode.next;
        }
        ListNode first = head;
        while(nodeBeforeNthNode.next != null){
            nodeBeforeNthNode = nodeBeforeNthNode.next;
            first = first.next;
        }
        first.next = first.next.next;
        return  head;
    }

    public static void main(String[] args) {
        ListNode listNode1 = new DeleteNthNode.ListNode(1);
        ListNode listNode2 = new DeleteNthNode.ListNode(2, listNode1);
        ListNode listNode3 = new DeleteNthNode.ListNode(3, listNode2);
        ListNode listNode4 = new DeleteNthNode.ListNode(4, listNode3);
        ListNode listNode5 = new DeleteNthNode.ListNode(5, listNode4);

        ListNode head = listNode5;
        while(head != null){
            System.out.println(head.val);
            head = head.next;
        }
        head = listNode5;
        head = removeNthFromEndPractice(head, 2);
        System.out.println("After Removal");
        while(head != null){
            System.out.println(head.val);
            head = head.next;
        }
    }
}
