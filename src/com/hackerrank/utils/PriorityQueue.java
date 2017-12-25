package com.hackerrank.utils;

import javafx.util.Pair;

import java.util.NoSuchElementException;

/**
 * Created by Rajesh on 12/23/2017.
 */
public class PriorityQueue<T extends Comparable<T>> {

    int maxSize;
    int pos; //last filled slot in vals. To get the next slot go to pos+1
    T[] vals; //priority quque - min at 1  // 1-based
    int [] pq;  // maps vals idx within pq to user's key idx  : pos -> obj-id  //1-based
    int [] qp;  // maps user key idx to key's idx within vals pq  : obj-id -> pos //1-based


    public PriorityQueue(int size){
        maxSize = size;
        pos = 0;
        vals = (T[]) new Comparable[size + 1];
        pq = new int[maxSize+1];   //1-based
        qp = new int[maxSize+1];   // 1-based
        for (int i = 0; i <= maxSize; i++)
            qp[i] = -1;
    }


    /**
     *
     * @param i Keys's index within pq
     * @param j
     */
    private void exchKeys(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
        T tmp = vals[i];
        vals[i] = vals[j];
        vals[j] = tmp;
    }

    /**
     *
     * @param i User's key index
     * @param j
     * @return
     */
    private boolean greater(int i, int j) {
        return vals[i].compareTo(vals[j]) > 0;
    }

    /**
     *
     * @param k key's index with PQ
     */
    private void swim(int k) {
        int parent = k>>1;
        while (k > 1 && greater(parent, k)) {
            exchKeys(k, parent);
            k = parent;
            parent = k>>1;
        }
    }

    /**
     *
     * @param k k key's index with PQ
     */
    private void sink(int k) {
        while (2*k <= pos) {
            int j = 2*k;
            if (j < pos && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exchKeys(k, j);
            k = j;
        }
    }

    public boolean contains(int i) {
        if (i < 0 || i >= maxSize) throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }

    public Pair<Integer, T> containsSafe(int i) {
        if (i < 0 || i >= maxSize || qp[i]<1 ) return null;
        return new Pair<Integer, T>(i, vals[qp[i]]);
    }

    /**
     * User supplies the id for each element. These id's can be recycled only after that object is removed from the PQ.
     * @param id user supplied id for a element
     * @param key value associated with a element which is used to rank the element within PQ.
     */
    public void add(int id, T key) {
        if (id < 0 || id >= maxSize) throw new IndexOutOfBoundsException();
        if (contains(id)) throw new IllegalArgumentException("index "+ id + " is already in the priority queue");
        pos++;
        qp[id] = pos;
        pq[pos] = id; //add at the end of queue and swim it up
        vals[pos] = key;
        swim(pos);
    }

    public void changeVal(int i, T newVal) {
        if (i < 0 || i >= maxSize) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        T oldVal = vals[qp[i]];
        vals[qp[i]] = newVal;
        if(newVal.compareTo(oldVal) < 0) swim(qp[i]);
        else if(newVal.compareTo(oldVal) > 0) sink(qp[i]);
    }

    public void increaseVal(int i, T newVal) {
        if (i < 0 || i >= maxSize) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        T oldVal = vals[qp[i]];
        if (oldVal.compareTo(newVal) >= 0)
            throw new IllegalArgumentException("Calling increaseVal() with given argument would not strictly increase the key");
        vals[qp[i]] = newVal ;
        sink(qp[i]);
    }

    public void decreaseVal(int i, T newVal) {
        if (i < 0 || i >= maxSize) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        T oldVal = vals[qp[i]];
        if (oldVal.compareTo(newVal) <= 0)
            throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
        vals[qp[i]] = newVal;
        swim(qp[i]);
    }

    public void delete(int i) {
        if (i < 0 || i >= maxSize) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        //move the element that is at the end of the queue to this empty slot, so that we do not create holes in PQ.
        //this will allows to add the new element at the end. i.e. at pos location.
        exchKeys(index, pos--);
        swim(index);
        sink(index);
        vals[pos+1] = null;
        qp[i] = -1;
        pq[pos+1] = -1;
    }

    public T minKey() {
        if (pos <= 0) throw new NoSuchElementException("Priority queue underflow");
        return vals[1];
    }

    public Pair<Integer, T> delMin() {
        if (pos <= 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        T minVal = vals[1];
        delete(min);
        return new Pair<Integer, T>(min, minVal);
    }

    public Pair<Integer, T> delMinSafe() {
        if (pos <= 0) return null;
        int min = pq[1];
        T minVal = vals[1];
        delete(min);
        return new Pair<Integer, T>(min, minVal);
    }

    public boolean isEmpty(){
        return pos<=0;
    }

    public int size(){
        return pos;
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
