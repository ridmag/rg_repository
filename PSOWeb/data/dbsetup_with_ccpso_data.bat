@ECHO OFF
CLS

SET PATH=c:\Program Files\PostgreSQL\9.0\bin\
SET usr=postgres
SET pwd=postgres

D:
cd\iTelaSoft\Workspaces\Helios\PSOWeb\data

ECHO Deploying Database
psql -U %usr% -c "SELECT pg_terminate_backend(procpid) FROM pg_stat_activity WHERE datname = 'psodb'"
dropdb -U %usr% psodb
createdb -U %usr% psodb
psql.exe -U %usr% --set ON_ERROR_STOP=on psodb < psodbccpso.sql

ECHO.
ECHO Deploying patch-sample_data_ccpso
psql.exe -U %usr% --set ON_ERROR_STOP=on psodb < patch-sample_data_ccpso.sql

ECHO.
ECHO Deploying Patch-2.1
psql.exe -U %usr% --set ON_ERROR_STOP=on psodb < patch-2.1.sql

ECHO.
ECHO Deploying Patch-2.2
psql.exe -U %usr% --set ON_ERROR_STOP=on psodb < patch-2.2.sql

ECHO.
ECHO Database setup complete.
pause