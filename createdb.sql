CREATE DATABASE IF NOT EXISTS funddb;
GRANT ALL ON funddb.* TO 'mariadb'@'%';

CREATE DATABASE IF NOT EXISTS investdb;
GRANT ALL ON investdb.* TO 'mariadb'@'%';

CREATE DATABASE IF NOT EXISTS inventorydb;
GRANT ALL ON inventorydb.* TO 'mariadb'@'%';

CREATE DATABASE IF NOT EXISTS tradedb;
GRANT ALL ON tradedb.* TO 'mariadb'@'%';

CREATE DATABASE IF NOT EXISTS generaldb;
GRANT ALL ON generaldb.* TO 'mariadb'@'%';
