package com.taskflow.web;

import com.taskflow.dto.ChangeStatusRequest;
import com.taskflow.dto.CreateTaskRequest;
import com.taskflow.dto.StatusCount;
import com.taskflow.dto.TaskDto;
import com.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {

    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDto> list(@RequestParam(required = false) String sort) {
        return taskService.findAll(sort);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto create(@Valid @RequestBody CreateTaskRequest request) {
        return taskService.create(request);
    }

    @PatchMapping("/{id}/status")
    public TaskDto changeStatus(@PathVariable Long id, @Valid @RequestBody ChangeStatusRequest request) {
        return taskService.changeStatus(id, request.status());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @GetMapping("/stats")
    public List<StatusCount> stats() {
        return taskService.stats();
    }
}
