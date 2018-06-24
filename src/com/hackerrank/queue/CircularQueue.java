package com.hackerrank.queue;

public class CircularQueue<E> {

    private int start=0, end=0, size=0;
    private final int capacity;

    private E[] elements;

    public CircularQueue(final int maxsize){
        this.capacity = maxsize;
        elements = (E[]) new Object[maxsize];
    }

    public boolean isFull(){
        //System.out.println(size + " " + maxSize );
        return size == capacity;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void add(E ele) throws Exception{
        if(isFull()){
            remove();
        }
        elements[end] = ele;
        ++end;
        ++size;
        if(end == capacity) end = 0;
    }

    public E remove() throws Exception{
        if(!isEmpty()){
            E ret = elements[start];
            ++start;
            --size;
            if(start==capacity) start=0;
            return ret;
        }
        throw new Exception("Queue is empty");
    }
    
    public String toString(){
        StringBuilder buff = new StringBuilder();
        if(isEmpty()) {
            buff.append("Queue is empty");
        }else{
            buff.append("Start: " + start +  " End: " + end + "\n");
            int l = size;
            int idx = start;
            while(l>0){
                buff.append(elements[idx] + " ");
                idx = (idx+1) %capacity;
                --l;
            }
        }
        return buff.toString();
    }
    public static void test() throws Exception{
        CircularQueue<Integer> q = new CircularQueue<Integer>(5);
        System.out.println(q);
        q.add(1);
        q.add(2);
        System.out.println(q);
        q.remove();
        System.out.println(q);
        q.remove();
        System.out.println(q);
        q.add(1);
        q.add(2);
        System.out.println(q);
        q.remove();q.remove();
        System.out.println(q);
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(4);
        System.out.println(q);
        q.add(5);
        System.out.println(q);
        q.remove();q.remove();
        System.out.println(q);
        q.add(6);
        q.add(7);
        System.out.println(q);
        q.add(8);
        System.out.println(q);
        q.add(9);
        System.out.println(q);
    }
}
