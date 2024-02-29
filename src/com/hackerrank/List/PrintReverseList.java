package com.hackerrank.List;

public class PrintReverseList {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static void mainForPrintReverseList(String[] args) {
        PrintReverseList printReverseList = new PrintReverseList();
        ListNode node = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        printReverseList.printReverse(node);

        ListNode listNode = reverseListRecursively(node);
        printReverseList.printReverse(listNode);
    }

    void printReverse(ListNode node) {
        if (node != null){
            printReverse(node.next);
            System.out.println(node.val);
        }
    }

    static ListNode reverseListRecursively(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode headNext = head.next;
        ListNode newHead = reverseListRecursively(headNext);
        headNext.next = head;
        head.next = null;
        return newHead;
    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while(current !=null){
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        return prev;
    }

    /**
     * Given the head of a singly linked list and two integers left and right where left <= right,
     * reverse the nodes of the list from position left to position right, and return the reversed list.
     * https://leetcode.com/problems/reverse-linked-list-ii/discuss/2311084/JavaC%2B%2B-Tried-to-Explain-every-step
     */

    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummyNode = new ListNode();
        dummyNode.next = head;
        ListNode prev = dummyNode, curr;
        //First locate the node before the m-th node (pre)
        for(int i=1; i< left; ++i){//<-- loop start from 1. #of steps are (left-1) since we want to get the node before the left-th node.
            prev = prev.next;
        }

        //and the m-th node (curr)
        curr = prev.next; //m-th node. This pointer does not change. It bubbles up to the end.
        //now reverse from m-th node to n-th node
        // reversing
        for(int i = 0; i < right - left; i++){
            ListNode forw = curr.next; // forw pointer will be after curr
            curr.next = forw.next;
            forw.next = prev.next;
            prev.next = forw;
        }

        return dummyNode.next;
    }


    /**
     * You are given the head of a singly linked-list. The list can be represented as:
     *
     * L0 → L1 → … → Ln - 1 → Ln
     * Reorder the list to be on the following form:
     *
     * L0 → Ln → L1 → L(n-1) → L2 → L(n-2) → …
     * You may not modify the values in the list's nodes. Only nodes themselves may be changed.
     *
     *
     *  * Definition for singly-linked list.
     *  * public class ListNode {
     *  *     int val;
     *  *     ListNode next;
     *  *     ListNode() {}
     *  *     ListNode(int val) { this.val = val; }
     *  *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     *  * }
     *  *
     *
     */
    public void reorderList(ListNode head){
        if (head == null || head.next == null) return;
        ListNode middle = findMiddle(head);
        //Reverse the half after middle  1->2->3->4->5->6 to 1->2->3->6->5->4 --> middle points to 3
        //for even case 1->2->3->4->5 to 1->2->3->5->4 --> middle points to 3
        reverse(head, middle, null);
        //Start reorder one by one  1->2->3->6->5->4 to 1->6->2->5->3->4
        //for even case 1->2->3->5->4 to 1->4->2->5->3

//        ListNode p1 = head;
//        ListNode p2 = middle.next;  //p2 goes next to p1
//
//        while(p1 != middle){
//            ListNode p1OrigNext = p1.next;
//            ListNode p2OrigNext = p2.next;
//
//            p1.next = p2; //p2 goes next to p1
//            p2.next = p1OrigNext; //p2.next points to p1's original next
//
//            p2 = p2OrigNext; //p2 moves to p2's original next
//            p1 = p1OrigNext; //p1 moves to p1's original next
//            middle.next = p2OrigNext; //p2 is moved, we need to move middle's next also to p2's original next.
//        }

        ListNode p1 = head;
        ListNode p2 = middle;
        //p2 does not move. P2.next is added after p1 and p1 is then moved to p1's original next.
        //p2 end up being the last node(odd case) or second last node(even case)
        while (p1 != middle){
            ListNode p1OrigNext = p1.next;
            ListNode p2OrigNext = p2.next;

            p2.next = p2OrigNext.next; //p2.next moves to next of p2.next

            p1.next = p2OrigNext;
            p2OrigNext.next = p1OrigNext;
            p1 = p1OrigNext;
        }

    }

    /**
     * Find the middle of the list.
     * @param head of the list
     * @return pointer to exact middle if size of the list is odd; otherwise the first middle.
     */
    public ListNode findMiddle(ListNode head) {
        ListNode p1 = head;
        ListNode p2 = head;
        while (p2.next != null && p2.next.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
        }
        return p1;
    }

    /**
     * Reverse the sublist starting from start+1 to end.
     * @param head
     * @param nodeBeforeStart
     * @param end  end=null means till end of the list
     */
    public void reverse(ListNode head, ListNode nodeBeforeStart, ListNode end){
        ListNode prev = nodeBeforeStart;
        ListNode curr = nodeBeforeStart.next;
        while(curr.next != null && prev.next != end){ //curr.next != null checks if we reached the end of the list
            //frank charley frank paul
            ListNode fwd = curr.next;
            curr.next = fwd.next;
            fwd.next = prev.next;
            prev.next = fwd;
        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1 , new ListNode(2 , new ListNode(3, new ListNode(4, null))));
//        new PrintReverseList().reorderList(head);
        ListNode s = head;
//        while (s!=null) {
//            System.out.println(s.val);
//            s = s.next;
//        }

        head = new ListNode(1 , new ListNode(2 , new ListNode(3, new ListNode(4, new ListNode(5, null)))));
        s = head;
        while (s!=null) {
            System.out.println(s.val);
            s = s.next;
        }
        //new PrintReverseList().reorderList(head);
        s = new PrintReverseList().reverseBetween(head, 1, 2);

        while (s!=null) {
            System.out.println(s.val);
            s = s.next;
        }
    }

    /**
     * Floyd's tortoise and hare
     * Detects cycle in the list
     * @param head
     * @return
     */
    public boolean findCycle(ListNode head) {
        if (head == null) return false;
        ListNode slow = head, fast = head;
        boolean cycleFound = false;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                cycleFound = true;
                break;
            }
        }
        /**
         * slowDist = a + xL + b, x >= 0
         * fastDist = a + yL + b, y >= 0
         *
         * slowDist is the total distance covered by slow pointer.
         * fastDist is the total distance covered by fast pointer.
         * a is the number of steps both pointers need to take to enter the cycle.  (beginning part of the list)
         * b is the distance between C and G, i.e., distance between the starting point of cycle and meeting point of both pointers.
         * x is the number of times the slow pointer has looped inside the cycle, starting from and ending at C.
         * y is the number of times the fast pointer has looped inside the cycle, starting from and ending at C.
         * fastDist = 2 * (slowDist)
         *
         * a + yL + b = 2(a + xL + b)
         *
         * Resolving the formula we get:
         *
         * a=(y-2x)L-b
         *
         * where y-2x is an integer
         *
         * This basically means that a steps is same as doing some number of full loops in cycle and go b steps backwards.
         * Since the fast pointer already is b steps ahead of the entry of cycle,
         * if fast pointer moves another a steps it will end up at the entry of the cycle.
         * And since we let the slow pointer start at the start of the linked list, after a steps it will also end up at the cycle entry.
         * So, if they both move a step they both will meet the entry of cycle
         */
        slow = head;
        int a = 0;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
            ++a;
        }
        //slow now point to the start of the cycle
        int L = 1;
        slow = slow.next;
        while (slow != fast) {
            slow = slow.next;
            ++L;
        }
        //now L is the length of the cycle
        return cycleFound;
    }

}
