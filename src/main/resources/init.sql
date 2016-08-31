--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: clients; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE clients (
    id bigint NOT NULL,
    access_token_validity_seconds_alias integer,
    additional_information_str character varying(255),
    authorities_str character varying(500),
    authorized_grant_type_str character varying(100) NOT NULL,
    client_id_alias character varying(50) NOT NULL,
    client_secret_alias character varying(100) NOT NULL,
    created_by bigint NOT NULL,
    created_date timestamp without time zone NOT NULL,
    last_modified_by bigint NOT NULL,
    last_modified_date timestamp without time zone NOT NULL,
    public_key character varying(5000) NOT NULL,
    refresh_token_validity_seconds_alias integer,
    registered_redirect_uri_str character varying(1024),
    resource_id_str character varying(100) NOT NULL,
    scope_str character varying(100) NOT NULL,
    valid_flag integer NOT NULL,
    version integer NOT NULL
);


ALTER TABLE clients OWNER TO core;

--
-- Name: clients_seq; Type: SEQUENCE; Schema: public; Owner: core
--

CREATE SEQUENCE clients_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE clients_seq OWNER TO core;

--
-- Name: groups; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE groups (
    id bigint NOT NULL,
    created_by bigint NOT NULL,
    created_date timestamp without time zone NOT NULL,
    description text,
    last_modified_by bigint NOT NULL,
    last_modified_date timestamp without time zone NOT NULL,
    name character varying(20) NOT NULL,
    valid_flag integer NOT NULL,
    version integer NOT NULL
);


ALTER TABLE groups OWNER TO core;

--
-- Name: groups_has_resources; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE groups_has_resources (
    group_id bigint NOT NULL,
    resource_id bigint NOT NULL
);


ALTER TABLE groups_has_resources OWNER TO core;

--
-- Name: groups_seq; Type: SEQUENCE; Schema: public; Owner: core
--

CREATE SEQUENCE groups_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE groups_seq OWNER TO core;

