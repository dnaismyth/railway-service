-- Temporary Client ids
insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity,
refresh_token_validity, autoapprove) values('railwayservice-web', 'res_railwayservice', 'AT7Qa52ffMvppDjd', 'read, write', 'password,refresh_token,authorization_code,implicit','ROLE_USER, ROLE_ADMIN',
3600, 360000, true);

insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity,
refresh_token_validity, autoapprove) values('railwayservice-ios', 'res_railwayservice', 'kJFKCt2EzsW3j2a4', 'read, write', 'password,refresh_token,authorization_code,implicit','ROLE_USER, ROLE_ADMIN',
3600, 360000, true);

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