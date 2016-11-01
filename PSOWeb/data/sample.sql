select * from studentgroups where name like '%T4%'
select * from studentgroups where id = 387
select * from groupedstaff where groupid = 388

select * from studentgroups rg join studentgroups pg on rg.parentid = pg.id join programs rp on rg.programid = rp.id 
join programs pp on pg.programid = pp.id where rg.parentid is not null and rp.id != pp.id

select rg.id from studentgroups rg join studentgroups pg on rg.parentid = pg.id where rg.name not like pg.name || '%'

delete from groupedstaffweekdaysxref x using groupedstaff s where x.groupedstaffid = s.id and s.groupid in (391,394);
delete from groupedstaff where groupid in (391,394);
delete from studentevents se using groupedstudents gs where se.groupedstudentid = gs.id and gs.groupid in (391,394);
delete from groupedstudents where groupid in (391,394);
delete from groupsweekdaysxref x where groupid in (391,394);
delete from programeventcoordinatorxref using programevents p where programeventid = p.id and p.studentgroupid in (391,394);
delete from programevents where studentgroupid in (391,394);
delete from studentgroups where id in (391,394);

-- ------------------------
-- Event Manager Clearing
-- ------------------------
delete from transactions;
delete from studentevents;
UPDATE programevents SET generateddate = null;
UPDATE programevents SET completeddate = null;
UPDATE programevents SET actualstarttime = null;
UPDATE programevents SET actualendtime = null;
UPDATE programevents SET invoiced = false;
UPDATE programevents SET status = 'pending';
UPDATE programevents SET fundingsstatus = 'Not-Banked';



delete from groupedstudents


select studentgroups.programid as p1, studentgroups.chargeamount as c1, programs.id, programs.chargeamount as c2
from studentgroups join programs on studentgroups.programid = programs.id

select studentgroups.programid as p1, studentgroups.chargeamount as c1, programevents.programid, programevents.chargeamount as c2
from studentgroups join programevents on studentgroups.programid = programevents.programid

