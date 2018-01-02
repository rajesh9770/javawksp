package com.hackerrank.graphs;

import com.hackerrank.DoNotCheckIn.Solution;

import java.io.*;
import java.util.*;

import com.hackerrank.utils.InputReader;


/**
 * Created by Rajesh on 1/2/2018.
 */
public class RepairRoads {

    static Map<Integer, Set<Integer>> adjLists = new HashMap<>();
    static int n;
    static boolean [] nodeMarking;
    static Set<Integer> edgeMarking;

    static Path[] pathsFromLeaf;
    static Set<Integer>[] pathsFromCNode;
    static int specialCNode;


    public static void main(String[] args) throws FileNotFoundException {

        //InputReader in = new InputReader(System.in);
        InputReader in = new InputReader(new FileInputStream("C:\\Users\\manjrr\\workspace\\javawksp\\data\\repairRoad-5"));
        int q = in.nextInt();
        while(q-->0){
            n = in.nextInt();
            nodeMarking = new boolean[n];
            edgeMarking = new HashSet<>(n);
            adjLists.clear();
            pathsFromLeaf = new Path[n];
            pathsFromCNode = new Set[n+1]; // slot n for specialCNode
            specialCNode = n;


            for(int i=0; i<n; ++i) adjLists.put(i, new HashSet<>());
            for(int i=0; i<n-1; ++i){
                int a = in.nextInt();
                int b = in.nextInt();
                adjLists.get(a).add(b);
                adjLists.get(b).add(a);
            }
            solve();
        }
    }

    private static void initialContraction(){
        boolean [] leaves = new boolean[n];
        for( Map.Entry<Integer, Set<Integer>> entries : adjLists.entrySet()) {
            if (entries.getValue().size() == 1 ) {
                leaves[entries.getKey()] = true;
            }
        }
        for(int i = 0; i<n ; ++i){
            if(leaves[i]) removeLeaf(i, true);
        }
    }

    private static void removeLeaf(Integer leafToRemove, boolean contract){
        if(nodeMarking[leafToRemove]) throw new RuntimeException("This leaf " + leafToRemove + " is marked; can't be removed" );
        Integer parent = adjLists.get(leafToRemove).iterator().next();
        int parentSize = adjLists.get(parent).size(); //parent's original size

        if(contract) nodeMarking[parent] = true;
        adjLists.remove(leafToRemove);
        adjLists.get(parent).remove(leafToRemove);

        if(parentSize==2){//parent will become a leaf after we remove the child, new path will now start from the parent.
            if(pathsFromLeaf[leafToRemove] != null){
                Path oldPathStartingFromLeaf = pathsFromLeaf[leafToRemove];
                pathsFromLeaf[leafToRemove] = null;
                oldPathStartingFromLeaf.deleteHead();
                pathsFromLeaf[parent] = oldPathStartingFromLeaf;
                Set<Integer> leaves = pathsFromCNode[oldPathStartingFromLeaf.getCNode()];
                if(leaves == null){
                    leaves = new HashSet<>();
                    pathsFromCNode[oldPathStartingFromLeaf.getCNode()] =  leaves;
                }
                leaves.add(parent);
                leaves.remove(leafToRemove);
            }
        }else if(parentSize==3){ //parent was a CNode, but now it will become regular node
            if(pathsFromLeaf[leafToRemove] != null){ //child-parent was a CNOde path; after child is removed we do not need this path.
                Path path = pathsFromLeaf[leafToRemove];
                pathsFromLeaf[leafToRemove] = null;
                pathsFromCNode[path.getCNode()].remove(leafToRemove); //not checking if size reduces to zero.
            }
            {// there might be another CNode path toward this parent from another leaf which we need to adjust now by further going up the parent.
                if(pathsFromCNode[parent] != null && pathsFromCNode[parent].size() >0){
                    if(pathsFromCNode[parent].size()==1) {
                        Integer anotherLeaf = pathsFromCNode[parent].iterator().next();
                        Path pathToParent = pathsFromLeaf[anotherLeaf];

                        pathsFromCNode[parent] = null;
                        pathsFromLeaf[anotherLeaf] = null;
                        //List<Integer> path = pathToParent_CNode.getKey();

                        int previousNodeOnPath = pathToParent.getElement(pathToParent.size() - 2);

                        Path pathFromParentToCNode = findPathTillCNode(parent, previousNodeOnPath);
                        pathFromParentToCNode.deleteHead(); //remove the leading parent.
                        pathToParent.concatenate(pathFromParentToCNode);
                        pathsFromLeaf[anotherLeaf] = pathToParent;
                        int newCNode = pathToParent.getCNode();

                        Set<Integer> leaves = pathsFromCNode[newCNode];
                        if(leaves == null){
                            leaves = new HashSet<>();
                            pathsFromCNode[newCNode] = leaves;
                        }
                        leaves.add(anotherLeaf);

                    }else{// there are two another leaves leading to this parent. In this case just concatenate their paths and set cNode to -1.
                        Iterator<Integer> it = pathsFromCNode[parent].iterator();
                        Integer anotherLeaf1 = it.next();
                        Integer anotherLeaf2 = it.next();
                        Path path1ToParent = pathsFromLeaf[anotherLeaf1];
                        Path path2ToParent = pathsFromLeaf[anotherLeaf2];

                        pathsFromCNode[parent] = null;
                        pathsFromLeaf[anotherLeaf1] = null;
                        pathsFromLeaf[anotherLeaf2] = null;

                        path2ToParent.deleteTail();
                        path1ToParent.concatenateReverse(path2ToParent, specialCNode);

                        int startingLeaf  = path1ToParent.getElement(0);
                        pathsFromLeaf[startingLeaf] =  path1ToParent;
                        Set<Integer> leaves = pathsFromCNode[specialCNode];
                        if(leaves == null) {
                            leaves = new HashSet<>();
                            pathsFromCNode[specialCNode] =  leaves;
                        }
                        leaves.add(startingLeaf);
                    }
                }

            }
        }else{ // parentSize > 3;  parent remains a CNode
            if(pathsFromLeaf[leafToRemove] != null){ //child-parent was a CNOde path; after child is removed we do not need this path.
                Path path = pathsFromLeaf[leafToRemove];
                pathsFromLeaf[leafToRemove] = null;
                pathsFromCNode[path.getCNode()].remove(leafToRemove); //not checking if size reduces to zero.
            }
        }
    }