--
-- Name: logs; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE logs (
    id bigint NOT NULL,
    access_resource character varying(255),
    client_id character varying(255),
    create_date timestamp without time zone NOT NULL,
    ip character varying(50) NOT NULL,
    type integer NOT NULL,
    user_id bigint NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE logs OWNER TO core;

--
-- Name: logs_seq; Type: SEQUENCE; Schema: public; Owner: core
--

CREATE SEQUENCE logs_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE logs_seq OWNER TO core;

--
-- Name: oauth_access_token; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE oauth_access_token (
    token_id character varying(256),
    token bytea,
    authentication_id character varying(256) NOT NULL,
    user_name character varying(256),
    client_id character varying(256),
    authentication bytea,
    refresh_token character varying(256)
);


ALTER TABLE oauth_access_token OWNER TO core;

--
-- Name: oauth_approvals; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE oauth_approvals (
    userid character varying(256),
    clientid character varying(256),
    scope character varying(256),
    status character varying(10),
    expiresat timestamp without time zone,
    lastmodifiedat timestamp without time zone
);


ALTER TABLE oauth_approvals OWNER TO core;

--
-- Name: oauth_client_details; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE oauth_client_details (
    client_id character varying(256) NOT NULL,
    resource_ids character varying(256),
    client_secret character varying(256),
    scope character varying(256),
    authorized_grant_types character varying(256),
    web_server_redirect_uri character varying(256),
    authorities character varying(256),
    access_token_validity integer,
    refresh_token_validity integer,
    additional_information character varying(4096),
    autoapprove character varying(256)
);


ALTER TABLE oauth_client_details OWNER TO core;

--
-- Name: oauth_client_token; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE oauth_client_token (
    token_id character varying(256),
    token bytea,
    authentication_id character varying(256) NOT NULL,
    user_name character varying(256),
    client_id character varying(256)
);


ALTER TABLE oauth_client_token OWNER TO core;

--
-- Name: oauth_code; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE oauth_code (
    code character varying(256),
    authentication bytea
);


ALTER TABLE oauth_code OWNER TO core;

--
-- Name: oauth_refresh_token; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE oauth_refresh_token (
    token_id character varying(256),
    token bytea,
    authentication bytea
);


ALTER TABLE oauth_refresh_token OWNER TO core;

--
-- Name: resources; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE resources (
    id bigint NOT NULL,
    created_by bigint NOT NULL,
    created_date timestamp without time zone NOT NULL,
    description text,
    last_modified_by bigint NOT NULL,
    last_modified_date timestamp without time zone NOT NULL,
    name character varying(20) NOT NULL,
    path character varying(1024) NOT NULL,
    priority integer NOT NULL,
    valid_flag integer NOT NULL,
    version integer NOT NULL
);


ALTER TABLE resources OWNER TO core;

--
-- Name: resources_seq; Type: SEQUENCE; Schema: public; Owner: core
--

CREATE SEQUENCE resources_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE resources_seq OWNER TO core;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE roles (
    id bigint NOT NULL,
    created_by bigint NOT NULL,
    created_date timestamp without time zone NOT NULL,
    description text,
    last_modified_by bigint NOT NULL,
    last_modified_date timestamp without time zone NOT NULL,
    name character varying(20) NOT NULL,
    valid_flag integer NOT NULL,
    version integer NOT NULL
);


ALTER TABLE roles OWNER TO core;

--
-- Name: roles_has_groups; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE roles_has_groups (
    role_id bigint NOT NULL,
    group_id bigint NOT NULL
);


ALTER TABLE roles_has_groups OWNER TO core;

--
-- Name: roles_seq; Type: SEQUENCE; Schema: public; Owner: core
--

CREATE SEQUENCE roles_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE roles_seq OWNER TO core;

--
-- Name: users; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE users (
    id bigint NOT NULL,
    created_by bigint NOT NULL,
    created_date timestamp without time zone NOT NULL,
    description text,
    is_account_non_expired_alias boolean NOT NULL,
    is_account_non_locked_alias boolean NOT NULL,
    is_credentials_non_expired_alias boolean NOT NULL,
    is_enabled_alias boolean NOT NULL,
    last_login_ip character varying(255),
    last_login_time timestamp without time zone,
    last_modified_by bigint NOT NULL,
    last_modified_date timestamp without time zone NOT NULL,
    name character varying(50) NOT NULL,
    pwd character varying(200) NOT NULL,
    usr character varying(20) NOT NULL,
    valid_flag integer NOT NULL,
    version integer NOT NULL
);


ALTER TABLE users OWNER TO core;

--
-- Name: users_has_roles; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE users_has_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE users_has_roles OWNER TO core;

--
-- Name: users_seq; Type: SEQUENCE; Schema: public; Owner: core
--

CREATE SEQUENCE users_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_seq OWNER TO core;

--
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: core
--

COPY clients (id, access_token_validity_seconds_alias, additional_information_str, authorities_str, authorized_grant_type_str, client_id_alias, client_secret_alias, created_by, created_date, last_modified_by, last_modified_date, public_key, refresh_token_validity_seconds_alias, registered_redirect_uri_str, resource_id_str, scope_str, valid_flag, version) FROM stdin;
1	1800	\N	\N	password,refresh_token,authorization_code	admin	123456	1	2016-06-27 14:24:50.327	1	2016-06-27 14:24:50.327	MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvpO6M1Ghv4YeEeOFHB41FtzwDLB49ovrjfYU4+YvTvXvwL1AdVlJhKfp/MveMK8tzL5Prya11nsIQnyz/dVdiWhu7xqC6fE/xbWswEskBRa/QUvOFaKZS6ZRenGsst7YTQmiEWlhZwduDvCcPrz4pEusRg+GtETdbWqO3D0O+NF9bmkEGcKvHB1BHKv6Nj8PSL0Zt8h2fbZLWNSEYWPLCA+onhtGL7pAkpGQxAtZLJTYhrTw4oo7+bcSjha/2AHfnsCcMa65EoU1BSjD18bjG+AAE6JNURH5Nl2NgRL7wT4LH1/0vJpUnCxjkWWN46648k22ogciDSr73msJuAzp9wIDAQAB	86400	http://github.com/saintdan	api	read	1	0
2	1800	\N	\N	password,refresh_token,authorization_code	ios_app	123456	1	2016-06-27 14:24:50.327	1	2016-06-27 14:24:50.327	MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvpO6M1Ghv4YeEeOFHB41FtzwDLB49ovrjfYU4+YvTvXvwL1AdVlJhKfp/MveMK8tzL5Prya11nsIQnyz/dVdiWhu7xqC6fE/xbWswEskBRa/QUvOFaKZS6ZRenGsst7YTQmiEWlhZwduDvCcPrz4pEusRg+GtETdbWqO3D0O+NF9bmkEGcKvHB1BHKv6Nj8PSL0Zt8h2fbZLWNSEYWPLCA+onhtGL7pAkpGQxAtZLJTYhrTw4oo7+bcSjha/2AHfnsCcMa65EoU1BSjD18bjG+AAE6JNURH5Nl2NgRL7wT4LH1/0vJpUnCxjkWWN46648k22ogciDSr73msJuAzp9wIDAQAB	86400	http://www.apple.com	api	read	1	0
\.


--
-- Name: clients_seq; Type: SEQUENCE SET; Schema: public; Owner: core
--

SELECT pg_catalog.setval('clients_seq', 4, true);


--
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: core
--

COPY groups (id, created_by, created_date, description, last_modified_by, last_modified_date, name, valid_flag, version) FROM stdin;
1	1	2016-06-27 14:24:50.589	root privileges group	1	2016-06-27 14:24:50.589	root	1	2
2	1	2016-06-27 14:24:50.589	client privileges group	1	2016-06-27 14:24:50.589	client	1	2
3	1	2016-06-27 14:24:50.589	guest privileges group	1	2016-06-27 14:24:50.589	guest	1	2
4	1	2016-06-27 14:24:50.589	user privileges group	1	2016-06-27 14:24:50.589	user	1	2
5	1	2016-06-27 14:24:50.589	authority privileges group	1	2016-06-27 14:24:50.589	authority	1	2
6	1	2016-06-27 14:24:50.589	resource privileges group	1	2016-06-27 14:24:50.589	resource	1	2
\.


--
-- Data for Name: groups_has_resources; Type: TABLE DATA; Schema: public; Owner: core
--

COPY groups_has_resources (group_id, resource_id) FROM stdin;
1	1
2	2
3	4
3	3
4	5
5	7
5	6
6	8
\.


--
-- Name: groups_seq; Type: SEQUENCE SET; Schema: public; Owner: core
--

SELECT pg_catalog.setval('groups_seq', 6, true);


--
-- Data for Name: logs; Type: TABLE DATA; Schema: public; Owner: core
--

COPY logs (id, access_resource, client_id, create_date, login_ip, type, user_id, username) FROM stdin;
\.


--
-- Name: logs_seq; Type: SEQUENCE SET; Schema: public; Owner: core
--

SELECT pg_catalog.setval('logs_seq', 72, true);


--
-- Data for Name: oauth_access_token; Type: TABLE DATA; Schema: public; Owner: core
--

COPY oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) FROM stdin;
\.


