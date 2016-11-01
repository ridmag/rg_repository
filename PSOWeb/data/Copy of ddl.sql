--****** Database Table Definition for PSOWeb *******  

-- -----------------------------------------------------
-- System Configuration
-- -----------------------------------------------------
DROP TABLE IF EXISTS configurations;
CREATE  TABLE configurations(
  id serial NOT NULL,
  parameter varchar(20),
  contents varchar(100),
  PRIMARY KEY (id));
  
-- ***************** Core Entity Tables ****************
-- -----------------------------------------------------
-- Drop Core Tables
-- -----------------------------------------------------
DROP TABLE IF EXISTS CommunicationNotes;
DROP TABLE IF EXISTS StudentSpecialNeedsXRef;
DROP TABLE IF EXISTS StudentCommunicatonXref;
DROP TABLE IF EXISTS programEventCoordinatorXRef;
DROP TABLE IF EXISTS StudentGuardians;
DROP TABLE IF EXISTS StudentGuardiansXRef;
DROP TABLE IF EXISTS Communications;
DROP TABLE IF EXISTS communicationCategories;
DROP TABLE IF EXISTS StudentFundingSources cascade;
DROP TABLE IF EXISTS studentevents;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS invoices;
DROP TABLE IF EXISTS programevents;
DROP TABLE IF EXISTS GroupedStudents;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS StudentGroups;
DROP TABLE IF EXISTS programs;
DROP TABLE IF EXISTS programtypes;
DROP TABLE IF EXISTS Leaves;
drop table if exists fileData cascade;
DROP TABLE IF EXISTS StaffSkills;
DROP TABLE IF EXISTS staffmembers cascade;
DROP TABLE IF EXISTS LeavePolicyDetails;
DROP TABLE IF EXISTS LeavePolicies;
DROP TABLE IF EXISTS stafftypes;
DROP TABLE IF EXISTS students cascade;
DROP TABLE IF EXISTS authrights;
DROP TABLE IF EXISTS rights;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS externalOrganizations;
DROP TABLE IF EXISTS contacts;
DROP TABLE IF EXISTS vehicles;
DROP TABLE IF EXISTS holidays;
DROP TABLE IF EXISTS calendars;
DROP TABLE IF EXISTS days;
DROP TABLE IF EXISTS reminders;
DROP TABLE IF EXISTS checkrecords cascade;
DROP TABLE IF EXISTS staffcheckrecords cascade;
DROP TABLE IF EXISTS consents cascade;
DROP TABLE IF EXISTS StudentConsents;
DROP TABLE IF EXISTS HoursUtilizedReportItem;
DROP TABLE IF EXISTS HoursUtilizedReport;

CREATE TABLE rights (
	id SERIAL,
	name varchar(20),
	description varchar(50),
	primary key(id)
	);
	
create table authrights (
	roleid INT,
	rightid INT,
	primary key(roleid, rightid)
	);
	
-- -----------------------------------------------------
-- Table contacts
-- -----------------------------------------------------
CREATE TABLE contacts (
	id BIGSERIAL  ,
	title VARCHAR(5) NULL ,
	firstName VARCHAR(20)  NULL ,
	lastName VARCHAR(20)  NULL ,
	middleNames VARCHAR(40) NULL ,
	businessName VARCHAR(30) NULL ,
	streetAddress VARCHAR(60) NULL ,
	city VARCHAR(30) NULL ,
	state VARCHAR(30) NULL ,
	postCode VARCHAR(15) NULL ,
	country VARCHAR(30) NULL ,
	geoCode VARCHAR(30) NULL ,
	mobilePhone VARCHAR(20) NULL ,
	homePhone VARCHAR(20) NULL ,
	officePhone VARCHAR(20) NULL ,
	fax VARCHAR(20) NULL ,
	email VARCHAR(40) NULL ,
	businessType VARCHAR(20) NULL ,
	sendNewsletter BOOLEAN NULL ,
	sendInvoice BOOLEAN,
	PRIMARY KEY (id)
) ;

-- -----------------------------------------------------
-- Table Reminders
-- -----------------------------------------------------
create table reminders (
    id BIGSERIAL,
    referenceId bigint,    
    createdDate timestamp with time zone,
    remindOn timestamp with time zone,
    type varchar(255),
    note varchar(255),
    no_of_reminders int,
    status varchar(255),
    primary key (id)
) ;

