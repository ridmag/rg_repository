--Add startfrom column to students table
alter table students
add column startfrom Date;

--set defalt date for startfrom column.user need to change this dates with actual dates.
UPDATE students
SET startfrom = '2015-01-01'
WHERE startfrom is null;