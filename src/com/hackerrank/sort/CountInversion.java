package com.hackerrank.sort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


/**
 * Created by Rajesh on 6/1/2018.
 */
public class CountInversion {


    // Complete the countInversions function below.
    static long countInversions(int[] arr) {
        return mergeSort(arr, 0, arr.length-1);
    }

    private static long mergeSort(int[] arr, int start, int end) {

        if(start >= end) return 0;
        int mid = (start+end)/2;
        long c1 = mergeSort(arr, start, mid);
        long c2 = mergeSort(arr, mid+1, end);
        long c3 = merge(arr, start, mid, end);
        return c1 + c2 + c3;
    }

    /**
     * merges arr[start, mid] and arr[mid+1, end]; both parts are sorted
     * @param arr
     * @param start
     * @param mid
     * @param end
     * @return the number of inversions during the merge phase.
     */
    private static long merge(int[] arr, int start, int mid, int end) {
        int i = start;
        int j = mid+1;
        long inversions = 0;
        int [] temp  = new int [end-start+1]; //store the sorted result here
        int idx = 0;
        while(i<=mid && j<=end){
            if(arr[i] <= arr[j]) {
                temp[idx++] = arr[i];
                ++i;
            }
            else{
                temp[idx++] = arr[j];
                j++;
                inversions += (mid-i+1); // all the elements in right of i i.e. a[i] ... a[mid], from the first array are greater than arr[j]
            }
        }
        //Copy the remaining elements of left subarray (if there are any) to temp
        while(i<=mid) temp[idx++] = arr[i++];

        //Copy the remaining elements of right subarray (if there are any) to temp
        while(j<=end) temp[idx++] = arr[j++];

        //copy back the sorted result back into arr
        for(int k=start; k<=end; ++k){
            arr[k] = temp[k-start];
        }
        return inversions;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void mainForInversion(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] arr = new int[n];

            String[] arrItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int arrItem = Integer.parseInt(arrItems[i]);
                arr[i] = arrItem;
            }