CREATE INDEX reminders_id_index ON reminders USING btree (id);

-- -----------------------------------------------------
-- User and Authority Tables
-- -----------------------------------------------------
CREATE  TABLE users (
  id bigSERIAL,
  contactId bigint not null,
  mailingAddressId bigint,
  username VARCHAR(20),
  password VARCHAR(20),
  user_type VARCHAR(10),
  enabled boolean ,
  systemUser boolean not null,
  createdOn timestamp with time zone NULL ,
  PRIMARY KEY (id)); 
CREATE INDEX users_contactId_index ON users USING btree (contactId);
ALTER TABLE ONLY users
    ADD CONSTRAINT FK_users_contactId FOREIGN KEY (contactId) REFERENCES contacts(id) MATCH FULL,
	ADD CONSTRAINT FK_users_mailingAddressId FOREIGN KEY (mailingAddressId) REFERENCES contacts(id) MATCH FULL;

CREATE  TABLE authorities(
  id SERIAL ,
  userId bigint DEFAULT NULL,
  username varchar(20),
  authority varchar(20),
  PRIMARY KEY (id)); 
  
CREATE INDEX authorities_userId_index ON authorities USING btree (userId);
ALTER TABLE ONLY authorities
    ADD CONSTRAINT FK_authorities_userId FOREIGN KEY (userId) REFERENCES users(id) MATCH FULL;
    
-- -----------------------------------------------------
-- Table ServiceAreas
-- -----------------------------------------------------
DROP TABLE IF EXISTS ServiceArea;
create table ServiceArea(
	id serial,
	name varchar(255),
	primary key (id));
	
-- -----------------------------------------------------
-- Table Students
-- -----------------------------------------------------
CREATE TABLE Students (
  id bigSERIAL,
  CISID varchar(20),
  MDSID varchar(20),
  gender VARCHAR(6),
  dob date NULL,
  contactId bigint not null,
  mailingAddress bigint,
  photoId bigint,
  transport boolean,
  activeFundingSrcId bigint,
  status varchar(20),
  mailAddressDefault boolean,
  PRIMARY KEY (id)); 
  
CREATE INDEX Students_photoId_index ON Students USING btree (photoId);
CREATE INDEX Students_contactId_index ON Students USING btree (contactId);
CREATE INDEX Students_mailingAddress_index ON Students USING btree (mailingAddress);
CREATE INDEX Students_activeFundingSrcId_index ON Students USING btree (activeFundingSrcId);
ALTER TABLE ONLY Students
    ADD CONSTRAINT FK_Students_contactId FOREIGN KEY (contactId) REFERENCES contacts(id) MATCH FULL,
    ADD CONSTRAINT FK_Students_mailingAddress FOREIGN KEY (mailingAddress) REFERENCES contacts(id) MATCH FULL;

-- -----------------------------------------------------
-- Table Vehicles
-- -----------------------------------------------------    
 CREATE TABLE Vehicles (
  id bigSERIAL,
  name VARCHAR(50),
  registrationId VARCHAR (20),
  passengers int not null,
  wheelChairs int,
  status VARCHAR (10),
  type VARCHAR (10),
  photoId bigint,
  PRIMARY KEY (id)); 
  
CREATE INDEX Vehicles_photoId_index ON Vehicles USING btree (photoId);
 
-- -----------------------------------------------------
-- Table LeavePolicies
-- -----------------------------------------------------
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

ALTER TABLE ONLY LeavePolicyDetails
    ADD CONSTRAINT FK_LeavePolicyDetails_leavePolicyId FOREIGN KEY (leavePolicyId) REFERENCES LeavePolicies(id) MATCH FULL;
        
-- -----------------------------------------------------
-- Table StaffTypes
-- -----------------------------------------------------
CREATE TABLE stafftypes(
  id serial NOT NULL,
  name varchar(50),
  PRIMARY KEY (id));

-------------------------------------------------------
--Table StaffMembers
-------------------------------------------------------
CREATE TABLE staffmembers (
  id bigSERIAL,
  staffId varchar(20),
  gender VARCHAR(6),
  dob date NULL,
  joinedDate date,
  serviceEndDate date,
  stafftypeId int not null,
  employmentType varchar(20),
  photoId bigint,
  leavePolicyId bigint,
  status varchar(20),
  PRIMARY KEY (id));
  
