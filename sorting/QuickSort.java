package sorting;


import java.util.Arrays;

public class QuickSort {

    private int partition(int[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin-1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
//                System.out.printf("swapping %d and %d\n", arr[i], arr[j]);
                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
//                System.out.println(Arrays.toString(arr));
            }
        }

        int swapTemp = arr[i+1];
        arr[i+1] = arr[end];
        arr[end] = swapTemp;

        return i+1;
    }

    private void sort(int[] arr, int begin, int end) {
        if(begin < end) {
            int partitionIndex = partition(arr, begin, end);
            sort(arr, begin, partitionIndex - 1);
            sort(arr, partitionIndex + 1, end);
        }
    }

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int[] input = new int[]{8, 9, 2, 3, 6, 7, 5, 11, 4};
        quickSort.sort(input, 0, 8);
        System.out.println(Arrays.toString(input));
    }
}
