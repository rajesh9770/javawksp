package com.hackerrank;

import java.util.Scanner;

import com.hackerrank.CountLuck.IntWrapper;

public class CountLuck {

    public static class IntWrapper {
            int count;
    }

    public CountLuck() {
        // TODO Auto-generated constructor stub
    }

    public static boolean isValidMove(char[][] mesh, int nx, int ny) {
        if(nx>=0 && ny>=0 && nx<mesh.length && ny <mesh[0].length && mesh[nx][ny] != 'X') return true;
        else return false;
    }
    
    public static boolean DFS(char [][] mesh, int x, int y, IntWrapper ct) {
        boolean found = false;

        if(mesh[x][y] == '*') {
            found = true;
            return found;
        }
        
        mesh[x][y] = 'X';
        int options = 0;
        if(isValidMove(mesh, x, y-1)){
            ++options;
            found |= DFS(mesh, x, y-1, ct);
        }
        if(isValidMove(mesh, x, y+1)){
            ++options;
            found |= DFS(mesh, x, y+1, ct);
        }
        if(isValidMove(mesh, x-1, y)){
            ++options;
            found |= DFS(mesh, x-1, y, ct);
        }
        if(isValidMove(mesh, x+1, y)){
            ++options;
            found |= DFS(mesh, x+1, y, ct);
        }
        if(found && options>1){
            ct.count++;
        }
        return found;
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int testcases = in.nextInt();
        while(testcases-- >0){
            int n = in.nextInt();
            int m = in.nextInt();
            int x=0,y=0;
            in.nextLine();
            char [][] mesh = new char[n][m];
            for(int i=0;i<n; ++i){
                String row = in.nextLine();
                for(int j=0;j<m; ++j){
                    mesh[i][j] = row.charAt(j);
                    if(mesh[i][j] == 'M'){
                        x = i; y= j;
                    }
                }
            }
            int guess = in.nextInt();
//            for(int i=0;i<n; ++i){
//                for(int j=0;j<m; ++j){
//                    System.out.print(mesh[i][j]);
//                }
//                System.out.println("");
//            }
//            System.out.println(guess + " " + mesh.length + " " + mesh[0].length);
            
            IntWrapper ct = new IntWrapper();
            ct.count = 0;
            DFS(mesh, x, y, ct);
            if(guess == ct.count) System.out.println("Impressed");
            else System.out.println("Oops!");
        }

    }

}
