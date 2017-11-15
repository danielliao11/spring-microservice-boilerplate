--
-- PostgreSQL database dump
--

-- Dumped from database version 10.0
-- Dumped by pg_dump version 10.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS core;
--
-- Name: core; Type: DATABASE; Schema: -; Owner: core
--

CREATE DATABASE core WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'C' LC_CTYPE = 'UTF-8';


ALTER DATABASE core OWNER TO core;

\connect core

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
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
-- Name: accounts; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE accounts (
    id bigint NOT NULL,
    account character varying(255),
    account_source_type integer,
    created_at bigint NOT NULL,
    created_by bigint NOT NULL,
    last_modified_at bigint NOT NULL,
    last_modified_by bigint NOT NULL,
    version integer NOT NULL,
    user_id bigint
);


ALTER TABLE accounts OWNER TO core;

--
-- Name: accounts_seq; Type: SEQUENCE; Schema: public; Owner: core
--

CREATE SEQUENCE accounts_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE accounts_seq OWNER TO core;

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
    last_modified_by bigint NOT NULL,
    public_key character varying(5000) NOT NULL,
    refresh_token_validity_seconds_alias integer,
    registered_redirect_uri_str character varying(1024),
    resource_id_str character varying(100) NOT NULL,
    scope_str character varying(100) NOT NULL,
    valid_flag integer NOT NULL,
    version integer NOT NULL,
    created_at bigint NOT NULL,
    last_modified_at bigint NOT NULL
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
-- Name: logs; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE logs (
    id bigint NOT NULL,
    access_resource character varying(255),
    client_id character varying(255),
    created_date timestamp without time zone NOT NULL,
    ip character varying(50) NOT NULL,
    type integer NOT NULL,
    user_id bigint NOT NULL,
    username character varying(255) NOT NULL,
    created_at bigint NOT NULL,
    method integer,
    path character varying(255),
    usr character varying(255) NOT NULL
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
    description character varying(500),
    last_modified_by bigint NOT NULL,
    name character varying(20) NOT NULL,
    valid_flag integer NOT NULL,
    version integer NOT NULL,
    created_at bigint NOT NULL,
    last_modified_at bigint NOT NULL
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
    description character varying(500),
    last_modified_by bigint NOT NULL,
    name character varying(20) NOT NULL,
    valid_flag integer NOT NULL,
    version integer NOT NULL,
    created_at bigint NOT NULL,
    last_modified_at bigint NOT NULL
);


ALTER TABLE roles OWNER TO core;

--
-- Name: roles_has_resources; Type: TABLE; Schema: public; Owner: core
--

CREATE TABLE roles_has_resources (
    role_id bigint NOT NULL,
    resource_id bigint NOT NULL
);


ALTER TABLE roles_has_resources OWNER TO core;

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
    description text,
    ip character varying(255),
    is_account_non_expired_alias boolean NOT NULL,
    is_account_non_locked_alias boolean NOT NULL,
    is_credentials_non_expired_alias boolean NOT NULL,
    is_enabled_alias boolean NOT NULL,
    last_modified_by bigint NOT NULL,
    name character varying(50) NOT NULL,
    pwd character varying(200) NOT NULL,
    usr character varying(20) NOT NULL,
    valid_flag integer NOT NULL,
    version integer NOT NULL,
    created_at bigint NOT NULL,
    last_modified_at bigint NOT NULL,
    last_login_at bigint NOT NULL
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
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: core
--

COPY accounts (id, account, account_source_type, created_at, created_by, last_modified_at, last_modified_by, version, user_id) FROM stdin;
1	root	0	1467037490081	0	1467037490081	0	1	1
2	admin	0	1467037490352	1	1467037490352	1	1	2
3	guest	0	1467037490352	1	1467037490352	1	1	3
\.


--
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: core
--

