set application_name=soccer-activities-management
set image_name=%application_name%-web-api

call docker build -t %image_name% .

set container_port=8080
set container_volume_path=C:\docker-volumes\%image_name%

docker run -itd ^
--name %image_name% ^
-p %container_port%:8080 ^
-v %container_volume_path%:/database ^
--network=app-network ^
%image_name%