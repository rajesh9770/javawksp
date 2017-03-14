package com.hackerrank;

import java.util.Comparator;


class Player{
    public String name;
    public int score;
    
    Player(String name, int score){
        this.name = name;
        this.score = score;
    }
}

public class Checker<T> implements Comparator<Player> {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compare(Player o1, Player o2) {
		if(o1.score == o2.score){
			return o1.name.compareTo(o2.name);
		}else return o2.score - o1.score;
	}

}
