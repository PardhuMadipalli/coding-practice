package javapractice;

// Java program to print sums
// of all possible subsets.
import java.io.*;

public class SubsetSums {

    // Prints sums of all
    // subsets of arr[l..r]
    static void subsetSums(int[] arr, int l, int r, int sum)
    {

        // Print current subset
        if (l > r) {
            System.out.println(sum + " ");
            return;
        }

        // Subset including arr[l]
        subsetSums(arr, l + 1, r, sum + arr[l]);

        // Subset excluding arr[l]
        subsetSums(arr, l + 1, r, sum);
    }

    // Driver code
    public static void main(String[] args)
    {
        int[] arr = { 5, 4, 3 };
        int n = arr.length;

        subsetSums(arr, 0, n - 1, 0);
    }
}

// This code is contributed by anuj_67
