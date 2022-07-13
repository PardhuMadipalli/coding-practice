package miscellaneous;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class PasswordCracker {

    /*
     * Complete the 'passwordCracker' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING_ARRAY passwords
     *  2. STRING loginAttempt
     */

    public static String passwordCracker(List<String> passwords, String loginAttempt) {

        return ans(passwords, loginAttempt, new HashSet<>()).replaceAll("\\s+$", "");
    }

    public static String ans(List<String> passwords, String att, HashSet<String> notfound) {
        if(att.length() == 0) {
            return att;
        }
        String WRONG = "WRONG PASSWORD";
        if(notfound.contains(att)) {
            return WRONG;
        }
        for(String pass: passwords) {
            if(att.startsWith(pass)) {
                String nextPart =  ans(passwords, att.substring(pass.length()), notfound);

                if(!nextPart.equals(WRONG)) {
                    return pass + " " + nextPart;
                }

            }
        }
        notfound.add(att);
        return WRONG;
    }
}
