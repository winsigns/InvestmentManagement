MYSQL_IP_ADDR=$1
if [ ! $MYSQL_IP_ADDR ]; then
    MYSQL_IP_ADDR="127.0.0.1"
fi

echo "MYSQL_IP_ADDR=${MYSQL_IP_ADDR}"
echo "mysql -h${MYSQL_IP_ADDR} -uroot -ppassword < createdb.sql"
mysql -h${MYSQL_IP_ADDR} -uroot -ppassword < createdb.sql
