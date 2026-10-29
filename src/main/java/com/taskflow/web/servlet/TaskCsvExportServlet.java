package com.taskflow.web.servlet;

import com.taskflow.dto.TaskDto;
import com.taskflow.service.TaskService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * A "classic" Java EE servlet (Servlet API directly, no Spring MVC).
 * It gets the Spring bean from the web application context.
 */
@WebServlet(name = "taskCsvExport", urlPatterns = "/export/tasks.csv")
public class TaskCsvExportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TaskService taskService = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext())
                .getBean(TaskService.class);

        resp.setContentType("text/csv");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=\"tasks.csv\"");

        try (PrintWriter out = resp.getWriter()) {
            out.println("id,title,status,priority,due_date,project");
            for (TaskDto t : taskService.findAll("priority")) {
                out.println(String.join(",",
                        String.valueOf(t.getId()),
                        csv(t.getTitle()),
                        t.getStatus().name(),
                        t.getPriority().name(),
                        t.getDueDate() == null ? "" : t.getDueDate().toString(),
                        csv(t.getProjectName())));
            }
        }
    }

    private static String csv(String value) {
        if (value == null) return "";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
