-- Table: contacts

-- DROP TABLE fct_participant;
drop table if exists fct_participant ;

CREATE TABLE fct_participant
(
  id bigint,
  cisid character varying(20),
  mdsid character varying(20),
  status character varying(20),
  serviceenddate date,
  photoid bigint,
  dob date,
  title character varying(5),
  firstname character varying(20),
  lastname character varying(20),
  middlenames character varying(40),
  businessname character varying(30),
  streetaddress character varying(60),
  city character varying(30),
  state character varying(30),
  postcode character varying(15),
  country character varying(30),
  mobilephone character varying(20),
  homephone character varying(20),
  officephone character varying(20),
  fax character varying(20),
  email character varying(40),
  sendnewsletter boolean,
  sendinvoice boolean,
  guard_title character varying(5),
  guard_firstname character varying(20),
  guard_lastname character varying(20),
  guard_middlenames character varying(40),
  guard_businessname character varying(30),
  guard_streetaddress character varying(60),
  guard_city character varying(30),
  guard_state character varying(30),
  guard_postcode character varying(15),
  guard_country character varying(30),
  guard_mobilephone character varying(20),
  guard_homephone character varying(20),
  guard_officephone character varying(20),
  guard_fax character varying(20),
  guard_email character varying(40),
  guard_sendnewsletter boolean,
  guard_sendinvoice boolean,
  CONSTRAINT fct_participant_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE contacts OWNER TO postgres;
