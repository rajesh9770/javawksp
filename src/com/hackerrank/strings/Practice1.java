package com.hackerrank.strings;

public class Practice1 {

    /*
    Reverse an integer
     */
    public static int reverse(int x) {
        int ret = 0;
        boolean pos = x>0;
        if (!pos) x = -x;
        while (x > 0){
            ret = ret * 10 + (x % 10);
            x = x / 10;
        }

        if (!pos) ret = -ret;
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(reverse(-210));
        System.out.println(reverse(1534236469));
    }
}