--
-- Data for Name: oauth_approvals; Type: TABLE DATA; Schema: public; Owner: core
--

COPY oauth_approvals (userid, clientid, scope, status, expiresat, lastmodifiedat) FROM stdin;
\.


--
-- Data for Name: oauth_client_details; Type: TABLE DATA; Schema: public; Owner: core
--

COPY oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) FROM stdin;
\.


--
-- Data for Name: oauth_client_token; Type: TABLE DATA; Schema: public; Owner: core
--

COPY oauth_client_token (token_id, token, authentication_id, user_name, client_id) FROM stdin;
\.


--
-- Data for Name: oauth_code; Type: TABLE DATA; Schema: public; Owner: core
--

COPY oauth_code (code, authentication) FROM stdin;
\.


--
-- Data for Name: oauth_refresh_token; Type: TABLE DATA; Schema: public; Owner: core
--

COPY oauth_refresh_token (token_id, token, authentication) FROM stdin;
\.


--
-- Data for Name: resources; Type: TABLE DATA; Schema: public; Owner: core
--

COPY resources (id, created_by, created_date, description, last_modified_by, last_modified_date, name, path, priority, valid_flag, version) FROM stdin;
1	1	2016-06-27 14:24:50.618	all resources	1	2016-06-27 14:24:50.618	root	/.*	10000	1	0
2	1	2016-06-27 14:24:50.618	client resource	1	2016-06-27 14:24:50.618	client	/client	10	1	0
3	1	2016-06-27 14:24:50.618	message resource	1	2016-06-27 14:24:50.618	message	/messages	10	1	0
4	1	2016-06-27 14:24:50.618	welcome resource	1	2016-06-27 14:24:50.618	welcome	/welcome	1	1	0
5	1	2016-06-27 14:24:50.618	user resource	1	2016-06-27 14:24:50.618	user	/users	10	1	0
6	1	2016-06-27 14:24:50.618	role resource	1	2016-06-27 14:24:50.618	role	/roles	10	1	0
7	1	2016-06-27 14:24:50.618	group resource	1	2016-06-27 14:24:50.618	group	/groups	10	1	0
8	1	2016-06-27 14:24:50.618	resource resource	1	2016-06-27 14:24:50.618	resource	/resources	10	1	0
\.


