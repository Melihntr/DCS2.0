package CoreJava.a4_core_api;

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age && name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) throws InterruptedException {
        Person person1 = new Person("John", 30);
        Person person2 = new Person("John", 30);
        Person person3 = new Person("Jane", 25);

        // equals() metodu
        System.out.println("equals() metodu:");
        System.out.println(person1.equals(person2)); // Output: true
        System.out.println(person1.equals(person3)); // Output: false

        // hashCode() metodu
        System.out.println("\nhashCode() metodu:");
        System.out.println(person1.hashCode() == person2.hashCode()); // Output: true
        System.out.println(person1.hashCode() == person3.hashCode()); // Output: false

        // toString() metodu
        System.out.println("\ntoString() metodu:");
        System.out.println(person1.toString()); // Output: Person{name='John', age=30}

        // getClass() metodu
        System.out.println("\ngetClass() metodu:");
        System.out.println(person1.getClass().getName()); // Output: Person

        // clone() metodu (protected olduğu için kullanılabilir)
        System.out.println("\nclone() metodu:");
        try {
            Person personClone = (Person) person1.clone();
            System.out.println(personClone.toString()); // Output: Person{name='John', age=30}
        } catch (CloneNotSupportedException e) {
            System.out.println("CloneNotSupportedException");
        }

        // wait() ve notify() metodları
        System.out.println("\nwait() ve notify() metodları:");
        final Object lock = new Object();
        new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Thread 1: Waiting...");
                    lock.wait();
                    System.out.println("Thread 1: Notified");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        Thread.sleep(1000);
        synchronized (lock) {
            System.out.println("Main Thread: Notifying...");
            lock.notify();
        }
    }
}
