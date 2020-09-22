package com.hackerrank.dollar;

import java.util.HashMap;
import java.util.Map;

public class RecurringDecimal {

    public static void main(String[] args) {
        System.out.println(new RecurringDecimal().fractionToDecimal(-1, -2147483648));
    }
    public String fractionToDecimal(int numerator, int denominator) {
         if(numerator ==0) return "0";
        StringBuilder buff = new StringBuilder();

        if ((numerator>=0) ^ (denominator>=0)) buff.append("-");
        long num = Math.abs((long)numerator);
        long deno = Math.abs((long)denominator);

        buff.append(num/deno);
        num = num % deno;
        if (num==0) return buff.toString();

        buff.append(".");
        Map<Long, Integer> idx = new HashMap<>();
        do {
            idx.put(num, buff.length());
            num *=10;
            buff.append(num/deno);
            num = num % deno;
            if (idx.containsKey(num)) {
                buff.insert(idx.get(num), "(");
                buff.append(")");
                return  buff.toString();
            }
        } while (num >0);
        return buff.toString();
    }
}
