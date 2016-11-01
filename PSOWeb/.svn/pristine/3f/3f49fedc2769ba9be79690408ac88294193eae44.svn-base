
-- ProgramEvent status

ALTER TABLE programevents ADD COLUMN bankeddate date;
UPDATE programevents SET status = 'banked' WHERE fundingsstatus = 'Banked';
ALTER TABLE programevents DROP COLUMN fundingsstatus;

-- ProgramType - Added Individual

insert into programtypes values(4, 'Individual');
update programs set programtypeid = 4 where name = 'Individual';

-- Staff Attendence changes

ALTER TABLE ProgramEventCoordinatorXRef ADD COLUMN attended boolean default false;

create table blobdata(id bigserial not null, data bytea, thumbnail bytea, primary key(id));
alter table filedata add column blobId bigint;
alter table filedata    ADD CONSTRAINT FK_filedata_blobId FOREIGN KEY (blobId) REFERENCES blobdata(id) MATCH FULL;