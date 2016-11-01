/*
Duplicated groupedstudent Ids(there are 14 records dupliated)
	SELECT groupid,studentid, count(*)
	FROM groupedstudents
	GROUP BY groupid,studentid
	HAVING count(*) > 1
	ORDER BY groupid
*/
-- Need to concern one by one about above groupedstudents ids.
-- --------------------------------------------------------------------------------
/*
 (1)(410,140) combination -(groupedstudent 4620,4682 duplicated,4620-original) delete groupedstudent 4682
 	groupedstudentid=410 studentid=140

	groupedstudentid related to student group=410 studentid=140
	
			select id
			from groupedstudents
			where groupid=410 and studentid=140
	
	result is 4620,4682

	Consider 4620 as original groupedstudentid then we can keep the students events related to groupstudentid 4620.
	now need to consider about duplicate groupedstudentid 4682
	If student is not marked as attended to duplicated student event,there is no transtractions.we can delete that studentevents directly.
	run query(1.1) it will delete records

	Attendance sheet marked true for duplicate student records 

			select id,eventid,groupedstudentid,attended
			from studentevents
			where groupedstudentid=4682 
			order by attended
	

	Result is program events (23237,105379),(23760,107952),(23923,108888),(24290,111073)
	There are two same transactions for each (programevent,studentid)combination.we can delete one of them. 

	(23237,105379)
			select tr.*
			from studentevents se
			inner join
			transactions tr
			on tr.programeventid=se.eventid and se.eventid=23237 and se.id=105379 and tr.studentid=140
			order by id

	(23760,107952)
			select tr.*
			from studentevents se
			inner join
			transactions tr
			on tr.programeventid=se.eventid and se.eventid=23760 and se.id=107952 and tr.studentid=140
			order by id

	(23923,108888)
			select tr.*
			from studentevents se
			inner join
			transactions tr
			on tr.programeventid=se.eventid and se.eventid=23923 and se.id=108888 and tr.studentid=140
			order by id

	(24290,111073)
			select tr.*
			from studentevents se
			inner join
			transactions tr
			on tr.programeventid=se.eventid and se.eventid=24290 and se.id=111073 and tr.studentid=140
			order by id

	We need to delete one duplicated transaction from transaction table.
	run query(1.2)
	run query(1.3) for delete duplicate grouped student
	*/

	--query 1.1
			delete
			from studentevents
			where groupedstudentid=4682 and attended=false;


	--query 1.2
	--to delete student event 105379 and transaction 51823
			delete 
			from studentevents
			where id=105379;

			delete 
			from transactions
			where id=51823;

	--to delete student event 107952 and transaction 51096
			delete 
			from studentevents
			where id=107952;

			delete 
			from transactions
			where id=51096;

	--to delete student event 108888 and transaction 53609
			delete 
			from studentevents
			where id=108888;

			delete 
			from transactions
			where id=53609;

	--to delete student event 108888 and transaction 53609
			delete 
			from studentevents
			where id=108888;

			delete 
			from transactions
			where id=53609;

	--to delete student event 111073 and transaction 55453
			delete 
			from studentevents
			where id=111073;

			delete 
			from transactions
			where id=55453;

	--query 1.3
			delete 
			from groupedstudents
			where id=4682;
		
-- ---------------------------------------------------------------------------------------
	
	/*
 	(2)(414,114) combination - (groupedstudent 3494,3623 duplicated,3494-original)delete groupedstudent 3623

 	groupedstudentid=414 studentid=114

 	groupedstudentid related to student group=414 studentid=114
	
			select id
			from groupedstudents
			where groupid=414 and studentid=114

	result is 3623,3494
	consider 3494 as original groupedstudentid then we can keep the students events related to groupstudentid 3494.
	now need to consider about duplicate groupedstudentid 3623
	if student is not marked as attended to duplicated student event,there is no transtractions.we can delete that studentevents directly.

	Attendance sheet marked true for duplicate student records  
	
			select id,groupedstudentid,eventid,attended
			from studentevents
			where groupedstudentid=3623
			order by attended
	
	run query(2.1) it will delete not attended records
	run query(2.2) for delete duplicate groupedstudent.

	**/
	
	--query 2.1	
			delete
			from studentevents
			where groupedstudentid=3623 and attended=false;

	--query2.2
			delete 
			from groupedstudents
			where id=3623;

