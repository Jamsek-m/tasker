-- miha : geslo123
INSERT INTO users(id, password, username) VALUES (1, '$2a$10$bSHSvVaW3m2DvPPdCazPT.JbSpGAQRktmhH78d1kA9txUBxsdgcLK', 'miha');

INSERT INTO configuration(id, config_key, config_value) VALUES (1, 'TASKER_ENABLED_REGISTRATION', 'false');
INSERT INTO docker_daemons(id, name, url) VALUES (1, 'DOCKER_MIHASERVER', 'http://localhost:2375');
INSERT INTO docker_daemons(id, name, url) VALUES (2, 'DEBUG', 'http://192.168.1.22:2376');

insert into deployments(id, container_name, container_id, version, docker_daemon_id) VALUES (1, 'kontejner', '123', '1.5.4', 1);
insert into deployments(id, container_name, container_id, version, docker_daemon_id) values(2, 'tasker', 'd45d677ac120bd3d90073365951854a46ef042d2b6792286e994d2c765b19a8c', '1.0.0', 2);
INSERT INTO DEPLOYMENTS (ID, CONTAINER_ID, CONTAINER_NAME, VERSION, DOCKER_DAEMON_ID) VALUES (3, 'e885a53516983c50ad4f797534967ffe1bc30490d6650849ade8a8731174fe27', 'tasker-test', null, 1);

insert into service_healthchecks(id, health_url) values (1, 'http://localhost:8080/health');
insert into service_healthchecks(id, health_url) values (2, 'http://localhost:4200/health');
insert into service_healthchecks(id, health_url) values (3, 'https://tasker.mjamsek.com/health');
insert into service_urls(id, url, version, url_versioning) VALUES (1, 'http://localhost:8080', 'v2', true );
insert into service_urls(id, url, version, url_versioning) VALUES (2, 'https://tasker.mjamsek.com', 'v2', true );
insert into services(description, version, name, health_check_id, url_id, active, deployment_id) VALUES ( 'Stari tasker', '1.0.0', 'tasker', 3, 2, true, 2);
insert into services(description, version, name, health_check_id, url_id, active, deployment_id) VALUES ( 'File upload', '1.0.0', 'lozigorbox', 1, 1, true, 1);
insert into services(description, version, name, health_check_id, url_id, active) VALUES ( 'File upload', 'v2', 'lozigorbox-2', 1, 1, true );
insert into services(description, version, name, health_check_id, url_id, active) VALUES ( 'File upload', '1.2.3', 'lozigorbox-3', 2, 1, true );

insert into services(description, version, name, active) VALUES ( 'File upload', '1.0.0', 'lozigorbox-4', true );
insert into services(description, version, name, active) VALUES ( 'File upload', '1.0.0', 'lozigorbox-5', true );
insert into services(description, version, name, active) VALUES ( 'File upload', '1.0.0', 'lozigorbox-6', true );
insert into services(description, version, name, active) VALUES ( 'File upload', '1.0.0', 'lozigorbox-7', true );
insert into services(description, version, name, active) VALUES ( 'File upload', '1.0.0', 'lozigorbox-8', true );
insert into services(description, version, name, active) VALUES ( 'File upload', '1.0.0', 'lozigorbox-9', true );
insert into services(description, version, name, active) VALUES ( 'File upload', '1.0.0', 'lozigorbox-10', true );
insert into services(description, version, name, active) VALUES ( 'File upload', '1.0.0', 'lozigorbox-11', true );
insert into services(description, version, name, active) VALUES ( 'File upload', '1.0.0', 'lozigorbox-12', true );
INSERT INTO PUBLIC.SERVICES (ID, ACTIVE, DESCRIPTION, NAME, TOKEN, VERSION, DEPLOYMENT_ID, HEALTH_CHECK_ID, URL_ID) VALUES (14, true, null, 'test-tasker', null, '1.0.0', 3, null, null);

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