COPY clients (id, access_token_validity_seconds_alias, additional_information_str, authorities_str, authorized_grant_type_str, client_id_alias, client_secret_alias, created_by, last_modified_by, public_key, refresh_token_validity_seconds_alias, registered_redirect_uri_str, resource_id_str, scope_str, valid_flag, version, created_at, last_modified_at) FROM stdin;
2	86400	\N	\N	password,refresh_token,authorization_code	ios_app	123456	1	1	MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvpO6M1Ghv4YeEeOFHB41FtzwDLB49ovrjfYU4+YvTvXvwL1AdVlJhKfp/MveMK8tzL5Prya11nsIQnyz/dVdiWhu7xqC6fE/xbWswEskBRa/QUvOFaKZS6ZRenGsst7YTQmiEWlhZwduDvCcPrz4pEusRg+GtETdbWqO3D0O+NF9bmkEGcKvHB1BHKv6Nj8PSL0Zt8h2fbZLWNSEYWPLCA+onhtGL7pAkpGQxAtZLJTYhrTw4oo7+bcSjha/2AHfnsCcMa65EoU1BSjD18bjG+AAE6JNURH5Nl2NgRL7wT4LH1/0vJpUnCxjkWWN46648k22ogciDSr73msJuAzp9wIDAQAB	86400	http://www.apple.com	api	read	1	0	1467037490327	1467037490327
1	86400	\N	\N	password,refresh_token,authorization_code	admin	123456	1	1	MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvpO6M1Ghv4YeEeOFHB41FtzwDLB49ovrjfYU4+YvTvXvwL1AdVlJhKfp/MveMK8tzL5Prya11nsIQnyz/dVdiWhu7xqC6fE/xbWswEskBRa/QUvOFaKZS6ZRenGsst7YTQmiEWlhZwduDvCcPrz4pEusRg+GtETdbWqO3D0O+NF9bmkEGcKvHB1BHKv6Nj8PSL0Zt8h2fbZLWNSEYWPLCA+onhtGL7pAkpGQxAtZLJTYhrTw4oo7+bcSjha/2AHfnsCcMa65EoU1BSjD18bjG+AAE6JNURH5Nl2NgRL7wT4LH1/0vJpUnCxjkWWN46648k22ogciDSr73msJuAzp9wIDAQAB	86400	http://github.com/saintdan	api	read	1	0	1467037490327	1467037490327
\.


--
-- Data for Name: logs; Type: TABLE DATA; Schema: public; Owner: core
--

COPY logs (id, access_resource, client_id, created_date, ip, type, user_id, username, created_at, method, path, usr) FROM stdin;
\.


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

COPY resources (id, created_by, description, last_modified_by, name, valid_flag, version, created_at, last_modified_at) FROM stdin;
1	1	all resources	1	root	1	1	1462285532000	1462285532000
2	1	management	1	management	1	1	1462285532000	1462285532000
3	1	app resource	1	app	1	1	1462285532000	1462285532000
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: core
--

COPY roles (id, created_by, description, last_modified_by, name, valid_flag, version, created_at, last_modified_at) FROM stdin;
1	1	root role	1	root	1	2	1467037490575	1467037490575
2	1	admin role	1	admin	1	2	1467037490575	1467037490575
3	1	guest role	1	guest	1	2	1467037490575	1467037490575
\.


--
-- Data for Name: roles_has_resources; Type: TABLE DATA; Schema: public; Owner: core
--

COPY roles_has_resources (role_id, resource_id) FROM stdin;
1	1
2	2
3	3
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: core
--

COPY users (id, created_by, description, ip, is_account_non_expired_alias, is_account_non_locked_alias, is_credentials_non_expired_alias, is_enabled_alias, last_modified_by, name, pwd, usr, valid_flag, version, created_at, last_modified_at, last_login_at) FROM stdin;
2	1	admin account	true	t	t	t	t	1	admin	$2a$10$QTh9AVEsHEexICX3Yu/rCuM/N4wDDslcx8bLJTU9oFBMeW0etPjGS	admin	1	1	1467037490352	1467037490352	1468495433700
1	0	root account	0:0:0:0:0:0:0:1	t	t	t	t	0	root	$2a$10$ZBqt0Z73hxXZWCGUM51g8OLqti.8XqBEQRpmIgjw/wcEtyXlG9Jey	root	1	2	1467037490081	1467037490081	1487606227598
3	1	guest account	true	t	t	t	t	1	guest	$2a$10$L45.IZTSKhdKD4bqBjHJHOpCY9x6eUse1URqoBn5Z0saFD/x.p92i	guest	1	1	1467037490352	1467037490352	1468495433700
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
-- Name: accounts_seq; Type: SEQUENCE SET; Schema: public; Owner: core
--

