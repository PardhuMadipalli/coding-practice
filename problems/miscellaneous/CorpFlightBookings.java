package problems.miscellaneous;

public class CorpFlightBookings {

    // brute force
    public int[] corpFlightBookingsBrute(int[][] bookings, int n) {
        int[] ans = new int[n];
        for (int[] booking : bookings) {
            int seats = booking[2];
            for (int j = booking[0]; j < booking[1]; j++) {
                ans[j - 1] += seats;
            }
        }
        return ans;
    }

    // Optimal solution
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] diff = new int[n];
        int seats;
        for(int[] booking: bookings) {
            // Instead of increasing each element in the given range,
            // note down only the difference at the first and at the last
            seats = booking[2];
            diff[booking[0] - 1] += seats;

            // booking[1] can be upto n, as they are 1 indexed. But in our array we don't have it.
            // Because we don't need it as we care only until the last flight.
            if (booking[1] < n) {
                diff[booking[1]] -= seats;
            }
        }
        int[] ans = new int[n];
        // By default, all elements are filled with 0
        ans[0] += diff[0];

        // Use the diff array to create the actual ans array
        for(int i=1; i<n; i++) {
            ans[i] = ans[i-1] + diff[i];
        }
        return ans;
    }

    public static void main(String[] args){
//        System.out.println(new CorpFlightBookings().corpFlightBookings());
    }
}
