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

-- kljuc
INSERT INTO tokens(id, name, secret_key) VALUES (1, 'test1', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La');
INSERT INTO tokens(id, name, secret_key) VALUES (2, 'test2', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La');
INSERT INTO tokens(id, name, secret_key) VALUES (3, 'test3', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La');
INSERT INTO tokens(id, name, secret_key) VALUES (4, 'test4', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La');
INSERT INTO tokens(id, name, secret_key) VALUES (5, 'test5', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La');
INSERT INTO tokens(id, name, secret_key) VALUES (6, 'test6', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La');
INSERT INTO tokens(id, name, secret_key, expired) VALUES (7, 'test7', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La', '2019-01-05 20:12:31');
INSERT INTO tokens(id, name, secret_key) VALUES (8, 'test8', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La');
INSERT INTO tokens(id, name, secret_key) VALUES (9, 'test9', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La');
INSERT INTO tokens(id, name, secret_key) VALUES (10, 'test10', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La');
INSERT INTO tokens(id, name, secret_key) VALUES (11, 'test11', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La');
INSERT INTO tokens(id, name, secret_key) VALUES (12, 'test12', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La');
INSERT INTO tokens(id, name, secret_key) VALUES (13, 'test13', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La');
INSERT INTO tokens(id, name, secret_key, expired) VALUES (14, 'test14', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La', '2019-01-05 20:12:31');
INSERT INTO tokens(id, name, secret_key, expired) VALUES (15, 'test15', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La', '2019-01-05 20:12:31');
INSERT INTO tokens(id, name, secret_key, expired) VALUES (16, 'test16', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La', '2019-01-05 20:12:31');
INSERT INTO tokens(id, name, secret_key) VALUES (17, 'test17', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La');
INSERT INTO tokens(id, name, secret_key, expired) VALUES (18, 'test18', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La', '2019-01-05 20:12:31');
INSERT INTO tokens(id, name, secret_key, expired) VALUES (19, 'test19', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La', '2019-01-05 20:12:31');
INSERT INTO tokens(id, name, secret_key, expired) VALUES (20, 'test20', '$2a$10$CxDKLvspXBfqs9E5HlxU.uPNMD7BQ7X1fuqcvv5I8vm/JjH.bQ7La', '2019-01-05 20:12:31');

INSERT INTO token_actions(id, action, token_id) VALUES (1, 'apiCall', 1);
INSERT INTO token_actions(id, action, token_id) VALUES (2, 'shellCmd', 1);
INSERT INTO token_actions(id, action, token_id) VALUES (3, 'DockerPlugin.start', 1);
INSERT INTO token_actions(id, action, token_id) VALUES (4, 'DockerPlugin.stop', 1);
INSERT INTO token_actions(id, action, token_id) VALUES (5, 'DockerPlugin.getId', 1);
INSERT INTO token_actions(id, action, token_id) VALUES (6, 'DockerPlugin.restart', 1);
INSERT INTO token_actions(id, action, token_id) VALUES (7, 'DockerPlugin.getInfo', 1);
