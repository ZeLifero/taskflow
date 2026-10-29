# TaskFlow

Небольшой таск-трекер, написанный как учебный пет-проект для стажировки Java-разработчика.
Цель — на одном небольшом проекте показать базовый стек: Java Core, ООП, Spring, Hibernate, Servlets/JSP, SQL и тесты.

## Стек

Java 17 · Spring Boot 3 (Spring MVC, Spring Data JPA, Validation) · Hibernate · PostgreSQL ·
Servlets / JSP / JSTL · JUnit 5 · Mockito · Maven · Docker Compose · Git

## Где что в проекте

| Требование | Где посмотреть |
|---|---|
| Принципы ООП | `model/` (инкапсуляция: `Task.moveTo`, переходы в `TaskStatus`), `strategy/` (интерфейс + полиморфизм, паттерн «Стратегия») |
| Алгоритмы и структуры данных | `algo/`: `Sorting` (merge sort), `Searching` (бинарный поиск), `SimpleLinkedList` (односвязный список на generics) |
| Java Core | generics, `record`, `enum`, `Optional`, Stream API, коллекции (`List`, `Set`, `Map`), собственные исключения |
| Java EE: Servlets, JSP | `web/servlet/TaskCsvExportServlet` (`@WebServlet`), `webapp/WEB-INF/jsp/tasks.jsp` (JSP + JSTL) |
| Реляционные БД | `db/schema.sql` (таблицы, FK, индексы, связь M:N), `db/data.sql`, PostgreSQL в Docker |
| Spring Framework | DI через конструктор, `@Service`, `@RestController`, `@Controller`, `@Transactional`, `@RestControllerAdvice` |
| Hibernate / ORM | `model/` (`@OneToMany`, `@ManyToOne`, `@ManyToMany`), `repository/` (derived query, JPQL, `join fetch` против N+1) |
| Модульные тесты | `src/test/`: чистые JUnit-тесты, Mockito (`TaskServiceTest`), `@DataJpaTest` (H2), `@WebMvcTest` |
| Git | история коммитов, `.gitignore` |

## Запуск

Нужны JDK 17+, Maven и Docker.

```bash
docker compose up -d      # PostgreSQL + схема + тестовые данные
mvn test                  # тесты (БД не нужна, используется H2)
mvn spring-boot:run       # приложение на http://localhost:8080
```

- Страница на JSP: http://localhost:8080/tasks
- Экспорт в CSV (сервлет): http://localhost:8080/export/tasks.csv
- REST API:

```bash
curl http://localhost:8080/api/tasks?sort=dueDate
curl -X POST http://localhost:8080/api/tasks -H "Content-Type: application/json" \
  -d '{"title":"Learn Hibernate","priority":"HIGH","projectId":1,"tags":["java"]}'
curl -X PATCH http://localhost:8080/api/tasks/1/status -H "Content-Type: application/json" \
  -d '{"status":"DONE"}'
curl http://localhost:8080/api/tasks/stats
```

## Архитектура

```
HTTP -> Controller / Servlet -> Service (@Transactional) -> Repository (Spring Data / Hibernate) -> PostgreSQL
              ^ DTO наружу, сущности остаются внутри сервисного слоя
```

## Что можно улучшить

Spring Security, миграции Flyway, пагинация, интеграционные тесты с Testcontainers, Dockerfile для приложения.
