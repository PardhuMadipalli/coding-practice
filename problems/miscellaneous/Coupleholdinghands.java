package miscellaneous;

public class Coupleholdinghands {
    public int minSwapsCouples(int[] row) {
        final int n= row.length;
        int[] indices = new int[n];
        for(int i=0; i<n; i++){
            indices[row[i]] = i;
        }

        int swap =0;
        for(int i=0; i<n; i+=2){
            int a = row[i];
            int b = row[i+1];
            int max = Math.max(a, b);
            int min = Math.min(a, b);
            if(max != min+1 || min%2 != 0) { // not a couple
                // find a's partner
                int aPartnerIndex = indices[a%2 == 0 ? a+1 : a-1];

                row[aPartnerIndex] = b;
                indices[b] = aPartnerIndex;

                swap++;
            }
        }

        return swap;
    }
}
