@ECHO OFF
CLS

SET PATH=C:\Program Files\PostgreSQL\9.2\bin
SET usr=postgres
SET pwd=postgres

D:
cd\iTelaSoft\Workspaces\Helios\PSOWeb\data

ECHO Deploying Database Schema
psql.exe -U %usr% --set ON_ERROR_STOP=on psodb < ddl.sql 
	
ECHO Deploying Sample Data
psql.exe -U %usr% --set ON_ERROR_STOP=on psodb < data.sql

ECHO .
ECHO Database setup complete.
pause