SELECT programs.name, fundingsources.fundingtype, sum(studentevents.amountpaid) as total
  FROM
       fundingsources JOIN students JOIN groupedstudents 
       ON students.id = groupedstudents.studentid JOIN programs 
       JOIN programevents ON programs.id = programevents.programid 
       JOIN studentevents ON programevents.id = studentevents.eventid 
       ON groupedstudents.id = studentevents.groupedstudentid JOIN studentfundingsources 
       ON students.id = studentfundingsources.studentid 
       ON fundingsources.id = studentfundingsources.fundingsrcid
  WHERE studentfundingsources.active = TRUE AND programevents.fundingsStatus = 'Not-Banked' 
  group by fundingsources.fundingtype , programs.name
 
  SELECT programs.name, studentgroups.name as groupName, fundingsources.fundingtype, sum(studentevents.amountpaid) as total
  FROM studentgroups JOIN students JOIN groupedstudents ON students.id = groupedstudents.studentid 
  ON studentgroups.id = groupedstudents.groupid JOIN fundingsources JOIN studentfundingsources 
  ON fundingsources.id = studentfundingsources.fundingsrcid ON students.id = studentfundingsources.studentid 
  JOIN programs JOIN programevents ON programs.id = programevents.programid 
  ON studentgroups.id = programevents.studentgroupid JOIN studentevents 
  ON groupedstudents.id = studentevents.groupedstudentid
    AND programevents.id = studentevents.eventid
    WHERE studentfundingsources.active = TRUE AND programevents.fundingsStatus = 'Not-Banked' 
    group by fundingsources.fundingtype , programs.name, studentgroups.name 

    SELECT contacts.title, contacts.firstname, contacts.lastname , studentgroups.name as groupname, programs.name as program, programevents.eventdate
  FROM
       contacts JOIN students ON contacts.id = students.contactid JOIN groupedstudents JOIN studentgroups 
       JOIN programs JOIN programevents ON programs.id = programevents.programid 
       ON studentgroups.id = programevents.studentgroupid AND programs.id = studentgroups.programid 
       JOIN studentevents ON programevents.id = studentevents.eventid 
       ON groupedstudents.id = studentevents.groupedstudentid AND studentgroups.id = groupedstudents.groupid 
       ON students.id = groupedstudents.studentid
      WHERE programevents.eventDate 
  BETWEEN '2011-01-01' AND '2012-12-31'
  group by contacts.title, contacts.firstname, contacts.lastname , studentgroups.name,
  programs.name, programevents.eventdate, students.id, programs.id, studentgroups.id, programevents.id
  ORDER BY students.id, programs.id, studentgroups.id, programevents.id

  
  SELECT staffmembers.id
  FROM staffmembers
  WHERE staffmembers.id != ()
  
  SELECT staffmembers.id FROM staffmembers JOIN leaves ON staffmembers.id = leaves.staffid WHERE leaves.staffid = 4 AND '2011-07-23'
  BETWEEN leaves.startdate AND leaves.enddate
  group by staffmembers.id

  
  
 SELECT *
  FROM
       students JOIN groupedstudents ON students.id = groupedstudents.studentid JOIN programs JOIN programevents ON programs.id = programevents.programid JOIN studentevents ON public.programevents.id = studentevents.eventid ON groupedstudents.id = studentevents.groupedstudentid

      

  
  SELECT programs.name, programevents.eventDate,
  cast(cast(programevents.endtime as time) - cast(programevents.starttime as time) as time) as hours
  FROM
        students JOIN groupedstudents ON students.id = groupedstudents.studentid 
        JOIN programs JOIN programevents ON programs.id = programevents.programid 
        JOIN studentevents ON public.programevents.id = studentevents.eventid 
        ON groupedstudents.id = studentevents.groupedstudentid
  WHERE studentevents.attended = FALSE AND students.id = 1 AND programevents.eventDate 
  BETWEEN '2011-05-23' AND '2011-08-30'
  
  SELECT StudentConsents.consentGiven, students.mdsid, contacts.title, contacts.firstname,contacts.lastname, programevents.eventDate,
  cast(cast(programevents.endtime as time) - cast(programevents.starttime as time) as time) as hours
  FROM
      contacts JOIN programevents JOIN students JOIN groupedstudents ON students.id = groupedstudents.studentid JOIN 
       studentevents ON groupedstudents.id = studentevents.groupedstudentid JOIN consents JOIN studentconsents 
       ON consents.id = studentconsents.consentid ON students.id = studentconsents.studentid 
       ON programevents.id = studentevents.eventid ON contacts.id = students.contactid
  WHERE studentevents.attended = FALSE AND programevents.eventDate 
  BETWEEN '2011-05-23' AND '2011-09-30'
  ORDER BY students.id, programevents.eventDate
  
  SELECT *
  FROM
       contacts JOIN programevents JOIN students JOIN groupedstudents ON students.id = groupedstudents.studentid JOIN 
       studentevents ON groupedstudents.id = studentevents.groupedstudentid JOIN consents JOIN studentconsents 
       ON consents.id = studentconsents.consentid ON students.id = studentconsents.studentid 
       ON programevents.id = studentevents.eventid ON contacts.id = students.contactid

  
 
  SELECT programevents.programId, programevents.id
  FROM programevents
  WHERE programevents.eventDate
  BETWEEN '2011-05-23' AND '2011-06-30'
  group by programevents.programid, programevents.id
   
SELECT students.mdsid, contacts.title, contacts.firstName, contacts.lastName, 
cast(sum(cast(cast(programevents.endtime as time) - cast(programevents.starttime as time) as time))as time) as hours
  FROM
       contacts JOIN students ON contacts.id = students.contactid JOIN studentfundingsources 
       ON students.id = studentfundingsources.studentid AND students.id = studentfundingsources.studentid
       JOIN groupedstudents ON students.id = groupedstudents.studentid JOIN fundingsources JOIN programevents
       JOIN studentevents ON programevents.id = studentevents.eventid ON fundingsources.id = studentevents.fundingsourceid
       ON groupedstudents.id = studentevents.groupedstudentid
WHERE programevents.eventDate BETWEEN '2011-05-23' AND '2011-09-30'
group by students.mdsid, contacts.title, contacts.firstName, contacts.lastName

