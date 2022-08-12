package javapractice;

public class ArrayUnique {
    public static int minIncrementForUnique(int[] nums) {
        int max = 100000+1;
        int res=0;
        boolean[] found = new boolean[max];
        for (int num : nums) {
            for (int j = num; j <= max; j++) {
                if (!found[j]) {
                    res += (j - num);
                    // System.out.println("i is " + i + " diff is "+(j-nums[i]));
                    found[j] = true;
                    break;
                }
            }
        }
        return res;
    }

    static void mod(String i) {
        i = i+ "abc";
        System.out.println(i);
    }

    public static void main(String[] args) {
        String a = "abc";
        mod(a);
        System.out.println(a);
    }
}
