SELECT 
  sm.id, 
  sm.status, 
  sm.serviceenddate, 
  sm.photoid, 
  sm.joineddate, 
  sm.employmenttype
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
  u.id 
FROM 
  public.contacts c, 
  public.staffmembers sm, 
  public.users u
WHERE 
  u.id = sm.id AND
  u.contactid = c.id;
