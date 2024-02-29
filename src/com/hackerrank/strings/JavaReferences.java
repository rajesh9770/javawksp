package com.hackerrank.strings;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * Created by Rajesh on 9/30/2017.
 */
public class JavaReferences {

    private Integer id;

    public JavaReferences(int id){
        this.id = id;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("In finalize");
    }

    public String toString(){
        return "JavaRefId" + id;
    }
    public static void main(String[] args) throws InterruptedException {
        //JavaReferences strongRef = new JavaReferences(5);

        //WeakReference<JavaReferences> ref = new WeakReference<JavaReferences>(strongRef);
        //System.out.println(ref.get());
        //strongRef = null;

//        System.gc();
//        System.gc();
//        System.out.println(ref.get());

        JavaReferences strongRef2 = new JavaReferences(10);
        ReferenceQueue<JavaReferences> queue = new ReferenceQueue<>();
        PhantomReference<JavaReferences> phRef = new PhantomReference<>(strongRef2, queue);
        strongRef2 = null;
        System.gc();
        System.gc();

//        while(!phRef.isEnqueued()){
//            System.out.println("Not yet");
//            //strongRef2 = null;
//            System.gc();
//        }
//        System.out.println("clear");
//        queue.poll().clear();

        //An object is phantom reachable if it is neither strongly, softly, nor weakly reachable, it has been finalized,
        // and some phantom reference refers to it.
        Reference<? extends JavaReferences> ref = null;
        while((ref = queue.remove(1000)) == null){
            System.out.println("calling gc");
            System.gc();
        }
        System.out.println("clear");
        ref.clear();



    }
}
