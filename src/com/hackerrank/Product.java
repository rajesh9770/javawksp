package com.hackerrank;

public class Product {
//    The goal is to define a function that takes an array of numbers and returns an array of numbers of the same length.
//            I'll call the input array 'in' and the output array 'out'. Each element of the output array out[i] should be equal
//    to the product of all of the elements of the input array _except_ for in[i].  No zero in inpiut
//    Example: in = { 1, 2, 3, 4} out = {24, 12, 8, 6}

    public static int[] process(int [] input){
        int[] out = new int [input.length];
        int product = 1;
        for(int i=0; i<input.length; ++i){
            product *= input[i];

        }
        for(int i=0; i<input.length; ++i){
            out[i] = product/input[i];
        }
        return out;

    }



//    public static int[] process2(int [] input){
//        int[] out = new int [input.length];
//        int product = 1;
//        int zeroCt = 0;
//        int zeroIdx= -1;
//        for(int i=0; i<input.length; ++i){
//            out[i] = 0;
//        }
//
//        for(int i=0; i<input.length; ++i){
//
//            if(input[i] == 0){
//                zeroCt++;
//                zeroIdx = i;
//            }else{
//                product *= input[i];
//            }
//        }
//        if(zeroCt == 0 ){
//            for(int i=0; i<input.length; ++i){
//                out[i] = product/input[i];
//            }
//        }else if(zeroCt==1){
//            out[i] = product;
//        }
//
//        return out;
//
//    }

    /**
    Now not use division, assume no zeros

 2 3 4 5

    f  2 6 24 120
    b 120 60 24 5

    Ans: 60 40 30 24
    **/
    public static int[] processWithoutDivison(int [] input){ //{2,3,4,5}
        int[] out = new int [input.length];

        int [] f = new int[input.length]; //{2,6,24,120}
        int [] b = new int[input.length]; //{120,60,24,5}

        f[0] = input[0];
        for(int i=1; i<input.length; ++i){
            f[i] = f[i-1] * input[i];
        }

        b[input.length-1] = input[input.length-1];
        for(int i=input.length-2; i>=0; --i){
            b[i] = b[i+1] * input[i];
        }

        for(int i=0; i<input.length; ++i){
            if(i>0 && i+1 <input.length) {
                out[i] = f[i-1] * b[i+1];
            }
            if(i==0) out[i] = b[1];
            if(i==input.length-1) out[i] = f[i-1];

        }
        return out;

    }





}
