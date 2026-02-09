package problems.miscellaneous;

import java.util.Map;
import java.util.TreeMap;

// https://leetcode.com/problems/my-calendar-ii/description/?envType=problem-list-v2&envId=segment-tree
public class MyCalendarTwo {

    /**
     * The range of intervals where it is booked at least once
     */
    private final TreeMap<Integer, Integer> booked;
    /**
     * The range of intervals where it is booked exactly twice
     */
    private final TreeMap<Integer, Integer> doubleBooked;

    public MyCalendarTwo() {
        booked = new TreeMap<>();
        doubleBooked = new TreeMap<>();
    }

    public boolean book(int startTime, int endTime) {
        // Node with the latest start time but strictly below the endTime
        Map.Entry<Integer, Integer> nearestNodeBeforeEndTime;
        if (doubleBooked.isEmpty() // nothing is double booked
                // No node starts before endTime
                || ((nearestNodeBeforeEndTime = doubleBooked.lowerEntry(endTime)) == null)
                // Node starts and ends before startTime, hence no overlap
                || nearestNodeBeforeEndTime.getValue() <= startTime) {
            updateBookingMaps(startTime, endTime);
            return true;
        }
        return false;
    }

    private void updateBookingMaps(int startTime, int endTime) {
        Map.Entry<Integer, Integer> nearestNodeBeforeEndTime;
        int newRangeStart = startTime;
        int newRangeEnd = endTime;
        // multiple nodes can overlap, use while loop
        while(!booked.isEmpty()
                // if there is an overlap with existing ranges in "booked"
                && !((nearestNodeBeforeEndTime = booked.lowerEntry(endTime)) == null || nearestNodeBeforeEndTime.getValue() <= startTime)) {
            // update doubleBooked with the intersection portion
            doubleBooked.put(Math.max(startTime, nearestNodeBeforeEndTime.getKey()), Math.min(endTime, nearestNodeBeforeEndTime.getValue()));
            // if startTime and endTime range is consumed by the node entirely, no need to modify any ranges
            if (nearestNodeBeforeEndTime.getKey() <= startTime && nearestNodeBeforeEndTime.getValue() >= endTime) {
                return;
            } else {
                // There is some overlap between new booking and the existing node.
                // remove the old node
                booked.remove(nearestNodeBeforeEndTime.getKey());
                // Find the union of the new booking and nearestNodeBeforeEndTime
                newRangeEnd = Math.max(newRangeEnd, Math.max(nearestNodeBeforeEndTime.getValue(), endTime));
                newRangeStart = Math.min(newRangeStart, Math.min(startTime, nearestNodeBeforeEndTime.getKey()));
            }
        }
        // Replace all overlapping nodes with one new node encompassing al of them.
        // This will ensure there are no overlapped regions in booked map.
        booked.put(newRangeStart, newRangeEnd);
    }

    public static void main(String[] args) {
        int[][] input = new int[][]{{},{10,20},{50,60},{10,40},{5,15},{5,10},{25,55}};
        MyCalendarTwo myCalendarTwo = new MyCalendarTwo();
        for (int i=1; i<input.length; i++) {
            System.out.println(myCalendarTwo.book(input[i][0], input[i][1]));
        }
    }
}
