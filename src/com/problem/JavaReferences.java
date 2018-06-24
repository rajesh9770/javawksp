package com.problem;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by Rajesh on 9/30/2017.
 */
public class JavaReferences {

    private Integer id;

    public JavaReferences(int id){
        this.id = id;
    }

    public String toString(){
        return "JavaRefId" + id;
    }
    public static void main(String[] args) {
        JavaReferences strongRef = new JavaReferences(5);
        JavaReferences strongRef2 = new JavaReferences(10);
        WeakReference<JavaReferences> ref = new WeakReference<JavaReferences>(strongRef);
        ReferenceQueue<JavaReferences> queue = new ReferenceQueue<>();
        PhantomReference<JavaReferences> phRef = new PhantomReference<>(strongRef2, queue);

        System.out.println(ref.get());
        strongRef = null;

        System.gc();
        System.gc();
        System.out.println(ref.get());

        strongRef2 = null;
        System.gc();
        System.gc();

        while(!phRef.isEnqueued()){
            System.out.println("Not yet");
            //strongRef2 = null;
            System.gc();
        }
        System.out.println("clear");
        queue.poll().clear();

    }
}
