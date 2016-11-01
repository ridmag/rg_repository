-- ****** Lookup and Sample Data for PSOWeb *******


delete from configurations;
delete from authorities;
delete from users;
delete from studentgroups;
delete from programs;
delete from staffcheckrecords;
delete from staffmembers;
delete from locations;
delete from contacts;
delete from stafftypes;
delete from fundingsources;
delete from leavepolicies;
delete from leavepolicydetails;
delete from LeaveCategory;
delete from programtypes;
delete from weekdays;
delete from calendars;
delete from vehicles;
delete from outlets;

-- -----------------------------------------------------
-- System Configuration
-- -----------------------------------------------------
insert into configurations values(0, 'CLINIC_NAME','iTelaVet Sample Clinic');
insert into configurations values(1, 'CLINIC_ADDRESS_1','Address Line 1');
insert into configurations values(2, 'CLINIC_ADDRESS_2','Address Line 2');
insert into configurations values(3, 'CLINIC_CONTACT_NO','000-000-0000');
insert into configurations values(4, 'CLINIC_CONTACT_EMAIL','test@itelasoft.com');
insert into configurations values(5, 'LICENSE','g81SPc3Ushz/464tFyJ23LxNSUjl9vSz');
insert into configurations values(6, 'THEME','./xmlhttp/css/rime/rime.css');
insert into configurations values(7, 'COUNTRY','Australia');
insert into configurations values(8, 'INVOICE_DESC','Professional Veterinary Services');
insert into configurations values(9, 'INVOICE_TAX','0.10');
insert into configurations values(10, 'VERSION_DB','1.0');
insert into configurations (id, parameter) values(11, 'LOGO_NAME');
insert into configurations (id, parameter) values(12, 'LOGO_ID');
insert into configurations (id, parameter) values(13, 'ABN');
insert into configurations values(14, 'PASSWORD', 'pass123');
insert into configurations values(15, 'SMTP_ADDRESS', 'smtp.gmail.com');
insert into configurations values(16, 'IMAP_ADDRESS', 'imap.gmail.com');