    private static void removeCPath(Path path){
        int previousNode = -1;
        int cNode = path.getCNode();
        for(int i=0; i<path.size(); ++i){
            int node = path.getElement(i);
            if(node != cNode){
                adjLists.remove(node);
                if(pathsFromLeaf[node] != null){
                    Path pathFromNode = pathsFromLeaf[node];
                    pathsFromLeaf[node] = null;
                    pathsFromCNode[pathFromNode.getCNode()].remove(node); //not checking if size reduces to zero.
                }
                previousNode = node;
            }else{
                Set<Integer> neighborsOfCNode = adjLists.get(cNode);
                neighborsOfCNode.remove(previousNode);
                neighborsOfCNode.remove(path.getElement(i+1));
                nodeMarking[cNode] = false;
                for(int neigh : neighborsOfCNode){
                    edgeMarking.add(getEdgeHash(neigh,cNode));
                }
            }
        }
    }

    private static int getEdgeHash(int i, int j){
        int a = i<j ? i : j;
        int b = i<j ? j : i;
        return (b*(b+1)/2) + a;
    }

    private static Path findPathTillCNode(int start, int previous){
        Path path = new Path(n);
        int child = start;
        boolean [] visited = new boolean[n];
        if(previous>=0) visited[previous] = true;
        int n_CNode=specialCNode;
        while(true){
            path.add(child);
            visited[child] = true;
            Set<Integer> neighbors = adjLists.get(child);
            if(neighbors.size()>2) {
                n_CNode = child;
                break;
            }
            if(neighbors.size() == 1){
                Integer next = neighbors.iterator().next();
                if(visited[next]){ //A-B @B
                    n_CNode = specialCNode;
                    break;
                }
                child = next;
            }else {
                Iterator<Integer> it = neighbors.iterator();
                Integer next1 = it.next();
                Integer next2 = it.next();
                int parent = (!visited[next1]) ? next1: next2;
                if(visited[parent]) break; //should not happen
                child = parent;
            }
        }
        path.setCNode(n_CNode);
        return path;
    }

