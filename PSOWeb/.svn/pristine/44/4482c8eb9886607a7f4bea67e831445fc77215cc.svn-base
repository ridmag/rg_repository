
DROP TABLE IF EXISTS groupstaffskillsxref;

CREATE TABLE groupstaffskillsxref
(
  groupid bigserial NOT NULL,
  referenceitemid bigint NOT NULL,

  CONSTRAINT groupstaffskillsxref_pkey PRIMARY KEY (groupid, referenceitemid),

  CONSTRAINT fk_groupstaffskills_referenceitemid FOREIGN KEY (referenceitemid)
      REFERENCES referenceitems (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,

  CONSTRAINT fk_groupstaffskills_groupid FOREIGN KEY (groupid)
      REFERENCES studentgroups (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