-- --------------------------------------------------------------------------------------------
/*
 	(3) (485,135) combination -(groupedstudent 2804,3782 )
	 groupedstudentid=485 studentid=135

 	groupedstudentid related to student group=485 studentid=135

			select id
			from groupedstudents
			where groupid=485 and studentid=135
	
	result is 2804,3782

	consider 2804 as original groupedstudentid then we can keep the students events related to groupstudentid 2804.
	now need to consider about duplicate groupedstudentid 3782

	If student is not marked as attended to duplicated student event,there is no transtractions.we can delete that studentevents directly.
	Attendance sheet marked true for duplicate student records 
	
			select id,groupedstudentid,eventid,attended
			from studentevents
			where groupedstudentid=3782
			order by attended
	
	but there are no events for duplicate groupedstudent
	run query(3.1) for delete duplicate groupedstudent.

 	*/


	--query 3.1
			delete 
			from groupedstudents
			where id=3782;

--------------------------------------------------------------------------------------------------------

/*
 	(4)groupedstudentid=519 studentid=16
    groupedstudentid related to student group=519 studentid=16

			select id
			from groupedstudents
			where groupid=519 and studentid=16

	result is 3457,3674

	consider 3674 as original groupedstudentid then we can keep the students events related to groupstudentid 3674.
	now need to consider about duplicate groupedstudentid 3457
	if student is not marked as attended to duplicated student event,there is no transtractions.we can delete that studentevents directly.

	Attendance sheet marked true for duplicate student records 

			select id,groupedstudentid,eventid,attended
			from studentevents
			where groupedstudentid=3457 
			order by attended

	all the duplicate student events are marked as absent.there are no transactions.
	run query(4.1) it will delete all the duplicate absent records
	run query(4.2) for delete duplicate groupedstudent.
 */

	--query 4.1
			delete
			from studentevents
			where groupedstudentid=3457 and attended=false;

	--query 4.2
			delete 
			from groupedstudents
			where id=3457;

----------------------------------------------------------------------------------------------
/*
 
	(5)groupedstudentid=521 studentid=133

	groupedstudentid related to student group=521 studentid=133

			select id
			from groupedstudents
			where groupid=521 and studentid=133

	result is 4391,4440

	consider 4391 as original groupedstudentid then we can keep the students events related to groupstudentid 4391.
	now need to consider about duplicate groupedstudentid 4440
	if student is not marked as attended to duplicated student event record,there is no transtractions.we can delete that studentevents directly.
	run query(5.1) it will delete duplicated student event record with absent.

	attendance sheet is marked as true for duplicate student records 

			select id,groupedstudentid,eventid,attended
			from studentevents
			where groupedstudentid=4440 
			order by attended

	result is program events (20721,92458)(22221,100294)(22777,103024)(24274,110982)
	there is only one transaction for each (programevent,studentid)combination.student event related to the original groupedstudentid is marked as absent.we need to update duplicated student event into original . 
	no need to change transactions.

	(20721,92458)
			select tr.*
			from studentevents se
			inner join
			transactions tr
			on tr.programeventid=se.eventid and se.eventid=20721 and se.id=92458 and tr.studentid=133
			order by id

	(22221,100294)
			select tr.*
			from studentevents se
			inner join
			transactions tr
			on tr.programeventid=se.eventid and se.eventid=22221 and se.id=100294 and tr.studentid=133
			order by id

	(22777,103024)
			select tr.*
			from studentevents se
			inner join
			transactions tr
			on tr.programeventid=se.eventid and se.eventid=22777 and se.id=103024 and tr.studentid=133
			order by id
	
	(24274,110982)
			select tr.*
			from studentevents se
			inner join
			transactions tr
			on tr.programeventid=se.eventid and se.eventid=24274 and se.id=110982 and tr.studentid=133
			order by id


	run query(5.2)to update duplicated student event into original.
	run query(5.3) for delete duplicate grouped student.
 */

	--query 5.1
			delete
			from studentevents
			where groupedstudentid=4440 and attended=false;

	--query 5.2
	--update groupstudentid of student events 92458,100294,103024,110982 into 4391 
			update studentevents
			set groupedstudentid=4391
			where id=92458;

			update studentevents
			set groupedstudentid=4391
			where id=100294;

			update studentevents
			set groupedstudentid=4391
			where id=103024;

			update studentevents
			set groupedstudentid=4391
			where id=110982;
	--query 5.3
			delete 
			from groupedstudents
			where id=4440;

