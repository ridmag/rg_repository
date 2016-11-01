﻿select s.id, 
  s.cisid, 
  s.mdsid, 
  s.status, 
  s.photoid,
  s.dob,
  c.title, 
  c.firstname, 
  c.lastname, 
  c.middlenames, 
  c.businessname,
  c.streetaddress, 
  c.city, 
  c.state, 
  c.postcode, 
  c.country, 
  c.mobilephone, 
  c.homephone, 
  c.officephone,
  c.fax, 
  c.email, 
  c.sendnewsletter, 
  c.sendinvoice,
  c2.title as gard_title, 
  c2.firstname as gard_firstname, 
  c2.lastname as gard_lastname, 
  c2.middlenames as gard_middlenames, 
  c2.businessname as gard_businessname,
  c2.streetaddress as gard_streetaddress, 
  c2.city as gard_city, 
  c2.state as gard_state, 
  c2.postcode as gard_postcode, 
  c2.country as gard_country, 
  c2.mobilephone as gard_mobilephone, 
  c2.homephone as gard_homephone, 
  c2.officephone as gard_officephone,
  c2.fax as gard_fax, 
  c2.email as gard_email, 
  c2.sendnewsletter as gard_sendnewsletter, 
  c2.sendinvoice as gard_sendinvoice
   from students s inner join contacts c on s.id=c.id left join public.studentguardiansxref sgx on s.id=sgx.studentid left join contacts c2 on c2.id=sgx.contactid;