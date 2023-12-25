package com.hackerrank.dynamicprog;

/**
 * let’s say there is a function called eggDrop(e,f)
 * there are two possibilities for egg to drop from a floor (say k)…
 * (The value of k may range from 1 to f.)
 * 1) It Breaks
 * 2) It doesn’t Break
 * The first possibility suggests that all the floors above the floor k breaks the egg.
 * So none of them are threshold but the ones below the floor k including floor k are possible candidates.
 * stating that now we have e-1 number of eggs and k-1 number of floors to check. Hence, to look at the eggDrop(e-1,k-1).
 * On the other hand, the second suggests that that the floors below the floor k also do not break the egg and are not threshold floors and floors above the floor k are the possible candidates. stating that we still have e number of eggs and f-k (total floors -the floor k) number of floors to check. Hence, to look at eggDrop(e,f-k).
 * Now, From these two possibilities we have to select the maximum one since we have to select the worst case
 * therefore, max( eggDrop(e-1,k-1) , eggDrop(e,f-k) )
 * where, loop goes on → for (k in 1:f)
 * now from these results of 1 to f values of k, Minimum of those worsts should be selected
 * min{max( eggDrop(e-1,k-1) , eggDrop(e,f-k) ) , k in 1:f}
 * and since we are making a trial on this floor too, therefore 1 is added with the above solution.
 * eggDrop(e,f) = 1+min{max( eggDrop(e-1,k-1) , eggDrop(e,f-k) ) , k in 1:f}
 */
public class EggDrop {



    public int superEggDrop(int e, int f) {
        int [][] numdrops = new int[e+1][f+1];
        int i,j,x;

        for(j=0;j<=f;j++) numdrops[0][j]=0; //not used
        for(j=0;j<=f;j++) numdrops[1][j]=j; //Because we need j drops if only 1 egg remains, W(1,j)=j.
        for(i=0;i<=e;i++) numdrops[i][0]=0; //Because 0 floors requires no drops, W(n,0)=0.

        //This loop fills up the matrix
        for(i=2;i<=e;i++){
            for(j=1;j<=f;j++){

                //Defines the minimum as the highest possible value
                int minimum=Integer.MAX_VALUE;

                //Evaluates 1+min{max(numeggs[i][j-x],numeggs[i-1][x-1])), for x:1,2,3...j-1,j}
                for(x=1;x<=j;x++) minimum=Math.min(minimum,(1+Math.max(numdrops[i][j-x],numdrops[i-1][x-1])));

                //Defines the minimum value for numeggs[i][j]
                numdrops[i][j]=minimum;
            }

        }
        return numdrops[e][f];
    }
}
