package javapractice;

class Person
{
    public Person()
    {
        System.out.println("Person class constructor called");
    }
}
class Employee extends Person
{
    public Employee()
    {
        System.out.println("Employee class empty constructor called");
    }

    public Employee(int salary) {
        this();
        System.out.println("Salary is " + salary);
    }

}

public class ImplicitConstructorDemo {
    public static void main (String args[])
    {
        Employee e = new Employee(20); // prints both Person and Employee constructors

        // Output:
        /*
Person class constructor called
Employee class empty constructor called
Salary is 20

         */
    }
}
