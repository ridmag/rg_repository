
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
      REFERENCES groupedstaff (id) MATCH FULL,
  CONSTRAINT fk_groupedstaffweekdays_weekdayid FOREIGN KEY (weekdayid)
      REFERENCES weekdays (id) MATCH FULL
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

DROP TABLE ndiscommittedevents;

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
  clusteroverride bigint,
  priceid bigint,
  noofunits bigint,
  kmsperday double precision,
  eventprice double precision,
  CONSTRAINT committedeventid PRIMARY KEY (id),
  CONSTRAINT fk_clusteroverride_id FOREIGN KEY (clusteroverride)
      REFERENCES ndissupportitems (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ndiscommittedevenss_ndisclusterid FOREIGN KEY (ndissupportcluster)
      REFERENCES ndissupportitems (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ndiscommittedevents_id FOREIGN KEY (studentgroup)
      REFERENCES studentgroups (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ndiscommittedevents_ndispriceid FOREIGN KEY (priceid)
      REFERENCES ndisprices (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentgroup_id FOREIGN KEY (studentgroup)
      REFERENCES studentgroups (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT participantid FOREIGN KEY (participant)
      REFERENCES students (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
