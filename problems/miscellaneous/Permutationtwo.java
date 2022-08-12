package problems.miscellaneous;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Permutationtwo {
    public List<List<Integer>> permuteUnique(int[] nums) {
        Set<List<Integer>> resultBackTracking = new HashSet<>();
        Deque<Integer> remaining = new ArrayDeque<>();
        for(int num: nums){
            remaining.add(num);
        }
        addToList(new LinkedList<>(), remaining, nums.length, resultBackTracking);
        return new ArrayList<>(resultBackTracking);
    }

    // backtracking
    private void addToList(LinkedList<Integer> currList, Deque<Integer> remaining, int n, Set<List<Integer>> answer) {
        if(currList.size()==n) {
            answer.add((new LinkedList<>(currList)));
        } else {
            int k = n-currList.size();
            for(int i=0; i<k; i++) {
                currList.addLast(remaining.removeFirst());
                addToList(currList, remaining, n, answer);
                remaining.addLast(currList.removeLast());
            }
        }
    }

    // recursion
    private Set<List<Integer>> findAns(int[] nums, int start, int end) {
        Set<List<Integer>> answer = new HashSet<>();
        if(start==end) {
//            System.out.println("start end are " + nums[start]);
            List<Integer> list = new ArrayList<>(1);
            list.add(nums[start]);
            answer.add(list);
        } else {
            for(List<Integer> list : findAns(nums, start+1, end)) {
                for(int i=0; i<=list.size(); i++) {
                    List<Integer> curr = new ArrayList<>(list);
//                    System.out.printf("added %d to %s\n", nums[start], list);
                    curr.add(i, nums[start]);
                    answer.add(curr);
                }
            }
        }
        return answer;
    }



    public static void main(String[] args) {
        int[] input = new int[]{1,2,3};
        for(List<Integer> li : new Permutationtwo().permuteUnique(input)) {
            System.out.println("------------");
            System.out.println(li);
        }
    }
}
