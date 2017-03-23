FROM mariadb

# comment out a few problematic configuration values
# don't reverse lookup hostnames, they are usually another container
RUN sed -Ei 's/^#bind-address=0.0.0.0/bind-address=0.0.0.0/' /etc/mysql/my.cnf 