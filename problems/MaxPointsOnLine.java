package problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

// Not a great problem

public class MaxPointsOnLine {
    public int maxPoints(int[][] points) {
        if(points.length <= 2) return 1;
        HashMap<List<Double>, Set<List<Integer>>> lineCounts = new HashMap<>();
        int max = 0;
        List<Double> maxline = new ArrayList<>();
        for(int i=0; i<points.length; i++){
            for (int j = i+1; j < points.length; j++) {
                double m = ((double) (points[j][1] - points[i][1]))/(points[j][0]-points[i][0]);
                double c = points[i][1] - m * points[i][0];
                List<Double> line = new ArrayList<>();
                line.add(m);
                line.add(c);
//                System.out.printf("Line: %f and %f\n", m, c);
                Set<List<Integer>> value = lineCounts.get(line);
                List<Integer> iList = new ArrayList<>();
                iList.add(points[i][0]);
                iList.add(points[i][1]);
                List<Integer> jList = new ArrayList<>();
                jList.add(points[j][0]);
                jList.add(points[j][1]);
                if(value != null) {
                    value.add(iList);
                    value.add(jList);
                } else {
                    Set<List<Integer>> set = new HashSet<>();
                    set.add(iList);
                    set.add(jList);
                    lineCounts.put(line, set);
                }
                if(lineCounts.get(line).size() > max) {
                    max = lineCounts.get(line).size();
                    maxline = line;
                }
            }
        }
        lineCounts.get(maxline).forEach(System.out::println);
//        lineCounts.entrySet().forEach(System.out::println);
        return max;
    }

    private static class Line {
        double m;
        double c;

        Line(double m, double c) {
            this.m = m;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Line line = (Line) o;
            return Double.compare(line.m, m) == 0 && Double.compare(line.c, c) == 0;
        }

        @Override
        public String toString() {
            return String.format("%f => %f", m, c);
        }

        @Override
        public int hashCode() {
            return Objects.hash(m, c);
        }
    }

    public static void main(String[] args){
//        int[][] input = new int[][]{{1,1},{2,2},{3,3}};
//        int[][] input = new int[][]{{1,1},{3,2},{5,3},{4,1},{2,3},{1,4}};
        int[][] input = new int[][]{{7,3},{19,19},{-16,3},{13,17},{-18,1},{-18,-17},{13,-3},{3,7},{-11,12},{7,19},{19,-12},
            {20,-18},{-16,-15},{-10,-15},{-16,-18},{-14,-1},{18,10},{-13,8},{7,-5},{-4,-9},{-11,2},{-9,-9},
                {-5,-16},{10,14},{-3,4},{1,-20},{2,16},{0,14},{-14,5},{15,-11},{3,11},{11,-10},{-1,-7},{16,7},{1,-11},
                    {-8,-3},{1,-6},{19,7},{3,6},{-1,-2},{7,-3},{-6,-8},{7,1},{-15,12},{-17,9},{19,-9},{1,0},{9,-10},
                        {6,20},{-12,-4},{-16,-17},{14,3},{0,-1},{-18,9},{-15,15},{-3,-15},{-5,20},{15,-14},{9,-17},{10,-14},
                            {-7,-11},{14,9},{1,-1},{15,12},{-5,-1},{-17,-5},{15,-2},{-12,11},{19,-18},{8,7},{-5,-3},
                                {-17,-1},{-18,13},{15,-3},{4,18},{-14,-15},{15,8},{-18,-12},{-15,19},
                                    {-9,16},{-9,14},{-12,-14},{-2,-20},{-3,-13},{10,-7},{-2,-10},{9,10},{-1,7},{-17,-6},
                                        {-15,20},{5,-17},{6,-6},{-11,-8}};
        System.out.println(new MaxPointsOnLine().maxPoints(input));
    }
}