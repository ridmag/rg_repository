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
CONSTRAINT fk_ndiscontributions_studentid FOREIGN KEY (studentid)
      REFERENCES students (id) MATCH FULL,
CONSTRAINT fk_ndiscontributions_clustertype FOREIGN KEY (clustertype)
      REFERENCES programtypes (id) MATCH FULL
);

ALTER TABLE authorisedstaff ADD COLUMN staffid bigint NOT NULL;
ALTER TABLE authorisedstaff rename column authorising to authorisingCode;
ALTER TABLE authorisedstaff ADD CONSTRAINT fk_authorisedstaff_staffid FOREIGN KEY (staffid)
      REFERENCES staffmembers (id) MATCH FULL;











 

