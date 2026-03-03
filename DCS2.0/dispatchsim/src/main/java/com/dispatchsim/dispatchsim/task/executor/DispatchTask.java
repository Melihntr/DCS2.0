package com.dispatchsim.dispatchsim.task.executor;

public class DispatchTask implements Runnable, Comparable<DispatchTask> {

    private final int priority;
    private final Runnable task;

    public DispatchTask(int priority, Runnable task) {
        this.priority = priority;
        this.task = task;
    }

    @Override
    public void run() {
        task.run();
    }

    @Override
    public int compareTo(DispatchTask o) {
        return Integer.compare(o.priority, this.priority);
    }
}