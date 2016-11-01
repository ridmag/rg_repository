-- -------------------------------------------------------
-- Table StudentGroups
-- -------------------------------------------------------
CREATE INDEX StudentGroups_parentId_index ON StudentGroups USING btree (parentId);
  
ALTER TABLE ONLY StudentGroups
	ADD CONSTRAINT FK_StudentGroups_parentId1 FOREIGN KEY (parentId) REFERENCES StudentGroups(id) MATCH FULL;

	
DELETE FROM ProgramEventCoordinatorXRef USING programEvents WHERE ProgramEventCoordinatorXRef.programEventId = programevents.id AND programevents.eventDate <= '2012/12/31';
DELETE FROM studentevents USING programEvents WHERE studentevents.eventId = programevents.id AND programevents.eventDate <= '2012/12/31';
DELETE FROM transactions USING programEvents WHERE transactions.programEventId = programevents.id AND programevents.eventDate <= '2012/12/31';
DELETE FROM programevents WHERE eventDate <= '2012/12/31' ;
DELETE FROM invoices WHERE invoiceDate <= '2012/12/31';

-- -----------------------------------------------------
-- Table GroupedStaffXRef
-- -----------------------------------------------------
CREATE TABLE GroupedStaff(
  id bigSERIAL,
  groupId bigint,
  staffId bigint,
  status varchar(50),
  remarks varchar(255),
  PRIMARY KEY (id));
  
ALTER TABLE ONLY GroupedStaff
    ADD CONSTRAINT FK_GroupedStaff_groupId FOREIGN KEY (groupId) REFERENCES studentgroups(id) MATCH FULL,
    ADD CONSTRAINT FK_GroupedStaff_staffId FOREIGN KEY (staffId) REFERENCES staffmembers(id) MATCH FULL;
  
-- -----------------------------------------------------
-- Table GroupedStaffWeekDaysXref
-- -----------------------------------------------------
CREATE TABLE GroupedStaffWeekDaysXref(
	groupedStaffId bigSERIAL,
	weekDayId bigSERIAL,
	primary key(groupedStaffId, weekDayId));
	
ALTER TABLE ONLY GroupedStaffWeekDaysXref
    ADD CONSTRAINT FK_GroupedStaffWeekDaysXref_groupId FOREIGN KEY (groupedStaffId) REFERENCES GroupedStaff(id) MATCH FULL,
    ADD CONSTRAINT FK_GroupedStaffWeekDaysXref_weekDayId FOREIGN KEY (weekDayId) REFERENCES WeekDays(id) MATCH FULL;
    
insert into groupedstaff (groupid,staffid) select groupid,staffid from studentgroupstaffxref;
DROP TABLE IF EXISTS studentgroupstaffxref;

UPDATE GroupedStudents SET status = 'Active' WHERE status is null;
  
