CREATE INDEX programevents_coordinatorId_index ON programevents USING btree (coordinatorId);
ALTER TABLE ONLY programevents
ADD CONSTRAINT FK_programevents_coordinatorId FOREIGN KEY (coordinatorId) REFERENCES staffmembers(id) MATCH FULL;

-- UPDATE programevents set coordinatorId = (SELECT coordinatorID FROM programs WHERE programevents.programid = programs.id) WHERE coordinatorId is null;