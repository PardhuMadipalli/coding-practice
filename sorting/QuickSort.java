package sorting;


import java.util.Arrays;

public class QuickSort {

    // Lomuto partition algorithm
    private int partition(int[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i+1, end);
        return i+1;
    }

    private int hoarePartition(int[] arr, int begin, int end) {
        int pivot = arr[begin];
        int i = begin - 1;
        int j = end + 1;
        
        while (true) {
            do {
                i++;
            } while (arr[i] < pivot);

            do {
                j--;
            } while (arr[j] > pivot);

            if (i >= j) {
                return j;
            }
            swap(arr, i, j);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void sort(int[] arr, int begin, int end) {
        if(begin < end) {
            int partitionIndex = partition(arr, begin, end);
            sort(arr, begin, partitionIndex - 1); // In Lomuto partition, the pivot is already ay the correct position. So, we don't need to include it in the next sort.
            sort(arr, partitionIndex + 1, end);
        }
    }

    private void sortHoare(int[] arr, int begin, int end) {
        if(begin < end) {
            int partitionIndex = hoarePartition(arr, begin, end);
            sortHoare(arr, begin, partitionIndex); // In Hoare partition, the pivot is not at the correct position. So, we need to include it in the next sort.
            sortHoare(arr, partitionIndex + 1, end);
        }
    }

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int[] input = new int[]{8, 9, 2, 3, 6, 7, 5, 11, 4, 8};
        quickSort.sort(input, 0, input.length-1);

        int[] inputHoare = new int[]{8, 9, 2, 3, 6, 7, 5, 8, 11, 4};
        quickSort.sortHoare(inputHoare, 0, inputHoare.length-1);

        System.out.println(Arrays.toString(input));
        System.out.println(Arrays.toString(inputHoare));
    }
}
