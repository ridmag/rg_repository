-- Table: contacts

-- DROP TABLE fct_participant;
drop table if exists fct_staff ;

CREATE TABLE fct_staff
(
  id bigint,
  status character varying(20),
  photoid bigint,
  joineddate date,
  serviceenddate date,
  employmenttype varchar(10),
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
  CONSTRAINT fct_staff_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE contacts OWNER TO postgres;
