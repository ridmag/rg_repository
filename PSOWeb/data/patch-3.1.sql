
-- Clear 0 charged Transactions
update studentevents set chargetxid = null where chargetxid in 
(select id from transactions where transactiontype = 'CREDIT' and amount = 0);
delete from transactions where transactiontype = 'CREDIT' and amount = 0;

-- Delete statements
update transactions set invoiceid = null;
delete from invoices;
delete from invoiceitems;
update programevents set invoiced = false;

-- Change tx date to event date
update transactions t set transactiondate = (select e.eventDate from programevents e where e.id = t.programeventid) 
where programeventid is not null;

-- Delete collection charges which don't have payments.
delete from transactions t where t.chargetype = 'COLLECTION' and t.collectionid not in
(select tx.collectionid from transactions tx where tx.paymenttype = 'COLLECTION' and tx.studentid = t.studentid)
