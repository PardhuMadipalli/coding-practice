package javapractice;

// Static methods are not overridden. They just look like they're getting overridden
class Vehicle {
    void printDimensions(){
        System.out.println("2x2");
    }

    static void printType() {
        System.out.println("vehicle");
    }
}

class FourWheeler extends Vehicle {
    void printDimensions(){
        System.out.println("3x3");
    }

    static void printType() {
        System.out.println("Fourwheeler");
    }
}

class Car extends FourWheeler {
    void printDimensions(){
        System.out.println("4x4");
    }
    static void printType() {
        System.out.println("car");
    }
}


public class MethodOverridingDemo {
    public static void main(String[] args) {
        Car car = new Car();
        car.printDimensions(); // prints 4x4
        car.printType(); // prints car

        FourWheeler fourWheeler = car;
        fourWheeler.printDimensions(); // prints 4x4
        fourWheeler.printType(); // prints fourWheeler

        Vehicle vehicle = car;
        vehicle.printDimensions(); // prints 4x4
        vehicle.printType(); // prints vehicle
    }
}
