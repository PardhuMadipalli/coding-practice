package problems.miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

public class SkylineProblem {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        TreeSet<Building> treeSet = new TreeSet<>(Comparator.comparingInt(b -> b.start));

        Location[] locations = new Location[buildings.length*2];
        List<Location> resultLoc = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();

        for(int i=0; i<buildings.length; i++) {
            Building building = new Building(buildings[i][0], buildings[i][1], buildings[i][2]);
            locations[2*i]   = new Location(building, buildings[i][0], true);
            locations[2*i+1] = new Location(building, buildings[i][1], false);
        }

        Arrays.sort(locations, Comparator.comparingInt(l -> l.pos));



        for (Location location : locations) {
            System.out.println(location.pos + " " + location.isStart);
            if (location.isStart) {
                if (treeSet.isEmpty() || treeSet.last().height < location.building.height) {
                    ArrayList<Integer> loc = new ArrayList<>();
                    loc.add(location.pos);
                    loc.add(location.building.height);
                    result.add(loc);
                }
                treeSet.add(location.building);
            } else {
                treeSet.remove(location.building);
                if (!treeSet.isEmpty() && treeSet.last().height != result.get(result.size() - 1).get(1)) {
                    // If the height changed after removal, add it to the list
                    ArrayList<Integer> loc = new ArrayList<>();
                    loc.add(location.pos);
                    loc.add(treeSet.last().height);
                    result.add(loc);
                } else if(treeSet.isEmpty()) {
                    ArrayList<Integer> loc = new ArrayList<>();
                    loc.add(location.pos);
                    loc.add(0);
                    result.add(loc);
                }
            }
        }
        return result;
    }

    private static class Building {
        int start;
        int end;
        int height;

        public Building(int start, int end, int height) {
            this.start = start;
            this.end = end;
            this.height = height;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Building building = (Building) o;
            return start == building.start && end == building.end && height == building.height;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end, height);
        }
    }

    private static class Location{
        Building building;
        int pos;
        boolean isStart;

        public Location(Building building, int pos, boolean isStart) {
            this.building = building;
            this.pos = pos;
            this.isStart = isStart;
        }
    }

    public static void main(String[] args) {
        int[][] inp1 = new int[][]{{2,9,10},{3,7,15},{5,12,12},{15,20,10},{19,24,8}};
        int[][] inp2 = new int[][]{{0,2,3}, {2,5,3}};
        System.out.println(new SkylineProblem().getSkyline(inp2));
    }
}
