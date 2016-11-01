
ALTER TABLE programevents ALTER COLUMN coordinatorId DROP not null;

-- -----------------------------------------------------
-- Table ProgramEventCoordinatorXRef
-- -----------------------------------------------------
DROP TABLE IF EXISTS ProgramEventCoordinatorXRef;
CREATE TABLE ProgramEventCoordinatorXRef(
	programEventId bigint not null,
	coordinatorId bigint not null,
	primary key(programEventId, coordinatorId));
	
CREATE INDEX ProgramEventCoordinatorXRef_programEventId_index ON ProgramEventCoordinatorXRef USING btree (programEventId);
CREATE INDEX ProgramEventCoordinatorXRef_coordinatorId_index ON ProgramEventCoordinatorXRef USING btree (coordinatorId);
ALTER TABLE ONLY ProgramEventCoordinatorXRef
    ADD CONSTRAINT FK_ProgramEventCoordinatorXRef_programEventId FOREIGN KEY (programEventId) REFERENCES programevents(id) MATCH FULL,
    ADD CONSTRAINT FK_ProgramEventCoordinatorXRef_coordinatorId FOREIGN KEY (coordinatorId) REFERENCES staffmembers(id) MATCH FULL;

INSERT INTO programEventCoordinatorXRef SELECT id, coordinatorid FROM programevents;