package com.dispatchsim.dispatchsim.task.executor;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DispatchThreadFactory implements ThreadFactory {

    private final AtomicInteger counter = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName("Dispatch-Worker-" + counter.getAndIncrement());
        return t;
    }
}