            long result = countInversions(arr);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }


    /**
     * #315, 327
     * You are given an integer array nums and you have to return a new counts array.
     * The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i]. i.e i<j and aj<ai
     */


    public List<Integer> countSmaller(int[] nums) {
        Map<Integer, Integer> smallerCt = new LinkedHashMap<>();// will not work if input has dups
        for(int i: nums) {
            smallerCt.put(i, 0);
        }
        mergeSort(nums, 0, nums.length-1, smallerCt);

        //System.out.println(new ArrayList<>(smallerCt.values()));
        return new ArrayList<>(smallerCt.values());
    }

    void mergeSort(int[] nums, int left, int right, Map<Integer, Integer> smallerCt){
        if(left >= right) return;
        int mid = (left + right)/2;
        mergeSort(nums, left, mid, smallerCt);
        mergeSort(nums, mid+1, right, smallerCt);
        //merge left and right sorted halves
        int i = left;
        int j = mid+1;

        int [] temp  = new int [right-left+1]; //store the sorted result here
        int idx = 0;

        while(i<=mid && j<=right){
            if(nums[i] <= nums[j]) {
                temp[idx++] = nums[i];
                Integer currCt = smallerCt.get(nums[i]);
                currCt += (j-mid-1); // all the elements in left array upto j, i.e. a[mid+1] ... a[j-1], are less than arr[i]
                smallerCt.put(nums[i], currCt);
                ++i;
            }else{
                temp[idx++] = nums[j];
                j++;

            }
        }

        //Copy the remaining elements of left subarray (if there are any) to temp
        while(i<=mid){
            temp[idx++] = nums[i];
            Integer currCt = smallerCt.get(nums[i]);
            currCt += (right-mid); // all the elements in left array are less than arr[i]
            smallerCt.put(nums[i], currCt);
            ++i;
        }

        //Copy the remaining elements of right subarray (if there are any) to temp
        while(j<=right) temp[idx++] = nums[j++];

        /**
         * In case, if we want to remove the last two while loops.
         * for (int i = 0, j = mid+1; i <=mid || j <= right;) {
         *             if (j == right+1 || i <= mid && left[i].val <= right[j].val) {
         *                 arr[i + j] = left[i];
         *                 smaller[left[i].index] += j;
         *                 i++;
         *             } else {
         *                 arr[i + j] = right[j];
         *                 j++;
         *             }
         *         }
         */

    }

    public static void mainForCountSmaller(String[] args) {
        CountInversion countInversion = new CountInversion();
        System.out.println(countInversion.countSmaller(new int[]{5,2,6,1}));
    }

    /**
     * #494 Reverse Pairs: Looks at the solution using BIT.
     * Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].
     *
     * You need to return the number of important reverse pairs in the given array.
     */

    public int reversePairs(int[] nums) {
        return mergeSortForReverse(nums, 0, nums.length-1);
    }

    private static int mergeSortForReverse(int[] arr, int left, int right) {

        if(left >= right) return 0;
        int mid = (left+right)/2;
        int c1 = mergeSortForReverse(arr, left, mid);
        int c2 = mergeSortForReverse(arr, mid+1, right);

        int[] tmp = new int[right-left+1];
        int idx = 0;
        int c3 = 0;

        int j = mid + 1;
        for (int i = left; i <= mid; i++) {
            while (j <= right && arr[i] > arr[j] * 2L) {
                j++;
            }
            c3 += j - (mid + 1);
        }

        //merge left and right subarray
        j=mid+1;
        for(int i=left; i<=mid || j<=right;) {
            if( j>right || i<=mid && ((long)arr[i]) <= ((long)arr[j])){ //mid+1, ...., j-1 are already picked up and now we are picking up a[i], so   mid+1, ...., j-1 are less than ai
                tmp[idx++] = arr[i];
                ++i;
            }else{
                tmp[idx++] = arr[j];
                j++;
            }
        }

        for (int i=left; i<=right; ++i){
            arr[i] = tmp[i-left];
        }
        return c1 + c2 + c3;
    }

    public static void main(String[] args) {



        CountInversion countInversion = new CountInversion();
        int reversePairs = 0;
        reversePairs = countInversion.reversePairs(new int[]{1, 3, 2, 3, 1});
        System.out.println(reversePairs + " "  + (reversePairs==2));

        reversePairs = countInversion.reversePairs(new int[] {2,4,3,5,1});
        System.out.println(reversePairs + " "  + (reversePairs==3));

        reversePairs = countInversion.reversePairs(new int[] {-5, -5}); //1
        System.out.println(reversePairs + " "  + (reversePairs==1));

        reversePairs = countInversion.reversePairs(new int[] {2147483647,2147483647,2147483647,2147483647,2147483647,2147483647});
        System.out.println(reversePairs + " "  + (reversePairs==0));

        reversePairs = countInversion.reversePairs(new int[] {5,5,-5,-5,-5,5});
        System.out.println(reversePairs + " "  + (reversePairs==9));

        reversePairs = countInversion.reversePairs(new int[] {5,5,-5,-5,5});
        System.out.println(reversePairs + " "  + (reversePairs==5));

        reversePairs = countInversion.reversePairs(new int[] {2147483647,2147483647,-2147483647,-2147483647,-2147483647,2147483647});
        System.out.println(reversePairs + " "  + (reversePairs==9));
        reversePairs = countInversion.reversePairs(new int[] {});
        System.out.println(reversePairs + " "  + (reversePairs==9));

    }

    /**
     * Approach 3: BIT
     * Intuition
     *
     * The problem with BST is that the tree can be skewed hence, making it O(n^2)O(n
     * 2
     *  ) in complexity. So, need a data structure that remains balanced. We could either use a Red-black or AVL tree to make a balanced BST, but the implementation would be an overkill for the solution. We can use BIT (Binary Indexed Tree, also called Fenwick Tree) to ensure that the complexity is O(n\log n)O(nlogn) with only 12-15 lines of code.
     *
     * BIT Overview:
     *
     * Fenwick Tree or BIT provides a way to represent an array of numbers in an array(can be visualized as tree), allowing prefix/suffix sums to be calculated efficiently(O(\log n)O(logn)). BIT allows to update an element in O(\log n)O(logn) time.
     *
     * We recommend having a look at BIT from the following link before getting into details:
     *
     * https://www.topcoder.com/community/data-science/data-science-tutorials/binary-indexed-trees/
     * So, BIT is very useful to accumulate information from front/back and hence, we can use it in the same way we used BST to get the count of elements that are greater than or equal to 2 * \text{nums[i]} + 12∗nums[i]+1 in the existing tree and then adding the current element to the tree.
     *
     * Algorithm
     *
     * First, lets review the BIT \text{query}query and \text{update}update routines of BIT. According to the convention, \text{query}query routine goes from \text{index}index to 00, i.e., \text{BIT[i]}BIT[i] gives the sum for the range [0,index][0,index], and \text{update}update updates the values from current \text{index}index to the end of array. But, since, we require to find the numbers greater than the given index, as and when we update an index, we update all the ancestors of the node in the tree, and for \text{search}search, we go from the node to the end.
     *
     * The modified \text{update}update algorithm is:
     *
     * update(BIT, index, val):
     *   while(index > 0):
     *     BIT[index] += val
     *     index -= (index & (-index))
     *
     * Herein, we find get the next index using: \text{index -= index \& (-index)}index -= index & (-index), which is essentially subtracting the rightmost 1 from the \text{index}index binary representation. We update the previous indices since, if an element is greater than the index
     *
     * And the modified \text{query}query algorithm is:
     *
     * query(BIT,index):
     *   sum=0
     *   while(index<BIT.size):
     *     sum+=BIT[index]
     *     index+=(index&(-index))
     *
     * Herein, we find get the next index using: \text{index += index \& (-index)}index += index & (-index). This gives the suffix sum from index to the end.
     *
     * So, the main idea is to count the number of elements greater than 2*\text{nums[i]}2∗nums[i] in range [0,i) as we iterate from 0 to size-1.
     * The steps are as follows:
     *
     * Create a copy of \text{nums}nums, say \text{nums\_copy}nums_copy ans sort \text{nums\_copy}nums_copy. This array is actually used for creating the Binary indexed tree
     * Initialize \text{count}=0count=0 and \text{BIT}BIT array of size \text{size(nums)} + 1size(nums)+1 to store the BIT
     * Iterate over ii from 00 to \text{size(nums)}-1size(nums)−1 :
     * Search the index of element not less than 2*\text{nums[i]}+12∗nums[i]+1 in \text{nums\_copy}nums_copy array. \text{query}query the obtained index+1 in the \text{BIT}BIT, and add the result to \text{count}count
     * Search for the index of element not less than nums[i]nums[i] in \text{nums\_copy}nums_copy. We need to \text{update}update the BIT for this index by 1. This essentially means that 1 is added to this index(or number of elements greater than this index is incremented). The effect of adding 11 to the index is passed to the ancestors as shown in \text{update}update algorithm
     */

    void update(int[] BIT, int index, int val)
    {
        while (index > 0) {
            BIT[index] += val;
            index -= index & (-index);
        }
    }

    int query(int[] BIT, int index)
    {
        int sum = 0;
        while (index < BIT.length) {
            sum += BIT[index];
            index += index & (-index);
        }
        return sum;
    }

    int reversePairsUsingBIT(int[] nums)
    {
        int n = nums.length;
        long [] sorted = new long[n];
        for(int i: nums){
            sorted[i] = nums[i];
        }

        Arrays.sort(sorted);

        int[] BITS = new int[n + 1];
        int count = 0;
        for (int i = 0; i < n; i++) {
            //find the index of 2*num[i]+1, all elements [idx+1, ....] are greater than 2*nums[i]
            //if these elements are already added in BIT, that means those indexes come before i.
            int idx = Arrays.binarySearch(sorted, 2L * nums[i] + 1);

            if (idx<0) idx = ~idx;
            count += query(BITS, idx  + 1);
            update(BITS, Arrays.binarySearch(sorted, nums[i]) + 1, 1);
        }
        return count;
    }
}
