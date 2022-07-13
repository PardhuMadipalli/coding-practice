package problems.miscellaneous;

public class NumOfSubarrays {
    public int numOfSubarrays(int[] arr) {
        int n = arr.length;
        int evens = 0, odds = 0;
        if(arr[0]%2 == 0){
            evens++;
        } else {
            odds++;
        }
        int sum = odds;
        int temp;
        for(int i=1; i<n; i++) {
            if(arr[i]%2 == 0) {
                evens += 1;
            } else {
                temp = odds;
                odds = evens + 1;
                evens = temp;
            }
            int MODV = (int) (1e9 + 7);
            sum += odds;
            sum %= MODV;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(new NumOfSubarrays().numOfSubarrays(new int[]{1,2,3,4,5,6,7}));
    }
}
