FROM mysql:8.0
ENV MYSQL_DATABASE=institute
ENV MYSQL_USER=appuser
ENV MYSQL_PASSWORD=apppass
ENV MYSQL_ROOT_PASSWORD=rootpass
COPY init.sql /docker-entrypoint-initdb.d/
EXPOSE 3306
