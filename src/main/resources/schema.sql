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