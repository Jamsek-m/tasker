-- miha : geslo123
INSERT INTO users(id, password, username) VALUES (1, '$2a$10$bSHSvVaW3m2DvPPdCazPT.JbSpGAQRktmhH78d1kA9txUBxsdgcLK', 'miha');

INSERT INTO configuration(id, config_key, config_value) VALUES (1, 'TASKER_ENABLED_REGISTRATION', 'false');
INSERT INTO docker_daemons(id, name, url) VALUES (1, 'DOCKER_MIHASERVER', 'http://localhost:2375');

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

insert into deployments(id, container_name, container_id, version, docker_daemon_id) VALUES (1, 'kontejner', '123', '1.5.4', 1);

insert into service_healthchecks(health_url) values ('http://localhost:8080/health');
insert into service_healthchecks(health_url) values ('http://localhost:4200/health');
insert into service_urls(url, version) VALUES ( 'http://localhost:8080', 'v2' );
insert into services(description, name, health_check_id, url_id, active, deployment_id) VALUES ( 'File upload', 'lozigorbox', 1, 1, true, 1);
insert into services(description, name, health_check_id, url_id, active) VALUES ( 'File upload', 'lozigorbox-2', 1, 1, true );
insert into services(description, name, health_check_id, url_id, active) VALUES ( 'File upload', 'lozigorbox-3', 2, 1, true );

insert into services(description, name, active) VALUES ( 'File upload', 'lozigorbox-4', true );
insert into services(description, name, active) VALUES ( 'File upload', 'lozigorbox-5', true );
insert into services(description, name, active) VALUES ( 'File upload', 'lozigorbox-6', true );
insert into services(description, name, active) VALUES ( 'File upload', 'lozigorbox-7', true );
insert into services(description, name, active) VALUES ( 'File upload', 'lozigorbox-8', true );
insert into services(description, name, active) VALUES ( 'File upload', 'lozigorbox-9', true );
insert into services(description, name, active) VALUES ( 'File upload', 'lozigorbox-10', true );
insert into services(description, name, active) VALUES ( 'File upload', 'lozigorbox-11', true );
insert into services(description, name, active) VALUES ( 'File upload', 'lozigorbox-12', true );