-- -----------------------------------------------------
-- Table FileData
-- -----------------------------------------------------
ALTER TABLE fileData ADD COLUMN groupId bigint;

CREATE INDEX filedata_groupId_index ON filedata USING btree (groupId);
	
-- -----------------------------------------------------
-- Table ProgramTypes
-- -----------------------------------------------------
UPDATE ProgramTypes SET name = 'Student' WHERE name = 'Standard';
INSERT INTO ProgramTypes values(3, 'Staff');

-- -----------------------------------------------------
-- Table StudentGroups
-- -----------------------------------------------------
ALTER TABLE StudentGroups ADD COLUMN chargeAmount double precision;	
ALTER TABLE StudentGroups ADD COLUMN locationId bigint;
ALTER TABLE StudentGroups ADD COLUMN parentId bigint;
ALTER TABLE StudentGroups ADD COLUMN photoId bigint;

UPDATE StudentGroups SET chargeAmount = (SELECT chargeAmount FROM programs WHERE studentgroups.programid = programs.id);

CREATE INDEX StudentGroups_programId_index ON StudentGroups USING btree (programId);
CREATE INDEX StudentGroups_locationId_index ON StudentGroups USING btree (locationId);
CREATE INDEX StudentGroups_photoId_index ON StudentGroups USING btree (photoId);

ALTER TABLE ONLY StudentGroups
    ADD CONSTRAINT FK_StudentGroups_locationId FOREIGN KEY (locationId) REFERENCES locations(id) MATCH FULL,
    ADD CONSTRAINT FK_StudentGroups_parentId FOREIGN KEY (parentId) REFERENCES StudentGroups(id) MATCH FULL,
	ADD CONSTRAINT FK_StudentGroups_photoId FOREIGN KEY (photoId) REFERENCES filedata(id) MATCH FULL;

-- -----------------------------------------------------
-- Table StudentGroupStaffXRef
-- -----------------------------------------------------
DROP TABLE IF EXISTS StudentGroupStaffXRef cascade;

CREATE TABLE StudentGroupStaffXRef(
  groupId bigint NOT NULL,
  staffId bigint NOT NULL,
  PRIMARY KEY (groupId,staffId));
  
ALTER TABLE ONLY StudentGroupStaffXRef
   ADD CONSTRAINT FK_StudentGroupStaffXRef_groupId FOREIGN KEY (groupId) REFERENCES StudentGroups(id) MATCH FULL,
   ADD CONSTRAINT FK_StudentGroupStaffXRef_staffId FOREIGN KEY (staffId) REFERENCES staffmembers(id) MATCH FULL;
   
-- -----------------------------------------------------
-- Table ProgramEvents
-- -----------------------------------------------------
ALTER TABLE ProgramEvents ADD COLUMN lunchincluded boolean NOT NULL DEFAULT false;
ALTER TABLE ProgramEvents ALTER COLUMN lunchincluded DROP DEFAULT;
ALTER TABLE ProgramEvents ADD COLUMN chargeAmount double precision;

UPDATE ProgramEvents SET chargeAmount = (SELECT chargeAmount FROM studentgroups 
	WHERE studentgroups.id = programevents.studentGroupId);

-- -----------------------------------------------------
-- Table LeavePolicyDetails
-- -----------------------------------------------------
UPDATE LeavePolicyDetails SET LeaveType = 'ANNUAL' WHERE LeaveType = 'Annual';
UPDATE LeavePolicyDetails SET LeaveType = 'PERSONAL' WHERE LeaveType = 'Medical';
UPDATE LeavePolicyDetails SET daysentitled = (LeavePolicyDetails.daysentitled 
	+ (SELECT lp.daysentitled FROM LeavePolicyDetails AS lp WHERE LeavePolicyDetails.leavepolicyid = lp.leavepolicyid AND lp.leavetype = 'Casual'))
	WHERE LeavePolicyDetails.leaveType = 'ANNUAL';
DELETE FROM LeavePolicyDetails WHERE LeaveType = 'Casual';

-- -----------------------------------------------------
-- Table Leaves
-- -----------------------------------------------------
UPDATE Leaves SET LeaveType = 'ANNUAL' WHERE LeaveType = 'Annual';
UPDATE Leaves SET LeaveType = 'PERSONAL' WHERE LeaveType = 'Medical';
UPDATE Leaves SET LeaveType = 'ANNUAL' WHERE LeaveType = 'Casual';
