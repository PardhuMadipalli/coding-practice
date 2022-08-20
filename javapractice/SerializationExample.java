package javapractice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Student implements Serializable {
    private static final long serialVersionUID=12345678910283L;

    int age;
    String name;

    Student(int a, String name) {
        this.age = a;
        this.name = name;
    }
}

public class SerializationExample {
    public static void main(String... args) throws IOException {
        FileOutputStream fout = new FileOutputStream("abc.txt");
        fout.flush();
        ObjectOutputStream oout = new ObjectOutputStream(fout);
        oout.writeObject(new Student(25, "Pardhu"));
        oout.flush();
        System.out.println("done");
    }
}
