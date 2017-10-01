package com.problem;

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
        WeakReference<JavaReferences> ref = new WeakReference<JavaReferences>(strongRef);

        System.out.println(ref.get());
        strongRef = null;

        System.gc();
        System.gc();
        System.out.println(ref.get());
    }
}