CREATE INDEX staffmembers_photoId_index ON staffmembers USING btree (photoId);
CREATE INDEX staffmembers_stafftypeId_index ON staffmembers USING btree (stafftypeId);
ALTER TABLE ONLY staffmembers
    ADD CONSTRAINT FK_staffmembers_stafftypeId FOREIGN KEY (stafftypeId) REFERENCES stafftypes(id) MATCH FULL,
    ADD CONSTRAINT FK_staffmembers_leavePolicyId FOREIGN KEY (leavePolicyId) REFERENCES leavePolicies(id) MATCH FULL;
 
-- -----------------------------------------------------
-- Table Leaves
-- -----------------------------------------------------
CREATE TABLE Leaves(
  id SERIAL ,
  reason varchar(255),
  startDate date,
  endDate date,
  leaveType varchar(50),
  days int,
  staffId bigint,
  PRIMARY KEY (id)); 
  
ALTER TABLE ONLY Leaves
    ADD CONSTRAINT FK_Leaves_staffId FOREIGN KEY (staffId) REFERENCES staffmembers(id) MATCH FULL;
    
-- -----------------------------------------------------
-- Table ExternalOrganizations
-- -----------------------------------------------------
CREATE TABLE externalOrganizations(
    id bigSERIAL,
    contactId bigint,
    contactPersonId bigint,
    serviceareaId bigint,
	name varchar(50),
	paymentOption varchar(50),
	serviceArea varchar,
	preferedSupplier boolean,
	status varchar(20),
	PRIMARY KEY (id));
	
ALTER TABLE ONLY externalOrganizations
   ADD CONSTRAINT FK_externalOrganizations_contactId FOREIGN KEY (contactId) REFERENCES contacts(id) MATCH FULL;
ALTER TABLE ONLY externalOrganizations
   ADD CONSTRAINT FK_externalOrganizations_contactPersonId FOREIGN KEY (contactPersonId) REFERENCES contacts(id) MATCH FULL;
ALTER TABLE ONLY externalOrganizations
   ADD CONSTRAINT FK_externalOrganizations_serviceareaId FOREIGN KEY (serviceareaId) REFERENCES servicearea(id) MATCH FULL;
  
  DROP TABLE IF EXISTS outlets;
  create table outlets (
    id BIGSERIAL,
    serviceid varchar (20),    
    name varchar(255),
    description varchar(255),
    mdsid varchar(255),
    fundingsourceid bigint,
    status varchar(255),
    primary key (id)
) ;

CREATE INDEX outlets_id_index ON outlets USING btree (id);
ALTER TABLE ONLY outlets
    ADD CONSTRAINT FK_outlets_fundingsourceid FOREIGN KEY (fundingsourceid) REFERENCES FundingSources(id) MATCH FULL;
  
-- -----------------------------------------------------
-- Table FundingSources
-- -----------------------------------------------------
DROP TABLE IF EXISTS FundingSources cascade;
CREATE TABLE FundingSources(
  id bigserial,
  fundingCode varchar(20),
  fundingDescription varchar(255),
  fundingtype varchar(255),
  status varchar(20),
  PRIMARY KEY (id));
  
-- -----------------------------------------------------
-- Table StudentFundingSources
-- -----------------------------------------------------
CREATE TABLE StudentFundingSources(
  id bigserial,
  studentId bigint NOT NULL,
  fundingSrcId bigint NOT NULL,
  outletId bigint,
  fundingLevel varchar(20),
  fundingHours bigint,
  fundingHoursUsed bigint,
  fundingStartDate date,
  active boolean not null,
  PRIMARY KEY (id));
  
CREATE INDEX StudentFundingSources_studentId_index ON StudentFundingSources USING btree (studentId);
CREATE INDEX StudentFundingSources_fundingSrcId_index ON StudentFundingSources USING btree (fundingSrcId);
CREATE INDEX StudentFundingSources_outletId_index ON StudentFundingSources USING btree (outletId);
ALTER TABLE ONLY StudentFundingSources
    ADD CONSTRAINT FK_StudentFundingSources_studentId FOREIGN KEY (studentId) REFERENCES students(id) MATCH FULL,
    ADD CONSTRAINT FK_StudentFundingSources_outletId FOREIGN KEY (outletId) REFERENCES outlets(id) MATCH FULL,
    ADD CONSTRAINT FK_StudentFundingSources_fundingSrcId FOREIGN KEY (fundingSrcId) REFERENCES FundingSources(id) MATCH FULL;
