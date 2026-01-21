package CoreJava.a6_functional;

import java.util.*;
import java.util.stream.*;

class Employee2 {
    private String name;
    private int age;
    private double salary;

    public Employee2(String name, int age, double salary) {
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

class Main2 {
    public static void main(String[] args) {
        List<Employee2> employees = new ArrayList<>();

        employees.add(new Employee2("John Doe", 30, 50000.0));
        employees.add(new Employee2("Jane Doe", 25, 40000.0));
        employees.add(new Employee2("Bob Smith", 40, 60000.0));
        employees.add(new Employee2("Alice Johnson", 35, 55000.0));

        // Stream API
        System.out.println("Stream API:");

        // Filtreleme
        System.out.println("\nFiltreleme:");
        List<Employee2> filteredEmployees = employees.stream()
                .filter(employee -> employee.getAge() > 30)
                .collect(Collectors.toList());
        filteredEmployees.forEach(employee -> System.out.println("Name: " + employee.getName() + ", Age: " + employee.getAge() + ", Salary: " + employee.getSalary()));

        // Sıralama
        System.out.println("\nSıralama:");
        List<Employee2> sortedEmployees = employees.stream()
                .sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .collect(Collectors.toList());
        sortedEmployees.forEach(employee -> System.out.println("Name: " + employee.getName() + ", Age: " + employee.getAge() + ", Salary: " + employee.getSalary()));

        // Gruplama
        System.out.println("\nGruplama:");
        Map<Integer, List<Employee2>> groupedEmployees = employees.stream()
                .collect(Collectors.groupingBy(Employee2::getAge));
        groupedEmployees.forEach((age, employeeList) -> {
            System.out.println("Age: " + age);
            employeeList.forEach(employee -> System.out.println("Name: " + employee.getName() + ", Salary: " + employee.getSalary()));
        });

        // İstatistikler
        System.out.println("\nİstatistikler:");
        DoubleSummaryStatistics salaryStats = employees.stream()
                .mapToDouble(Employee2::getSalary)
                .summaryStatistics();
        System.out.println("Count: " + salaryStats.getCount());
        System.out.println("Sum: " + salaryStats.getSum());
        System.out.println("Average: " + salaryStats.getAverage());
        System.out.println("Min: " + salaryStats.getMin());
        System.out.println("Max: " + salaryStats.getMax());
    }
}
