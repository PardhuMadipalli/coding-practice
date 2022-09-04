package problems.miscellaneous;

import java.util.Arrays;

public class MultiplyLargerNumbers {

    private int[] multiplyBigNumbers(int[] a, int[] b) {
        final int blength = b.length, alength = a.length, reslength = alength+blength;
        int[] result = new int[reslength];
        int carry=0;
        for(int i=blength-1; i>=0; i--) {
            int bdigit = b[i];
            carry=0;
            for(int j=alength-1; j>=0; j--) {
                int resdigit  = reslength - 1 - (alength-i-1+ blength-j-1);
//                System.out.printf("mulliplying  %d with %d and a carry %d at digit: %d\n", bdigit, a[j], carry, resdigit);
                int product = result[resdigit] + bdigit*a[j] + carry;
                result[resdigit] = product%10;
                carry = product/10;
            }
            result[reslength -1 - (alength + blength-i-1)] += carry;
        }
        return result;
    }

    public static void main(String... args) {
        int[] a = {9,8,0};
        int[] b = {7,3,4};
        System.out.println("expected: " + 980*734);
        System.out.println(Arrays.toString(new MultiplyLargerNumbers().multiplyBigNumbers(a, b)));
    }
}
