package com.hackerrank.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateIPAddress {


    static String chunkIPv4 = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";

    static Pattern pattenIPv4 =
            Pattern.compile("^(" + chunkIPv4 + "\\.){3}" + chunkIPv4 + "$");

    //IPv6 addresses are eight groups of four hexadecimal digits, separated by colons.
    static String chunkIPv6 = "([0-9a-fA-F]{1,4})";
    static Pattern pattenIPv6 =
            Pattern.compile("^(" + chunkIPv6 + "\\:){7}" + chunkIPv6 + "$");

    public static String validIPAddress(String IP) {
        if (pattenIPv4.matcher(IP).matches()) return "IPv4";
        return (pattenIPv6.matcher(IP).matches()) ? "IPv6" : "Neither";
    }

    public static void main(String[] args) {
        //System.out.println(validIPAddress("123.1.2.0"));
        quantifier();
    }

    String ipv4Part = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
    String ipv4Pattern = "^(" + ipv4Part + "\\.){3}" + ipv4Part + "$";

    public  static void quantifier(){
        String possesive = "(f*)+(bar)";
        Pattern compile = Pattern.compile(possesive);
        Matcher matcher = compile.matcher("ffffbar");
        boolean matches = matcher.matches();
        System.out.println(matches + " " + matcher.regionStart() + "-" + matcher.regionEnd());
        System.out.println(matcher.group());
        System.out.println(matcher.groupCount());
        for(int i=0; i<matcher.groupCount(); ++i){
            System.out.println(matcher.group(i));
        }
        System.out.println("---");
    }

}