----------------------------------------------------------------------------------------------------
/*
 	(6)groupedstudentid=539 studentid=160

 	groupedstudentid related to student group=539 studentid=160

			select id
			from groupedstudents
			where groupid=539 and studentid=160

	result is 3916,4824

	consider 3916 as original groupedstudentid then we can keep the students events related to groupstudentid 3916.
	now need to consider about duplicate groupedstudentid 4824
	if student is not marked as attended to duplicated student event,there is no transtractions.we can delete that studentevents directly.
	run query 6.1
	attendance sheet is marked as true for duplicate student records 

			select id,groupedstudentid,eventid,attended
			from studentevents
			where groupedstudentid=4824 
			order by id
	
	Result is (program events,student events) combinations (23353,106058),(23566,106982),(23798,108241) 
	There are two same transactions for each (programevent,studentid)combination.we can delete one of them. 

	(23353,106058)

				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=23353 and se.id=106058 and tr.studentid=160
				order by id

	(23566,106982)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=23566 and se.id=106982 and tr.studentid=160
				order by id

	(23798,108241) 
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=23798 and se.id=108241 and tr.studentid=160
				order by id

	we need to delete one duplicated transaction from transaction table.
	run query(6.2)
	run query(6.3) for delete duplicate grouped student

	 */

	--query 6.1
				delete
				from studentevents
				where groupedstudentid=4824 and attended=false;

	--query 6.2
	--to delete student event 106058 and transaction 52255
				delete 
				from studentevents
				where id=106058;

				delete 
				from transactions
				where id=52255;

	--to delete student event 106982 and transaction 54791
				delete 
				from studentevents
				where id=106982;

				delete 
				from transactions
				where id=54791;

	--to delete student event 108241 and transaction 51696
				delete 
				from studentevents
				where id=108241;

				delete 
				from transactions
				where id=51696;

	--query6.3
				delete 
				from groupedstudents
				where id=4824;

---------------------------------------------------------------------------------------------
/*
	(7)groupedstudentid=557 studentid=154

	groupedstudentid related to student group=557 studentid=154

				select id
				from groupedstudents
				where groupid=557 and studentid=154
	
	result is 3233,4683

	consider 3233 as original groupedstudentid then we can keep the students events related to groupstudentid 3233.
	now need to consider about duplicate groupedstudentid 4683
	if student is not marked as attended to duplicated student event,there is no transtractions.we can delete that studentevents directly.
	attendance sheet marked for duplicate student records 

				select id,groupedstudentid,eventid,attended
				from studentevents
				where groupedstudentid=4683 
				order by attended

	result is (program events,student events) combinations (23735,107906),(23767,107998),(23938,108947)(24336,111281)
	there are two same transactions for each (programevent,studentid)combination(23938,108947)(24336,111281) .we can delete one of them. 

	(23938,108947)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=23938 and se.id=108947 and tr.studentid=154
				order by id

	(24336,111281)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=24336 and se.id=111281 and tr.studentid=154
				order by id

	run query(7.2)

	there is only one transaction for each (programevent,studentid)combination(23735,107906)(23767,107998).student event related to the original groupedstudentid is marked as absent.we need to update duplicated student event into original . 
	no need to change transactions.

	(23735,107906)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=23735 and se.id=107906 and tr.studentid=154
				order by id

	(23767,107998)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=23767 and se.id=107998 and tr.studentid=154
				order by id

	query(7.3) to update duplicated student event into original

	run query(7.4) for delete duplicate grouped student

 */

	--query7.1
				delete
				from studentevents
				where groupedstudentid=4683 and attended=false;

	--query 7.2
	--to delete student event 108947 and transaction 53250
				delete 
				from studentevents
				where id=108947;

				delete 
				from transactions
				where id=53250;

	-- to delete student event 111281 and transaction 54445
				delete 
				from studentevents
				where id=111281;

				delete 	
				from transactions
				where id=54445;

	--query 7.3
	--update groupstudentid of student events 107906,107998 into 3233
				update studentevents
				set groupedstudentid=3233
				where id=107906;

				update studentevents
				set groupedstudentid=3233
				where id=107998;
	--query 7.4
				delete 
				from groupedstudents
				where id=4683;
	
	-----------------------------------------------------------------------------------------
