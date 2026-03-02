package com.dispatchsim.dispatchsim.task.controller;


import com.dispatchsim.dispatchsim.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/cpu")
    public void createCpuTask(@RequestParam(defaultValue = "1") int priority) {
        taskService.createCpuTask(priority);
    }
}