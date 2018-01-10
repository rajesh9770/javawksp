package com.hackerrank.recursion;

import java.util.Scanner;

/**
 * Created by Rajesh on 1/10/2018.
 */
public class BowlingSG {

    static private int [] SGNums = new int[300];
    static{
        SGNums[0] = 0;
        SGNums[1] = 1;
        SGNums[2] = 2;
        for(int i=3; i<SGNums.length; ++i) SGNums[i] = -1;
    }

    static private int getGrundyNumberOfAGame(int game){
        if(SGNums[game] != -1) return SGNums[game];
        boolean [] mexValues = new boolean[300];
        for(int i=0; i<= (game-1)/2; ++i){
           int mexValue = getGrundyNumberOfAGame(i) ^ getGrundyNumberOfAGame(game-1-i);
           mexValues[mexValue] = true;
        }
        for(int i=0; i<= (game-2)/2; ++i){
            int mexValue = getGrundyNumberOfAGame(i) ^ getGrundyNumberOfAGame(game-2-i);
            mexValues[mexValue] = true;
        }
        int SGNum = 0;
        while(mexValues[SGNum]) ++SGNum;

        SGNums[game] = SGNum;
        return SGNum;
    }

    static private String isWinning(int n, String config) {
        String[] games = config.split("X");
        int SGNum = 0;
        for(String game: games){
            SGNum ^= getGrundyNumberOfAGame(game.length());
        }
        return SGNum>0 ? "WIN" : "LOSE";
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for(int a0 = 0; a0 < t; a0++){
            int n = in.nextInt();
            String config = in.next();
            String result = isWinning(n, config);
            System.out.println(result);
        }
        in.close();
    }
}
