package com.taskflow.web.servlet;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

/** Makes Spring Boot register classes annotated with @WebServlet / @WebFilter. */
@Configuration
@ServletComponentScan(basePackageClasses = ServletConfig.class)
public class ServletConfig {
}