SELECT students.id AS studentid, fundingsources.fundingtype AS fundingtype,
  cast(sum(programevents.endtime - programevents.starttime) as time) AS totalhours,
  cast(sum(case when programtypes.name != 'Transport' and studentevents.attended = 'true' 
  then cast(programevents.endtime - programevents.starttime as time) else cast('0:00:00' as time) end) as time) 
  AS programhours,
  cast(sum(case when programtypes.name = 'Transport' and studentevents.attended = 'true' 
  then cast(programevents.endtime - programevents.starttime as time) else cast('0:00:00' as time) end) as time)
AS transporthours
  FROM
       studentevents JOIN programevents ON studentevents.eventid = programevents.id 
       JOIN programs ON programevents.programid = programs.id JOIN programtypes 
       ON programs.programtypeid = programtypes.id JOIN studentfundingsources 
       ON studentevents.stdfundingsrcid = studentfundingsources.id JOIN students 
       ON studentfundingsources.studentid = students.id JOIN fundingsources 
       ON studentfundingsources.fundingsrcid = fundingsources.id JOIN contacts ON students.contactid = contacts.id
  WHERE programevents.EVENTDATE >= '2011-09-05'
    AND programevents.EVENTDATE <= '2011-09-14'
  GROUP BY students.id, cisid, fundingtype
  ORDER BY studentid ASC, fundingtype ASC

SELECT students.id AS studentid, fundingsources.fundingtype AS fundingtype, 
sum(date_part('hour', programevents.endtime) - date_part('hour', programevents.starttime)) AS totalhours, 
				sum(case when programtypes.name != 'Transport' and studentevents.attended = 'true' 
				then date_part('hour', programevents.endtime) - date_part('hour', programevents.starttime) else 0 end) 
				AS programhours, sum(case when programtypes.name = 'Transport' and studentevents.attended = 'true' 
				then date_part('hour', programevents.endtime) - date_part('hour', programevents.starttime) else 0 end) 
				AS transporthours 
  FROM
       fundingsources JOIN students JOIN consents JOIN studentconsents ON consents.id = studentconsents.consentid 
       ON students.id = studentconsents.studentid JOIN studentfundingsources ON students.id = studentfundingsources.studentid
    AND studentfundingsources.id = students.activefundingsrcid ON fundingsources.id = studentfundingsources.fundingsrcid 
    JOIN groupedstudents JOIN programtypes JOIN programs ON programtypes.id = programs.programtypeid JOIN programevents 
    ON programs.id = programevents.programid JOIN studentevents ON programevents.id = studentevents.eventid 
    ON groupedstudents.id = studentevents.groupedstudentid ON students.id = groupedstudents.studentid
    WHERE programevents.EVENTDATE >= '2011/10/01' AND programevents.EVENTDATE <= '2011/10/31' AND consents.name = 
    GROUP BY students.id, fundingtype ORDER BY studentid ASC, fundingtype ASC
    
SELECT students.id AS studentid, fundingsources.fundingtype AS fundingtype, 
(date_part('hour', programevents.endtime - programevents.starttime)*60) + date_part('minute', programevents.endtime - programevents.starttime) as totalminutes, 
case when programtypes.name != 'Transport' and studentevents.attended = 'true' 
then (date_part('hour', programevents.endtime - programevents.starttime)*60) + date_part('minute', programevents.endtime - programevents.starttime) else 0 end 
as programminutes , case when programtypes.name = 'Transport' and studentevents.attended = 'true' 
then (date_part('hour', programevents.endtime - programevents.starttime)*60) + date_part('minute', programevents.endtime - programevents.starttime) else 0 end 
AS transportminutes FROM studentevents JOIN programevents ON studentevents.eventid = programevents.id 
JOIN groupedstudents ON studentevents.groupedstudentid = groupedstudents.id JOIN programs 
ON programevents.programid = programs.id JOIN programtypes ON programs.programtypeid = programtypes.id 
JOIN students ON groupedstudents.studentid = students.id JOIN studentfundingsources 
ON students.activefundingsrcid = studentfundingsources.id AND studentfundingsources.studentid = students.id 
JOIN fundingsources ON studentfundingsources.fundingsrcid = fundingsources.id 
WHERE programevents.EVENTDATE >= '2012/02/01' AND programevents.EVENTDATE <= '2012-02-21' 
GROUP BY students.id, fundingtype,programevents.starttime,programevents.endtime,programtypes.name,studentevents.attended
ORDER BY studentid ASC, fundingtype ASC
