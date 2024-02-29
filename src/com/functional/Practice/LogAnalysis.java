package com.functional.Practice;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * There are N server writing to log stream. Each individual stream in sorted by TS.
 * Write analyser which gives log events in a sorted order.
 */

public class LogAnalysis{

    public interface MyStream {
        Event getNext();
        boolean isEmpty();
    }

    public static class Event{
        long ts;
        MyStream stream; //handle to the stream,
        String msg;
    }

    PriorityQueue<Event> q = new PriorityQueue(10, new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            return (int) (o1.ts - o2.ts);
        }
    });

    public Event getNext(){
        Event evt = null;
        synchronized (q){
            while(q.isEmpty()){
                try {
                    q.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            evt = q.remove();
        }
        if(evt != null){
            addNextFromStream(evt);
        }
        return evt;
    }

    private void addNextFromStream(Event evt) {
        if(!evt.stream.isEmpty()){
            q.add(evt.stream.getNext());
            q.notify();
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    q.add(evt.stream.getNext()); //blocking call.
                    q.notify();
                }
            }).start();
        }
    }


    /**
     app1     app2  app3    app4
     [10... ] [1..] [ 2.. ] [4...]
     0x19

     p [ ]
     **/

}