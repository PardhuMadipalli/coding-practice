package problems.miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class CountNumbersSmallerAfterSelf {
    public List<Integer> countSmaller(int[] nums) {
        Integer[] indexes = new Integer[nums.length];

        for (int i = 0; i < nums.length; i++) {
            indexes[i] = i;
        }

        Arrays.sort(indexes, (ind1, ind2) -> nums[ind1] == nums[ind2] ? ind2-ind1 : nums[ind2]-nums[ind1]);

        List<Integer> numbers = new ArrayList<>();

        IntStream.range(0, nums.length).forEach(numbers::add);

        Integer[] resultArr = new Integer[nums.length];

        for (Integer index : indexes) {
//            System.out.println("number: " + numLoc.num);
            int searchIndex = Collections.binarySearch(numbers, index);
            resultArr[index] = numbers.size() - searchIndex - 1;
            numbers.remove(searchIndex);
        }

        return Arrays.asList(resultArr);

    }

//    private static class NumLoc {
//        int num;
//        int index;
//
//        NumLoc (int num, int index) {
//            this.index=index;
//            this.num=num;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            NumLoc numLoc = (NumLoc) o;
//            return num == numLoc.num && index == numLoc.index;
//        }
//    }

    public static void main(String... args) {
        System.out.println(new CountNumbersSmallerAfterSelf().countSmaller(new int[]{5,2,6,1}));
    }
}
