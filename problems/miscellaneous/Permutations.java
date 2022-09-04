package problems.miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutations {
    public List<List<Integer>> permute(int[] nums) {
        List<Integer> input = new ArrayList<>();
        for(int num: nums) {
            input.add(num);
        }
        return recurse(input);
    }

    public List<List<Integer>> recurse(List<Integer> given) {
        List<List<Integer>> answer = new ArrayList<>();
        if(given.size() == 1) {
            List<Integer> sub = new ArrayList<>();
            sub.add(given.get(0));
            answer.add(sub);
            return answer;
        }
        List<Integer> original = new ArrayList<>(given);
        for(int num: original){
            given.remove((Integer) num);
            List<List<Integer>> subanswer = recurse(given);
            for(List<Integer> each: subanswer) {
                each.add(num);
                answer.add(each);
            }
            given.add(num);
        }
        return answer;
    }

    public static void main(String[] args) {
        int[] input = new int[] {1,2,3};
        List<List<Integer>> answer = new Permutations().permute(input);
        for(List<Integer> list: answer) {
            System.out.println(list);
        }
    }
}
