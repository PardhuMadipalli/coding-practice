package problems.miscellaneous;

public class Utf8validation {
    public boolean validUtf8(int[] data) {
        byte feight = 0b1111111;
        int i=0;
        while(i<data.length){
            System.out.println((byte)data[i]);
            i++;
        }
        return true;
    }

    public static void main(String[] args){
        new Utf8validation().validUtf8(new int[]{Integer.MAX_VALUE, 121});
    }
}
