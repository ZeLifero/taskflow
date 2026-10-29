INSERT INTO projects (name, description) VALUES
    ('Internship prep', 'Everything needed to get ready for the Java internship'),
    ('TaskFlow', 'This pet project itself');

INSERT INTO tags (name) VALUES ('java'), ('spring'), ('sql'), ('git');

INSERT INTO tasks (title, description, status, priority, due_date, project_id)
SELECT 'Review OOP principles', 'Encapsulation, inheritance, polymorphism, abstraction', 'IN_PROGRESS', 'HIGH', DATE '2026-11-05', id
FROM projects WHERE name = 'Internship prep';

INSERT INTO tasks (title, description, status, priority, due_date, project_id)
SELECT 'Practice JOINs and GROUP BY', 'Solve 20 SQL exercises', 'TODO', 'MEDIUM', DATE '2026-11-08', id
FROM projects WHERE name = 'Internship prep';

INSERT INTO tasks (title, description, status, priority, due_date, project_id)
SELECT 'Push project to GitHub', 'Write README, make clean commits', 'TODO', 'HIGH', DATE '2026-11-01', id
FROM projects WHERE name = 'TaskFlow';

INSERT INTO task_tags (task_id, tag_id)
SELECT t.id, g.id FROM tasks t, tags g WHERE t.title = 'Review OOP principles' AND g.name = 'java';
INSERT INTO task_tags (task_id, tag_id)
SELECT t.id, g.id FROM tasks t, tags g WHERE t.title = 'Practice JOINs and GROUP BY' AND g.name = 'sql';
INSERT INTO task_tags (task_id, tag_id)
SELECT t.id, g.id FROM tasks t, tags g WHERE t.title = 'Push project to GitHub' AND g.name = 'git';
