#!/bin/bash
#
docker stop SQLServer
docker rm SQLServer
docker pull mcr.microsoft.com/mssql/server:2017-latest
docker run -e 'ACCEPT_EULA=Y' -e 'SA_PASSWORD=passW0rd' -e 'MSSQL_PID=Express' -p 1433:1433 --name SQLServer -d mcr.microsoft.com/mssql/server:2017-latest
sleep 30
docker exec -it SQLServer /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P passW0rd
# -q 'CREATE DATABASE timeregistration;'
