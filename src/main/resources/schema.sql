-- Tempoerary Client ids
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