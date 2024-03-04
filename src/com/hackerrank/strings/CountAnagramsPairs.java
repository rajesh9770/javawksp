package com.hackerrank.strings;

import java.util.Arrays;

/**
 * You are given a string s containing one or more words. Every consecutive pair of words is separated by a single space ' '.
 *
 * A string t is an anagram of string s if the ith word of t is a permutation of the ith word of s.
 *
 * For example, "acb dfe" is an anagram of "abc def", but "def cab" and "adc bef" are not.
 * Return the number of distinct anagrams of s. Since the answer may be very large, return it modulo 109 + 7.
 */
public class CountAnagramsPairs {
    public static int MOD = 1_000_000_007;

    public int countAnagrams(String s) {

        String[] words = s.split("\\s+");

        long ans = 1;
        for(String word: words){
            ans = (ans * countAnagramsSingleWord(word)) % MOD;
        }
        return (int) ans;
    }

    public static long countAnagramsSingleWord(String str){
        int [] counts = new int[26];
        Arrays.fill(counts, 0);
        long[] factorials = new long[str.length()+1];
        factorials[0] = 1;
        for(int i=0; i<str.length(); ++i){
            counts[str.charAt(i) - 'a']++;
            factorials[i+1] = (factorials[i] * (i+1)) % MOD;
        }
        long ans = factorials[str.length()];
        long denominator = 1;
        for (int count : counts) {
            if(count>1){
                denominator = (denominator * factorials[count]) % MOD;
            }
        }
        //a^p = a mod p; so a^(p-1) = a ^(-1) mod p
        long modpow = modpow(denominator, MOD - 2, MOD);

        return (ans*modpow) % MOD;
    }

    /**
     * caluculate a^b modulo m
     * @param base_value
     * @param exponent
     * @param modulus
     * @return
     * base_value and result should be long
     */
    public static long modpow(long base_value, long exponent, long modulus){
        if(exponent == 0) return 1L;

        base_value = base_value % modulus;
        if(exponent == 1) return base_value;
        long result = 1L;
        while(exponent>0){
            if(exponent %2 ==1) {//odd
                result = (result * base_value) % modulus; //this could overflow of result is int
            }
            exponent = exponent/2;
            if(exponent>0){
                base_value = (base_value * base_value) % modulus;  //this could overflow of base_value is int
            }
        }
        return result;
    }
    public static long modpow2(long base_value, long exponent, long modulus){
        long ans =1L;
        for(; exponent>0; exponent /=2){
            if(exponent%2 == 1){
                ans = (ans * base_value) % modulus;
            }
            base_value = (base_value * base_value) % modulus;
        }
        return ans;
    }
    public static void main(String[] args) {
        int too_hot ;
//        too_hot = new CountAnagramsPairs().countAnagrams("too hot");
//        System.out.println(too_hot);
//        too_hot = new CountAnagramsPairs().countAnagrams("aa");
//        System.out.println(too_hot);
        too_hot = new CountAnagramsPairs().countAnagrams("b okzojaporykbmq tybq zrztwlolvcyumcsq jjuowpp");
        System.out.println(too_hot);//210324488
        too_hot = new CountAnagramsPairs().countAnagrams("smuiquglfwdepzuyqtgujaisius ithsczpelfqp rjm");
        System.out.println(too_hot);//200923648

    }
}
