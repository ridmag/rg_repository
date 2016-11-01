@ECHO OFF
CLS

SET PATH=C:\Program Files\PostgreSQL\9.3\bin
SET usr=postgres

D:
cd\Workspaces\masworkspace\PSOWeb\data

ECHO Dropping Database psodb
::dropdb.exe -U %usr% -W %pwd% psodb

psql -U %usr% --set ON_ERROR_STOP=on -d psodb -c "drop schema public cascade;"
pause
psql -U %usr% --set ON_ERROR_STOP=on -d psodb -c "create schema public;"
ECHO 

ECHO Restoring Database Dump
psql.exe -U %usr% --set ON_ERROR_STOP=on  psodb < NDIS_Live_22012016_cleansed.sql
::ECHO Patching DB for NDIS
::psql.exe -U %usr% --set ON_ERROR_STOP=on  psodb < patch_ndis_database.sql
	
ECHO .
ECHO Database setup complete.
pause