--
-- Name: resources_seq; Type: SEQUENCE SET; Schema: public; Owner: core
--

SELECT pg_catalog.setval('resources_seq', 8, true);


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: core
--

COPY roles (id, created_by, created_date, description, last_modified_by, last_modified_date, name, valid_flag, version) FROM stdin;
1	1	2016-06-27 14:24:50.575	root role	1	2016-06-27 14:24:50.575	root	1	2
2	1	2016-06-27 14:24:50.575	admin role	1	2016-06-27 14:24:50.575	admin	1	2
3	1	2016-06-27 14:24:50.575	guest role	1	2016-06-27 14:24:50.575	guest	1	2
\.


--
-- Data for Name: roles_has_groups; Type: TABLE DATA; Schema: public; Owner: core
--

COPY roles_has_groups (role_id, group_id) FROM stdin;
1	1
2	6
2	2
2	3
2	4
2	5
3	3
\.


--
-- Name: roles_seq; Type: SEQUENCE SET; Schema: public; Owner: core
--

SELECT pg_catalog.setval('roles_seq', 3, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: core
--

COPY users (id, created_by, created_date, description, is_account_non_expired_alias, is_account_non_locked_alias, is_credentials_non_expired_alias, is_enabled_alias, last_login_ip, last_login_time, last_modified_by, last_modified_date, name, pwd, usr, valid_flag, version) FROM stdin;
1	0	2016-06-27 14:24:50.081	root account	t	t	t	t	\N	\N	0	2016-06-27 14:24:50.081	root	$2a$10$ZBqt0Z73hxXZWCGUM51g8OLqti.8XqBEQRpmIgjw/wcEtyXlG9Jey	root	1	2
3	1	2016-06-27 14:24:50.352	guest account	t	t	t	t	\N	\N	1	2016-06-27 14:24:50.352	guest	$2a$10$L45.IZTSKhdKD4bqBjHJHOpCY9x6eUse1URqoBn5Z0saFD/x.p92i	guest	1	2
2	1	2016-06-27 14:24:50.352	admin account	t	t	t	t	0:0:0:0:0:0:0:1	2016-07-14 11:23:53.7	1	2016-06-27 14:24:50.352	admin	$2a$10$QTh9AVEsHEexICX3Yu/rCuM/N4wDDslcx8bLJTU9oFBMeW0etPjGS	admin	1	66
\.


--
-- Data for Name: users_has_roles; Type: TABLE DATA; Schema: public; Owner: core
--

COPY users_has_roles (user_id, role_id) FROM stdin;
1	1
2	2
3	3
\.


--
-- Name: users_seq; Type: SEQUENCE SET; Schema: public; Owner: core
--

SELECT pg_catalog.setval('users_seq', 8, true);


--
-- Name: clients_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- Name: groups_has_resources_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY groups_has_resources
    ADD CONSTRAINT groups_has_resources_pkey PRIMARY KEY (group_id, resource_id);


--
-- Name: groups_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id);


