FROM bhgedigital/envsubst AS build
ARG SAM_DEFAULT_ADMIN_USER_EMAIL
ARG SAM_DEFAULT_ADMIN_USER_PASSWORD
ARG SAM_DEFAULT_ADMIN_USER_NAME
ARG SAM_DEFAULT_ADMIN_USER_SURNAME
COPY initialization-scripts/add_default_admin_user.sql /home
RUN envsubst < /home/add_default_admin_user.sql > /home/output.sql

FROM ibmcom/db2
# additional setup scripts to execute after Db2 setup has completed
RUN mkdir /var/custom
COPY initialization-scripts/createschema.sh /var/custom
COPY initialization-scripts/init.sql /var/custom
COPY --from=build /home/output.sql /var/custom/add_default_admin_user.sql
RUN chmod a+x /var/custom/createschema.sh