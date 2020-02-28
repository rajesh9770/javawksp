package com.hackerrank.recursion;

import java.util.Arrays;

public class PermutationCombination {

    static void permute(char[] in, char[] out, boolean[] used, int currentIdx, int length) {
        if(currentIdx == length) {
            //Process(out)
            System.out.println(new String(out));
            return;
        }
        for(int i = 0; i<length; ++i)
        {
            if(used[i]) continue;
            out[currentIdx] = in[i];
            used[i] = true;
            permute(in, out, used, currentIdx + 1, length);
            used[i] = false;
        }
    }

    static void combine(char[] in , char[] out, int startIdx, int currentIdx, int length)
    {
        for(int i=startIdx; i<length; ++i)
        {
            out[currentIdx] = in[i];
            //Process(out, currentIdx); // out is filled from 0 to currentIdx
            System.out.println(new String(Arrays.copyOfRange(out, 0, currentIdx +1)));
            if(i+1 < length) {
                combine(in, out, i + 1, currentIdx + 1, length);
            }
        }
    }


    public static void main(String[] args) {
        char[] str = new char[]{'a', 'b', 'c', 'd'};
        permute(str, new char[str.length], new boolean[str.length], 0, str.length);
        combine(str, new char[str.length], 0, 0, str.length);
    }
}
