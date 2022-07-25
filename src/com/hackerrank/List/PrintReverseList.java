package com.hackerrank.List;

public class PrintReverseList {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static void main(String[] args) {
        PrintReverseList printReverseList = new PrintReverseList();
        ListNode node = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        printReverseList.printReverse(node);

        ListNode listNode = reverseList(node);
        printReverseList.printReverse(listNode);
    }

    void printReverse(ListNode node) {
        if (node != null){
            printReverse(node.next);
            System.out.println(node.val);
        }
    }

    static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode headNext = head.next;
        ListNode newHead = reverseList(headNext);
        headNext.next = head;
        head.next = null;
        return newHead;
    }
}
