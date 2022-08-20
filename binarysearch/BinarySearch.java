package binarysearch;

public class BinarySearch {

    public static int findExactElementIndex(int[] arr, int key, int low, int high) { // TODO
        return 0;
    }


    /**
     * Find the first index of the key element greater than or equal to in the given array.
     * @return First index, if it exists, else -1
     */
    public static int findCeilElementIndexBinarySearch(int[] arr, int key, int low, int high) {
        if(key > arr[high])
            return -1;
        while(low<high) {
            int mid = low + (high-low)/2;
            if(arr[mid] < key) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return high;
    }

    public static void main(String... args) {
        int[] input = {3, 3,4,5, 6, 6, 6, 7, 8};
        System.out.println(findCeilElementIndexBinarySearch(input, 3, 0, input.length-1));
    }
}