ALTER TABLE ONLY Students
    ADD CONSTRAINT FK_students_activeFundingSrcId FOREIGN KEY (activeFundingSrcId) REFERENCES StudentFundingSources(id) MATCH FULL;
  
-- -----------------------------------------------------
-- CommunicationCategories and Communications Tables
-- -----------------------------------------------------
create table communicationCategories(
	id serial,
	name varchar(50),
	parentId bigint,
	primary key (id));
 
DROP TABLE IF EXISTS StudentCommunicatonXref;
CREATE TABLE StudentCommunicatonXref(
  communicationId bigint NOT NULL,
  studentId bigint NOT NULL,
  PRIMARY KEY (communicationId,studentId));
	
CREATE TABLE Communications(
  id SERIAL ,
  method varchar,
  createdDate date,
  createdTime timestamp with time zone,
  createdBy bigint,
  keyPersonStaff bigint,
  keyPersonStudent bigint,
  keyPersonOrg bigint,
  keyPersonVehicle bigint,
  keyPersonFunding bigint,
  summary varchar(255),
  category bigint,
  status varchar(20),
  reminderId bigint,
  PRIMARY KEY (id)); 
  
ALTER TABLE ONLY Communications
    ADD CONSTRAINT FK_Communication_category FOREIGN KEY (category) REFERENCES communicationCategories(id) MATCH FULL,
    ADD CONSTRAINT FK_Communication_keyPersonStaff FOREIGN KEY (keyPersonStaff) REFERENCES staffmembers(id) MATCH FULL,
    ADD CONSTRAINT FK_Communication_keyPersonStudent FOREIGN KEY (keyPersonStudent) REFERENCES students(id) MATCH FULL,
    ADD CONSTRAINT FK_Communication_keyPersonVehicle FOREIGN KEY (keyPersonVehicle) REFERENCES vehicles(id) MATCH FULL,
    ADD CONSTRAINT FK_Communication_keyPersonFunding FOREIGN KEY (keyPersonFunding) REFERENCES fundingsources(id) MATCH FULL,
    ADD CONSTRAINT FK_Communication_reminderId FOREIGN KEY (reminderId) REFERENCES reminders(id) MATCH FULL,
    ADD CONSTRAINT FK_Communication_keyPersonOrg FOREIGN KEY (keyPersonOrg) REFERENCES externalorganizations(id) MATCH FULL;
    
ALTER TABLE ONLY StudentCommunicatonXref
   ADD CONSTRAINT FK_StudentCommunicatonXref_communicationId FOREIGN KEY (communicationId) REFERENCES Communications(id) MATCH FULL,
   ADD CONSTRAINT FK_StudentCommunicatonXref_studentId FOREIGN KEY (studentId) REFERENCES students(id) MATCH FULL;
    
-- -----------------------------------------------------
-- Table CommunicationNotes
-- -----------------------------------------------------
create table CommunicationNotes(
	id bigserial,
	communicationId bigint,
	note varchar(255),
	notify varchar(255),
	primary key (id));
	
ALTER TABLE ONLY CommunicationNotes
    ADD CONSTRAINT FK_CommunicationNotes_communicationId FOREIGN KEY (communicationId) REFERENCES Communications(id) MATCH FULL;
   
-- -----------------------------------------------------
-- Table SpecialNeeds
-- -----------------------------------------------------
DROP TABLE IF EXISTS specialneeds;
CREATE TABLE specialneeds(
  id serial NOT NULL,
  name varchar(50),
  fileName varchar(50),
  photoId bigint,
  PRIMARY KEY (id));
  
  CREATE INDEX specialneeds_photoId_index ON specialneeds USING btree (photoId);
  
-- -----------------------------------------------------
-- Table filedata
-- -----------------------------------------------------
create table fileData (
    id bigserial not null,
    fileName varchar(100),
   	contenttype VARCHAR(20),
    filetype VARCHAR(20),
    dateCreated date,
    staffId bigint null,
    studentId bigint null,
    sneedId bigint null,
    vehicleId bigint null,
    refId bigint null,
    data bytea,
    thumbnail bytea,
    primary key (id));
    
CREATE INDEX filedata_staffId_index ON filedata USING btree (staffId);
CREATE INDEX filedata_studentId_index ON filedata USING btree (studentId);
CREATE INDEX filedata_sneedId_index ON filedata USING btree (sneedId);
CREATE INDEX filedata_vehicleId_index ON filedata USING btree (vehicleId);

