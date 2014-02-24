CREATE USER mcloud WITH PASSWORD 'mCloudopir&3(!&#()*$';

--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: mcloud; Type: SCHEMA; Schema: -; Owner: mcloud
--

CREATE SCHEMA mcloud;


ALTER SCHEMA mcloud OWNER TO "mcloud";

SET search_path = mcloud, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: access_group; Type: TABLE; Schema: mcloud; Owner: mcloud; Tablespace: 
--

CREATE TABLE access_group (
    id bigserial NOT NULL,
    group_name text NOT NULL,
    access_resources text NOT NULL,
    access_type text NOT NULL
);


ALTER TABLE mcloud.access_group OWNER TO "mcloud";

--
-- Name: complaint; Type: TABLE; Schema: mcloud; Owner: mcloud; Tablespace: 
--

CREATE TABLE complaint (
    id bigserial NOT NULL,
    date_created date NOT NULL,
    date_acknowledged date,
    date_due date,
    date_closed date,
    status integer NOT NULL,
    priority integer NOT NULL,
    contents text NOT NULL,
    creator integer NOT NULL,
    track_list text NOT NULL
);


ALTER TABLE mcloud.complaint OWNER TO "mcloud";

--
-- Name: complaint_history; Type: TABLE; Schema: mcloud; Owner: mcloud; Tablespace: 
--

CREATE TABLE complaint_history (
    id bigserial NOT NULL,
    date_created date NOT NULL,
    date_acknowledged date,
    date_due date,
    date_closed date,
    status integer NOT NULL,
    priority integer NOT NULL,
    contents text NOT NULL,
    creator integer NOT NULL,
    track_list text NOT NULL,
    complaint_id integer NOT NULL,
    history_entry_created date
);


ALTER TABLE mcloud.complaint_history OWNER TO "mcloud";

--
-- Name: default_seq; Type: SEQUENCE; Schema: mcloud; Owner: mcloud
--

CREATE SEQUENCE default_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 25
    CYCLE;


ALTER TABLE mcloud.default_seq OWNER TO "mcloud";

--
-- Name: job; Type: TABLE; Schema: mcloud; Owner: mcloud; Tablespace: 
--

CREATE TABLE job (
    id bigserial NOT NULL,
    date_created date NOT NULL,
    date_acknowledged date,
    date_due date,
    date_closed date,
    status integer NOT NULL,
    priority integer NOT NULL,
    assignee integer,
    complaint integer,
    contents text NOT NULL,
    component integer,
    service integer,
    module integer,
    track_list text NOT NULL,
    planned_work_start date,
    planned_work_end date,
    creator integer NOT NULL
);


ALTER TABLE mcloud.job OWNER TO "mcloud";

--
-- Name: job_history; Type: TABLE; Schema: mcloud; Owner: mcloud; Tablespace: 
--

CREATE TABLE job_history (
    id bigserial NOT NULL,
    date_created date NOT NULL,
    date_acknowledged date,
    date_due date,
    date_closed date,
    status integer NOT NULL,
    priority integer NOT NULL,
    assignee integer,
    complaint integer,
    contents text NOT NULL,
    component integer,
    service integer,
    module integer,
    track_list text NOT NULL,
    planned_work_start date,
    planned_work_end date,
    creator integer NOT NULL,
    job_id integer  NOT NULL,
    history_entry_created date
);


ALTER TABLE mcloud.job_history OWNER TO "mcloud";

--
-- Name: platform_component; Type: TABLE; Schema: mcloud; Owner: mcloud; Tablespace: 
--

CREATE TABLE platform_component (
    id bigserial NOT NULL,
    component_name text NOT NULL
);


ALTER TABLE mcloud.platform_component OWNER TO "mcloud";

--
-- Name: platform_module; Type: TABLE; Schema: mcloud; Owner: mcloud; Tablespace: 
--

CREATE TABLE platform_module (
    id bigserial NOT NULL,
    module_name text NOT NULL
);


ALTER TABLE mcloud.platform_module OWNER TO "mcloud";

