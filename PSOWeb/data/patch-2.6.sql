-- -------------------------------------------------------
-- Table GroupedStudents
-- -------------------------------------------------------
ALTER TABLE GroupedStudents ADD COLUMN sequence int DEFAULT 0;

CREATE SEQUENCE serial START 1;

CREATE OR REPLACE FUNCTION updateGroupedStudents(studentGroupId bigint) RETURNS SETOF bigint AS
$BODY$
DECLARE
    r bigint;
BEGIN
    FOR r IN select gs.id from groupedstudents gs join studentgroups g on gs.groupid = g.id where g.id = studentGroupId order by gs.id
    LOOP
	update groupedstudents set sequence = nextval('serial') where id = r;
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE 'plpgsql' ;

CREATE OR REPLACE FUNCTION updateSequence() RETURNS SETOF bigint AS
$BODY$
DECLARE
    r bigint;
BEGIN
    FOR r IN select g.id from studentgroups g join programs p on g.programid = p.id join programtypes pt on p.programtypeid = pt.id where pt.name = 'Transport' order by g.id
    LOOP
	ALTER SEQUENCE serial RESTART WITH 1;
	PERFORM updateGroupedStudents(r);
    END LOOP;
    RETURN;
END
$BODY$
LANGUAGE 'plpgsql' ;

select updateSequence();

drop function updateGroupedStudents(bigint);
drop function updateSequence();