/*
 	(8)groupedstudentid=567 studentid=154

	 groupedstudentid related to student group=567 studentid=154
	
				select id
				from groupedstudents
				where groupid=567 and studentid=154

	result is 3580,3307,4688

	consider 3580 as original groupedstudentid. then we can keep the students events related to groupstudentid 3580.
	now need to consider about duplicate groupedstudentids 3307,4688
	if student is not marked as attended to duplicated student event,there is no transtractions.we can delete that studentevents directly.
	attendance sheet marked for duplicate student records 

				select id,groupedstudentid,eventid,attended
				from studentevents
				where groupedstudentid=4688 or groupedstudentid=3307
				order by attended
	
	result is (program events,student events) combinations (23810,108262),(23584,107057)

	there are two same transactions for each (programevent,studentid)combination(23584,107057).we can delete one of them. 

	(23584,107057)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=23584 and se.id=107057 and tr.studentid=154
				order by id

	run query(8.2)

	there is only one transaction for each (programevent,studentid)combination(23810,108262).student event related to the original groupedstudentid is marked as absent.we need to update duplicated student event into original . 
	no need to change transactions.

	(23810,108262)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=23810 and se.id=108262 and tr.studentid=154
				order by id

	query(8.3) to update duplicated student event into original student event
	
	run query(8.4) for delete duplicate grouped students
	 */
	
	--query 8.1
				delete
				from studentevents
				where groupedstudentid=3307 and attended=false;

				delete
				from studentevents
				where groupedstudentid=4688 and attended=false;

	--query 8.2
	--to delete student event 107057 and transaction 54055

				delete 
				from studentevents
				where id=107057;
				
				delete 
				from transactions
				where id=54055;

	--query 8.3
	--update groupstudentid of student events 108262 into 3580 
				update studentevents
				set groupedstudentid=3580 
				where id=108262;

	--query 8.4
				delete 
				from groupedstudents
				where id=4688;
				
				delete 
				from groupedstudents
				where id=3307;

--------------------------------------------------------------------------------------------
/*
	  (9)(575,114) combination - (groupedstudent 3343,3638)
	
	 groupedstudentid=575 studentid=114
	
	 groupedstudentid related to student group=575 studentid=114
		
				select id
				from groupedstudents
				where groupid=575 and studentid=114

	result is 3343,3638
	consider 3343 as original groupedstudentid then we can keep the students events related to groupstudentid 3343.
	now need to consider about duplicate groupedstudentid 3638
	if student is not marked as attended to duplicated student event,there is no transtractions.we can delete that studentevents directly.
	
	Attendance sheet marked true for duplicate student records  
	
				select id,groupedstudentid,eventid,attended
				from studentevents
				where groupedstudentid=3638
				order by attended
	
	run query(9.1) it will delete not attended records
	run query(9.2) for delete duplicate groupedstudent.
	
	**/
 
	--query 9.1
				delete
				from studentevents
				where groupedstudentid=3638 and attended=false;

	--query 9.2
				delete 
				from groupedstudents
				where id=3638;

