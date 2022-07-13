package miscellaneous;

import java.util.Arrays;

public class IntNoConsOnes {

    int[] powtwoansers;
    public int findIntegers(int n) {
        if(n<3) return n+1;
        int totBits = findBits(n);
        powtwoansers = new int[totBits+1];
        Arrays.fill(powtwoansers, -1);
        powtwoansers[0] = 0;
        powtwoansers[1] = 0;
        powtwoansers[2] = 1;
        for(int i=3; i<=totBits; i++){
            powtwoansers[i] = countForNBits(i);
        }
        int p = findconsecs(n);
        return n+1-p;
    }

    public int findconsecs(int n){
        if(n<3) return 0;
        if(n==3) return 1;
        int totBits = findBits(n);

        if(n == (1<<totBits) - 1)
            return powtwoansers[totBits];

        int ans = powtwoansers[totBits-1];

        int q = n&((1 << (totBits-2)) -1); // Mask first two bits of the number

        if(kthbit(n, totBits-1) == 1) { // If the 2nd most significant bit is 1
            ans += powtwoansers[totBits-2] + q + 1;
        } else {
            ans += findconsecs(q);
        }

        return ans;
    }

    int kthbit(int n, int k){
        return (n & (1 << (k - 1))) >> (k - 1);
    }

    int findBits(int n) {
        int count = 0;
        while(n != 0){
            n>>=1;
            count++;
        }
        return count;
    }

    int countForNBits(int n) {
        if(powtwoansers[n]!=-1) return powtwoansers[n];
        powtwoansers[n] = countForNBits(n-1) + countForNBits(n-2) + (1 << (n-2));
        return powtwoansers[n];
    }

    public static void main(String[] args) {
        System.out.println(new IntNoConsOnes().findIntegers(1));
    }
}

