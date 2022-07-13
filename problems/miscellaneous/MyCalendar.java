package miscellaneous;

import java.util.Map;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/my-calendar-i
 */
public class MyCalendar {

    private final TreeMap<Integer, Integer> map;
    public MyCalendar(){
        map = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        Map.Entry<Integer, Integer> lessThanEnd;
        if (map.isEmpty() || (lessThanEnd = map.lowerEntry(end)) == null || start >= lessThanEnd.getValue()) {
            map.put(start, end);
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        MyCalendar calendar = new MyCalendar();
        System.out.println(calendar.book(47, 50));
        System.out.println(calendar.book(33, 41));
        System.out.println(calendar.book(39, 45));
        System.out.println(calendar.book(33, 42));
        System.out.println(calendar.book(25, 32));
        System.out.println(calendar.book(26, 35));
        System.out.println(calendar.book(19, 25));
//[[],[47,50],[33,41],[39,45],[33,42],[25,32],[26,35],[19,25],[3,8],[8,13],[18,27]]
        System.out.println();
    }
}
