package binarysearch;

public class BinarySearch {

    public static int findExactElementIndex(int[] arr, int key, int low, int high) { // TODO
        if (high >= low) {
            int mid = low + (high - low) / 2;

            // If the element is present at the
            // middle itself
            if (arr[mid] == key)
                return mid;

            // If element is smaller than mid, then
            // it can only be present in left subarray
            if (arr[mid] > key)
                return findExactElementIndex(arr, key, low, mid - 1);

            // Else the element can only be present
            // in right subarray
            return findExactElementIndex(arr, key, mid + 1, high);
        }

        // We reach here when element is not present
        // in array
        return -1;
    }

    public static int findExactElementIndexIterative(int[] arr, int key, int low, int high) { // TODO
        while (low <= high) {
            int mid = low + (high - low) / 2;

            // Check if key is present at mid
            if (arr[mid] == key)
                return mid;

            // If key greater, ignore left half
            if (arr[mid] < key)
                low = mid + 1;

                // If key is smaller, ignore high half
            else
                high = mid - 1;
        }

        // if we reach here, then element was
        // not present
        return -1;
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
