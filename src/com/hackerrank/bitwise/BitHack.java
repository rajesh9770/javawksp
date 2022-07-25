package com.hackerrank.bitwise;

public class BitHack {

    static int getMSB(int n){
        if(n==0) return 0;
        int msb =0;
        n /=2;
        while(n !=0){
            ++msb;
            n/=2;
        }
        return msb;
    }
    //#476. Number Complement
    public static int findComplement(int num) {
        int msb = getMSB(num);
        for(int i=0; i<=msb; ++i){ //flip the bits from lsb to msb.
            num ^= (1<<i);
        }
        return num;
    }

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(5));
        System.out.println(Integer.toBinaryString(findComplement(5)));
    }
}
