-- patch related to task#4235
--add overrideprice column into programevents table

--add invoiceid to ndiscommitedevents
ALTER TABLE ndiscommittedevents ADD COLUMN invoiceid bigint;

-- add ndisinvoice table
CREATE TABLE ndisinvoice
(
  id bigserial NOT NULL,
  authorisedstaffid bigint NOT NULL,
  registrationcode character varying(255),
  startday timestamp with time zone,
  endday timestamp with time zone,
  generatedate timestamp with time zone,
  filepath character varying(255),
  CONSTRAINT ndisinvoice_pkey PRIMARY KEY (id),
  CONSTRAINT fk_ndisinvoice_authorisedstaffid FOREIGN KEY (authorisedstaffid)
      REFERENCES authorisedstaff (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- add forign key
ALTER TABLE ndiscommittedevents ADD FOREIGN KEY (invoiceid)
REFERENCES ndisinvoice(id) MATCH FULL
ON UPDATE NO ACTION ON DELETE NO ACTION;

-- change ndisinvoiceitem table
DROP TABLE ndisinvoiceitems;
CREATE TABLE ndisinvoiceitems
(
  id bigserial NOT NULL,
  invoiceid bigint not null,
  registrationnumber character varying(100),
  ndisnumber character varying(100),
  supportsdeliveredfrom character varying(100),
  supportsdeliveredto character varying(100),
  supportnumber character varying(100),
  claimreference character varying(100),
  quantity character varying(100),
  hours character varying(100),
  unitprice character varying(100),
  gstcode character varying(100),
  authorisedby character varying(100)
);

-- add forign key
ALTER TABLE ndisinvoiceitems ADD FOREIGN KEY (invoiceid)
REFERENCES ndisinvoice(id) MATCH FULL;