ALTER TABLE ONLY staffmembers
    ADD CONSTRAINT FK_staffmembers_photoId FOREIGN KEY (photoId) REFERENCES filedata(id) MATCH FULL;
ALTER TABLE ONLY Students
    ADD CONSTRAINT FK_Students_photoId FOREIGN KEY (photoId) REFERENCES filedata(id) MATCH FULL;
ALTER TABLE ONLY specialneeds
    ADD CONSTRAINT FK_specialneeds_photoId FOREIGN KEY (photoId) REFERENCES filedata(id) MATCH FULL;   
ALTER TABLE ONLY Vehicles
	ADD CONSTRAINT FK_Vehicles_photoId FOREIGN KEY (photoId) REFERENCES filedata(id) MATCH FULL;

-- -----------------------------------------------------
-- Table ProgramTypes
-- -----------------------------------------------------
CREATE TABLE programtypes(
  id serial NOT NULL,
  name varchar(50),
  PRIMARY KEY (id));

-------------------------------------------------------
--Table Programs
-------------------------------------------------------
CREATE TABLE programs (
  id bigSERIAL,
  name VARCHAR(100),
  startDate date,
  endDate date,
  programTypeId int not null,
  chargeAmount float,
  status varchar(50),
  coordinatorID bigint null,
  vehicleId int,
  PRIMARY KEY (id));
  
CREATE INDEX programs_programTypeId_index ON programs USING btree (programTypeId);
CREATE INDEX programs_coordinatorID_index ON programs USING btree (coordinatorID);
ALTER TABLE ONLY programs
    ADD CONSTRAINT FK_programs_programTypeId FOREIGN KEY (programTypeId) REFERENCES programtypes(id) MATCH FULL;
ALTER TABLE ONLY programs
    ADD CONSTRAINT FK_programs_coordinatorID FOREIGN KEY (coordinatorID) REFERENCES staffmembers(id) MATCH FULL;
ALTER TABLE ONLY programs
    ADD CONSTRAINT FK_programs_vehicleId FOREIGN KEY (vehicleId) REFERENCES Vehicles(id) MATCH FULL;

-- -----------------------------------------------------
-- Table Locations
-- -----------------------------------------------------
CREATE TABLE locations(
  id serial NOT NULL,
  name varchar(50),
  locationCode varchar(10),
  contactId bigint,
  status varchar(20),
  PRIMARY KEY (id));
  
CREATE INDEX locations_contactId_index ON locations USING btree (contactId);
ALTER TABLE ONLY locations
    ADD CONSTRAINT FK_locations_contactId FOREIGN KEY (contactId) REFERENCES contacts(id) MATCH FULL;

-- -----------------------------------------------------
-- Table StudentGroups
-- -----------------------------------------------------
CREATE TABLE StudentGroups(
  id bigSERIAL,
  programId bigint,
  name varchar(50),
  status varchar(20),
  PRIMARY KEY (id));

ALTER TABLE ONLY StudentGroups
    ADD CONSTRAINT FK_StudentGroups_programId FOREIGN KEY (programId) REFERENCES programs(id) MATCH FULL;

  
-- -----------------------------------------------------
-- Table GroupedStudentXRef
-- -----------------------------------------------------
CREATE TABLE GroupedStudents(
  id bigSERIAL,
  groupId bigint,
  studentId bigint,
  status varchar(50),
  remarks varchar(255),
  pickup bigint,
  dropoff bigint,
  PRIMARY KEY (id));
  
ALTER TABLE ONLY GroupedStudents
    ADD CONSTRAINT FK_GroupedStudents_groupId FOREIGN KEY (groupId) REFERENCES studentgroups(id) MATCH FULL,
    ADD CONSTRAINT FK_GroupedStudents_studentId FOREIGN KEY (studentId) REFERENCES students(id) MATCH FULL,
    ADD CONSTRAINT FK_GroupedStudents_pickup FOREIGN KEY (pickup) REFERENCES locations(id) MATCH FULL,
    ADD CONSTRAINT FK_GroupedStudents_dropoff FOREIGN KEY (dropoff) REFERENCES locations(id) MATCH FULL;

