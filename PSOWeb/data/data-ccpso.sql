--copy refdrugcat(parentId,id,code,name,type) from 'C:\\Users\\vajira\\workspace\\ITelaVet3\\data\\atccodes1.csv' with delimiter '|';
-- ****** Lookup and Sample Data for PSOWeb *******


delete from configurations;
delete from authorities;
delete from users;
delete from studentgroups;
delete from programs;
delete from staffcheckrecords;
delete from staffmembers;
delete from StudentFundingSources;
delete from locations;
delete from contacts;
delete from stafftypes;
delete from fundingsources;
delete from leavepolicies;
delete from leavepolicydetails;
delete from programtypes;
delete from weekdays;
delete from calendars;
delete from vehicles;
delete from outlets;

-- -----------------------------------------------------
-- System Configuration
-- -----------------------------------------------------
insert into configurations values(5, 'LICENSE','g81SPc3Ushz/464tFyJ23LxNSUjl9vSz');
insert into configurations values(6, 'THEME','./xmlhttp/css/rime/rime.css');
insert into configurations values(7, 'COUNTRY','Australia');
insert into configurations values(9, 'INVOICE_TAX','0.10');
insert into configurations values(10, 'VERSION_DB','1.0');
insert into configurations (id, parameter) values(11, 'LOGO_NAME');
insert into configurations (id, parameter) values(12, 'LOGO_ID');
insert into configurations (id, parameter) values(13, 'ABN');
insert into configurations values(14, 'PASSWORD', 'pass123');
insert into configurations values(15, 'SMTP_ADDRESS', 'smtp.gmail.com');
insert into configurations values(16, 'IMAP_ADDRESS', 'imap.gmail.com');



insert into programtypes values(1, 'Standard');
insert into programtypes values(2, 'Transport');

insert into weekdays values(1, 'Monday');
insert into weekdays values(2, 'Tuesday');
insert into weekdays values(3, 'Wednesday');
insert into weekdays values(4, 'Thursday');
insert into weekdays values(5, 'Friday');

insert into calendars values(1, 'General');
insert into calendars values(2, 'Staff');
insert into calendars values(3, 'Student');
insert into calendars values(4, 'Location');
insert into calendars values(5, 'Vehicle');

insert into stafftypes values(1, 'Administrator');
insert into stafftypes values(2, 'Coordinator');
insert into stafftypes values(3, 'Other');


insert into leavepolicydetails values(1, 'CASUAL', 10, '');
insert into leavepolicydetails values(2, 'MEDICAL', 10, '');
insert into leavepolicydetails values(3, 'ANNUAL', 10, '');
insert into leavepolicydetails values(4, 'ANNUAL', 10, '');
insert into leavepolicydetails values(5, 'CASUAL', 10, '');

insert into leavepolicies values(1, 'Policy 1', 1);
insert into leavepolicies values(2, 'Policy 2', 2);
insert into leavepolicies values(3, 'Policy 3', 3);
insert into leavepolicies values(4, 'Policy 4', 4);
insert into leavepolicies values(5, 'Policy 5', 5);




delete from specialneeds;
insert into specialneeds values(1,'Blind','Blind.png');
insert into specialneeds values(2,'Cochlear','Cochlear.png');
insert into specialneeds values(3,'Deaf','Deaf.png');
insert into specialneeds values(4,'Glasses','Glasses.png');
insert into specialneeds values(5,'Walking','Walking.png');
insert into specialneeds values(6,'Wheelchair','Wheelchair.png');


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
insert into contacts (id, title, firstName, lastName, middleNames, businessName, streetAddress, city, state, postCode, country, geoCode, mobilePhone, homePhone, officePhone, email, businessType, sendNewsletter) values(8, 'Mr.', 'Administrator', '', 'System', 'NA', 'NA', 'NA', 'NA', '0000', 'NA', '2345, 5676', '0000', '0000', '0000', 'noreply@generic.com', 'NA', false);
insert into users (id,contactId,userName,password,user_type,enabled,systemUser,createdOn) values(1,8,'admin','admin', 'MGR', true, true, '1990/01/08');

insert into authorities values(1,1,'admin','ROLE_USER');
insert into authorities values(2,1,'admin','ROLE_SUPERVISOR');

delete from referenceitems;
insert into referenceitems values(1, 'STAFF_SKILL', 'First Aid');
insert into referenceitems values(2, 'STAFF_SKILL', 'One to One');
insert into referenceitems values(3, 'STAFF_SKILL', 'Sign Language');
insert into referenceitems values(4, 'STAFF_SKILL', 'Blind Care');

delete from checkrecords;
insert into checkrecords values(1, 'Criminal Record Check');
insert into checkrecords values(2, 'Working with Children');

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