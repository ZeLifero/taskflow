<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TaskFlow</title>
    <style>
        body { font-family: system-ui, sans-serif; margin: 2rem auto; max-width: 960px; color: #222; }
        table { border-collapse: collapse; width: 100%; margin-top: 1rem; }
        th, td { border-bottom: 1px solid #ddd; padding: .5rem; text-align: left; }
        th { background: #f4f4f4; }
        .tag { background: #e8f0fe; border-radius: 4px; padding: 1px 6px; margin-right: 4px; font-size: .85em; }
        .stats span { margin-right: 1rem; font-weight: 600; }
    </style>
</head>
<body>
<h1>TaskFlow</h1>

<p class="stats">
    <c:forEach items="${stats}" var="entry">
        <span><c:out value="${entry.key}"/>: <c:out value="${entry.value}"/></span>
    </c:forEach>
</p>

<p>
    Sort by:
    <a href="<c:url value='/tasks?sort=priority'/>">priority</a> |
    <a href="<c:url value='/tasks?sort=dueDate'/>">due date</a>
    (current: <b><c:out value="${sort}"/></b>) &nbsp;|&nbsp;
    <a href="<c:url value='/export/tasks.csv'/>">Export CSV (servlet)</a>
</p>

<table>
    <thead>
    <tr><th>Title</th><th>Project</th><th>Status</th><th>Priority</th><th>Due</th><th>Tags</th></tr>
    </thead>
    <tbody>
    <c:forEach items="${tasks}" var="t">
        <tr>
            <td><c:out value="${t.title}"/></td>
            <td><c:out value="${t.projectName}"/></td>
            <td><c:out value="${t.status}"/></td>
            <td><c:out value="${t.priority}"/></td>
            <td><c:out value="${t.dueDate}"/></td>
            <td><c:forEach items="${t.tags}" var="tag"><span class="tag"><c:out value="${tag}"/></span></c:forEach></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
