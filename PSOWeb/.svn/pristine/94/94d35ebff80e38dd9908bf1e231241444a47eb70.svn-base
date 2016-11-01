-- Function: test()

-- DROP FUNCTION test();

CREATE OR REPLACE FUNCTION fnc_participant()
  RETURNS void AS
$BODY$delete from fct_participant;
insert into public.fct_participant (select * from dblink('dbname=psodb201406 user=postgres password=pass','SELECT 
  students.id, 
  students.cisid, 
  students.mdsid, 
  students.status, 
  students.serviceenddate,
  students.photoid,
  students.dob,
  contacts.title, 
  contacts.firstname, 
  contacts.lastname, 
  contacts.middlenames, 
  contacts.businessname,
  contacts.streetaddress, 
  contacts.city, 
  contacts.state, 
  contacts.postcode, 
  contacts.country, 
  contacts.mobilephone, 
  contacts.homephone, 
  contacts.officephone,
  contacts.fax, 
  contacts.email, 
  contacts.sendnewsletter, 
  contacts.sendinvoice
FROM 
  public.contacts, 
  public.students
WHERE 
  students.contactid = contacts.id
') as t1(  id bigint,
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
  sendinvoice boolean
 ));$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION fnc_participant() OWNER TO postgres;
