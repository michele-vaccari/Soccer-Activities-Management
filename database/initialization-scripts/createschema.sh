#!/bin/bash

echo "#### Start creating the $DBNAME database schema ####"

export db2_profile_path=/home/db2inst1/sqllib/db2profile
echo "Load the $db2_profile_path"
source $db2_profile_path

echo "Connect to $DBNAME database"
cd /home/db2inst1/sqllib/bin/
./db2 connect to $DBNAME

export sql_init_script_path=/var/custom/init.sql
echo "Run $sql_init_script"
./db2 -tvf $sql_init_script_path

echo "Add default admin user"
export sql_add_default_admin_user_script_path=/var/custom/add_default_admin_user.sql
./db2 -tvf $sql_add_default_admin_user_script_path

echo "Remove all script"
rm /var/custom/*

echo "#### End creating the $DBNAME database schema ####"