-------------------------------------------------------
--Table ProgramEvents
-------------------------------------------------------
CREATE TABLE programevents (
  id bigSERIAL,
  name varchar(50),
  programId bigint not null,
  studentGroupId bigint not null,
  eventDate date NULL,
  startTime timestamp with time zone NULL,
  endTime timestamp with time zone NULL,
  actualStartTime timestamp with time zone NULL,
  actualEndTime timestamp with time zone NULL,
  coordinatorId bigint,
  locationId int,
  status varchar(50),
  fundingsStatus varchar(20),
  vehicleId int,
  printedUserId int,
  printedDate date,
  generatedDate date,
  completedDate date,
  invoiced boolean,
  PRIMARY KEY (id));
  
CREATE INDEX programevents_programId_index ON programevents USING btree (programId);
CREATE INDEX programevents_locationId_index ON programevents USING btree (locationId);
CREATE INDEX programevents_studentGroupId_index ON programevents USING btree (studentGroupId);
ALTER TABLE ONLY programevents
    ADD CONSTRAINT FK_programevents_programId FOREIGN KEY (programId) REFERENCES programs(id) MATCH FULL,
    ADD CONSTRAINT FK_programevents_locationId FOREIGN KEY (locationId) REFERENCES locations(id) MATCH FULL,
    ADD CONSTRAINT FK_programevents_studentGroupId FOREIGN KEY (studentGroupId) REFERENCES studentGroups(id) MATCH FULL,
    ADD CONSTRAINT FK_programevents_vehicleId FOREIGN KEY (vehicleId) REFERENCES Vehicles(id) MATCH FULL;
  
-- -----------------------------------------------------
-- Table ProgramEventCoordinatorXRef
-- -----------------------------------------------------
CREATE TABLE ProgramEventCoordinatorXRef(
	id SERIAL,
	programEventId bigint not null,
	coordinatorId bigint not null,
	primary key(id));

CREATE INDEX ProgramEventCoordinatorXRef_programEventId_index ON ProgramEventCoordinatorXRef USING btree (programEventId);
CREATE INDEX ProgramEventCoordinatorXRef_coordinatorId_index ON ProgramEventCoordinatorXRef USING btree (coordinatorId);
ALTER TABLE ONLY ProgramEventCoordinatorXRef
    ADD CONSTRAINT FK_ProgramEventCoordinatorXRef_programEventId FOREIGN KEY (programEventId) REFERENCES programevents(id) MATCH FULL,
    ADD CONSTRAINT FK_ProgramEventCoordinatorXRef_coordinatorId FOREIGN KEY (coordinatorId) REFERENCES staffmembers(id) MATCH FULL;
-- -----------------------------------------------------
-- Table Invoices
-- -----------------------------------------------------
CREATE TABLE invoices(
	id SERIAL,
	studentId bigint NOT NULL ,
	invoiceeId bigint,
	type VARCHAR(10) NULL,
	description VARCHAR(50) NULL,
	previousoutstanding double precision NULL,
	totalCharge double precision NULL,
	previousPayments double precision NULL,
	transportCharge double precision NULL,
	eftCharge double precision NULL,
	otherPayment double precision NULL,
	currentPayments double precision NULL,
	subTotal double precision NULL,
	discount double precision NULL,
	tax double precision NULL ,
	total double precision NULL ,
	invoiceDate timestamp with time zone NULL ,
	followupNote VARCHAR(200) NULL ,
	billedToThirdParty BOOLEAN NULL,
	status varchar(20),
	PRIMARY KEY (id));
	
CREATE INDEX invoices_studentId_index ON invoices USING btree (studentId);
CREATE INDEX invoices_invoiceeId_index ON invoices USING btree (invoiceeId);
ALTER TABLE ONLY invoices
    ADD CONSTRAINT FK_invoices_studentId FOREIGN KEY (studentId) REFERENCES students(id) MATCH FULL,
    ADD CONSTRAINT FK_invoices_invoiceeId FOREIGN KEY (invoiceeId) REFERENCES contacts(id) MATCH FULL;
        
