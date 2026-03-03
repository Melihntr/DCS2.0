package com.dispatchsim.dispatchsim.task.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskLog {

    @Id
    @GeneratedValue
    private UUID id;

    private String type; // CPU or IO

    private String priority;

    private LocalDateTime createdAt;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private Long executionTime;

    private String status;

    private Long memorySnapshot;
}