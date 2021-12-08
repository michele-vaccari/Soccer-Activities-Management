set application_name=soccer-activities-management
set image_name=%application_name%-database

call docker build -t %image_name% .

set database_name=sam
set database_password=12345678

set container_port=50000
set container_volume_path=C:\docker-volumes\%image_name%

docker run -itd ^
--name %image_name% ^
--privileged=true ^
-p %container_port%:50000 ^
-e LICENSE=accept ^
-e DB2INST1_PASSWORD=%database_password% ^
-e DBNAME=%database_name% ^
-e PERSISTENT_HOME=false ^
-v %container_volume_path%:/database ^
%image_name%