--
-- Name: platform_service; Type: TABLE; Schema: mcloud; Owner: mcloud; Tablespace: 
--

CREATE TABLE platform_service (
    id bigserial NOT NULL,
    service_name text NOT NULL
);


ALTER TABLE mcloud.platform_service OWNER TO "mcloud";

--
-- Name: platform_user; Type: TABLE; Schema: mcloud; Owner: mcloud; Tablespace: 
--

CREATE TABLE platform_user (
    id bigserial NOT NULL,
    username text NOT NULL,
    access_group_116 text
);


ALTER TABLE mcloud.platform_user OWNER TO "mcloud";

--
-- Name: priority; Type: TABLE; Schema: mcloud; Owner: mcloud; Tablespace: 
--

CREATE TABLE priority (
    id bigserial NOT NULL,
    priority_name text NOT NULL,
    job_deadline_days integer not NULL,
    complaint_deadline_days integer not NULL
);


ALTER TABLE mcloud.priority OWNER TO "mcloud";

--
-- Name: status; Type: TABLE; Schema: mcloud; Owner: mcloud; Tablespace: 
--

CREATE TABLE status (
    id bigserial NOT NULL,
    status_name text NOT NULL
);


ALTER TABLE mcloud.status OWNER TO "mcloud";

--
-- Name: Job_pkey; Type: CONSTRAINT; Schema: mcloud; Owner: mcloud; Tablespace: 
--

ALTER TABLE ONLY job
    ADD CONSTRAINT "Job_pkey" PRIMARY KEY (id);


ALTER TABLE ONLY job_history
    ADD CONSTRAINT "Job_history_pkey" PRIMARY KEY (id);

--
-- Name: access_group_pkey; Type: CONSTRAINT; Schema: mcloud; Owner: mcloud; Tablespace: 
--

ALTER TABLE ONLY access_group
    ADD CONSTRAINT access_group_pkey PRIMARY KEY (id);


--
-- Name: complaint_pkey; Type: CONSTRAINT; Schema: mcloud; Owner: mcloud; Tablespace: 
--

ALTER TABLE ONLY complaint
    ADD CONSTRAINT complaint_pkey PRIMARY KEY (id);


--
-- Name: platform_component_pkey; Type: CONSTRAINT; Schema: mcloud; Owner: mcloud; Tablespace: 
--

ALTER TABLE ONLY platform_component
    ADD CONSTRAINT platform_component_pkey PRIMARY KEY (id);


--
-- Name: platform_module_pkey; Type: CONSTRAINT; Schema: mcloud; Owner: mcloud; Tablespace: 
--

ALTER TABLE ONLY platform_module
    ADD CONSTRAINT platform_module_pkey PRIMARY KEY (id);


--
-- Name: platform_service_pkey; Type: CONSTRAINT; Schema: mcloud; Owner: mcloud; Tablespace: 
--

ALTER TABLE ONLY platform_service
    ADD CONSTRAINT platform_service_pkey PRIMARY KEY (id);


--
-- Name: priority_pkey; Type: CONSTRAINT; Schema: mcloud; Owner: mcloud; Tablespace: 
--

ALTER TABLE ONLY priority
    ADD CONSTRAINT priority_pkey PRIMARY KEY (id);


--
-- Name: status_pkey; Type: CONSTRAINT; Schema: mcloud; Owner: mcloud; Tablespace: 
--

ALTER TABLE ONLY status
    ADD CONSTRAINT status_pkey PRIMARY KEY (id);


--
-- Name: user_pkey; Type: CONSTRAINT; Schema: mcloud; Owner: mcloud; Tablespace: 
--

ALTER TABLE ONLY platform_user
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: fk_complaint_creator; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY complaint
    ADD CONSTRAINT fk_complaint_creator FOREIGN KEY (creator) REFERENCES platform_user(id);

ALTER TABLE ONLY complaint_history
    ADD CONSTRAINT "complaint_history_pkey" PRIMARY KEY (id);
