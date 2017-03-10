-- Temporary Client ids
insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity,
refresh_token_validity, autoapprove) values('railwayservice-web', 'res_railwayservice', 'AT7Qa52ffMvppDjd', 'read, write', 'password,refresh_token,authorization_code,implicit','ROLE_USER, ROLE_ADMIN',
3600, 360000, true);

insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity,
refresh_token_validity, autoapprove) values('railwayservice-ios', 'res_railwayservice', 'kJFKCt2EzsW3j2a4', 'read, write', 'password,refresh_token,authorization_code,implicit','ROLE_USER, ROLE_ADMIN',
3600, 360000, true);

-- Table: public.railway_user

-- DROP TABLE public.railway_user;

CREATE TABLE public.railway_user
(
    id bigint NOT NULL DEFAULT nextval('railway_user_id_seq'::regclass),
    login character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password_hash character varying(60) COLLATE pg_catalog."default",
    name character varying(50) COLLATE pg_catalog."default",
    role_type integer,
    email character varying(100) COLLATE pg_catalog."default",
    activated boolean NOT NULL,
    lang_key character varying(5) COLLATE pg_catalog."default",
    activation_key character varying(20) COLLATE pg_catalog."default",
    reset_key character varying(20) COLLATE pg_catalog."default",
    created_by character varying(50) COLLATE pg_catalog."default" NOT NULL,
    created_date timestamp without time zone NOT NULL DEFAULT now(),
    reset_date timestamp without time zone,
    last_modified_by character varying(50) COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    address character varying(255) COLLATE pg_catalog."default",
    city character varying(255) COLLATE pg_catalog."default",
    x_coordinate double precision,
    y_coordinate double precision,
    province character varying(255) COLLATE pg_catalog."default",
    region character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT pk_railway_user PRIMARY KEY (id),
    CONSTRAINT railway_user_email_key UNIQUE (email),
    CONSTRAINT railway_user_login_key UNIQUE (login),
    CONSTRAINT uc_railway_useremail_col UNIQUE (email)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.railway_user
    OWNER to postgres;

-- Index: idx_user_email

-- DROP INDEX public.idx_user_email;

CREATE UNIQUE INDEX idx_user_email
    ON public.railway_user USING btree
    (email COLLATE pg_catalog."default")
    TABLESPACE pg_default;

-- Index: idx_user_login

-- DROP INDEX public.idx_user_login;

CREATE UNIQUE INDEX idx_user_login
    ON public.railway_user USING btree
    (login COLLATE pg_catalog."default")
    TABLESPACE pg_default;

-- Tables

-- Table: public.train_crossing

-- DROP TABLE public.train_crossing;

CREATE TABLE public.train_crossing
(
    id bigint NOT NULL,
    address character varying(255) COLLATE pg_catalog."default",
    city character varying(255) COLLATE pg_catalog."default",
    x_coordinate double precision,
    y_coordinate double precision,
    province character varying(255) COLLATE pg_catalog."default",
    region character varying(255) COLLATE pg_catalog."default",
    railway character varying(255) COLLATE pg_catalog."default",
    notification_topic character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT train_crossing_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.train_crossing
    OWNER to postgres;

-- Index: train_crossing_topic_idx

-- DROP INDEX public.train_crossing_topic_idx;

CREATE INDEX train_crossing_topic_idx
    ON public.train_crossing USING btree
    (notification_topic COLLATE pg_catalog."default")
    TABLESPACE pg_default;

-- Table: public.audio_notification

-- DROP TABLE public.audio_notification;

CREATE TABLE public.audio_notification
(
    id bigint NOT NULL,
    file_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT audio_notification_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.audio_notification
    OWNER to postgres;
    
-- Table: public.train_alert

-- DROP TABLE public.train_alert;

CREATE TABLE public.train_alert
(
    user_id bigint NOT NULL,
    train_crossing_id bigint NOT NULL,
    audio_notification_id bigint,
    CONSTRAINT train_alert_pkey PRIMARY KEY (train_crossing_id, user_id),
    CONSTRAINT fk7qp39kdlemc9lhr0tqnln5glh FOREIGN KEY (user_id)
        REFERENCES public.railway_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fki824ukc74yhrp3xhfu0286747 FOREIGN KEY (audio_notification_id)
        REFERENCES public.audio_notification (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkiddfkuu9s52wige48cq3ryveo FOREIGN KEY (train_crossing_id)
        REFERENCES public.train_crossing (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.train_alert
    OWNER to postgres;
    
-- Table: public.job

-- DROP TABLE public.job;

CREATE TABLE public.job
(
    id bigint NOT NULL,
    enabled boolean,
    job_type character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT job_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.job
    OWNER to postgres;

-- Index: job_type_enabled_idx

-- DROP INDEX public.job_type_enabled_idx;

CREATE INDEX job_type_enabled_idx
    ON public.job USING btree
    (job_type COLLATE pg_catalog."default", enabled)
    TABLESPACE pg_default;
    
-- Table: public.train_crossing_report

-- DROP TABLE public.train_crossing_report;

CREATE TABLE public.train_crossing_report
(
    id bigint NOT NULL,
    reported_date timestamp without time zone NOT NULL,
    train_crossing_id bigint,
    user_id bigint,
    CONSTRAINT train_crossing_report_pkey PRIMARY KEY (id),
    CONSTRAINT fk76x9ngmillywkrixu1pom4yxj FOREIGN KEY (train_crossing_id)
        REFERENCES public.train_crossing (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fktcy9d1a911d0qklix5kb8s5tx FOREIGN KEY (user_id)
        REFERENCES public.railway_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.train_crossing_report
    OWNER to postgres;

-- Index: crossing_report_date_idx

-- DROP INDEX public.crossing_report_date_idx;

CREATE INDEX crossing_report_date_idx
    ON public.train_crossing_report USING btree
    (reported_date)
    TABLESPACE pg_default;
    
------------------------------------------------------------------------------------------------------------------------
----------------------------------------------------- Updates ----------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------

-- Column: public.railway_user.device_token

-- ALTER TABLE public.railway_user DROP COLUMN device_token;

ALTER TABLE public.railway_user
    ADD COLUMN device_token character varying(255) COLLATE pg_catalog."default";
    
-- Column: public.railway_user.platform

-- ALTER TABLE public.railway_user DROP COLUMN platform;

ALTER TABLE public.railway_user
    ADD COLUMN platform character varying(255) COLLATE pg_catalog."default";
    
-- Column: public.railway_user.fcm_token

-- ALTER TABLE public.railway_user DROP COLUMN fcm_token;

ALTER TABLE public.railway_user
    ADD COLUMN fcm_token character varying(255) COLLATE pg_catalog."default";
    
-- Column: public.train_crossing.flagged_active

-- ALTER TABLE public.train_crossing DROP COLUMN flagged_active;

ALTER TABLE public.train_crossing
    ADD COLUMN flagged_active boolean;
    
-- Column: public.train_crossing.time_flagged_active

-- ALTER TABLE public.train_crossing DROP COLUMN time_flagged_active;

ALTER TABLE public.train_crossing
    ADD COLUMN time_flagged_active timestamp without time zone;
    
-- Index: train_crossing_active_idx

-- DROP INDEX public.train_crossing_active_idx;

CREATE INDEX train_crossing_active_idx
    ON public.train_crossing USING btree
    (flagged_active, time_flagged_active)
    TABLESPACE pg_default;
    
-- Column: public.railway_user.firebase_auth_token

-- ALTER TABLE public.railway_user DROP COLUMN firebase_auth_token;

ALTER TABLE public.railway_user
    ADD COLUMN firebase_auth_token character varying(1000) COLLATE pg_catalog."default";
    
-- Column: public.train_crossing.is_formatted

-- ALTER TABLE public.train_crossing DROP COLUMN is_formatted;

ALTER TABLE public.train_crossing
    ADD COLUMN is_formatted boolean;
---------------------------------------------------------------------------------------------------------------------------- 
---------------------------------------------------------- Jobs ------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------
    
insert into job(id, enabled, job_type) values (1, true, 'MONITOR_ACTIVE_TRAIN_CROSSINGS');