SELECT pg_catalog.setval('accounts_seq', 1, false);


--
-- Name: clients_seq; Type: SEQUENCE SET; Schema: public; Owner: core
--

SELECT pg_catalog.setval('clients_seq', 2, false);


--
-- Name: logs_seq; Type: SEQUENCE SET; Schema: public; Owner: core
--

SELECT pg_catalog.setval('logs_seq', 1, true);


--
-- Name: resources_seq; Type: SEQUENCE SET; Schema: public; Owner: core
--

SELECT pg_catalog.setval('resources_seq', 3, false);


--
-- Name: roles_seq; Type: SEQUENCE SET; Schema: public; Owner: core
--

SELECT pg_catalog.setval('roles_seq', 3, false);


--
-- Name: users_seq; Type: SEQUENCE SET; Schema: public; Owner: core
--

SELECT pg_catalog.setval('users_seq', 3, false);


--
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);


--
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- Name: logs logs_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY logs
    ADD CONSTRAINT logs_pkey PRIMARY KEY (id);


--
-- Name: oauth_access_token oauth_access_token_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY oauth_access_token
    ADD CONSTRAINT oauth_access_token_pkey PRIMARY KEY (authentication_id);


--
-- Name: oauth_client_details oauth_client_details_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY oauth_client_details
    ADD CONSTRAINT oauth_client_details_pkey PRIMARY KEY (client_id);


--
-- Name: oauth_client_token oauth_client_token_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY oauth_client_token
    ADD CONSTRAINT oauth_client_token_pkey PRIMARY KEY (authentication_id);


--
-- Name: resources resources_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY resources
    ADD CONSTRAINT resources_pkey PRIMARY KEY (id);


--
-- Name: roles_has_resources roles_has_resources_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY roles_has_resources
    ADD CONSTRAINT roles_has_resources_pkey PRIMARY KEY (role_id, resource_id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: resources uk_l85pqajoc7v2drqv3tj3rcmpq; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY resources
    ADD CONSTRAINT uk_l85pqajoc7v2drqv3tj3rcmpq UNIQUE (name);


--
-- Name: roles uk_ofx66keruapi6vyqpv6f2or37; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY roles
    ADD CONSTRAINT uk_ofx66keruapi6vyqpv6f2or37 UNIQUE (name);


--
-- Name: users_has_roles users_has_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY users_has_roles
    ADD CONSTRAINT users_has_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users_has_roles fk29e00q3gcfq3trteh62ibxujv; Type: FK CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY users_has_roles
    ADD CONSTRAINT fk29e00q3gcfq3trteh62ibxujv FOREIGN KEY (role_id) REFERENCES roles(id);


--
-- Name: roles_has_resources fk3mnb7c3fl2ib9ssby4rhac041; Type: FK CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY roles_has_resources
    ADD CONSTRAINT fk3mnb7c3fl2ib9ssby4rhac041 FOREIGN KEY (resource_id) REFERENCES resources(id);


--
-- Name: roles_has_resources fk4pcumypy4t9kx5vftpauuj6l; Type: FK CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY roles_has_resources
    ADD CONSTRAINT fk4pcumypy4t9kx5vftpauuj6l FOREIGN KEY (role_id) REFERENCES roles(id);


--
-- Name: users_has_roles fkinp2sirarlxndem3m3bfo61d2; Type: FK CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY users_has_roles
    ADD CONSTRAINT fkinp2sirarlxndem3m3bfo61d2 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: accounts fknjuop33mo69pd79ctplkck40n; Type: FK CONSTRAINT; Schema: public; Owner: core
--

ALTER TABLE ONLY accounts
    ADD CONSTRAINT fknjuop33mo69pd79ctplkck40n FOREIGN KEY (user_id) REFERENCES users(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: Yifan
--

GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

