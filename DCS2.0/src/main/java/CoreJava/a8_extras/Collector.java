package CoreJava.a8_extras;

import  java.util.*;
import java.util.stream.Collectors;

public class Collector {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Apple", "Banana", "Cherry");
        Set<String> set = list.stream().collect(Collectors.toSet());
        System.out.println(set); // [Apple, Banana, Cherry]
        List<Person> people = Arrays.asList(
                new Person("Alice", 30),
                new Person("Bob", 25),
                new Person("Charlie", 35)
        );

        Map<String, Integer> ageMap = people.stream()
                .collect(Collectors.toMap(
                        Person::getName,
                        Person::getAge
                ));
        System.out.println(ageMap); // {Alice=30, Bob=25, Charlie=35}
    }
}
class Person{
    private String name;
    private int age;
    public  Person(String name,int age){
        this.name=name;
        this.age=age;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }

}