---------------------------------------------------------------------------------------------
/*
 	(10)(575,160) combination - (groupedstudent 4760,4822)

	 groupedstudentid=575 studentid=160
	
	 groupedstudentid related to student group=575 studentid=160
				
				select id
				from groupedstudents
				where groupid=575 and studentid=160

	result is 4760,4822
	consider 4760 as original groupedstudentid then we can keep the students events related to groupstudentid 4760.
	now need to consider about duplicate groupedstudentid 4822
	if student is not marked as attended to duplicated student event,there is no transtractions.we can delete that studentevents directly.
	run query 10.1
	Attendance sheet marked true for duplicate student records  
	
				select id,groupedstudentid,eventid,attended
				from studentevents
				where groupedstudentid=4822
				order by attended
	
	result is (program events,student events) combinations (23268,105510),(23596,107116),(23793,108206)

	(23268,105510)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=23268 and se.id=105510 and tr.studentid=160
				order by id
	
	(23596,107116)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=23596 and se.id=107116 and tr.studentid=160
				order by id
	
	(23793,108206)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=23793 and se.id=108206 and tr.studentid=160
				order by id
	
	There is no transactions for above (program events,student events) combinations.
	delete duplicate students events.
	run query 10.2
	
	run query(10.2) for delete duplicate groupedstudent.
	
 */

	--query 10.1
				delete
				from studentevents
				where groupedstudentid=4822 and attended=false;

	--query 10.2
				delete 
				from studentevents
				where id=105510;
				
				delete 
				from studentevents
				where id=107116;
				
				delete 
				from studentevents
				where id=108206;

	--query 10.3
				delete 
				from groupedstudents
				where id=4822;

-------------------------------------------------------------------------------------------------
/*
	 (11)groupedstudentid=577 studentid=160
	
	 groupedstudentid related to student group=577 studentid=160
	
				select id
				from groupedstudents
				where groupid=577 and studentid=160

	result is 4690,4823
	
	consider 4690 as original groupedstudentid. then we can keep the students events related to groupstudentid 4690.
	now need to consider about duplicate groupedstudentid 4823
	if student is not marked as attended to duplicated student event,there is no transtractions.we can delete that studentevents directly.
	run query 11.1
	attendance sheet marked for duplicate student records 

				select id,groupedstudentid,eventid,attended
				from studentevents
				where groupedstudentid=4823
				order by attended

	result is (program events,student events) combinations (23266,113259),(24099,109827)(23468,106234)
	
	there are two same transactions for each (programevent,studentid)combination(23266,113259),(24099,109827).we can delete one of them. 

	(23266,113259)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=23266 and se.id=113259 and tr.studentid=160
				order by id
				
	(24099,109827)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=24099 and se.id=109827 and tr.studentid=160
				order by id

	run query(11.2)

	there is only one transaction for (programevent,studentid)combination(23468,106234).student event related to the original groupedstudentid is marked as absent.we need to update duplicated student event into original . 
	no need to change transactions.

	(23468,106234)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=23468 and se.id=106234 and tr.studentid=160
				order by id

	query(11.3) to update duplicated student event into original student event
	
	run query(11.4) for delete duplicate grouped students
	 */


	--query 11.1
				delete
				from studentevents
				where groupedstudentid=4823 and attended=false;

	--query 11.2
	--to delete student event 113259 and transaction 54060
				delete 
				from studentevents
				where id=113259;
				
				delete 
				from transactions
				where id=54060;

	--to delete student event 109827 and transaction 54420
				delete 
				from studentevents
				where id=109827;
				
				delete 
				from transactions
				where id=54420;

	--query 11.3
				update studentevents
				set groupedstudentid=4690
				where id=106234;

	--query 11.4
				delete 
				from groupedstudents
				where id=4823;

