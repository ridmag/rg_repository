 @ECHO OFF
CLS

SET PATH=c:\Program Files\PostgreSQL\9.0\bin\
SET usr=postgres
SET pwd=pass

D:
cd\iTelaSoft\Workspaces\Helios\PSOWeb\data


ECHO Deploying Database Schema
psql.exe -U %usr% --set ON_ERROR_STOP=on psodb < ddl-ccpso.sql 
ECHO.	

ECHO Deploying Sample Data
psql.exe -U %usr% --set ON_ERROR_STOP=on psodb < data-ccpso.sql
ECHO.

ECHO Deploying Database Schema patch 1.1
psql.exe -U %usr% psodb < patch-1.1.sql 
ECHO.

ECHO Deploying Database Schema patch 2.0
psql.exe -U %usr% psodb < patch-2.0.sql 
ECHO.

ECHO Deploying Database Schema Patch-2.1
psql.exe -U %usr% --set ON_ERROR_STOP=on psodb < patch-2.1.sql

ECHO.
ECHO Deploying Patch-2.2
psql.exe -U %usr% --set ON_ERROR_STOP=on psodb < patch-2.2.sql
ECHO.

ECHO Database setup complete.
pause