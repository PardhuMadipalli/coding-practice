package javapractice;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Main {
    // Main method is only called for void String args[]
    public static void main(String[] args) {
        Base64.Encoder encoder = Base64.getEncoder();

        String name = "pardhu";

        byte[] base64encodedbytes = encoder.encode(name.getBytes(StandardCharsets.UTF_8));

        System.out.println(encoder.encodeToString(name.getBytes(StandardCharsets.UTF_8)));

        String encoded = new String(base64encodedbytes, StandardCharsets.UTF_8);

        System.out.println(new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8));
    }
}
