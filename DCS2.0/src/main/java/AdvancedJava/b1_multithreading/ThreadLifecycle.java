package AdvancedJava.b1_multithreading;

class ThreadLifecycle {
    public static void main(String[] args) {
        // Thread.State enum'undaki tüm durumları yazdır
        for (Thread.State state : Thread.State.values()) {
            System.out.println(state);
        }
    }
}