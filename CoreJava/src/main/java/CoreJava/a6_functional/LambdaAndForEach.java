package CoreJava.a6_functional;

import java.util.*;

class Employee {
    private String name;
    private int age;
    private double salary;

    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }
}

class Main {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee("John Doe", 30, 50000.0));
        employees.add(new Employee("Jane Doe", 25, 40000.0));
        employees.add(new Employee("Bob Smith", 40, 60000.0));
        employees.add(new Employee("Alice Johnson", 35, 55000.0));

        // Lambda ve Foreach
        System.out.println("Lambda ve Foreach:");
        employees.forEach(employee -> System.out.println("Name: " + employee.getName() + ", Age: " + employee.getAge() + ", Salary: " + employee.getSalary()));

        // Lambda ve Foreach ile Filtreleme
        System.out.println("\nLambda ve Foreach ile Filtreleme:");
        employees.stream()
                .filter(employee -> employee.getAge() > 30)
                .forEach(employee -> System.out.println("Name: " + employee.getName() + ", Age: " + employee.getAge() + ", Salary: " + employee.getSalary()));

        // Lambda ve Foreach ile Sıralama
        System.out.println("\nLambda ve Foreach ile Sıralama:");
        employees.stream()
                .sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .forEach(employee -> System.out.println("Name: " + employee.getName() + ", Age: " + employee.getAge() + ", Salary: " + employee.getSalary()));

        // Method Reference
        System.out.println("\nMethod Reference:");
        employees.forEach(System.out::println);
    }
}