------------------------------------------------------------------------------------------------
	/*
	  (12)groupedstudentid=581 studentid=176
	
	 groupedstudentid related to student group=581 studentid=176
	
				select id
				from groupedstudents
				where groupid=581 and studentid=176

	result is 4726,4850,4851
	
	consider 4726 as original groupedstudentid. then we can keep the students events related to groupstudentid 4726.
	now need to consider about duplicate groupedstudentids 4850,4851
	if student is not marked as attended to duplicated student event,there is no transtractions.we can delete that studentevents directly.
	run query 12.1
	attendance sheet marked for duplicate student records 

				select id,groupedstudentid,eventid,attended
				from studentevents
				where groupedstudentid=4850 or groupedstudentid=4851
				order by attended
	
	result is (program events,student events) combinations (24095,109804)(24097,109818)(24097,109817)(24344,111313)(24344,111314)
	there are two same transactions for each (programevent,studentid)combination.we can delete two of them.
	 
 	(24095,109804)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=24095 and se.id=109804 and tr.studentid=176
				order by id
	there are three same transactions for each (programevent,studentid)combination.we can delete two of them.

	(24097,109818)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=24097 and se.id=109818 and tr.studentid=176
				order by id
	
	 (24097,109817)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=24097 and se.id=109817 and tr.studentid=176
				order by id
	
	 (24344,111313)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=24344 and se.id=111313 and tr.studentid=176
				order by id
	
	 (24344,111314)
				select tr.*
				from studentevents se
				inner join
				transactions tr
				on tr.programeventid=se.eventid and se.eventid=24344 and se.id=111314 and tr.studentid=176
				order by id
	
	run query 12.2
	
	run query(12.3) for delete duplicate groupedstudent.
	*/

	--query 12.1

				delete
				from studentevents
				where groupedstudentid=4850 and attended=false;
				
				
				delete
				from studentevents
				where groupedstudentid=4851 and attended=false;

	--query 12.2
	--to delete student event 109804 and transaction 54069
				delete 
				from studentevents
				where id=109804;
				
				delete 
				from transactions
				where id=54069;

	--to delete student event 109818,109817 and transaction 54412,54411 
				delete 
				from studentevents
				where id=109818;
				
				delete 
				from studentevents
				where id=109817;
				
				delete 
				from transactions
				where id=54411;
				
				delete 
				from transactions
				where id=54412;

	--to delete student event 111313,111314 and transaction 54467,54468 
				delete 
				from studentevents
				where id=111313;
				
				delete 
				from studentevents
				where id=111314;
				
				delete 
				from transactions
				where id=54467;
				
				delete 
				from transactions
				where id=54468;

	--query 12.3
				delete 
				from groupedstudents
				where id=4850 ;
				
				delete 
				from groupedstudents
				where id=4851;
---------------------------------------------------------------------------------------------

	/*(13)(592,154) combination - (groupedstudent 3593,3410)
	
	 groupedstudentid=592 studentid=154
	
	 groupedstudentid related to student group=592 studentid=154
	
				select id
				from groupedstudents
				where groupid=592 and studentid=154

	result is 3593,3410
	consider 3593 as original groupedstudentid then we can keep the students events related to groupstudentid 3593.
	now need to consider about duplicate groupedstudentid 3410
	if student is not marked as attended to duplicated student event,there is no transtractions.we can delete that studentevents directly.
	run query 13.1
	Attendance sheet marked true for duplicate student records  
	
				select id,groupedstudentid,eventid,attended
				from studentevents
				where groupedstudentid=3410
				order by attended
	all the duplicate student events marked as absent here.

	run query(13.2) for delete duplicate groupedstudent.

**/

	--query 13.1
				delete
				from studentevents
				where groupedstudentid=3410 and attended=false;

	--query 13.2
				delete 
				from groupedstudents
				where id=3410;
----------------------------------------------------------------------------------------
	/*(13)(607,32) combination - (groupedstudent 3649,3510)
	
	 groupedstudentid=607 studentid=32
	
	 groupedstudentid related to student group=607 studentid=32
	
				select id
				from groupedstudents
				where groupid=607 and studentid=32

	result is 3649,3510
	consider 3510 as original groupedstudentid then we can keep the students events related to groupstudentid 3510.
	now need to consider about duplicate groupedstudentid 3649
	if student is not marked as attended to duplicated student event,there is no transtractions.we can delete that studentevents directly.
	
	Attendance sheet marked true for duplicate student records  
	
				select id,groupedstudentid,eventid,attended
				from studentevents
				where groupedstudentid=3649
				order by attended
	all the duplicate student events marked as absent here.
	run query(14.1) it will delete not attended records
	run query(14.2) for delete duplicate groupedstudent.
	*/

	--query 14.1
				delete
				from studentevents
				where groupedstudentid=3649 and attended=false;

	--query 14.2
				delete 
				from groupedstudents
				where id=3649;



