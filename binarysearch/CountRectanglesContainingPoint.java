package binarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// https://leetcode.com/problems/count-number-of-rectangles-containing-each-point/submissions/
public class CountRectanglesContainingPoint {
    private static final int MAX_Y_VALUE = 100;
    public int[] countRectangles(int[][] rectangles, int[][] points) {
        int[] answer = new int[points.length];
        List<Integer>[] lists = new ArrayList[MAX_Y_VALUE+1];

        for(int[] rectangle: rectangles) {
            if(lists[rectangle[1]]== null) {
                lists[rectangle[1]] = new ArrayList<>();
            }
            lists[rectangle[1]].add(rectangle[0]);
        }
        for(int i=0; i<=MAX_Y_VALUE; i++) {
            if(lists[i]!=null)
                Collections.sort(lists[i]);
        }
        for(int j=0; j<points.length; j++) {
            for(int i=points[j][1]; i<=MAX_Y_VALUE; i++) {
                List<Integer> list = lists[i];
                if(list!=null && list.size()>0) {
                    int ceil = findCeilElementIndexBinarySearch(list, points[j][0], 0, list.size()-1);
                    if(ceil!=-1)
                        answer[j] += list.size()-ceil;
                }
            }
        }

        return answer;
    }

    public static int findCeilElementIndexBinarySearch(List<Integer> list, int key, int low, int high) {
        if(list.size()==0 || key > list.get(high))
            return -1;
        while(low<high) {
            int mid = low + (high-low)/2;
            if(list.get(mid) < key) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return high;
    }
    
    public static void main(String... args) {
        int[][] rects = {{4,7},{4,9},{8,5},{6,2},{6,4}};
        int[][] points = {{4,2}, {5,6}};
        System.out.println(Arrays.toString(new CountRectanglesContainingPoint().countRectangles(rects, points)));
    }
}
