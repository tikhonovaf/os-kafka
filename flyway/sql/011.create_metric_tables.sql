-- Table: public.metric_type

-- DROP TABLE IF EXISTS public.metric_type;

CREATE TABLE IF NOT EXISTS public.metric_type
(
    id bigint NOT NULL,
    code character varying(100) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT metric_type_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

-- Table: public.migration_vm_metric

-- DROP TABLE IF EXISTS public.migration_vm_metric;

CREATE TABLE IF NOT EXISTS public.vm_metric
(
    id bigint NOT NULL,
    metric_type_id bigint,
    vm_name character varying(255) COLLATE pg_catalog."default",
    value bigint,
    CONSTRAINT migration_vm_metric_pkey PRIMARY KEY (id),
    CONSTRAINT fk_vm_metric_metric_type FOREIGN KEY (metric_type_id)
        REFERENCES public.metric_type (id) MATCH SIMPLE
        ON UPDATE SET NULL
        ON DELETE SET NULL
)

    TABLESPACE pg_default;

DROP VIEW IF EXISTS public.vm_metric_view;

CREATE OR REPLACE VIEW public.vm_metric_view
AS
SELECT
    m.id,
    m.vm_name,
    m.metric_type_id,
    mt.name as metric_type_name,
    m.value
FROM vm_metric m
         JOIN metric_type mt on mt.id = m.metric_type_id
;