--
-- Name: fk_complaint_priority; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY complaint
    ADD CONSTRAINT fk_complaint_priority FOREIGN KEY (priority) REFERENCES priority(id);


--
-- Name: fk_complaint_status; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY complaint
    ADD CONSTRAINT fk_complaint_status FOREIGN KEY (status) REFERENCES status(id);

--
-- Name: fk_complaint_creator; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY complaint_history
    ADD CONSTRAINT fk_complaint_history_creator FOREIGN KEY (creator) REFERENCES platform_user(id);


--
-- Name: fk_complaint_priority; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY complaint_history
    ADD CONSTRAINT fk_complaint_history_priority FOREIGN KEY (priority) REFERENCES priority(id);


--
-- Name: fk_complaint_status; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY complaint_history
    ADD CONSTRAINT fk_complaint_history_status FOREIGN KEY (status) REFERENCES status(id);

ALTER TABLE ONLY complaint_history
    ADD CONSTRAINT fk_complaint_history_complaint FOREIGN KEY (complaint_id) REFERENCES complaint(id);

--
-- Name: fk_job_assignee; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job
    ADD CONSTRAINT fk_job_assignee FOREIGN KEY (assignee) REFERENCES platform_user(id);


--
-- Name: fk_job_complaint; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job
    ADD CONSTRAINT fk_job_complaint FOREIGN KEY (complaint) REFERENCES complaint(id);


--
-- Name: fk_job_component; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job
    ADD CONSTRAINT fk_job_component FOREIGN KEY (component) REFERENCES platform_component(id);


--
-- Name: fk_job_creator; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job
    ADD CONSTRAINT fk_job_creator FOREIGN KEY (creator) REFERENCES platform_user(id);


--
-- Name: fk_job_module; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job
    ADD CONSTRAINT fk_job_module FOREIGN KEY (module) REFERENCES platform_module(id);


--
-- Name: fk_job_priority; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job
    ADD CONSTRAINT fk_job_priority FOREIGN KEY (priority) REFERENCES priority(id);


--
-- Name: fk_job_service; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job
    ADD CONSTRAINT fk_job_service FOREIGN KEY (service) REFERENCES platform_service(id);


--
-- Name: fk_job_status; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job
    ADD CONSTRAINT fk_job_status FOREIGN KEY (status) REFERENCES status(id);

--
-- Name: fk_job_assignee; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job_history
    ADD CONSTRAINT fk_job_history_job FOREIGN KEY (job_id) REFERENCES job(id);


ALTER TABLE ONLY job_history
    ADD CONSTRAINT fk_job_history_assignee FOREIGN KEY (assignee) REFERENCES platform_user(id);


--
-- Name: fk_job_complaint; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job_history
    ADD CONSTRAINT fk_job_history_complaint FOREIGN KEY (complaint) REFERENCES complaint(id);


--
-- Name: fk_job_component; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job_history
    ADD CONSTRAINT fk_job_history_component FOREIGN KEY (component) REFERENCES platform_component(id);


--
-- Name: fk_job_creator; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job_history
    ADD CONSTRAINT fk_job_history_creator FOREIGN KEY (creator) REFERENCES platform_user(id);


--
-- Name: fk_job_module; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job_history
    ADD CONSTRAINT fk_job_history_module FOREIGN KEY (module) REFERENCES platform_module(id);


--
-- Name: fk_job_priority; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job_history
    ADD CONSTRAINT fk_job_history_priority FOREIGN KEY (priority) REFERENCES priority(id);


--
-- Name: fk_job_service; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job_history
    ADD CONSTRAINT fk_job_history_service FOREIGN KEY (service) REFERENCES platform_service(id);


--
-- Name: fk_job_status; Type: FK CONSTRAINT; Schema: mcloud; Owner: mcloud
--

ALTER TABLE ONLY job_history
    ADD CONSTRAINT fk_job_history_status FOREIGN KEY (status) REFERENCES status(id);


--
-- PostgreSQL database dump complete
--


