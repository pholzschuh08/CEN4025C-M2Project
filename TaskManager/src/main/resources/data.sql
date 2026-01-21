-- Sample data for task manager

-- Priorities
INSERT INTO priorities (level) VALUES ('LOW') ON CONFLICT DO NOTHING;
INSERT INTO priorities (level) VALUES ('MEDIUM') ON CONFLICT DO NOTHING;
INSERT INTO priorities (level) VALUES ('HIGH') ON CONFLICT DO NOTHING;

-- Users (updated to match salt/hash fields)
INSERT INTO users (username, salt, hash) VALUES
                                             ('alice', 'YWxpY2Utc2FsdC1wbGFjZWhvbGRlcg==', 'YWxpY2UtaGFzaC1wbGFjZWhvbGRlcg=='),
                                             ('bob',   'Ym9iLXNhbHQtcGxhY2Vob2xkZXI=',       'Ym9iLWhhc2gtcGxhY2Vob2xkZXI='),
                                             ('carol', 'Y2Fyb2wtc2FsdC1wbGFjZWhvbGRlcg==',  'Y2Fyb2wtaGFzaC1wbGFjZWhvbGRlcg=='),
                                             ('dave',  'ZGF2ZS1zYWx0LXBsYWNlaG9sZGVy',      'ZGF2ZS1oYXNoLXBsYWNlaG9sZGVy'),
                                             ('eve',   'ZXZlLXNhbHQtcGxhY2Vob2xkZXI=',      'ZXZlLWhhc2gtcGxhY2Vob2xkZXI=');

-- Tasks (round-robin priorities)
INSERT INTO tasks (title, description, status, due_date, priority_id)
VALUES
    ('Task 1', 'Description for Task 1', 'PENDING', '2025-10-01', 1),
    ('Task 2', 'Description for Task 2', 'IN_PROGRESS', '2025-10-02', 2),
    ('Task 3', 'Description for Task 3', 'COMPLETED', '2025-10-03', 3),
    ('Task 4', 'Description for Task 4', 'PENDING', '2025-10-04', 1),
    ('Task 5', 'Description for Task 5', 'IN_PROGRESS', '2025-10-05', 2),
    ('Task 6', 'Description for Task 6', 'COMPLETED', '2025-10-06', 3),
    ('Task 7', 'Description for Task 7', 'PENDING', '2025-10-07', 1),
    ('Task 8', 'Description for Task 8', 'IN_PROGRESS', '2025-10-08', 2),
    ('Task 9', 'Description for Task 9', 'COMPLETED', '2025-10-09', 3),
    ('Task 10', 'Description for Task 10', 'PENDING', '2025-10-10', 1);

-- Subtasks (1–3 per task, mix of sta
