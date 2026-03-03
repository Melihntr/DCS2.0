package com.dispatchsim.dispatchsim.websocket.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SimulationEvent {

    private String type;        // THREAD, TASK, GC
    private String action;      // STARTED, FINISHED, CREATED, GC_RUN
    private String threadName;
    private String taskType;
    private Long value;         // memory, executionTime vs
    private LocalDateTime timestamp;
}