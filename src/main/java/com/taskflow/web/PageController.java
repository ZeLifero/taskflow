package com.taskflow.web;

import com.taskflow.dto.StatusCount;
import com.taskflow.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.Map;

/** Server-side rendered page: Spring MVC controller -> JSP view (WEB-INF/jsp/tasks.jsp). */
@Controller
public class PageController {

    private final TaskService taskService;

    public PageController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/tasks";
    }

    @GetMapping("/tasks")
    public String tasks(@RequestParam(defaultValue = "priority") String sort, Model model) {
        Map<String, Long> stats = new LinkedHashMap<>();
        for (StatusCount s : taskService.stats()) {
            stats.put(s.status().name(), s.count());
        }
        model.addAttribute("tasks", taskService.findAll(sort));
        model.addAttribute("stats", stats);
        model.addAttribute("sort", sort);
        return "tasks";
    }
}
