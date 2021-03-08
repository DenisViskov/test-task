@echo off


rem Stop Docker container
echo Shutdown container:
docker stop docker-compose_api_1
docker stop docker-compose_db_1
echo done


exit 0;