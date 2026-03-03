package com.dispatchsim.dispatchsim.task.executor;


import com.dispatchsim.dispatchsim.websocket.EventPublisher;
import com.dispatchsim.dispatchsim.websocket.dto.SimulationEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.*;

@Component
public class DispatchExecutor extends ThreadPoolExecutor {

    private final EventPublisher eventPublisher;

    public DispatchExecutor(EventPublisher eventPublisher) {
        super(
                4,
                8,
                60,
                TimeUnit.SECONDS,
                new PriorityBlockingQueue<>(),
                new DispatchThreadFactory()
        );
        this.eventPublisher = eventPublisher;
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);

        eventPublisher.publish(
                SimulationEvent.builder()
                        .type("THREAD")
                        .action("STARTED")
                        .threadName(t.getName())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);

        eventPublisher.publish(
                SimulationEvent.builder()
                        .type("THREAD")
                        .action("FINISHED")
                        .threadName(Thread.currentThread().getName())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}