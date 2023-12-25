package com.problem;

import java.util.regex.Pattern;

public class ValidateIPAddress {


    static String chunkIPv4 = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";

    static Pattern pattenIPv4 =
            Pattern.compile("^(" + chunkIPv4 + "\\.){3}" + chunkIPv4 + "$");

    static String chunkIPv6 = "([0-9a-fA-F]{1,4})";
    static Pattern pattenIPv6 =
            Pattern.compile("^(" + chunkIPv6 + "\\:){7}" + chunkIPv6 + "$");

    public static String validIPAddress(String IP) {
        if (pattenIPv4.matcher(IP).matches()) return "IPv4";
        return (pattenIPv6.matcher(IP).matches()) ? "IPv6" : "Neither";
    }

    public static void main(String[] args) {
        System.out.println(validIPAddress("123.1.2.0"));
    }

    String ipv4Part = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
    String ipv4Pattern = "^(" + ipv4Part + "\\.){3}" + ipv4Part + "$";

}
