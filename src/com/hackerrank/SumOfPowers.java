package com.hackerrank;

import java.util.Scanner;

public class SumOfPowers {

	public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int x = scan.nextInt(), n = scan.nextInt();
        scan.close();
        System.out.println(pof(x, n, 0));
    }

    static int pof(int x, int n, int i) {
        x = x - (int) Math.pow(i, n);
        if (x == 0) return 1;
        if (x > 0) {
            int sum = 0;
            for (int j = i + 1; Math.pow(j, n) <= x; j++)
                sum += pof(x, n, j);
            return sum;
        } else return 0;
    }

}