--
-- Name: logs_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY logs
    ADD CONSTRAINT logs_pkey PRIMARY KEY (id);


--
-- Name: oauth_access_token_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY oauth_access_token
    ADD CONSTRAINT oauth_access_token_pkey PRIMARY KEY (authentication_id);


--
-- Name: oauth_client_details_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY oauth_client_details
    ADD CONSTRAINT oauth_client_details_pkey PRIMARY KEY (client_id);


--
-- Name: oauth_client_token_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY oauth_client_token
    ADD CONSTRAINT oauth_client_token_pkey PRIMARY KEY (authentication_id);


--
-- Name: resources_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY resources
    ADD CONSTRAINT resources_pkey PRIMARY KEY (id);


--
-- Name: roles_has_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY roles_has_groups
    ADD CONSTRAINT roles_has_groups_pkey PRIMARY KEY (role_id, group_id);


--
-- Name: roles_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: uk_8mf0is8024pqmwjxgldfe54l7; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT uk_8mf0is8024pqmwjxgldfe54l7 UNIQUE (name);


--
-- Name: uk_cj74a2dsyo07gi8dmdkagf76b; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY resources
    ADD CONSTRAINT uk_cj74a2dsyo07gi8dmdkagf76b UNIQUE (path);


--
-- Name: uk_l85pqajoc7v2drqv3tj3rcmpq; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY resources
    ADD CONSTRAINT uk_l85pqajoc7v2drqv3tj3rcmpq UNIQUE (name);


--
-- Name: uk_ofx66keruapi6vyqpv6f2or37; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT uk_ofx66keruapi6vyqpv6f2or37 UNIQUE (name);


--
-- Name: uk_p1b4g6jljk2xus918cn55uvvd; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_p1b4g6jljk2xus918cn55uvvd UNIQUE (usr);


--
-- Name: users_has_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY users_has_roles
    ADD CONSTRAINT users_has_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: fk_3n6w8b3qt15han3kibman5mv4; Type: FK CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY users_has_roles
    ADD CONSTRAINT fk_3n6w8b3qt15han3kibman5mv4 FOREIGN KEY (role_id) REFERENCES roles(id);


--
-- Name: fk_4ggirb3otb8ldnxpbau2jivkq; Type: FK CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY users_has_roles
    ADD CONSTRAINT fk_4ggirb3otb8ldnxpbau2jivkq FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: fk_9lqoqk9or4o3pwukt2dk00ecc; Type: FK CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY groups_has_resources
    ADD CONSTRAINT fk_9lqoqk9or4o3pwukt2dk00ecc FOREIGN KEY (group_id) REFERENCES groups(id);


--
-- Name: fk_d50q1cq8ctwu9ugm557tuqt2l; Type: FK CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY roles_has_groups
    ADD CONSTRAINT fk_d50q1cq8ctwu9ugm557tuqt2l FOREIGN KEY (role_id) REFERENCES roles(id);


--
-- Name: fk_ieeuga2qjc14opj9vy46v7mhy; Type: FK CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY roles_has_groups
    ADD CONSTRAINT fk_ieeuga2qjc14opj9vy46v7mhy FOREIGN KEY (group_id) REFERENCES groups(id);


--
-- Name: fk_q34fh9kihnqe19nh25ghcw6b7; Type: FK CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY groups_has_resources
    ADD CONSTRAINT fk_q34fh9kihnqe19nh25ghcw6b7 FOREIGN KEY (resource_id) REFERENCES resources(id);


--
-- PostgreSQL database dump complete
--

