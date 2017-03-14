package com.hackerrank;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.Scanner;
import java.util.Set;

public class SubsetComponent2 {

    public static long powerSet(BitSet[] bSet, BitSet[] bitSets, int startIdx, int outIdx, int[] compoents){
        long noOfComponents = 0;
        for(int i=startIdx; i<bSet.length; ++i){
            bitSets[outIdx] = bSet[i];
            int [] componentsAfterMerge = new int[64];
            noOfComponents += merge(compoents, bSet[i], componentsAfterMerge);
            if(i+1 < bSet.length)
                noOfComponents += powerSet(bSet, bitSets, i+1, outIdx+1, componentsAfterMerge);
        }
        return noOfComponents;
    }

    private static int[] convertBitSetToCompoent(BitSet bs) {
        int [] ret = new int[64];
        int compoentIdx = bs.nextSetBit(0);
        for(int i=0; i<64; ++i){
            if(bs.get(i)){
                ret [i] = compoentIdx;
            }else ret [i] = i;
        }
        return ret;
    }

    public static int merge(int[] graphComponents, BitSet bs, int [] ret){

        Set<Integer> componetsToMerge = new java.util.HashSet<Integer>();
        int minComponent = 0;
        int startIdx = bs.nextSetBit(0);
        if(startIdx>=0){
            minComponent = graphComponents[startIdx];
            for (int i = startIdx; i >= 0; i = bs.nextSetBit(i+1)) {
                int comp = graphComponents[i];
                if(comp<minComponent) minComponent = comp;
                componetsToMerge.add(comp);
            }
        }

        int noOfComponets = 0;
        for (int i = 0; i < graphComponents.length; ++i) {
            int comp = graphComponents[i];
            if(componetsToMerge.contains(comp)){
                //replace with the min component
                ret[i] = minComponent;
            }else{
                //otherwise keep the old comp
                ret[i] = comp;
            }
            if(ret[i] == i) noOfComponets++;
        }
        return noOfComponets;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        BitSet[] bSet = new BitSet[n];
        //long timeStart = System.currentTimeMillis();
        for(int i=0; i<n; ++i){
            BigInteger a = new BigInteger(in.next());
            bSet[i] = new BitSet();
            for(int k =0; k <64; ++k){
                if(a.testBit(k)) bSet[i].set(k);
            }
        }
        in.close();
        BitSet defaultBitSet = new BitSet(64);
        //System.out.println(">" + (System.currentTimeMillis()-timeStart));
        //timeStart = System.currentTimeMillis();
        long powerSet = 64 + powerSet(bSet, new BitSet[n], 0, 0, convertBitSetToCompoent(defaultBitSet));
        //System.out.println(">" + (System.currentTimeMillis()-timeStart));
        System.out.println(powerSet);
    }

}
