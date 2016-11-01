-- -------------------------------------------------------
-- Table Collections
-- -------------------------------------------------------
DROP TABLE IF EXISTS collections;
CREATE TABLE collections(
 id SERIAL NOT NULL,
 name VARCHAR(20),
 description VARCHAR(20),
 amount float,
 startDate date,
 endDate date,
 status VARCHAR(20),
 PRIMARY KEY (id));
 
-- -------------------------------------------------------
-- Table Transactions
-- -------------------------------------------------------
ALTER TABLE transactions RENAME paymentType TO paymentMethod;
ALTER TABLE transactions ADD COLUMN chargeType varchar(20);
ALTER TABLE transactions ADD COLUMN paymentType varchar(20);
ALTER TABLE transactions ADD COLUMN collectionId bigint;
ALTER TABLE transactions ADD COLUMN gstIncluded boolean DEFAULT false;
CREATE INDEX transactions_collectionId_index ON transactions USING btree (collectionId);
ALTER TABLE ONLY transactions
    ADD CONSTRAINT FK_transactions_collectionId FOREIGN KEY (collectionId) REFERENCES collections(id) MATCH FULL;
UPDATE transactions SET chargeType = 'EVENT' WHERE transactionType = 'CREDIT';
UPDATE transactions SET paymentType = 'EVENT' WHERE transactionType = 'DEBIT';
 
-- -------------------------------------------------------
-- Table invoiceItems
-- -------------------------------------------------------
ALTER TABLE invoiceItems RENAME paymentType TO paymentMethod;
ALTER TABLE invoiceItems ADD COLUMN chargeType varchar(20);
ALTER TABLE invoiceItems ADD COLUMN paymentType varchar(20);
ALTER TABLE invoices ADD COLUMN otherCharge double precision DEFAULT 0;
ALTER TABLE invoices ALTER COLUMN otherCharge DROP DEFAULT;
UPDATE invoiceItems SET chargeType = 'EVENT' WHERE chargeAmount > 0;
UPDATE invoiceItems SET paymentType = 'EVENT' WHERE paidAmount > 0;

-- -------------------------------------------------------
-- Table studentGroups
-- -------------------------------------------------------
ALTER TABLE studentGroups ADD COLUMN startDate date;
ALTER TABLE studentGroups ADD COLUMN endDate date;
ALTER TABLE studentGroups ADD COLUMN startTime timestamp with time zone;
ALTER TABLE studentGroups ADD COLUMN endTime timestamp with time zone;
ALTER TABLE studentGroups ADD COLUMN vehicleId bigint;
ALTER TABLE studentGroups ADD COLUMN lunchincluded boolean default false;

CREATE INDEX StudentGroups_vehicleId_index ON StudentGroups USING btree (vehicleId);
ALTER TABLE ONLY StudentGroups
    ADD CONSTRAINT FK_StudentGroups_vehicleId FOREIGN KEY (vehicleId) REFERENCES vehicles(id) MATCH FULL;
    
-- -----------------------------------------------------
-- Table GroupsWeekDaysXref
-- -----------------------------------------------------
DROP TABLE IF EXISTS GroupsWeekDaysXref;
CREATE TABLE GroupsWeekDaysXref(
	groupId bigSERIAL,
	weekDayId bigSERIAL,
	primary key(groupId, weekDayId));
	
ALTER TABLE ONLY GroupsWeekDaysXref
    ADD CONSTRAINT FK_GroupsWeekDaysXref_groupId FOREIGN KEY (groupId) REFERENCES StudentGroups(id) MATCH FULL,
    ADD CONSTRAINT FK_GroupsWeekDaysXref_weekDayId FOREIGN KEY (weekDayId) REFERENCES WeekDays(id) MATCH FULL;

