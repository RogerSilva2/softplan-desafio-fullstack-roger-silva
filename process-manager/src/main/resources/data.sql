INSERT INTO table_user(username, password, role, created_at, updated_at) VALUES('administrator', '$2a$10$.cpk5T7bzw0qL3ZiHte9t.h01iVfseMwHxn/v.S4PY2GfUmrU3lv6', 'ADMINISTRATOR', now(), now());
INSERT INTO table_user(username, password, role, created_at, updated_at) VALUES('grader', '$2a$10$.cpk5T7bzw0qL3ZiHte9t.h01iVfseMwHxn/v.S4PY2GfUmrU3lv6', 'GRADER', now(), now());
INSERT INTO table_user(username, password, role, created_at, updated_at) VALUES('evaluator', '$2a$10$.cpk5T7bzw0qL3ZiHte9t.h01iVfseMwHxn/v.S4PY2GfUmrU3lv6', 'EVALUATOR', now(), now());

INSERT INTO table_process(name, content, id_creator, created_at, updated_at) VALUES('process', 'content', 2, now(), now());

INSERT INTO table_evaluation(id_evaluator, id_process, feedback, created_at, updated_at) VALUES(3, 1, 'comments', now(), now());