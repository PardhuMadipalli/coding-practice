package javapractice;

public class ArrayUnique {
    public static int minIncrementForUnique(int[] nums) {
        int max = 100000+1;
        int res=0;
        boolean[] found = new boolean[max];
        for(int i=0; i<nums.length; i++) {
            for(int j=nums[i]; j<=max; j++) {
                if(!found[j]){
                    res += (j-nums[i]);
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
