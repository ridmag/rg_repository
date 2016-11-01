ALTER TABLE staffmembers DROP CONSTRAINT FK_staffmembers_leavepolicyid;

DROP TABLE IF EXISTS LeavePolicies;
DROP TABLE IF EXISTS LeavePolicyDetails;

/*

-- Old tables

CREATE TABLE LeavePolicyDetails(
  id SERIAL ,
  leaveType varchar(20),
  daysEntitled int,
  remarks varchar (250),
  PRIMARY KEY (id)); 
  
CREATE TABLE LeavePolicies(
  id SERIAL ,
  name varchar(20),
  leavePolicyDetailId bigint,
  status varchar(20),
  PRIMARY KEY (id));  

-- Old FKs (dropped and re-created)

ALTER TABLE ONLY LeavePolicies
    ADD CONSTRAINT FK_LeavePolicies_leavePolicyDetailId FOREIGN KEY (leavePolicyDetailId) REFERENCES LeavePolicyDetails(id) MATCH FULL;
*/
    
    
CREATE TABLE LeavePolicies(
  id SERIAL ,
  name varchar(20),
  status varchar(20),
  PRIMARY KEY (id)); 

CREATE TABLE LeavePolicyDetails(
  id SERIAL ,
  leaveType varchar(20),
  daysEntitled int,
  remarks varchar (250),
  leavePolicyId bigint not null,
  PRIMARY KEY (id));  

CREATE INDEX LeavePolicyDetails_leavePolicyId_index ON LeavePolicyDetails USING btree (leavePolicyId);

ALTER TABLE ONLY LeavePolicyDetails
    ADD CONSTRAINT FK_LeavePolicyDetails_leavePolicyId FOREIGN KEY (leavePolicyId) REFERENCES LeavePolicies(id) MATCH FULL;
ALTER TABLE ONLY staffmembers
   ADD CONSTRAINT FK_staffmembers_leavepolicyid FOREIGN KEY (leavepolicyid) REFERENCES LeavePolicies(id) MATCH FULL;
   
-- -------------------
-- Table programevents
-- -------------------

ALTER TABLE programevents RENAME COLUMN rosterStartTime TO actualStartTime;
ALTER TABLE programevents RENAME COLUMN rosterEndTime TO actualEndTime;

-- ----------------------------------------
-- Table ckeckrecords and staffcheckrecords
-- ----------------------------------------

/*

-- Old table

CREATE TABLE checkrecords(
  id SERIAL ,
  name varchar(255),
  needRenew boolean,
  PRIMARY KEY (id)); 

-- New table

CREATE TABLE checkrecords(
  id SERIAL ,
  name varchar(255),
  needRemarks boolean,
  needCompleted boolean,
  needExpiry boolean,
  PRIMARY KEY (id)); 

-- Old table
   
CREATE TABLE staffcheckrecords(
  id SERIAL ,
  checkrecordId bigint NOT NULL,
  staffId bigint NOT NULL,
  datecompleted date,
  renewdate date,
  checked boolean,
  renewable boolean,
  PRIMARY KEY (id)); 

-- New table

CREATE TABLE staffcheckrecords(
  id SERIAL ,
  checkrecordId bigint NOT NULL,
  staffId bigint NOT NULL,
  remarks varchar(100),
  dateCompleted date,
  expiryDate date,
  PRIMARY KEY (id));

-- Old FKs (dropped and re-created)
    
ALTER TABLE ONLY staffcheckrecords
   ADD CONSTRAINT FK_staffcheckrecords_staffId FOREIGN KEY (staffId) REFERENCES StaffMembers(id) MATCH FULL,
   ADD CONSTRAINT FK_staffcheckrecords_checkrecordId FOREIGN KEY (checkrecordId) REFERENCES checkrecords(id) MATCH FULL;
*/

ALTER TABLE staffcheckrecords DROP CONSTRAINT FK_staffcheckrecords_staffId;
ALTER TABLE staffcheckrecords DROP CONSTRAINT FK_staffcheckrecords_checkrecordId;

DROP TABLE IF EXISTS checkrecords;
CREATE TABLE checkrecords(
  id SERIAL ,
  name varchar(255),
  needRemarks boolean,
  needCompleted boolean,
  needExpiry boolean,
  PRIMARY KEY (id));
  
insert into checkrecords values(1, 'Criminal Record Check', false, true, true);
insert into checkrecords values(2, 'Working with Children', false, true, false);
insert into checkrecords values(3, 'Confidentiality Agreement', false, false, false);
insert into checkrecords values(4, 'Copy of Drivers license', true, false, true);
insert into checkrecords values(5, 'Copy of Car Registration', false, false, true);
insert into checkrecords values(6, 'Copy of Comprehensive Insurance', false, false, false);
insert into checkrecords values(7, 'Eligibility to work in Australia', true, false, true);

DELETE FROM staffcheckrecords WHERE checked = false;
ALTER TABLE staffcheckrecords RENAME COLUMN renewdate TO expiryDate;
ALTER TABLE staffcheckrecords DROP COLUMN renewable;
ALTER TABLE staffcheckrecords DROP COLUMN checked;
ALTER TABLE staffcheckrecords ADD remarks varchar(100);

ALTER TABLE ONLY staffcheckrecords
   ADD CONSTRAINT FK_staffcheckrecords_staffId FOREIGN KEY (staffId) REFERENCES StaffMembers(id) MATCH FULL,
   ADD CONSTRAINT FK_staffcheckrecords_checkrecordId FOREIGN KEY (checkrecordId) REFERENCES checkrecords(id) MATCH FULL;

-- ---------------------
-- Table StudentConsents
-- ---------------------

/*

-- New table

CREATE TABLE StudentConsents(
  id SERIAL ,
  consentId bigint NOT NULL,
  studentId bigint NOT NULL,  
  consentGiven boolean,
  PRIMARY KEY (id)); 

-- New table

CREATE TABLE StudentConsents(
  id SERIAL ,
  consentId bigint NOT NULL,
  studentId bigint NOT NULL, 
  PRIMARY KEY (id)); 

-- Old FKs (kept as it is)

ALTER TABLE ONLY StudentConsents
   ADD CONSTRAINT FK_StudentConsents_consentId FOREIGN KEY (consentId) REFERENCES consents(id) MATCH FULL,
   ADD CONSTRAINT FK_StudentConsents_studentId FOREIGN KEY (studentId) REFERENCES students(id) MATCH FULL;
*/
   
DELETE FROM StudentConsents WHERE consentGiven = false;
ALTER TABLE StudentConsents DROP COLUMN consentGiven;


   
   