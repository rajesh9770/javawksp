package com.hackerrank;


import java.util.Scanner;


public class PlaceUNLeadersMinMax {

    public PlaceUNLeadersMinMax() {
        // TODO Auto-generated constructor stub
    }

    private static int highestOne(long val) {
        if(val == 0 ) return -1;
        int ret = 63;
        long left = 1<<ret;
        while((val & left) == 0l){
            ret--;
            left = (left >>1);
        }
        return ret;
    }
    
    private static long preProcess(long[] vals) {
        int maxOneBitIdx = -1;
        for(int i=0; i<vals.length; ++i){
            int highestOne = highestOne(vals[i]);
            if(maxOneBitIdx < highestOne) maxOneBitIdx = highestOne;
        }
        if(maxOneBitIdx<0) return 0;
        long maxOne = 1<<maxOneBitIdx;
        long [] valsWith1AtMaxIdx = new long[vals.length];
        int ct = 0;
        for(int i=0; i<vals.length; ++i){
            if((vals[i] & maxOne) == maxOne){
                valsWith1AtMaxIdx[ct++] = vals[i];
            }
        }
        if(ct == vals.length){
            //delete highest one from all elements
            for(int i=0; i<vals.length; ++i){
                vals[i] ^= maxOne;
            }
            //System.out.println("calling preProcess ...");
            //for(long c: vals) System.out.println(c + " ");
            return preProcess(vals);
        }else{
            long min = Integer.MAX_VALUE;
            for(int i=0; i<vals.length; ++i){
                if((vals[i] & maxOne) == 0){
                    //find the dist from maxones
                    for(int j=0; j<ct; ++j){
                        long tmp = vals[i] ^ valsWith1AtMaxIdx[j];
                        if(tmp < min) min = tmp;
                    }
                }
            }
            return min;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        long [] vals = new long[n];
        while(n-->0){
            vals[n] = in.nextLong();
        }
        System.out.println(preProcess(vals));
    }
    //5 12 8 9 11 14  Ans: 4
    //5 6 3 1 0 4   Ans : 4
    //100 123 67 201 164 64 247 215 179 114 184 99 111 52 46 163 224 237 228 79 54 201 204 36 66 54 184 101 250 105 226 85 250 215 101 190 254 188 193 109 105 151 91 179 194 172 250 230 49 129 75 222 6 97 95 120 51 220 40 22 205 69 255 30 143 51 33 119 86 0 45 15 166 77 168 87 35 124 121 197 25 53 109 81 246 225 180 248 202 50 98 61 206 153 239 163 218 207 183 52 236
    // Ans: 128
    //10  12 0 4 3 1 1 12 3 11 11
    //Ans 8
}
