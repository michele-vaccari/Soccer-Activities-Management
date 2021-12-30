#!/bin/bash

export database_name=sam

echo "#### Start creating the $database_name database schema ####"

export db2_profile_path=/home/db2inst1/sqllib/db2profile
echo "Load the $db2_profile_path"
source $db2_profile_path

echo "Connect to $database_name database"
cd /home/db2inst1/sqllib/bin/
./db2 connect to $database_name

export sql_init_script_path=/var/custom/init.sql
echo "Run $sql_init_script"
./db2 -tvf $sql_init_script_path

echo "#### End creating the $database_name database schema ####"