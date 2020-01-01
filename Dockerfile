FROM adoptopenjdk/openjdk11

# Install MySQL.
RUN \
  apt-get update && \
  DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server

RUN mkdir -p /var/run/mysqld
RUN chown mysql:mysql /var/run/mysqld

RUN   \
  echo "mysqld_safe --skip-grant-tables &" > /tmp/config && \
  echo "mysqladmin --silent --wait=10 ping || exit 1" >> /tmp/config && \
  bash /tmp/config && \
  rm -f /tmp/config

# Define mountable directories.
VOLUME ["/etc/mysql", "/var/lib/mysql"]

RUN mkdir /opt/app
COPY build/libs/car-pooling-challenge-yair.kukielka-0.0.1-SNAPSHOT.jar /opt/app/app.jar
RUN ls /opt/app

# mysql parameters are configurable through environment variables
ARG MYSQL_HOST=localhost
ARG MYSQL_PORT=3306
ARG MYSQL_DB=mydb
ENV MYSQL_HOST=${MYSQL_HOST}
ENV MYSQL_PORT=${MYSQL_PORT}
ENV MYSQL_DB=${MYSQL_DB}

EXPOSE 9091
COPY docker-init-script.sh /

ENTRYPOINT ["/docker-init-script.sh"]