package problems.dp;

import java.util.ArrayList;
import java.util.List;

public class StringPartitioner {

    /**
     * Generates all possible partitions of a given string.
     *
     * @param s The input string.
     * @return A List of Lists of Strings, where each inner list is a partition.
     */
    public static List<List<String>> generateAllPartitions(String s) {
        List<List<String>> result = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return result;
        }
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }

    /**
     * Helper function using backtracking to find all partitions.
     *
     * @param s The original string.
     * @param start The starting index for the current partition.
     * @param currentPartition The current list of substrings in a partition.
     * @param result The final list to store all partitions.
     */
    private static void backtrack(String s, int start, List<String> currentPartition, List<List<String>> result) {
        // Base case: if we have reached the end of the string, add the current partition to the result
        if (start == s.length()) {
            result.add(new ArrayList<>(currentPartition));
            return;
        }

        // Recursive step: try all possible lengths for the current substring
        for (int i = start; i < s.length(); i++) {
            // Extract the current substring
            String sub = s.substring(start, i + 1);

            // Add the current substring to the partition
            System.out.println("Adding " + sub);
            currentPartition.add(sub);

            // Recurse for the remaining part of the string
            backtrack(s, i + 1, currentPartition, result);

            // Backtrack: remove the last added substring to explore other possibilities
            System.out.println("Removing " + currentPartition.get(currentPartition.size()-1));
            currentPartition.remove(sub);
        }
    }

    public static void main(String[] args) {
        String input = "abcd";
        List<List<String>> partitions = generateAllPartitions(input);

        System.out.println("All partitions of the string \"" + input + "\":");
        for (List<String> partition : partitions) {
            System.out.println(partition);
        }
    }
}
