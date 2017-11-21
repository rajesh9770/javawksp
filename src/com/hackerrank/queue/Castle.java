package com.hackerrank.queue;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


/**
 * Created by Rajesh on 11/20/2017.
 * This is modified BFS. getNeighbors will add all nodes on all straight directions(NEWS) originating from the given node
 * till X is encountered. This also yields that to reach the a neighbor, we need to turn the direction, since if a new neighbor was in the
 * same direction as parent was reached from it's predecessor then this neighbor would have been added when parent's predecessor' neighbors were found.
 */
public class Castle {

    static int minimumMoves(String[] grid, int startX, int startY, int goalX, int goalY) {

        Queue<Pair> nodesOnBFS = new LinkedList<>();
        nodesOnBFS.add(new Pair(startX, startY));
        boolean [][] visited = new boolean[grid.length][grid.length];
        for(int i=0; i<grid.length; ++i){
            for(int j=0; j<grid.length; ++j){
                visited[i][j] = false;
            }
        }

        while(!nodesOnBFS.isEmpty()){
            Pair node = nodesOnBFS.remove();
            visited[node.row][node.col] = true;
            Queue<Pair> neighbors = getNeighbors(grid, node.row, node.col, visited);
            for(Pair neighbor : neighbors){
                neighbor.turns = node.turns + 1;
                if(neighbor.row == goalX && neighbor.col == goalY){
                    return neighbor.turns;
                }
                nodesOnBFS.add(neighbor);
            }
        }
        return -1;
    }

    static class Pair{
        int row, col;
        int turns=0;
        public Pair(int r, int c) {
            row = r;
            col = c;
        }
    }

    static Queue<Pair> getNeighbors(String[] grid, int row, int col, boolean [] [] visited){
        Queue<Pair> neighbors = new LinkedList<Pair>();

        for(int i=col+1; i<grid.length &&  grid[row].charAt(i) != 'X' ; ++i) {//go east
            if (!visited[row][i]) {
                neighbors.add(new Pair(row, i));
                visited[row][i] = true;
            }
        }
        for(int i=col-1; i>=0 &&  grid[row].charAt(i) != 'X' ; --i) {//go west
            if (!visited[row][i]) {
                neighbors.add(new Pair(row, i));
                visited[row][i] = true;
            }
        }
        for(int i=row-1; i>=0 &&  grid[i].charAt(col) != 'X' ; --i) {//north
            if (!visited[i][col]) {
                neighbors.add(new Pair(i, col));
                visited[i][col] = true;
            }
        }
        for(int i=row+1; i<grid.length &&  grid[i].charAt(col) != 'X' ; ++i) {//south
            if (!visited[i][col]) {
                neighbors.add(new Pair(i, col));
                visited[i][col] = true;
            }
        }
        return neighbors;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String[] grid = new String[n];
        for(int grid_i = 0; grid_i < n; grid_i++){
            grid[grid_i] = in.next();
        }
        int startX = in.nextInt();
        int startY = in.nextInt();
        int goalX = in.nextInt();
        int goalY = in.nextInt();
        int result = minimumMoves(grid, startX, startY, goalX, goalY);
        System.out.println(result);
        in.close();
    }
}
