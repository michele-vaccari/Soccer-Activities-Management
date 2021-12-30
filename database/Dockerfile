FROM ibmcom/db2

# additional setup scripts to execute after Db2 setup has completed
RUN mkdir /var/custom
COPY initialization-scripts/createschema.sh /var/custom
COPY initialization-scripts/init.sql /var/custom
RUN chmod a+x /var/custom/createschema.sh