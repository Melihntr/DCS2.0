package AdvancedJava.b1_multithreading;

public class VirtualThreadExample {

    public static void main(String[] args) throws InterruptedException {

        Thread vThread = Thread.startVirtualThread(() -> {
            System.out.println("Virtual thread başladı");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Virtual thread bitti");
        });

        vThread.join();

        System.out.println("Main thread bitti");
    }
}
