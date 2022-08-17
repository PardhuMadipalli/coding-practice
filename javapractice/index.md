<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**

- [Java and Object-Oriented Programming](#java-and-object-oriented-programming)
  - [OOP Concepts](#oop-concepts)
    - [Abstraction](#abstraction)
    - [Encapsulation](#encapsulation)
    - [Inheritance](#inheritance)
    - [Polymorphism](#polymorphism)
  - [Java Interview Questions](#java-interview-questions)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Java and Object-Oriented Programming

## OOP Concepts

### Abstraction
Classes, abstract classes, inheritance all abstract the complete details of the members of it.

### Encapsulation
Wrapping up of data under a single unit. For example, we declare all instance variables as private and access a few of the, using setters and getters.

### Inheritance
Classes are made reusable and they can have parent child relationships.

### Polymorphism
One item taking multiple forms is called polymorphism. For example method overloading and overriding.

- [Method overloading demo](https://github.com/PardhuMadipalli/coding-practice/blob/main/javapractice/MethodOverloadingDemo.java)
- [Method overriding demo](https://github.com/PardhuMadipalli/coding-practice/blob/main/javapractice/MethodOverridingDemo.java)
- **[Producer Extends Consumer Super](https://stackoverflow.com/questions/4343202/difference-between-super-t-and-extends-t-in-java/4343547#4343547)**

## Java Interview Questions

- #### Can you access private members (methods and fields) of an object from outside the class?
<details>
  <summary>Answer</summary>
Yes, we can access them using Java reflection API. 
Check this [GeeksforGeeks article](https://www.geeksforgeeks.org/how-to-access-private-field-and-method-using-reflection-in-java) to understand how to do it.
</details>

- #### Does HashMap allow null key?
<details>
  <summary>Answer</summary>
Yes, one null key.
</details>

- #### What are differences between abstract class and interface?
<details>
  <summary>Answer</summary>
  - Multiple interfaces can be implemented by a single class. It is not possible extend multiple abstract classes.
  - Abstract class should be used when there is a parent-child relationship. Interface is used when you want to define only a partial behaviour.
</details>

