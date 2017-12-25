package com.hackerrank;


import java.util.NoSuchElementException;

//http://algs4.cs.princeton.edu/24pq/IndexMinPQ.java.html
public class PriorityQueue<E extends Comparable<E>> {

	int maxSize;
	int currSize;
	E[] keys; //priority quque - min on top
	int [] pq;  // maps keys idx within pq to user's key idx 
	int [] qp;  // maps user key idx to key's idx within keys pq
	
	
	public PriorityQueue(int size){
		maxSize = size;
		currSize = 0;
		keys = (E[]) new Comparable[size + 1];
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
    }
	
	/**
	 * 
	 * @param i User's key index
	 * @param j
	 * @return
	 */
	private boolean greater(int i, int j) {
	        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
	}

	/**
	 * 
	 * @param k key's index with PQ
	 */
    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exchKeys(k, k/2);
            k = k/2;
        }
    }

    /**
     * 
     * @param k k key's index with PQ
     */
    private void sink(int k) {
        while (2*k <= currSize) {
            int j = 2*k;
            if (j < currSize && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exchKeys(k, j);
            k = j;
        }
    }
    
    public boolean contains(int i) {
        if (i < 0 || i >= maxSize) throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }
    
    public void insert(int i, E key) {
        if (i < 0 || i >= maxSize) throw new IndexOutOfBoundsException();
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        currSize++;
        qp[i] = currSize;
        pq[currSize] = i; //add at the end of queue and swim it up
        keys[i] = key;
        swim(currSize);
    }
    
    public void changeKey(int i, E key) {
        if (i < 0 || i >= maxSize) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }
    
    public void increaseKey(int i, E key) {
        if (i < 0 || i >= maxSize) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (keys[i].compareTo(key) >= 0)
            throw new IllegalArgumentException("Calling increaseVal() with given argument would not strictly increase the key");
        keys[i] = key;
        sink(qp[i]);
    }
    
    public void decreaseKey(int i, E key) {
        if (i < 0 || i >= maxSize) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if (keys[i].compareTo(key) <= 0)
            throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
        keys[i] = key;
        swim(qp[i]);
    }
    
    public void delete(int i) {
        if (i < 0 || i >= maxSize) throw new IndexOutOfBoundsException();
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        exchKeys(index, maxSize--);
        swim(index);
        sink(index);
        keys[i] = null;
        qp[i] = -1;
    }
    
    public E minKey() {
        if (maxSize == 0) throw new NoSuchElementException("Priority queue underflow");
        return keys[pq[1]];
    }
    
    public int delMin() {
        if (maxSize == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        exchKeys(1, maxSize--);
        sink(1);
        assert min == pq[maxSize+1];
        qp[min] = -1;        // delete
        keys[min] = null;    // to help with garbage collection
        pq[maxSize+1] = -1;        // not needed
        return min;
    }

	public static void main(String[] args) {
		PriorityQueue<Long> pQ = new PriorityQueue<>(4);
		pQ.insert(1, 100l);
		pQ.insert(2,80l);
        pQ.insert(3,80l);
        pQ.delete(1);
        pQ.insert(1,10l);
        System.out.println(pQ.minKey());

	}

}
