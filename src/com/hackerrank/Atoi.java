package com.hackerrank;

/**
 * The algorithm for myAtoi(string s) is as follows:
 *
 * Read in and ignore any leading whitespace.
 * Check if the next character (if not already at the end of the string) is '-' or '+'. Read this character in if it is either.
 * This determines if the final result is negative or positive respectively. Assume the result is positive if neither is present.
 * Read in next the characters until the next non-digit character or the end of the input is reached. The rest of the string is ignored.
 * Convert these digits into an integer (i.e. "123" -> 123, "0032" -> 32). If no digits were read, then the integer is 0.
 * Change the sign as necessary (from step 2).
 * If the integer is out of the 32-bit signed integer range [-2^31, 2^(31) - 1], then clamp the integer so that it remains in the range.
 * Specifically, integers less than -231 should be clamped to -2^31, and integers greater than 2^(31) - 1 should be clamped to 2^(31) - 1.
 * Return the integer as the final result.
 * Note:
 *
 * Only the space character ' ' is considered a whitespace character.
 * Do not ignore any characters other than the leading whitespace or the rest of the string after the digits.
 */
public class Atoi {

    public int myAtoi(String s) {
        int i = 0;

        int maxValueDivide10 = Integer.MAX_VALUE/10;
        int magic = Integer.MAX_VALUE%10;
        while (i < s.length() && s.charAt(i) == ' '){
            ++i;
            continue;
        }
        boolean isNegative = i < s.length() && s.charAt(i) == '-';
        if (isNegative || (i < s.length() && s.charAt(i) == '+')) ++i;
        char c;
        int ans = 0;
        while (i < s.length()) {
            c = s.charAt(i++);
            if ('0' <= c && c <= '9') {
                int add = (c - '0');
                if(ans > maxValueDivide10 || (ans == maxValueDivide10 && add> magic)){
                    if(isNegative) return Integer.MIN_VALUE;
                    else return Integer.MAX_VALUE;
                }

                ans = ans * 10 + (c - '0');
            } else {
                break;
            }
        }
        if(isNegative) ans = - ans;
        if (ans > Integer.MAX_VALUE) ans = Integer.MAX_VALUE;
        if (ans < Integer.MIN_VALUE) ans = Integer.MIN_VALUE;
        return (int) ans;
    }

    public static void main1(String[] args) {
        long input = Integer.MAX_VALUE ;//2147483647
        input +=10L;
        System.out.println(input);
        System.out.println(new Atoi().myAtoi("" + input));
        input = Integer.MAX_VALUE;
        System.out.println(new Atoi().myAtoi("" + input));

        System.out.println(new Atoi().myAtoi("" + "9223372036854775808"));
        System.out.println(new Atoi().myAtoi("" + "-21474836470"));
    }

    /**
     * A valid number can be split up into these components (in order):
     *
     * A decimal number or an integer.
     * (Optional) An 'e' or 'E', followed by an integer.
     * A decimal number can be split up into these components (in order):
     *
     * (Optional) A sign character (either '+' or '-').
     * One of the following formats:
     * One or more digits, followed by a dot '.'.
     * One or more digits, followed by a dot '.', followed by one or more digits.
     * A dot '.', followed by one or more digits.
     * An integer can be split up into these components (in order):
     *
     * (Optional) A sign character (either '+' or '-').
     * One or more digits.
     * For example, all the following are valid numbers:
     * ["2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"],
     * while the following are not valid numbers: ["abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"].
     *
     * Given a string s, return true if s is a valid number.
     * [-+]?(([0-9]+(.[0-9]*)?)|.[0-9]+)(e[-+]?[0-9]+)?
     */

    public static boolean isNumber(String s) {
        int len = s.length();
        boolean isDotSeen = false,isESeen = false, isSignESeen=false, witinN= false, withinEx=false;
        int i=0;
        char c = s.charAt(i);
        //first char can be digit, ., +, -
        boolean isValid = isDigit(c) || isSign(c) || (isDotSeen = isDot(c));
        if(!isValid) return false;
        ++i;

        //parse the number - only chars allowed are digits, . and E
        for(; i<s.length(); ++i){
            c = s.charAt(i);
            if(isDigit(c)) continue;
            if(isDot(c)){
                if(isDotSeen) return false;
                isDotSeen = true;

                continue;
            }

            if(isExp(c)) {
                if(isESeen) return false;
                isESeen = true;
                ++i; //before breaking move to the next character
                break;
            }
            //sign can come only in the beginning
            return false;
        }
        //we are either processed the string or at the next character after E
        if(i==s.length()){
            if(isESeen) return false; //string does not have exponent but have E in the end
        }

        //parse the exponent
        //no E, dot allowed anymore
        //first char can be sign or digit
        if(i<s.length()){
            c = s.charAt(i);
            isSignESeen = isSign(c);
            if(isSignESeen || isDigit(c)){
                ++i;
            }else{
                return false;
            }
        }
        for(; i<s.length(); ++i){
            if(!isDigit(s.charAt(i))) return false;
        }
        return true;
    }


    public static boolean isDigit(char c){
        return (c>='0' && c<='9');
    }
    public static boolean isDot(char c){
        return (c == '.');
    }
    public static boolean isExp(char c){
        return (c == 'e' || c == 'E');
    }
    public static boolean isSign(char c){
        return (c == '+' || c == '-');
    }

    public static void main(String[] args) {
        String [] good = new String[]{"2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789", "10."};
        String [] bads = new String[] {"abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53", ".", "+.", "+", "+E"};
        //System.out.println(isNumber("2"));
        //System.out.println(isNumber("-90E3"));
        System.out.println(isNumber("3e+7"));
        System.out.println(isNumber("+6e-1"));

        for (String in : good) {
            System.out.println(in + " " + isNumber(in));
        }

        for (String in : bads) {
            System.out.println(in + " " + isNumber(in));
        }
    }
}
