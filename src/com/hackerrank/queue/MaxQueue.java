package com.hackerrank.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rajesh on 11/19/2017.
 * Class that keeps track of maximum on sliding window where a new element is added to tail and old element is removed from head.
 * This class may contain less elements than the sliding window size.
 */
public class MaxQueue<T extends Comparable> extends LinkedList<T> {

    public boolean add(T e){

        while(!isEmpty() && peekLast().compareTo(e) <0){
            removeLast();
        }
        return super.add(e);
    }

    public boolean remove(T e){
           return super.remove((Object)e);
    }
}