    private static Path findCPath(){
        if(adjLists.size() == 1){
            Path path = new Path(1);
            int ele = adjLists.keySet().iterator().next();
            path.add(ele);
            return path;
        }
        Set<Integer> wholeTree = pathsFromCNode[specialCNode];
        if(wholeTree != null && !wholeTree.isEmpty()){
            return pathsFromLeaf[wholeTree.iterator().next()];
        }

        for( Map.Entry<Integer, Set<Integer>> entries : adjLists.entrySet()){
            Integer child = entries.getKey();
            int numChildNeighbors = entries.getValue().size();
            if(numChildNeighbors == 1){//leaf
                Path path = null;
                Integer n_CNode = null;
                if(pathsFromLeaf[child] != null){
                    path = pathsFromLeaf[child];
                    n_CNode = path.getCNode();
                }else {
                    path = findPathTillCNode(child, -1);
                    n_CNode = path.getCNode();
                    pathsFromLeaf[child] =  path;
                    Set<Integer> leaves = pathsFromCNode[n_CNode];
                    if(leaves == null){
                        leaves = new HashSet<>();
                        pathsFromCNode[n_CNode] = leaves;
                    }
                    leaves.add(child);
                }
                if(pathsFromCNode[n_CNode].size() >1){
                    Iterator<Integer> leaves = pathsFromCNode[n_CNode].iterator();
                    Path path1_CNode = pathsFromLeaf[leaves.next()];
                    Path path2_CNode = pathsFromLeaf[leaves.next()];

                    path2_CNode.deleteTail();
                    path1_CNode.concatenateReverse(path2_CNode, n_CNode);
                    return path1_CNode;
                }else if(n_CNode == -1){
                    return path;
                }
            }
        }
        throw new RuntimeException("CPath not found");
    }


    private static long solve() {
        long robots = 0;
        initialContraction();

        while(adjLists.size()>0) {
            Path cPath = findCPath();
            ++robots;

            int node = cPath.getCNode();
            removeCPath(cPath);
            while (adjLists.containsKey(node) && adjLists.get(node).size() == 1 && !nodeMarking[node]) {
                int parent = adjLists.get(node).iterator().next();
                if (edgeMarking.contains(getEdgeHash(node, parent))) { //node is not marked, edge is marked, we can delete the node
                    removeLeaf(node, false);
                } else { //node is not marked, edge is not marked, we can contract the node
                    removeLeaf(node, true);
                }
                node = parent;
            }
            if (adjLists.size() == 1) {
                ++robots;
                break;
            }
        }
        System.out.println(robots);
        return robots;
    }


    private static class Path{
        int [][] arr;
        int cNode;
        int numCols = 1000;
        int currRow, currCol; //next avail slot
        int deleted; //only deletion allowed is from front and back

        public Path(int maxSize){
            int numRows = maxSize/numCols;
            if(maxSize % numCols > 0 ) ++numRows;
            arr = new int [numRows][numCols];
            currRow = 0;
            currCol = 0;
            cNode = specialCNode;
            deleted = 0;
        }

        /**
         * appends an element
         * @param a - new element
         */
        public void add(int a){
            arr[currRow][currCol] = a;
            currCol = (currCol + 1) % numCols;
            if(currCol == 0 ){
                ++currRow;
                arr[currRow] = new int [numCols];
            }
        }

        public int getCNode(){
            return cNode;
        }

        public void setCNode(int cNode){
            this.cNode = cNode;
        }

        public int getStartingLeaf(){
            return getElement(0);
        }

        /**
         * appends elements from another path to this path and sets CNode of this path equal to the other path
         * @param anotherPath
         */
        public void concatenate(Path anotherPath){
            int size = anotherPath.size();
            for (int i=0; i<size; ++i){
                add(anotherPath.getElement(i));
            }
            this.cNode = anotherPath.cNode;
        }

        /**
         * appends elements from another path to this path in reverse order and sets cNode of this path equal to the specified cNode
         * @param anotherPath
         * @param cNode
         */
        public void concatenateReverse(Path anotherPath, int cNode){
            int size = anotherPath.size();
            for (int i=size-1; i>=0; --i){
                add(anotherPath.getElement(i));
            }
            this.cNode = cNode;
        }

        /**
         * returns the first element of this path, skips the deleted elements, if any.
         * @return
         */
        public int getFirst(){
            return getElement(0);
        }

        /**
         * returns the element at index i of this path, skips the deleted elements, if any.
         * @return
         */
        public int getElement(int index) {
            index += deleted;
            int row = index/numCols;
            int col = index%numCols;
            return arr[row][col];
        }

        public int deleteHead() {
            ++deleted;
            return deleted;
        }

        public int deleteTail() {
            if(currCol>0) --currCol;
            else{
                --currRow;
                currCol = numCols-1;
            }
            return deleted;
        }

        //@Override
        public int size() {
            return (currRow)*numCols + currCol - deleted;
        }

        //@Override
        public boolean isEmpty() {
            return currRow == 0 && currCol ==0;
        }
    }

}