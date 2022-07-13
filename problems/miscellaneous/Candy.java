package miscellaneous;

class Candy {
    int[] rates;
    int[] values;
    int[] funcs;
    public int candy(int[] ratings) {
        rates = ratings;
        final int n = ratings.length;
        if(n==1) return 1;
        values = new int[n];
        funcs = new int[n];

        if(ratings[0] <= ratings[1]){
            values[0] = 1;
        } else {
            funcs[0] = 1;
        }

        for(int i=1; i<n-1; i++){
            // greater than both neighbours
            if(ratings[i]>ratings[i+1]) {
                if(ratings[i] > ratings[i-1]) {
                    funcs[i] = -1;
                } else {
                    funcs[i] = i+1;
                }
            } else if(ratings[i] > ratings[i-1]) {
                funcs[i] = i-1;
            } else {
                values[i] = 1;
            }
        }

        if(ratings[n-1] > ratings[n-2]) {
            funcs[n-1] = n-2;
        } else {
            values[n-1] = 1;
        }

        int sum=0;
        for(int i=0; i<n; i++){
            sum+=get(i);
        }
        return sum;
    }

    private int get(int k) {
        if(values[k] != 0) {
            return values[k];
        }
        return values[k] = funcs[k] >= 0 ?
                get(funcs[k]) + 1 : Math.max(get(k - 1), get(k + 1)) + 1;
    }

    public static void main(String[] args){
        int[] input = new int[]{1,0,2};
        System.out.println(new Candy().candy(input));
    }
}