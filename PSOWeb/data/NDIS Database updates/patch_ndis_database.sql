--Patch file for NDIS changes

--Add column to studentgroups
alter table studentgroups
add column refno bigint;


alter table studentgroups
add column ndisunitprice double precision;

--Add column to locations

alter table locations
add column criminalcheck boolean default false;

--Add column to students

alter table students
add column ndisnumber varchar(20);

-- Create ndisprices table;


CREATE TABLE ndissupportitems
(
  id bigserial NOT NULL,
  itemname character varying(50),
  uom character varying(15),
  ndisclustertype character varying(15),
  denominator integer,
  numerator integer,
  skills boolean,
  gstcode varchar(50),
  CONSTRAINT pid PRIMARY KEY (id)
);

CREATE TABLE ndisprices
(
  id bigserial NOT NULL,
  itemnumber varchar(20),
  pricename varchar(50),
  supportitem bigint,
  description varchar(150),
  startdate date,
  ndistime character varying(50),
  price double precision,
  uom character varying(20),

  CONSTRAINT "ID" PRIMARY KEY (id),
  CONSTRAINT ndisprices_supportitem_fkey FOREIGN KEY (supportitem)
      REFERENCES ndissupportitems (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--Add ndisinvoiceitems column to programevents

alter table programevents
add column ndisinvoiceitems varchar(10);


--Add ndisinvoiceitems table 


CREATE TABLE ndisinvoiceitems
(
  id bigserial NOT NULL,
  registrationnumber character varying(30) DEFAULT NULL::character varying,
  ndisnumber character varying(20) DEFAULT NULL::character varying,
  supportfrom date,
  supportto date,
  supportnumber character varying(20) DEFAULT NULL::character varying,
  claimref character varying(50) DEFAULT NULL::character varying,
  quantity double precision,
  hours character varying(6) DEFAULT NULL::character varying,
  unitprice double precision,
  gstcode character varying(50) DEFAULT NULL::character varying,
  authorisedby character varying(50) DEFAULT NULL::character varying,
  participantapproved boolean,
  inkindfundingprogram character varying(30) DEFAULT NULL::character varying,
  programeventid bigint,
  studenteventid bigint,
  CONSTRAINT id PRIMARY KEY (id),
  CONSTRAINT fk_ndisinvoiceitem_programevents FOREIGN KEY (programeventid)
      REFERENCES programevents (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ndisinvoiceitem_studentevent FOREIGN KEY (studenteventid)
      REFERENCES studentevents (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

--Add paymentmethod column to students

ALTER TABLE students 
ADD COLUMN paymentmethod varchar DEFAULT 'Cash';

--Add Hours per fortnight column to staffmembers

ALTER TABLE staffmembers 
ADD COLUMN hoursperfortnight double precision DEFAULT 0.0;

--Add ancillaryprogramcost Table


CREATE TABLE ancillaryprogramcost
(
  id bigserial NOT NULL,
  name character varying(40),
  price double precision,
  CONSTRAINT ancillaryprogramcost_pkey PRIMARY KEY (id)
);

--Add authorisedstaff Table

CREATE TABLE authorisedstaff
(
  id bigserial NOT NULL,
  staffid bigint,
  authorisingCode character varying(40),
  internalorgid bigserial NOT NULL,
  CONSTRAINT authorisedstaff_pkey PRIMARY KEY (id)
);

ALTER TABLE ONLY authorisedstaff
	ADD CONSTRAINT FK_authorisedstaff_staffId FOREIGN KEY (staffId) REFERENCES staffmembers(id) MATCH FULL;
	
	
--add ndisstafftimes




--Add internalorganizations Table



CREATE TABLE internalorganizations
(
  id bigserial NOT NULL,
  contactid bigint,
  serviceareaid bigint,
  "name" character varying(50),
  servicearea character varying,
  preferedsupplier boolean,
  ndisregistrationcode bigint,
  gst varchar(30),
  fortnightstartdate date,
  status character varying(20),

  CONSTRAINT internalorganizations_pkey PRIMARY KEY (id)
);

--Add allowprogramtorunonaholiday column to Studentgroups 2015/09/22
ALTER TABLE studentgroups ADD COLUMN allowprogramtorunonaholiday boolean DEFAULT false;


ALTER TABLE studentgroups add column inactiveovernight boolean DEFAULT false;

-- Add transportndis column to students table

ALTER TABLE students ADD COLUMN transportndis boolean DEFAULT false;



ALTER TABLE studentgroups ADD COLUMN gstcode varchar(15);


  

   
 --add gstcode to studentgroups
--add column gstcode varchar(20);


CREATE TABLE ndisstafftimes
(
  id serial NOT NULL,
  employmenttype varchar(20),
  hours double precision,
  organizationid bigint NOT NULL,
  PRIMARY KEY (id));
  
CREATE INDEX ndisstafftimes_organizationid_index ON ndisstafftimes USING btree (organizationId);
ALTER TABLE ONLY ndisstafftimes
    ADD CONSTRAINT FK_ndisstafftimes_organizationid FOREIGN KEY (organizationid) REFERENCES internalOrganizations(id) MATCH FULL;
;
 --Add ndisancillarycost Table  


CREATE TABLE ndisancillarycost
(
  id bigserial NOT NULL,
  ndisitemid bigint,
  date date,
  noofunit integer,
  uom character varying(20),
  claimed boolean DEFAULT false,
  studentid bigint,
  ndispriceid bigint,

  CONSTRAINT ndisancillarycost_pkey PRIMARY KEY (id)
);   
 CREATE INDEX ndisancillarycost_ndisitemid_index ON ndisancillarycost USING btree (ndisitemid);  
 CREATE INDEX ndisancillarycost_ndispriceid_index ON ndisancillarycost USING btree (ndispriceid);
 CREATE INDEX ndisancillarycost_studentid_index ON ndisancillarycost USING btree (studentid);
 ALTER TABLE ONLY ndisancillarycost
 ADD CONSTRAINT FK_ndisancillarycost_ndissupportitems FOREIGN KEY (ndisitemid) REFERENCES ndissupportitems(id) MATCH FULL,
 ADD CONSTRAINT FK_ndisancillarycost_ndispriceid FOREIGN KEY (ndispriceid) REFERENCES ndisprices(id) MATCH FULL,
 ADD CONSTRAINT FK_ndisancillarycost_studentid FOREIGN KEY (studentid) REFERENCES students(id) MATCH FULL;





-- add table ndiscommittedevents

CREATE TABLE ndiscommittedevents
(
  id bigserial NOT NULL,
  participant bigint,
  eventdate date,
  starttime timestamp with time zone,
  endtime timestamp with time zone,
  ndissupportcluster bigint,
  claimed boolean DEFAULT false,
  studentgroup bigint DEFAULT 0,
  hours double precision,
  kmsperday double precision,
  uncommittedhours double precision,
  committedhours double precision,
  clusteroverride bigint,
  priceid bigint,
  committedunits bigint,
  noofunits bigint,
  uncommittedunits bigint,

  CONSTRAINT committedeventid PRIMARY KEY (id),
  CONSTRAINT participantid FOREIGN KEY (participant)
      REFERENCES students (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ndiscommittedevenss_ndisclusterid FOREIGN KEY (ndissupportcluster)
      REFERENCES ndissupportitems (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ndiscommittedevents_ndispriceid FOREIGN KEY (priceid)
      REFERENCES ndisprices (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
-- add sevice area to service area table
INSERT INTO servicearea
VALUES (17,'Disability Support');

ALTER TABLE ndisprices ALTER COLUMN itemnumber type VARCHAR(30);

ALTER TABLE students
ADD COLUMN ndisfund double precision;

ALTER TABLE ndisancillarycost
ADD COLUMN authorized boolean DEFAULT false;

ALTER TABLE groupedstudents
ADD COLUMN chargeamount double precision;

  --Table outlets
			ALTER TABLE outlets
			ADD CONSTRAINT FK_Outlets_ID FOREIGN KEY (fundingsourceid)
			REFERENCES fundingsources(id);


--Table filedata
			ALTER TABLE filedata
			ADD CONSTRAINT FK_Filedata_ID FOREIGN KEY (staffid)
			REFERENCES staffmembers(id);

			ALTER TABLE filedata
			ADD CONSTRAINT FK_Students_ID FOREIGN KEY (studentid)
			REFERENCES students(id);

			ALTER TABLE filedata
			ADD CONSTRAINT FK_SpecialNeeds_ID FOREIGN KEY (sneedid)
			REFERENCES specialneeds(id);

			ALTER TABLE filedata
			ADD CONSTRAINT FK_Vehicles_ID FOREIGN KEY (vehicleid)
			REFERENCES vehicles(id);

			ALTER TABLE filedata
			ADD CONSTRAINT FK_StudentGroups_ID FOREIGN KEY (groupid)
			REFERENCES studentgroups(id);


--Table communications
			ALTER TABLE communications
			ADD CONSTRAINT FK_Communication_ID FOREIGN KEY (createdby)
			REFERENCES users(id);

--Table programs
			ALTER TABLE programs
			ADD CONSTRAINT FK_Programs_ID FOREIGN KEY (coordinatorid)
			REFERENCES staffmembers(id);

--Table prgramevents
			ALTER TABLE programevents
			ADD CONSTRAINT FK_Programevents_ID FOREIGN KEY (coordinatorid)
			REFERENCES staffmembers(id);

--Table ndiscommittedevents
			ALTER TABLE ndiscommittedevents
			ADD CONSTRAINT FK_StudentGroup_ID FOREIGN KEY (studentgroup)
			REFERENCES studentgroups(id);

			ALTER TABLE ndiscommittedevents
			ADD CONSTRAINT FK_ClusterOverride_ID FOREIGN KEY (clusteroverride)
			REFERENCES ndissupportitems(id);

--Table internalorganizations
			ALTER TABLE internalorganizations
			ADD CONSTRAINT FK_Contact_ID FOREIGN KEY (contactid)
			REFERENCES contacts(id);

			ALTER TABLE internalorganizations
			ADD CONSTRAINT FK_Servicearea_ID FOREIGN KEY (serviceareaid)
			REFERENCES servicearea(id);
 
--Table communication 
			ALTER TABLE communication
			ADD CONSTRAINT FK_Communication_ID FOREIGN KEY (createdby)
			REFERENCES users(id);
			
CREATE TABLE ndiscontributions
(
id bigserial NOT NULL,
clustertype bigint,
studentid bigint,
amount double precision,
contributeddate Date,

CONSTRAINT pk_ndiscontibutionid PRIMARY KEY (id),
CONSTRAINT fk_studentid FOREIGN KEY (studentid)
      REFERENCES students (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- create table groupstaffweekdays
CREATE TABLE groupedstaffweekdays
(
  id bigserial NOT NULL,
  groupedstaffid bigserial NOT NULL,
  weekdayid bigserial NOT NULL,
  starttime timestamp with time zone,
  endtime timestamp with time zone,
  lunch integer,
  CONSTRAINT groupedstaffweekdays_pkey PRIMARY KEY (id),
  CONSTRAINT fk_groupedstaffweekdays_groupedstaffid FOREIGN KEY (groupedstaffid)
      REFERENCES groupedstaff (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_groupedstaffweekdays_weekdayid FOREIGN KEY (weekdayid)
      REFERENCES weekdays (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- alter table programeventcoordinatorxref
ALTER TABLE programeventcoordinatorxref ADD COLUMN rosterstarttime timestamp with time zone;
ALTER TABLE programeventcoordinatorxref ADD COLUMN rosterendtime timestamp with time zone;

-- patch for 0 hoursperfortnight
UPDATE staffmembers SM 
	SET hoursperfortnight = (SELECT hours FROM ndisstafftimes NT WHERE SM.employmenttype = NT.employmenttype)
	WHERE SM.hoursperfortnight = 0;

-- group staff weekday patch
DELETE FROM groupedstaffweekdays;
INSERT INTO groupedstaffweekdays
(groupedstaffid,weekdayid,starttime,endtime)
SELECT GX.groupedstaffid,GX.weekdayid,SG.starttime,SG.endtime
FROM groupedstaffweekdaysxref GX
LEFT JOIN groupedstaff GS ON GS.id = GX.groupedstaffid
LEFT JOIN studentgroups SG ON SG.id = GS.groupid;


-- remove constraint from groupedstaffweekdaysxref
ALTER TABLE groupedstaffweekdaysxref
DROP CONSTRAINT fk_groupedstaffweekdaysxref_groupid,
DROP CONSTRAINT fk_groupedstaffweekdaysxref_weekdayid;

--alter column ndisregistrationcode type to varchar in internalorganization table
ALTER TABLE internalorganizations ALTER COLUMN ndisregistrationcode TYPE varchar(50);

--programeventcoordinatorxref patch
UPDATE programeventcoordinatorxref PEX
SET rosterstarttime = PE.starttime, rosterendtime = PE.endtime
FROM programevents PE
WHERE PEX.programeventid = PE.id AND PEX.rosterstarttime is null;

--groupedstudents patch TO UPDATE CHARGE AMOUNT FROM STUDENTGROUP
UPDATE groupedstudents GS
SET chargeamount = S.chargeamount
FROM studentgroups S
WHERE GS.groupid = S.id AND GS.chargeamount is null;

--add overrideprice column into programevents table
ALTER TABLE programevents
ADD COLUMN overrideprice boolean;