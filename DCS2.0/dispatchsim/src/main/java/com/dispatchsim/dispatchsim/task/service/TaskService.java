package com.dispatchsim.dispatchsim.task.service;

import com.dispatchsim.dispatchsim.task.entity.TaskLog;
import com.dispatchsim.dispatchsim.task.executor.DispatchExecutor;
import com.dispatchsim.dispatchsim.task.executor.DispatchTask;
import com.dispatchsim.dispatchsim.websocket.EventPublisher;
import com.dispatchsim.dispatchsim.websocket.dto.SimulationEvent;
import com.dispatchsim.dispatchsim.task.repository.TaskLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final DispatchExecutor executor;
    private final TaskLogRepository repository;
    private final EventPublisher eventPublisher;

    public void createCpuTask(int priority) {

        // Task CREATED event
        eventPublisher.publish(
                SimulationEvent.builder()
                        .type("TASK")
                        .action("CREATED")
                        .taskType("CPU")
                        .timestamp(LocalDateTime.now())
                        .build()
        );

        DispatchTask task = new DispatchTask(priority, () -> {

            TaskLog log = TaskLog.builder()
                    .type("CPU")
                    .priority(String.valueOf(priority))
                    .createdAt(LocalDateTime.now())
                    .build();

            // Task STARTED event
            eventPublisher.publish(
                    SimulationEvent.builder()
                            .type("TASK")
                            .action("STARTED")
                            .taskType("CPU")
                            .threadName(Thread.currentThread().getName())
                            .timestamp(LocalDateTime.now())
                            .build()
            );

            log.setStartedAt(LocalDateTime.now());

            long start = System.currentTimeMillis();

            // CPU-bound simülasyon
            fibonacci(40);

            long end = System.currentTimeMillis();

            log.setFinishedAt(LocalDateTime.now());
            log.setExecutionTime(end - start);
            log.setStatus("SUCCESS");

            repository.save(log);

            // Task COMPLETED event
            eventPublisher.publish(
                    SimulationEvent.builder()
                            .type("TASK")
                            .action("COMPLETED")
                            .taskType("CPU")
                            .threadName(Thread.currentThread().getName())
                            .value(end - start)
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        });

        executor.execute(task);
    }

    private long fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}}