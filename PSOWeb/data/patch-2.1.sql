-- -----------------------------------------------------
-- Table holidays
-- -----------------------------------------------------
ALTER TABLE holidays RENAME date TO startDate;
ALTER TABLE holidays ADD COLUMN endDate date;

UPDATE holidays SET enddate = startdate;

-- -----------------------------------------------------
-- Table HoursUtilizedReportItem
-- -----------------------------------------------------
ALTER TABLE HoursUtilizedReportItem RENAME totalHours TO totalMinutes;
ALTER TABLE HoursUtilizedReportItem RENAME programHrsUsed TO programMinsUsed;
ALTER TABLE HoursUtilizedReportItem RENAME transportHrsUsed TO transportMinsUsed;
ALTER TABLE HoursUtilizedReportItem RENAME balanceHrs TO balanceMins;

-- Earlier we had only number of hours saved within the database.
-- Since now we save minutes, all existing hours are converted to minutes and still these existing data wont be 100% acurate.
-- But all newly creating records will be acurate and it wont effect existing data.
UPDATE HoursUtilizedReportItem SET totalMinutes = totalMinutes * 60;
UPDATE HoursUtilizedReportItem SET programMinsUsed = programMinsUsed * 60;
UPDATE HoursUtilizedReportItem SET transportMinsUsed = transportMinsUsed * 60;
UPDATE HoursUtilizedReportItem SET balanceMins = balanceMins * 60;

