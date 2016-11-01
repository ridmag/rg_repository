-- -------------------------------------------------------
-- Table StudentFundingSources
-- -------------------------------------------------------
ALTER TABLE studentfundingsources DROP COLUMN active;

-- -------------------------------------------------------
-- Table InvoiceItems
-- -------------------------------------------------------
DELETE FROM invoiceItems WHERE chargeType = 'COLLECTION';
UPDATE invoiceItems SET chargeAmount = paidAmount WHERE paymentType = 'COLLECTION';

-- -------------------------------------------------------
-- Table Leaves
-- -------------------------------------------------------
ALTER TABLE leaves ADD COLUMN nopayHours double precision DEFAULT 0;