drop table if exists invoiceitems;
create table invoiceitems(
id bigserial not null,
programname varchar(255),
programtype varchar(50),
studentgrp varchar(50),
eventdate date,
transactiondate date,
chargeamount double precision,
paidamount double precision,
balance double precision,
remarks varchar(255),
invoiceid bigint,
transportCharge double precision,
programCharge double precision,
paymentType varchar(40),
primary key(id)
);
-- -----------------------------------------------------
-- Table Transactions
-- -----------------------------------------------------
create TABLE transactions(
  id serial NOT NULL,
  contactId bigint,
  studentId bigint,
  studenteventId bigint,
  programEventId bigint,
  invoiceId bigint,
  transactionType varchar(20),
  amount double precision,
  remarks varchar(250),
  transactionDate date,
  createdUserId bigint,
  paymentType varchar(20),
  PRIMARY KEY (id));
  
CREATE INDEX transactions_contactId_index ON transactions USING btree (contactId);
CREATE INDEX transactions_studentId_index ON transactions USING btree (studentId);
CREATE INDEX transactions_programEventId_index ON transactions USING btree (programEventId);
CREATE INDEX transactions_invoiceId_index ON transactions USING btree (invoiceId);
CREATE INDEX transactions_createdUserId_index ON transactions USING btree (createdUserId);
ALTER TABLE ONLY transactions
    ADD CONSTRAINT FK_transactions_contactId FOREIGN KEY (contactId) REFERENCES contacts(id) MATCH FULL,
    ADD CONSTRAINT FK_transactions_programEventId FOREIGN KEY (programEventId) REFERENCES programevents(id) MATCH FULL,
    ADD CONSTRAINT FK_transactions_studentId FOREIGN KEY (studentId) REFERENCES students(id) MATCH FULL,
    ADD CONSTRAINT FK_transactions_invoiceId FOREIGN KEY (invoiceId) REFERENCES invoices(id) MATCH FULL,
    ADD CONSTRAINT FK_transactions_createdUserId FOREIGN KEY (createdUserId) REFERENCES users(id) MATCH FULL; 

-------------------------------------------------------
-- Table StudentEvents
-------------------------------------------------------
CREATE TABLE studentevents (
  id bigSERIAL,
  eventId bigint not null,
  groupedstudentId bigint not null,
  amountpaid double precision,
  attended boolean,
  remarks varchar(255),
  chargeTxId bigint,
  PRIMARY KEY (id)); 
  
CREATE INDEX studentevents_eventId_index ON studentevents USING btree (eventId);
CREATE INDEX studentevents_groupedstudentId_index ON studentevents USING btree (groupedstudentId);
CREATE INDEX studentevents_chargeTxId_index ON studentevents USING btree (chargeTxId);
ALTER TABLE ONLY studentevents
    ADD CONSTRAINT FK_studentevents_eventId FOREIGN KEY (eventId) REFERENCES programevents(id) MATCH FULL,
    ADD CONSTRAINT FK_studentevents_groupedstudentId FOREIGN KEY (groupedstudentId) REFERENCES groupedstudents(id) MATCH FULL,
    ADD CONSTRAINT FK_studentevents_chargeTxId FOREIGN KEY (chargeTxId) REFERENCES transactions(id) MATCH FULL;
  
-- -----------------------------------------------------
-- Table StudentGuardiansXRef
-- -----------------------------------------------------
  
CREATE TABLE StudentGuardiansXRef(
  contactId bigint NOT NULL,
  studentId bigint NOT NULL,
  PRIMARY KEY (contactId,studentId));
  
ALTER TABLE ONLY StudentGuardiansXRef
   ADD CONSTRAINT FK_StudentGuardiansXRef_contactId FOREIGN KEY (contactId) REFERENCES Contacts(id) MATCH FULL,
   ADD CONSTRAINT FK_StudentGuardiansXRef_studentId FOREIGN KEY (studentId) REFERENCES students(id) MATCH FULL;
  
DROP TABLE IF EXISTS Week;
DROP TABLE IF EXISTS WeekDays;
CREATE TABLE WeekDays(
  id bigSERIAL,
  name varchar(50),
  PRIMARY KEY (id));
  
-- -----------------------------------------------------
-- Table StudentSpecialNeedsXRef
-- -----------------------------------------------------
CREATE TABLE StudentSpecialNeedsXRef(
  studentId bigint NOT NULL,
  specialNeedId bigint NOT NULL,
  status varchar(50),
  reamarks varchar(255),
  PRIMARY KEY (studentId,specialNeedId));
  
ALTER TABLE ONLY StudentSpecialNeedsXRef
   ADD CONSTRAINT FK_StudentSpecialNeedsXRef_specialNeedId FOREIGN KEY (specialNeedId) REFERENCES SpecialNeeds(id) MATCH FULL,
   ADD CONSTRAINT FK_StudentSpecialNeedsXRef_studentId FOREIGN KEY (studentId) REFERENCES students(id) MATCH FULL;
    