-- -----------------------------------------------------
-- Sample Data for Testing and Demo Purposes
-- -----------------------------------------------------
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(1, 'Mr', 'Generic', 'Contact', 'Client', 'NA', 'NA', 'NA', 'NA', '0000', 'NA', '2345, 5676', '0000', '0000', '0000', 'noreply@generic.com', 'NA', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(2, 'Mr', 'Loyyd', 'Brigg', 'Kerry', 'Actors Inc.', '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123', '0287456456', '0296678678', 'amy@actorsinc.com', 'Entertainment', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(3, 'Mr', 'Austin', 'Powers', '', 'Unilevers Inc.', '14 Albert Lane', 'Hornsby', 'NSW', '2077', 'Australia', '2345, 5676', '0400151231', '0286456456', '0293678678', 'austin@hotmail.com', 'Food', true);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(4, 'Dr', 'Frown', 'Elvis', 'Jay', 'GP Medical Pty Ltd.', '4 Bentwood Tce', 'Stanhope Gardens', 'NSW', '2768', 'Australia', '2345, 5676', '0488283241', '0276456456', '0286786754', 'frown_elvis@yahoo.com.au', 'Medical', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(5, 'Mr', 'Chris', 'Murry', 'Gibbs', 'Gibbs Milkbar', '6 Sharne ct.', 'Cranbourne', 'VIC', '3977', 'Australia', '2345, 5676', '0466233123', '0356456456', '0367867823', 'chrismurry@gmail.com', 'Retail', true);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(6, 'Ms', 'Kirsten', 'Smith', '', 'Oz Minerals', '9/17 Flinders Way', 'Griffith', 'ACT', '2603', 'Australia', '2345, 5676', '04001231', '0245645650', '0267867882', 'kirsten_smith@yahoo.com.au', 'Engineering', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(7, 'Ms', 'Melissa', 'Downes', 'Sue', 'Coles supermarket', '9 Strathfield Ave.', 'Strathfield', 'NSW', '2131', 'Australia', '2345, 5676', '0432123123', '0276456456', '0267867874', 'melissa-downes@hotmail.com', 'Retail', true);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(8, 'Mr', 'Administrator', '', 'System', 'NA', 'NA', 'NA', 'NA', '0000', 'NA', '2345, 5676', '0000', '0000', '0000', 'noreply@generic.com', 'NA', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(9, 'Mr', 'Tom', '', 'Hanks', 'NA', 'NA', 'NA', 'NA', '0000', 'NA', '2345, 5676', '0000', '0000', '0000', 'noreply@generic.com', 'NA', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(10, 'Mr', 'Nicole', '', 'Kidman', 'NA', 'NA', 'NA', 'NA', '0000', 'NA', '2345, 5676', '0000', '0000', '0000', 'noreply@generic.com', 'NA', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(11, 'Ms', 'Pink', 'Brigg', 'Nerd', 'Actors Inc.', '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123', '0287456456', '0296678678', 'amy@actorsinc.com', 'Entertainment', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(12, 'Ms', 'Red', 'Brigg', 'Dallas', 'Actors Inc.', '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123', '0287456456', '0296678678', 'amy@actorsinc.com', 'Entertainment', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(13, 'Ms', 'Tani', 'Brigg', 'Kate', 'Actors Inc.', '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123', '0287456456', '0296678678', 'amy@actorsinc.com', 'Entertainment', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(14, 'Ms', 'Lesi', 'Brigg', 'Amstron', 'Actors Inc.', '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123', '0287456456', '0296678678', 'amy@actorsinc.com', 'Entertainment', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(15, 'Ms', 'Nadia', 'Brigg', 'Sing', 'Actors Inc.', '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123', '0287456456', '0296678678', 'amy@actorsinc.com', 'Entertainment', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(16, 'Mr', 'Greg', 'Brigg', 'Cader', 'Actors Inc.', '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123', '0287456456', '0296678678', 'amy@actorsinc.com', 'Entertainment', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(17, 'Mr', 'Rush', 'Brigg', 'Mate', 'Actors Inc.', '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123', '0287456456', '0296678678', 'amy@actorsinc.com', 'Entertainment', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(18, 'Mr', 'Oniell', 'Brigg', 'Jack', 'Actors Inc.', '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123', '0287456456', '0296678678', 'amy@actorsinc.com', 'Entertainment', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(19, 'Mr', 'Routh', 'Brigg', 'Dig', 'Actors Inc.', '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123', '0287456456', '0296678678', 'amy@actorsinc.com', 'Entertainment', false);
insert into contacts (id, businessName, streetAddress, city, state, postCode, country, geoCode, officePhone) values(20, 'PSO', '236 George st.', 'Strathfield', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123');
insert into contacts (id, businessName, streetAddress, city, state, postCode, country, geoCode, officePhone) values(21, 'PSO', '236 George st.', 'Strathfield', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123');
insert into contacts (id, businessName, streetAddress, city, state, postCode, country, geoCode, officePhone) values(22, 'PSO', '236 George st.', 'Strathfield', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123');
insert into contacts (id, businessName, streetAddress, city, state, postCode, country, geoCode, officePhone) values(23, 'PSO', '236 George st.', 'Strathfield', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123');
insert into contacts (id, businessName, streetAddress, city, state, postCode, country, geoCode, officePhone) values(24, 'PSO', '236 George st.', 'Strathfield', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123');
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(25, 'Mr', 'Ronald', 'Devin', 'Dig', 'Actors Inc.', '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123', '0287456456', '0296678678', 'amy@actorsinc.com', 'Entertainment', false);
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(26, 'Ms', 'Samantha', 'Cader', 'Dig', 'Actors Inc.', '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia', '2345, 5676', '0418123123', '0287456456', '0296678678', 'amy@actorsinc.com', 'Entertainment', false);
insert into contacts (id, streetAddress, city, state, postCode, country) values(27, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(28, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(29, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(30, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(31, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(32, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(33, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(34, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(35, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(36, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(37, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(38, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(39, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(40, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(41, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(42, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');
insert into contacts (id, streetAddress, city, state, postCode, country) values(43, '236 George st.', 'Liverpool', 'NSW', '2170', 'Australia');

insert into programtypes values(1, 'Student');
insert into programtypes values(2, 'Transport');
insert into programtypes values(3, 'Staff');
insert into programtypes values(4, 'Individual');

insert into weekdays values(1, 'Sunday');
insert into weekdays values(2, 'Monday');
insert into weekdays values(3, 'Tuesday');
insert into weekdays values(4, 'Wednesday');
insert into weekdays values(5, 'Thursday');
insert into weekdays values(6, 'Friday');
insert into weekdays values(7, 'Saturday');

insert into calendars values(1, 'Default');
insert into calendars values(2, 'Staff');
insert into calendars values(3, 'Student');
insert into calendars values(4, 'Location');
insert into calendars values(5, 'Vehicle');

insert into stafftypes values(1, 'Administrator');
insert into stafftypes values(2, 'Coordinator');
insert into stafftypes values(3, 'Other');

insert into fundingsources values(1, '22100', 'Sample description 1', 'PSO', 'Active');
insert into fundingsources values(2, '25385', 'Sample description 2', 'TTW', 'Active');
insert into fundingsources values(3, '22513', 'Sample description 3', 'CP', 'Active');
insert into fundingsources values(4, '22514', 'Sample description 4', 'CP Indi', 'Active');
insert into fundingsources values(5, '23036', 'Sample description 5', 'CP Life Wyong', 'Active');
insert into fundingsources values(6, '25391', 'Sample description 6', 'CP Life Kariong', 'Active');

insert into vehicles values(1, 'Chevrolet Aveo', 'PC-5567', 4, 3, 'Active', 'Car');
insert into vehicles values(2, 'Mercedes-Benz Sprinter', 'PC-5567', 10, 4, 'Active', 'Van');
insert into vehicles values(3, 'Volvo 9500', 'PC-5567', 60, 20, 'Active', 'Bus');

insert into leavepolicies values(1, 'Policy 1', 'Active');
insert into leavepolicies values(2, 'Policy 2', 'Active');
insert into leavepolicies values(3, 'Policy 3', 'Active');
insert into leavepolicies values(4, 'Policy 4', 'Active');
insert into leavepolicies values(5, 'Policy 5', 'Active');

insert into leavepolicydetails values(1, 'ANNUAL', 10, '', 1);
insert into leavepolicydetails values(2, 'PERSONAL', 10, '', 1);
insert into leavepolicydetails values(3, 'PARENTAL', 10, '', 1);
insert into leavepolicydetails values(4, 'ANNUAL', 10, '', 2);
insert into leavepolicydetails values(5, 'SERVICE', 10, '', 2);

insert into staffmembers (id,staffId,gender,dob,joinedDate,leavepolicyid,stafftypeid,status,employmenttype) values(4,'1','Male','1991/01/01','1991/02/01',1,1,'Current','FULLTIME');
insert into staffmembers (id,staffId,gender,dob,joinedDate,leavepolicyid,stafftypeid,status,employmenttype) values(5,'2','Female','1992/02/02','1981/03/01',2,2,'Current','PARTTIME');
insert into staffmembers (id,staffId,gender,dob,joinedDate,leavepolicyid,stafftypeid,status,employmenttype) values(6,'3','Male','1993/03/03','1985/05/01',3,1,'Exited','CASUAL');
insert into staffmembers (id,staffId,gender,dob,joinedDate,leavepolicyid,stafftypeid,status,employmenttype) values(7,'4','Female','1994/04/04','1998/05/31',4,2,'Exited','FULLTIME');
insert into staffmembers (id,staffId,gender,dob,joinedDate,leavepolicyid,stafftypeid,status,employmenttype) values(8,'5','Male','1995/05/05','2001/07/01',5,1,'Returning','PARTTIME');

insert into students values(1,'cisid','mds1','Male','1991/01/01',2,27,null,true,'Active');
insert into students values(2,'cisid','mds2','Male','1991/01/01',16,28,null,true,'Active');
insert into students values(3,'cisid','mds3','Male','1991/01/01',17,29,null,true,'Active');
insert into students values(4,'cisid','mds4','Male','1991/01/01',18,30,null,true,'Active');
insert into students values(5,'cisid','mds5','Female','1991/01/01',11,31,null,true,'Active');
insert into students values(6,'cisid','mdsid','Female','1991/01/01',12,32,null,true,'Active');
insert into students values(7,'cisid','mdsid','Male','1991/01/01',19,33,null,true,'Active');
insert into students values(8,'cisid','mdsid','Female','1991/01/01',13,34,null,true,'Active');
insert into students values(9,'cisid','mdsid','Female','1991/01/01',14,35,null,true,'Active');
insert into students values(10,'cisid','mdsid','Female','1991/01/01',15,36,null,true,'Active');
insert into students values(11,'cisid','mdsid','Male','1991/01/01',25,37,null,true,'Active');
insert into students values(12,'cisid','mdsid','Female','1991/01/01',26,38,null,true,'Active');

insert into outlets values(1,'30892','Wyong','Bla bla bla','6406',1,'Active');
insert into outlets values(2,'30893','Gosford','Bla bla bla','20186',1,'Active');
insert into outlets values(3,'36136','Wyong','Bla bla bla','27380',2,'Active');
insert into outlets values(4,'36135','Gosford','Bla bla bla','27379',2,'Active');
insert into outlets values(5,'31671','Kariong','Bla bla bla','21608',3,'Active');
insert into outlets values(6,'31674','Wyong','Bla bla bla','21611',3,'Active');
insert into outlets values(7,'31673','Kariong','Bla bla bla','21613',4,'Active');
insert into outlets values(8,'32371','Wyong','Bla bla bla','Leave Provision',5,'Active');
insert into outlets values(9,'36150','Kariong','Bla bla bla','Leave Provision',6,'Active');

insert into locations (id, name )values(-1,'Home');
insert into locations values(1,'Location 1','l1',20);
insert into locations values(2,'Location 2','l2',21);
insert into locations values(3,'Location 3','l3',22);
insert into locations values(4,'Location 4','l4',23);
insert into locations values(5,'Location 5','l5',24);

insert into programs values(1,'Student Program 1','2014/01/01','2014/12/31',1,10.0,'Active',4);
insert into programs values(2,'Student Program 2','2014/01/01','2014/12/31',1,20.0,'Active',5);
insert into programs values(3,'Transport Program 1','2014/01/01','2014/12/31',2,30.0,'Inactive');
insert into programs values(4,'Student Program 3','2014/01/01','2014/12/31',1,3.0,'Inactive',6,1);
insert into programs values(5,'Transport Program 2','2014/01/01','2014/12/31',2,40.0,'Active',5,2);
insert into programs values(6,'Transport Program 3','2014/01/01','2014/12/31',2,20.0,'Active',7,3);
insert into programs values(7,'Staff Program 1','2014/01/01','2014/12/31',3,0,'Active',7);
insert into programs values(8,'Staff Program 2','2014/01/01','2014/12/31',3,0,'Active',7);

insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(1, 1, 'Student Group 1.1', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 05:00:00.0','2014-01-01 06:00:00.0',1,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(2, 1, 'Student Group 1.2', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 03:00:00.0','2014-01-01 04:00:00.0',2,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(3, 1, 'Student Group 1.3', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 03:00:00.0','2014-01-01 04:00:00.0',3,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(4, 2, 'Student Group 2.1', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 02:00:00.0','2014-01-01 03:20:00.0',4,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(5, 2, 'Student Group 2.2', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 06:00:00.0','2014-01-01 07:00:00.0',5,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(6, 2, 'Student Group 2.3', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 04:00:00.0','2014-01-01 06:00:00.0',1,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(7, 3, 'Transport Group 1.1', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 12:00:00.0','2014-01-01 13:00:00.0',1,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(8, 3, 'Transport Group 1.2', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 13:00:00.0','2014-01-01 14:00:00.0',2,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(9, 3, 'Transport Group 1.3', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 13:00:00.0','2014-01-01 15:00:00.0',3,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(10, 4, 'Student Group 3.1', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 11:00:00.0','2014-01-01 16:00:00.0',1,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(11, 4, 'Student Group 3.2', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 16:00:00.0','2014-01-01 17:00:00.0',2,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(12, 4, 'Student Group 3.3', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 17:00:00.0','2014-01-01 18:00:00.0',3,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,vehicleId,lunchIncluded) values(13, 5, 'Transport Group 2.1', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 15:00:00.0','2014-01-01 19:00:00.0',1,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,vehicleId,lunchIncluded) values(14, 5, 'Transport Group 2.2', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 16:00:00.0','2014-01-01 20:00:00.0',2,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,vehicleId,lunchIncluded) values(15, 5, 'Transport Group 2.3', 'Active',10,'2014/01/01','2014/07/31','2014-01-01 06:00:00.0','2014-01-01 21:00:00.0',3,false);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(16, 7, 'Staff Group 1.1', 'Active',0,'2014/01/01','2014/07/31','2014-01-01 19:00:00.0','2014-01-01 22:00:00.0',2,true);
insert into studentgroups (id,programId,name,status,chargeAmount,startDate,endDate,startTime,endTime,locationId,lunchIncluded) values(17, 8, 'Staff Group 2.1', 'Active',0,'2014/01/01','2014/07/31','2014-01-01 20:00:00.0','2014-01-01 23:00:00.0',3,false);


delete from specialneeds;
insert into specialneeds values(1,'Blind','Blind.png');
insert into specialneeds values(2,'Cochlear','Cochlear.png');
insert into specialneeds values(3,'Deaf','Deaf.png');
insert into specialneeds values(4,'Glasses','Glasses.png');
insert into specialneeds values(5,'Walking','Walking.png');
insert into specialneeds values(6,'Wheelchair','Wheelchair.png');

insert into StudentSpecialNeedsXRef values(1,1);
insert into StudentSpecialNeedsXRef values(2,2);
insert into StudentSpecialNeedsXRef values(3,3);
insert into StudentSpecialNeedsXRef values(1,3);
insert into StudentSpecialNeedsXRef values(2,1);
insert into StudentSpecialNeedsXRef values(3,5);
insert into StudentSpecialNeedsXRef values(1,4);
insert into StudentSpecialNeedsXRef values(2,5);
insert into StudentSpecialNeedsXRef values(3,1);

delete from communicationCategories;
insert into communicationCategories (id, name) values(1, 'Administration');
insert into communicationCategories (id, name) values(2, 'Governance');
insert into communicationCategories (id, name) values(3, 'Staff Member');
insert into communicationCategories (id, name) values(4, 'Student');
insert into communicationCategories (id, name) values(5, 'Vehicle');
insert into communicationCategories (id, name, parentId) values(6, 'Facilities - Kariong', 1);
insert into communicationCategories (id, name, parentId) values(7, 'Facilities - Parkside', 1);
insert into communicationCategories (id, name, parentId) values(8, 'Facilities - Wyong', 1);
insert into communicationCategories (id, name, parentId) values(9, 'General Maintenance', 1);
insert into communicationCategories (id, name, parentId) values(10, 'Management Team', 2);
insert into communicationCategories (id, name, parentId) values(11, 'Funding Source', 2);
insert into communicationCategories (id, name, parentId) values(12, 'Finance', 2);
insert into communicationCategories (id, name, parentId) values(13, 'Board', 2);
insert into communicationCategories (id, name, parentId) values(14, 'Other', 2);
insert into communicationCategories (id, name, parentId) values(15, 'Administration', 10);
insert into communicationCategories (id, name, parentId) values(16, 'Coordinators', 10);
insert into communicationCategories (id, name, parentId) values(17, 'Other', 10);
insert into communicationCategories (id, name, parentId) values(18, 'Existing', 4);
insert into communicationCategories (id, name, parentId) values(19, 'Inquiry', 4);
insert into communicationCategories (id, name, parentId) values(20, 'Bank', 12);
insert into communicationCategories (id, name, parentId) values(21, 'Other', 12);

delete from ServiceArea;
insert into ServiceArea (id, name) values(1, 'Air Conditioning');
insert into ServiceArea (id, name) values(2, 'Computers');
insert into ServiceArea (id, name) values(3, 'Electricians');
insert into ServiceArea (id, name) values(4, 'Fire and Security');
insert into ServiceArea (id, name) values(5, 'Fire Estinguishers');
insert into ServiceArea (id, name) values(6, 'First Aid Suppliers');
insert into ServiceArea (id, name) values(7, 'Fridges');
insert into ServiceArea (id, name) values(8, 'Glass and Glazing');
insert into ServiceArea (id, name) values(9, 'Locksmiths');
insert into ServiceArea (id, name) values(10, 'Office Furniture');
insert into ServiceArea (id, name) values(11, 'OH and S');
insert into ServiceArea (id, name) values(12, 'Pay');
insert into ServiceArea (id, name) values(13, 'Personnel Protective Equipment');
insert into ServiceArea (id, name) values(14, 'Pest Control');
insert into ServiceArea (id, name) values(15, 'Plumbers');
insert into ServiceArea (id, name) values(16, 'Printers');

-- -----------------------------------------------------
-- System User and Access Level Data
-- -----------------------------------------------------

insert into users (id,contactId,userName,password,user_type,enabled,systemUser,createdOn) values(1,8,'admin','pass', 'MGR', true, true, '1990/01/08');
insert into users (id,contactId,userName,password,user_type,enabled,systemUser,createdOn) values(2,9,'tom','pass', 'CON', true, true, '1990/01/08');
insert into users (id,contactId,userName,password,user_type,enabled,systemUser,createdOn) values(3,10,'nicole','pass', 'CON', true, true, '1990/01/08');
insert into users (id,contactId,mailingaddressid,user_type,enabled,systemUser,createdOn) values(4,3,39,'STF', false, false, '1990/01/08');
insert into users (id,contactId,mailingaddressid,user_type,enabled,systemUser,createdOn) values(5,6,40,'STF', false, false, '1990/01/08');
insert into users (id,contactId,mailingaddressid,user_type,enabled,systemUser,createdOn) values(6,4,41,'STF', false, false, '1990/01/08');
insert into users (id,contactId,mailingaddressid,user_type,enabled,systemUser,createdOn) values(7,7,42,'STF', false, false, '1990/01/08');
insert into users (id,contactId,mailingaddressid,user_type,enabled,systemUser,createdOn) values(8,5,43,'STF', false, false, '1990/01/08');

insert into authorities values(1,1,'admin','ROLE_USER');
insert into authorities values(2,1,'admin','ROLE_SUPERVISOR');
insert into authorities values(3,2,'tom','ROLE_VET');
insert into authorities values(4,2,'tom','ROLE_USER');
insert into authorities values(5,3,'nicole','ROLE_VET');
insert into authorities values(6,3,'nicole','ROLE_ASSISTANT');

delete from collections;
insert into collections values(1,'Trip to Gosford','A trip to gosford park',20.00,'2014/01/01','2014/06/30','Active');
insert into collections values(2,'Movie out','Going out for a movie at ...',10.00,'2014/01/01','2014/12/31','Inactive');

delete from referenceitems;
insert into referenceitems values(1, 'STAFF_SKILL', 'First Aid','Active');
insert into referenceitems values(2, 'STAFF_SKILL', 'One to One','Active');
insert into referenceitems values(3, 'STAFF_SKILL', 'Sign Language','Active');
insert into referenceitems values(4, 'STAFF_SKILL', 'Blind Care','Active');

delete from checkrecords;
insert into checkrecords values(1, 'Criminal Record Check', false, true, true);
insert into checkrecords values(2, 'Working with Children', false, true, false);
insert into checkrecords values(3, 'Confidentiality Agreement', false, false, false);
insert into checkrecords values(4, 'Copy of Drivers license', true, false, true);
insert into checkrecords values(5, 'Copy of Car Registration', false, false, true);
insert into checkrecords values(6, 'Copy of Comprehensive Insurance', false, false, false);
insert into checkrecords values(7, 'Eligibility to work in Australia', true, false, true);

delete from consents;
insert into consents values(1, 'Monitoring');
insert into consents values(2, 'MDS Sharing');
insert into consents values(3, 'Photo Sharing');
insert into consents values(4, 'Personal Info Sharing');

-- reset start value of serial fields --

SELECT setval('contacts_id_seq',(SELECT MAX(id) FROM contacts)+1);
SELECT setval('programtypes_id_seq',(SELECT MAX(id) FROM programtypes)+1);
SELECT setval('stafftypes_id_seq',(SELECT MAX(id) FROM stafftypes)+1);
SELECT setval('staffmembers_id_seq',(SELECT MAX(id) FROM staffmembers)+1);
SELECT setval('students_id_seq',(SELECT MAX(id) FROM students)+1);
SELECT setval('programs_id_seq',(SELECT MAX(id) FROM programs)+1);
SELECT setval('locations_id_seq',(SELECT MAX(id) FROM locations)+1);
SELECT setval('specialneeds_id_seq',(SELECT MAX(id) FROM specialneeds)+1);
SELECT setval('users_id_seq',(SELECT MAX(id) FROM users)+1);
SELECT setval('authorities_id_seq',(SELECT MAX(id) FROM authorities)+1);
SELECT setval('groupedstudents_id_seq',(SELECT MAX(id) FROM groupedstudents)+1);
SELECT setval('communicationCategories_id_seq',(SELECT MAX(id) FROM communicationCategories)+1);
SELECT setval('studentgroups_id_seq',(SELECT MAX(id) FROM studentgroups)+1);
SELECT setval('referenceitems_id_seq',(SELECT MAX(id) FROM referenceitems)+1);
SELECT setval('leavepolicies_id_seq',(SELECT MAX(id) FROM leavepolicies)+1);
SELECT setval('leavepolicydetails_id_seq',(SELECT MAX(id) FROM leavepolicydetails)+1);
SELECT setval('vehicles_id_seq',(SELECT MAX(id) FROM vehicles)+1);
SELECT setval('fundingsources_id_seq',(SELECT MAX(id) FROM fundingsources)+1);
SELECT setval('outlets_id_seq',(SELECT MAX(id) FROM outlets)+1);
SELECT setval('checkrecords_id_seq',(SELECT MAX(id) FROM checkrecords)+1);
SELECT setval('consents_id_seq',(SELECT MAX(id) FROM consents)+1);
SELECT setval('studentconsents_id_seq',(SELECT MAX(id) FROM studentconsents)+1);
SELECT setval('staffcheckrecords_id_seq',(SELECT MAX(id) FROM staffcheckrecords)+1);
SELECT setval('collections_id_seq',(SELECT MAX(id) FROM collections)+1);
-- SELECT setval('leavecategory_id_seq',(SELECT MAX(id) FROM leavecategory)+1);