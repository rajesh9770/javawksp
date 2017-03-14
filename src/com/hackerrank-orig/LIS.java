package com.hackerrank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class LIS {

    private static class Node<E extends Comparable<? super E>> implements Comparable<Node<E>>
    {
        public E val;
        public Node<E> ptr;
        public Node(E val){
            this.val = val;
            this.ptr = null;
        }
        
        public int compareTo(Node<E> o) {
            
            return val.compareTo(o.val);
        }
    }

    public static <E extends Comparable<? super E>>List<E> lis(List<E> n){
        List<Node<E>> pilesTop = new ArrayList<Node<E>>();
        for(E e: n){
            Node<E> node = new Node<E>(e);
            int i = Collections.binarySearch(pilesTop, node);
            if(i <0) i = ~i;
            if(i != 0) node.ptr = pilesTop.get(i-1);
            if(i==pilesTop.size())
                pilesTop.add(node);
            else
                pilesTop.set(i, node);
        }
        List<E> result = new ArrayList<E>();
        for(Node<E> it = pilesTop.get(pilesTop.size()-1); it!=null; it=it.ptr){
            result.add(it.val);
        }
        Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        List<Integer> arr = new ArrayList<Integer>();
        for(int i=0; i<N; ++i){
            arr.add(in.nextInt());
        }
        System.out.println(lis(arr).size());
        in.close();
    }

}
