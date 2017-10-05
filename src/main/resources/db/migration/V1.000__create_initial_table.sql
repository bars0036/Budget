CREATE TABLE budget (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(255)
);
ALTER TABLE budget OWNER TO localmattuser;
ALTER TABLE ONLY budget
    ADD CONSTRAINT budget_pkey PRIMARY KEY (id);


CREATE TABLE budget_by_period (
    id bigint NOT NULL,
    budget_category_id bigint,
    budget_end_date bytea,
    budget_start_date bytea,
    type character varying(255),
    value numeric(19,2)
);

ALTER TABLE budget_by_period OWNER TO localmattuser;
ALTER TABLE ONLY budget_by_period
    ADD CONSTRAINT budget_by_period_pkey PRIMARY KEY (id);

CREATE TABLE budget_category (
    id bigint NOT NULL,
    budget_id bigint,
    category_description character varying(255),
    category_name character varying(255),
    category_parent_id bigint
);
ALTER TABLE ONLY budget_category
    ADD CONSTRAINT budget_category_pkey PRIMARY KEY (id);
ALTER TABLE ONLY budget_category
    ADD CONSTRAINT fkosa9i1lxo3j8v3reheus8gubg FOREIGN KEY (budget_id) REFERENCES budget(id);

  CREATE TABLE budget_transaction (
    id bigint NOT NULL,
    auto_assigned boolean NOT NULL,
    budget_category_id bigint,
    date_assigned bytea,
    person_id bigint,
    transaction_id bigint,
    value numeric(19,2)
);
ALTER TABLE budget_transaction OWNER TO localmattuser;
ALTER TABLE ONLY budget_transaction
    ADD CONSTRAINT budget_transaction_pkey PRIMARY KEY (id);

CREATE TABLE person (
  id bigint NOT NULL,
  first_name character varying(255),
  last_name character varying(255),
  email_address character varying(255),
  phone_number character varying(255)
);
ALTER TABLE person OWNER TO localmattuser;
ALTER TABLE ONLY person
  ADD CONSTRAINT person_pkey PRIMARY KEY (id);

CREATE TABLE transaction (
  id bigint NOT NULL,
  account character varying(255),
  category character varying(255),
  date_created bytea,
  description character varying(255),
  institution character varying(255),
  is_hidden boolean,
  source character varying(255),
  transaction_date bytea,
  value numeric(19,2)
);
ALTER TABLE transaction OWNER TO localmattuser;
CREATE SEQUENCE transaction_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
ALTER TABLE transaction_id_seq OWNER TO localmattuser;
ALTER SEQUENCE transaction_id_seq OWNED BY transaction.id;
ALTER TABLE ONLY transaction ALTER COLUMN id SET DEFAULT nextval('transaction_id_seq'::regclass);
ALTER TABLE ONLY transaction
  ADD CONSTRAINT transaction_pkey PRIMARY KEY (id);

CREATE TABLE transaction_rule (
  id bigint NOT NULL,
  category_id character varying(255),
  match_string character varying(255)
);
ALTER TABLE transaction_rule OWNER TO localmattuser;
ALTER TABLE ONLY transaction_rule
  ADD CONSTRAINT transaction_rule_pkey PRIMARY KEY (id);

CREATE SEQUENCE hibernate_sequence
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;
ALTER TABLE hibernate_sequence OWNER TO localmattuser;