-- miha : geslo123
INSERT INTO users(id, password, username) VALUES (1, '$2a$10$bSHSvVaW3m2DvPPdCazPT.JbSpGAQRktmhH78d1kA9txUBxsdgcLK', 'miha');

INSERT INTO configuration(id, config_key, config_value) VALUES (1, 'DOCKER_API_0', 'http://localhost:2375');
INSERT INTO configuration(id, config_key, config_value) VALUES (2, 'TASKER_ENABLED_REGISTRATION', 'false');

INSERT INTO logs(id, log_date, message, severity) VALUES (1, '2019-01-04 20:35:22', 'Random message', 'INFO');
INSERT INTO logs(id, log_date, message, severity) VALUES (2, '2019-01-03 20:35:22', 'Random message', 'INFO');
INSERT INTO logs(id, log_date, message, severity) VALUES (3, '2019-01-02 20:35:22', 'Random message', 'INFO');
INSERT INTO logs(id, log_date, message, severity) VALUES (4, '2019-01-05 20:35:22', 'Random message', 'INFO');
INSERT INTO logs(id, log_date, message, severity) VALUES (5, '2019-01-01 20:35:22', 'Random message', 'INFO');
INSERT INTO logs(id, log_date, message, severity) VALUES (6, '2019-01-06 20:35:22', 'Random message', 'INFO');
INSERT INTO logs(id, log_date, message, severity) VALUES (7, '2019-01-05 20:35:22', 'Random message', 'INFO');
INSERT INTO logs(id, log_date, message, severity) VALUES (8, '2019-01-07 20:35:22', 'Random message', 'INFO');
INSERT INTO logs(id, log_date, message, severity) VALUES (9, '2019-01-08 20:35:22', 'Random message', 'INFO');
INSERT INTO logs(id, log_date, message, severity) VALUES (10, '2019-01-09 20:35:22', 'Random message', 'INFO');
INSERT INTO logs(id, log_date, message, severity) VALUES (11, '2019-02-03 20:35:22', 'Random message', 'INFO');
