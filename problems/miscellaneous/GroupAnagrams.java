package problems.miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://leetcode.com/problems/group-anagrams/
public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for(String str: strs) {
            String sorted = sortString(str);
            List<String> group = map.get(sorted);
            if(group == null) {
                List<String> list = new ArrayList<>();
                list.add(str);
                map.put(sorted, list);
            } else {
                group.add(str);
            }
        }
        return new ArrayList<>(map.values());
    }

    public static String sortString(String inputString)
    {
        char[] tempArray = inputString.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }

    public static void main(String[] args) {

    }
}