-- -----------------------------------------------------
-- Table ReferenceItems
-- -----------------------------------------------------
DROP TABLE IF EXISTS referenceitems;
CREATE TABLE referenceitems(
  id bigserial not null,
  category varchar(20),
  itemvalue varchar(50),
  status varchar(20),
  PRIMARY KEY (id));
    
CREATE TABLE calendars(
  id SERIAL ,
  name varchar(20),
  PRIMARY KEY (id)); 
  
CREATE TABLE holidays(
  id SERIAL ,
  calendarId bigint NOT NULL,
  date date,
  holidayType varchar(20),
  remarks varchar(255),
  PRIMARY KEY (id)); 

ALTER TABLE ONLY holidays
    ADD CONSTRAINT FK_holidays_calendarId FOREIGN KEY (calendarId) REFERENCES calendars(id) MATCH FULL;
    
CREATE TABLE StaffSkills(
  id bigserial not null,
  staffId bigint NOT NULL,
  referenceItemId bigint NOT NULL,
  level varchar(50),
  PRIMARY KEY (staffId,referenceItemId));
  
ALTER TABLE ONLY StaffSkills
   ADD CONSTRAINT FK_StaffSkills_staffId FOREIGN KEY (staffId) REFERENCES StaffMembers(id) MATCH FULL,
   ADD CONSTRAINT FK_StaffSkills_referenceItemId FOREIGN KEY (referenceItemId) REFERENCES referenceitems(id) MATCH FULL;
   
   -- -----------------------------------------------------
-- Table Check Records
-- -----------------------------------------------------
CREATE TABLE checkrecords(
  id SERIAL ,
  name varchar(255),
  needRemarks boolean,
  needCompleted boolean,
  needExpiry boolean,
  PRIMARY KEY (id)); 
   
   -- -----------------------------------------------------
-- Table StaffCheckRecords
-- -----------------------------------------------------
CREATE TABLE staffcheckrecords(
  id SERIAL ,
  checkrecordId bigint NOT NULL,
  staffId bigint NOT NULL,
  remarks varchar(100),
  dateCompleted date,
  expiryDate date,
  PRIMARY KEY (id)); 
  
ALTER TABLE ONLY staffcheckrecords
   ADD CONSTRAINT FK_staffcheckrecords_staffId FOREIGN KEY (staffId) REFERENCES StaffMembers(id) MATCH FULL,
   ADD CONSTRAINT FK_staffcheckrecords_checkrecordId FOREIGN KEY (checkrecordId) REFERENCES checkrecords(id) MATCH FULL;
   
CREATE TABLE consents(
  id SERIAL ,
  name varchar(255),
  PRIMARY KEY (id)); 
  
CREATE TABLE StudentConsents(
  id SERIAL ,
  consentId bigint NOT NULL,
  studentId bigint NOT NULL, 
  PRIMARY KEY (id)); 
  
ALTER TABLE ONLY StudentConsents
   ADD CONSTRAINT FK_StudentConsents_consentId FOREIGN KEY (consentId) REFERENCES consents(id) MATCH FULL,
   ADD CONSTRAINT FK_StudentConsents_studentId FOREIGN KEY (studentId) REFERENCES students(id) MATCH FULL;
   
CREATE TABLE HoursUtilizedReport(
  id SERIAL ,
  generatedDate date,
  fromDate date,
  toDate date,
  PRIMARY KEY (id)); 
   
CREATE TABLE HoursUtilizedReportItem(
  id SERIAL ,
  studentId bigint NOT NULL,
  fundingType varchar(255),
  reportId bigint,
  totalHours double precision,
  programHrsUsed double precision,
  transportHrsUsed double precision,
  balanceHrs double precision,
  remarks varchar(255),
  PRIMARY KEY (id)); 
  
ALTER TABLE ONLY HoursUtilizedReportItem
  ADD CONSTRAINT FK_HoursUtilizedReportItem_studentId FOREIGN KEY (studentId) REFERENCES students(id) MATCH FULL,
  ADD CONSTRAINT FK_HoursUtilizedReportItem_reportId FOREIGN KEY (reportId) REFERENCES HoursUtilizedReport(id) MATCH FULL;

