package com.dispatchsim.dispatchsim.task.repository;

import com.dispatchsim.dispatchsim.task.entity.TaskLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskLogRepository extends JpaRepository<TaskLog, UUID> {
}