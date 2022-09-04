package problems.miscellaneous;

public class TrappingRainWater {
    public int trap(int[] height) {
        final int n = height.length;
        int max = height[0];
        int[] leftHighs = new int[height.length];
        for(int i=0; i<n; i++) {
            leftHighs[i]=-1;
        }

        for(int i=1; i<height.length-1; i++) {
            if(max > height[i]) {
                leftHighs[i] = max;
            } else {
                max = height[i];
            }
        }

        int answer = 0;
        max = height[n-1];
        for(int i=n-2; i>0; i--) {
            if(max > height[i]) {
                if(leftHighs[i]!=-1)
                    answer += Math.min(max, leftHighs[i])-height[i];
            } else {
                max = height[i];
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        int[] input = {4,2,0,3,2,5};
        System.out.println(new TrappingRainWater().trap(input));
    }
}
