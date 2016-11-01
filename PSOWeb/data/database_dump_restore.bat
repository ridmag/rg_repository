@ECHO OFF
CLS

SET PATH=D:\Development\bin
SET usr=postgres
SET pwd=pass

D:
cd\Development

ECHO Dropping Database psodb
::dropdb.exe -U %usr% W %pwd% psodb

psql -U %usr% -W %pwd% -d psodb -c "drop schema public cascade;"
psql -U %usr% -W %pwd% -d psodb -c "create schema public;"
ECHO 

::ECHO Creating Database psodb
::createdb.exe -U %usr% -W %pwd% psodb

ECHO 

ECHO Restoring Database Dump
psql.exe -U %usr% -W %pwd% --set ON_ERROR_STOP=on psodb < NDIS_Live_22012016_cleansed.sql
	
ECHO .
ECHO Database setup complete.
pause