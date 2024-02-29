package com.hackerrank.strings;

public class NumberToWords
{
    private final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private final String[] TENS = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private final String[] THOUSANDS = {"", "Thousand", "Million", "Billion"};

    public String numberToWords(int num) {
        if (num == 0) return "Zero";

        int i = 0;
        String words = "";

        while (num >0) {
            if (num % 1000 != 0)
                words = helper(num % 1000) + " " + THOUSANDS[i] + " " + words;
            num /= 1000;
            i++;
        }

        return words.trim();
    }


    /**
     * Takes care of numbers less than 1000. (<=999)
     * @param num
     * @return
     */
    private String helper(int num) {
        if (num == 0)
            return "";
        if (num <20) {
            return LESS_THAN_20[num];
        } else if(num <100) {
            return (TENS[num/10] + " " + LESS_THAN_20[num%10]).trim();
        } else {
            return  (LESS_THAN_20[num/100] + " Hundred "  + helper( num %100)).trim();
        }
    }

    public static void main(String[] args) {
        NumberToWords numberToWords = new NumberToWords();
//        System.out.println(numberToWords.numberToWords(1234567));
//        System.out.println(numberToWords.numberToWords(50868));
//        System.out.println(numberToWords.numberToWords(100000));
        System.out.println(numberToWords.numberToWords(2000));
    }
}

