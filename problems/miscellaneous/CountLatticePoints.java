package problems.miscellaneous;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CountLatticePoints {
    public int countLatticePoints(int[][] circles) {

        Set<Circle> circlesSet = new HashSet<>();

        for(int[] circle: circles) {
            circlesSet.add(new Circle(circle));
        }
        HashSet<Point> answerPoints = new HashSet<>();
        for(Circle circle: circlesSet) {

            // add center
            answerPoints.add(new Point(circle.x, circle.y));

            // same line points
            for(int i=1; i<=circle.r;i++) {
                answerPoints.add(new Point(circle.x+i, circle.y));
                answerPoints.add(new Point(circle.x-i, circle.y));
                answerPoints.add(new Point(circle.x, circle.y+i));
                answerPoints.add(new Point(circle.x, circle.y-i));
            }

            // other points
            int radSquare = circle.r * circle.r;
            for(int x=1; x<=circle.r; x++) {
                for(int y=1; y<= circle.r; y++) {
                    if(x*x + y*y <= radSquare) {
                        addAllQuardrantPoints(answerPoints, circle.x, circle.y, x, y);
                    }
                }
            }
        }
//        answerPoints.forEach(System.out::println);
        return answerPoints.size();
    }

    private void addAllQuardrantPoints(HashSet<Point> answerPoints, int cx, int cy, int x, int y) {
        answerPoints.add(new Point(cx+x, cy+y));
        answerPoints.add(new Point(cx-x, cy+y));
        answerPoints.add(new Point(cx+x, cy-y));
        answerPoints.add(new Point(cx-x, cy-y));
    }

    private static class Point {
        final int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return
                    "x=" + x +
                    ", y=" + y;
        }
    }

    private static class Circle {
        final int x, y, r;

        public Circle(int[] arr) {
            this.x = arr[0];
            this.y = arr[1];
            this.r = arr[2];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Circle circle = (Circle) o;
            return x == circle.x && y == circle.y && r == circle.r;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, r);
        }
    }

    public static void main(String... args) {
        int[][] input = {{0,0,3}}, input2 = {{2,2,2},{3,4,1}};
        int[][] input3 = {{8,9,6},{9,8,4},{4,1,1},{8,5,1},{7,1,1},{6,7,5},{7,1,1},{7,1,1},{5,5,3}};
        System.out.println(new CountLatticePoints().countLatticePoints(input3));
    }
}
