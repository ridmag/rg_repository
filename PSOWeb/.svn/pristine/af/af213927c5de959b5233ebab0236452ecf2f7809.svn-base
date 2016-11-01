ALTER TABLE StudentGuardiansXRef ADD COLUMN relationId bigint;

CREATE INDEX StudentGuardiansXRef_contactId_index ON StudentGuardiansXRef USING btree (contactId);
CREATE INDEX StudentGuardiansXRef_studentId_index ON StudentGuardiansXRef USING btree (studentId);
CREATE INDEX StudentGuardiansXRef_relationId_index ON StudentGuardiansXRef USING btree (relationId);
ALTER TABLE ONLY StudentGuardiansXRef
   ADD CONSTRAINT FK_StudentGuardiansXRef_relationId FOREIGN KEY (relationId) REFERENCES ReferenceItems(id) MATCH FULL;
   
ALTER TABLE programevents ADD COLUMN totalMoneyCollected double precision not null DEFAULT 0;
ALTER TABLE programevents ADD COLUMN totalEFTCollected double precision not null DEFAULT 0;

UPDATE programevents pe SET totalMoneyCollected = (SELECT case when SUM(amount) is null then 0 else SUM(amount) end FROM transactions tx where transactiontype = 'DEBIT' and paymentmethod != 'EFT' and pe.id = tx.programeventid );
UPDATE programevents pe SET totalEFTCollected = (SELECT case when SUM(amount) is null then 0 else SUM(amount) end FROM transactions tx where transactiontype = 'DEBIT' and paymentmethod = 'EFT' and pe.id = tx.programeventid );

CREATE INDEX transactions_studenteventId_index ON transactions USING btree (studenteventId);
ALTER TABLE ONLY transactions
	ADD CONSTRAINT FK_transactions_studenteventId FOREIGN KEY (studenteventId) REFERENCES studentevents(id) MATCH FULL;
	
/*
 * studenteventId FK relationship was not added to the transaction table, so transactions not deleted even after
 * its associated studentEvents deleted. This patch will delete the transaction those are not in use
 * 
 */
DELETE from transactions tx where tx.studenteventId not in (select id from studentevents);

