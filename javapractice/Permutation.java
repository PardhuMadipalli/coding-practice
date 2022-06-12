package javapractice;

import java.util.*;

public class Permutation {

    static void permute(String s , String answer)
    {
        if (s.length() == 0)
        {
//            System.out.println(answer + " ");
            return;
        }

        System.out.println(" answer: " + answer + " s: " + s);

        for(int i = 0 ;i < s.length(); i++)
        {
            char ch = s.charAt(i);
            String left_substr = s.substring(0, i);
            String right_substr = s.substring(i + 1);
            String rest = left_substr + right_substr;
            permute(rest, answer + ch);
        }
    }

    // Driver code
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        String s;
        String answer="";

        System.out.print("Enter the string : ");
        s = scan.next();

        System.out.println("\nAll possible strings are : ");
        permute(s, answer);
    }
}

// This code is contributed by adityapande88

