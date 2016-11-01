-- ------------------------------------------------------------------
-- Delete studentevents of hidden groupedstuents(return)
-- ------------------------------------------------------------------
delete from studentevents se where se.attended is not true and se.groupedstudentid in
(select rs.id from groupedstudents rs join studentgroups rg on rs.groupid = rg.id 
where rg.parentid is not null and rs.studentid not in
(select gs.studentid from groupedstudents gs join studentgroups g on gs.groupid = g.id and rg.parentid = g.id));

-- ------------------------------------------------------------------
-- Delete GroupedStuents from ReturnGroups who are not in MainGroups
-- ------------------------------------------------------------------
delete from groupedstudents s where s in 
(select rs from groupedstudents rs join studentgroups rg on rs.groupid = rg.id 
where rg.parentid is not null and rs.studentid not in
(select gs.studentid from groupedstudents gs join studentgroups g on gs.groupid = g.id and rg.parentid = g.id));

-- ------------------------------------------------------------------
-- Delete studentevents of hidden groupedstuents(return) REPEATED
-- ------------------------------------------------------------------
delete from studentevents se where se.attended is not true and se.groupedstudentid in
(select gs.id FROM groupedstudents gs, groupedstudents gs2
WHERE gs.studentid = gs2.studentid AND gs.groupid = gs2.groupid and gs.id < gs2.id);

-- --------------------------------------------------------------------------
-- Delete GroupedStuents from ReturnGroups who are not in MainGroups REPEATED
-- --------------------------------------------------------------------------
delete from groupedstudents gs using groupedstudents gs2
WHERE gs.studentid = gs2.studentid AND gs.groupid = gs2.groupid and gs.id < gs2.id;