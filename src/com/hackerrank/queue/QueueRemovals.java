package com.hackerrank.queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *  You're given a list of n integers arr, which represent elements in a queue (in order from front to back).
 *  You're also given an integer x, and must perform x iterations of the following 3-step process:
 * Pop x elements from the front of queue (or, if it contains fewer than x elements, pop all of them)
 * Of the elements that were popped, find the one with the largest value (if there are multiple such elements,
 * take the one which had been popped the earliest), and remove it
 * For each one of the remaining elements that were popped (in the order they had been popped),
 * decrement its value by 1 if it's positive (otherwise,
 * if its value is 0, then it's left unchanged), and then add it back to the queue
 * Compute a list of x integers output, the ith of which is the 1-based index in the original array of the element which had been removed in step 2 during the ith iteration.
 */
public class QueueRemovals {
    public static class Node{
        int val, idx;
        public Node(int val, int id){
            this.val = val;
            this.idx = id;
        }
    }

    public static int[] findPositions(int[] arr, int x){
        List<Node> queue = new LinkedList<>();
        for(int i=0; i< arr.length; ++i){
            queue.add(new Node(arr[i], i));
        }
        int [] ret = new int[x];
        for(int i = 0; i< x; ++i){
            List<Node> removals = new ArrayList<>();
            Node max = null;
            for(int j =0 ; j<x; ++j){
                if(!queue.isEmpty()){
                    Node remove = ((LinkedList<Node>) queue).remove();
                    if(max == null || max.val < remove.val) {
                        max = remove;
                    }
                    removals.add(remove);
                }
            }
            if(max != null) {
                ret[i] = max.idx + 1;
            }
            for(Node removedNode: removals){
                if(removedNode.val>0) removedNode.val--;
                if(max != removedNode) {
                    queue.add(removedNode);
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(findPositions(new int[] {1, 2, 2, 3, 4, 5}, 5))); //5, 6, 4, 1, 2
        System.out.println(Arrays.toString(findPositions(new int[] {2, 4, 2, 4, 3, 1, 2, 2, 3, 4, 3, 4, 4}, 4))); //2, 5, 10, 13
    }
}
