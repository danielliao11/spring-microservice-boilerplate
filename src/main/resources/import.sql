-- users
CREATE TABLE users (
  id          LONG         PRIMARY KEY,
  name        VARCHAR(50)  NOT NULL,
  usr         VARCHAR(20)  NOT NULL,
  pwd         VARCHAR(100)  NOT NULL,
  description VARCHAR(500)
);
INSERT INTO users (id, name, usr, pwd, description) VALUES (1, 'root', 'root', '$2a$10$.7skSXpnyzAF117vv8yWkOma96BgSj5.m/OsVQaoVDAaw.XwaorIu', 'root account');
INSERT INTO users (id, name, usr, pwd, description) VALUES (2, 'admin', 'admin', '$2a$10$6EPOpTYNgn9RyluFnOD49.HuvUmijFJJV/R2t8nzM/exJVuzHSD5y', 'admin account');
INSERT INTO users (id, name, usr, pwd, description) VALUES (3, 'user', 'user', '$2a$10$VxrOW3ml1ySPi.rB8CQaaOeXIqbU21/acTvIYHsVN..v4.4JXHb2G', 'user account');
INSERT INTO users (id, name, usr, pwd, description) VALUES (4, 'guest', 'guest', '$2a$10$RbkJJzX5CG9Kg0ZkSpbfieS7SXqpXS2jQ7/JdbBpKn7ODSIKcyIWW', 'guest account');

-- roles
DROP TABLE roles;
CREATE TABLE roles (
  id          LONG         PRIMARY KEY,
  name        VARCHAR(20)  NOT NULL,
  description VARCHAR(500)
);
INSERT INTO roles (id, name, description) VALUES (1, 'ROLE_ROOT', 'root role');
INSERT INTO roles (id, name, description) VALUES (2, 'ROLE_ADMIN', 'admin role');
INSERT INTO roles (id, name, description) VALUES (3, 'ROLE_USER', 'user role');
INSERT INTO roles (id, name, description) VALUES (4, 'ROLE_GUEST', 'guest role');

-- groups
DROP TABLE groups;
CREATE TABLE groups (
  id          LONG        PRIMARY KEY,
  name        VARCHAR(20) NOT NULL,
  description VARCHAR(500)
);
INSERT INTO groups (id, name, description) VALUES (1, 'GROUP_ROOT', 'root privileges');
INSERT INTO groups (id, name, description) VALUES (2, 'GROUP_ADMIN', 'admin privileges');
INSERT INTO groups (id, name, description) VALUES (3, 'GROUP_MESSAGE', 'message privileges');
INSERT INTO groups (id, name, description) VALUES (4, 'GROUP_INFO', 'information privileges');
INSERT INTO groups (id, name, description) VALUES (5, 'GROUP_ACTION', 'action privileges');

-- resources
DROP TABLE resources;
CREATE TABLE resources (
  id          LONG        PRIMARY KEY,
  name        VARCHAR(20) NOT NULL,
  path        VARCHAR(1024) NOT NULL,
  priority    INT         NOT NULL,
  description VARCHAR(500)
);
INSERT INTO resources (id, name, path, priority, description) VALUES (1, 'root', '/.*', 10000, 'all resources');
INSERT INTO resources (id, name, path, priority, description) VALUES (2, 'admin', '/(?!root/).*', 1000, 'admin resources');
INSERT INTO resources (id, name, path, priority, description) VALUES (3, 'message', '/message/.*', 10, 'message resources');
INSERT INTO resources (id, name, path, priority, description) VALUES (4, 'resources', '/resources/.*', 10, 'information resources');
INSERT INTO resources (id, name, path, priority, description) VALUES (5, 'action', '/action/.*', 10, 'actions');
INSERT INTO resources (id, name, path, priority, description) VALUES (6, 'welcome', '/welcome/.*', 10, 'welcome resource');

-- user_roles
DROP TABLE user_roles;
CREATE TABLE user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL
);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3);
INSERT INTO user_roles (user_id, role_id) VALUES (4, 4);

-- role_groups
DROP TABLE role_groups;
CREATE TABLE role_groups (
  role_id  INT NOT NULL,
  group_id INT NOT NULL
);
INSERT INTO role_groups (role_id, group_id) VALUES (1, 1);
INSERT INTO role_groups (role_id, group_id) VALUES (2, 2);
INSERT INTO role_groups (role_id, group_id) VALUES (3, 3);
INSERT INTO role_groups (role_id, group_id) VALUES (3, 4);
INSERT INTO role_groups (role_id, group_id) VALUES (3, 5);
INSERT INTO role_groups (role_id, group_id) VALUES (3, 6);
INSERT INTO role_groups (role_id, group_id) VALUES (4, 3);

-- group_resources
DROP TABLE group_resources;
CREATE TABLE group_resources (
  group_id    INT NOT NULL,
  resource_id INT NOT NULL
);
INSERT INTO group_resources (group_id, resource_id) VALUES (1, 1);
INSERT INTO group_resources (group_id, resource_id) VALUES (2, 2);
INSERT INTO group_resources (group_id, resource_id) VALUES (3, 3);
INSERT INTO group_resources (group_id, resource_id) VALUES (4, 4);