-- -----------------------------------------------------
-- Table GroupsWeekDaysXref
-- -----------------------------------------------------
UPDATE contacts SET title = 'Mr' WHERE title = 'Mr.';
UPDATE contacts SET title = 'Ms' WHERE title = 'Ms.';
UPDATE contacts SET title = 'Mrs' WHERE title = 'Mrs.';
UPDATE contacts SET title = 'Miss' WHERE title = 'Miss.';

-- -----------------------------------------------------
-- Table StaffNotAvailableDayXRef
-- -----------------------------------------------------
CREATE TABLE StaffNotAvailableDayXRef(
	staffId bigint NOT NULL,
	weekdayId bigint NOT NULL,
	PRIMARY KEY(staffId,weekdayId));
	
ALTER TABLE ONLY StaffNotAvailableDayXRef
	ADD CONSTRAINT FK_StaffNotAvailableDayXRef_staffId FOREIGN KEY (staffId) REFERENCES staffmembers(id) MATCH FULL,
	ADD CONSTRAINT FK_StaffNotAvailableDayXRef_weekdayId FOREIGN KEY (weekdayId) REFERENCES WeekDays(id) MATCH FULL;
	
-- -------------------------------------------------------
-- Table ProgramEventCoordinatorXRef
-- -------------------------------------------------------
ALTER TABLE ProgramEventCoordinatorXRef ADD COLUMN startTime timestamp with time zone;
ALTER TABLE ProgramEventCoordinatorXRef ADD COLUMN endTime timestamp with time zone;
ALTER TABLE ProgramEventCoordinatorXRef ADD COLUMN lunchMinutes int DEFAULT 0;
ALTER TABLE ProgramEventCoordinatorXRef ADD COLUMN status varchar(10);
ALTER TABLE ProgramEventCoordinatorXRef DROP CONSTRAINT ProgramEventCoordinatorXRef_pkey RESTRICT;
ALTER TABLE ProgramEventCoordinatorXRef ADD COLUMN id bigSERIAL;
ALTER TABLE ProgramEventCoordinatorXRef ADD PRIMARY KEY (id, programEventId, coordinatorId);
SELECT setval('ProgramEventCoordinatorXRef_id_seq',(SELECT MAX(id) FROM ProgramEventCoordinatorXRef)+1);

-- -------------------------------------------------------
-- Table StudentFundingSources
-- -------------------------------------------------------
ALTER TABLE StudentFundingSources DROP COLUMN fundingHoursUsed;

-- -------------------------------------------------------
-- Table Students
-- -------------------------------------------------------
DROP INDEX Students_activeFundingSrcId_index;
ALTER TABLE ONLY Students DROP CONSTRAINT FK_students_activeFundingSrcId;
ALTER TABLE Students DROP COLUMN activeFundingSrcId;

-- -------------------------------------------------------
-- Table StudentEvents
-- -------------------------------------------------------
ALTER TABLE StudentEvents ADD COLUMN studentFundingSourceId bigint;
ALTER TABLE ONLY studentevents
    ADD CONSTRAINT FK_studentevents_studentFundingSourceId FOREIGN KEY (studentFundingSourceId) REFERENCES studentFundingSources(id) MATCH FULL;
    
-- -----------------------------------------------------
-- Table LeaveCategories
-- -----------------------------------------------------        
CREATE TABLE LeaveCategory(
	id BIGSERIAL,
	leaveType varchar(20),
	earnedHours double precision,
	usedHours double precision,
	staffId BIGINT,
	PRIMARY KEY(id));
	
ALTER TABLE ONLY LeaveCategory
	ADD CONSTRAINT FK_LeaveCategory_staffId FOREIGN KEY (staffId) REFERENCES staffmembers(id) MATCH FULL;
	
-- -----------------------------------------------------
-- Table StudentGroups
-- -----------------------------------------------------        
UPDATE StudentGroups SET lunchIncluded = false WHERE programId IN 
(SELECT p.id FROM programs p JOIN programTypes pe ON p.programTypeId = pe.id WHERE pe.name != 'Staff');

