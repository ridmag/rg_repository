@ECHO OFF
CLS

SET PATH=D:\Development\PostgreSQL\9.0\bin
SET usr=postgres

D:
cd\Development\Workspaces\Mars\NDIS\data\NDIS Database updates

ECHO Dropping Database ndis
::dropdb.exe -U %usr% -W %pwd% ndis

psql -U %usr% --set ON_ERROR_STOP=on -d ndis -c "drop schema public cascade;"
pause
psql -U %usr% --set ON_ERROR_STOP=on -d ndis -c "create schema public;"
ECHO 

ECHO Restoring Database Dump
psql.exe -U %usr% --set ON_ERROR_STOP=on  ndis < 19102015-cleansed.sql
ECHO Patching DB for NDIS
psql.exe -U %usr% --set ON_ERROR_STOP=on  ndis < patch_ndis_database.sql
ECHO Patching DB for NDIS RC_1.1
psql.exe -U %usr% --set ON_ERROR_STOP=on  ndis < NDIS_Patch_RC_1.1.sql
	
ECHO .
ECHO Database setup complete.
pause