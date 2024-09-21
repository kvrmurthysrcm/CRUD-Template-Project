# Github URL:
https://github.com/kvrmurthysrcm/CRUD-Template-Project.git

# Following are the helpful SQL for populating Products for testing:

SELECT * FROM devschema.products;

INSERT INTO `devschema`.`products`
(`id`,`price`,`product_name`,`volume`,`wight`)
VALUES (1, 20, 'Banana', 3, 5);

INSERT INTO `devschema`.`products`
(`id`,`price`,`product_name`,`volume`,`wight`)
VALUES (2, 25, 'Apple', 2, 4);

INSERT INTO `devschema`.`products`
(`id`,`price`,`product_name`,`volume`,`wight`)
VALUES (3, 50, 'Grapes Bunch', 4, 6);

INSERT INTO `devschema`.`products`
(`id`,`price`,`product_name`,`volume`,`wight`)
VALUES (4, 60, 'Pear', 4, 5);
=====================================================================================
# http commands for API CRUD testing
# http [flags] [METHOD] URL [ITEM [ITEM]]

# Get all products:
# Base URL: http://localhost:9000/api/v1/products
# run following command to test
# http GET http://localhost:9000/api/v1/products

# Following orks for BASIC Auth, not for FormAuth login!!
http -a KVRM:password GET http://localhost:9000/api/v1/products

http -a KVRM:password POST http://localhost:9000/api/v1/token
# Above command receives a token back from REST API
eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiS1ZSTSIsImV4cCI6MTcyNjg0NDkwOSwiaWF0IjoxNzI2ODQxMzA5LCJzY29wZSI6IlJPTEVfVVNFUiJ9.ofP4ZC4GDg-AMNs8AyTsNosjADRL9_CpiXleZWojalqR1NMrgECqMRJSvrPmqYt7ozC2n3cKPRnaF_xXK1Nc9A_UVLV4nOBheWuANiRMFnPZxXxBV7n0ycFj1J5kQxDWXnKsjRRVpsfKiL67kQyxAachgVjdV8AAv-fA4Tv91qN44j1SPVYEi2345dnXtvOL1x1YuAadPcICVTSxGjtFkKK6ddaPCT1duwenmRBL-EaosV_RYRFUjGHAy-QGxWjQjHLSDVidR--X0_75d2KGlm8WW0qbhYmhUMgrDUxUlrJsH8yMgFJT1mXDYes456f0sSgFwp30i9PnX9VzsYwn6w
# EX::  http -A bearer -a token pie.dev/bearer

# following command is working to access the REST API end point using the TOEKN received.
http -A bearer -a eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiS1ZSTSIsImV4cCI6MTcyNjg0NDkwOSwiaWF0IjoxNzI2ODQxMzA5LCJzY29wZSI6IlJPTEVfVVNFUiJ9.ofP4ZC4GDg-AMNs8AyTsNosjADRL9_CpiXleZWojalqR1NMrgECqMRJSvrPmqYt7ozC2n3cKPRnaF_xXK1Nc9A_UVLV4nOBheWuANiRMFnPZxXxBV7n0ycFj1J5kQxDWXnKsjRRVpsfKiL67kQyxAachgVjdV8AAv-fA4Tv91qN44j1SPVYEi2345dnXtvOL1x1YuAadPcICVTSxGjtFkKK6ddaPCT1duwenmRBL-EaosV_RYRFUjGHAy-QGxWjQjHLSDVidR--X0_75d2KGlm8WW0qbhYmhUMgrDUxUlrJsH8yMgFJT1mXDYes456f0sSgFwp30i9PnX9VzsYwn6w GET http://localhost:9000/api/v1/products

# Get product by ID:
# URL: http://localhost:9000/api/v1/products/{ID}
# run following command to test
http GET http://localhost:9000/api/v1/products/7
http -a KVRM:password GET http://localhost:9000/api/v1/products/12

# Delete product by ID:
http://localhost:9000/api/v1/products/{ID}
http DELETE http://localhost:9000/api/v1/products/2
http -a KVRM:password DELETE http://localhost:9000/api/v1/products/3

# Create Product:
# Base URL: http POST http://localhost:9000/api/v1/products
# Run the following to test creation of the product...
http POST http://localhost:9000/api/v1/products productName='Cycle' price=20 wight=5 volume=7
http -a KVRM:password POST http://localhost:9000/api/v1/products productName='Sleeve' price=2000 wight=5 volume=7

# Update Product
http -a KVRM:password PUT http://localhost:9000/api/v1/products/12 id=12 productName='Jacket' price=25 wight=15 volume=7

# DDL for Security schema is found here:
JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION
"org/springframework/security/core/userdetails/jdbc/users.ddl"
# Manually create these tables, index to start using the security:

create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);
# Two users are added to Security DB by default.
# We can use them to test
# KVRM / password
# admin / Myadmin#123

# Added logback.xml to configure logging, logs working now using the xml file.
# console, file rotator, async logs are configured now.
# logback-classic version 1.5.8
# The ch.qos.logback.classic.jmx package was removed for security reasons and for lack of use since version 1.4

# following URL display the logger in the JVM..
http://localhost:9000/actuator/loggers

# levels [ "OFF", "ERROR", "WARN", "INFO", "DEBUG", "TRACE" ]

# the following is showing the current log level...run it from a CMD prompt...
http -a KVRM:password GET http://localhost:9000/actuator/loggers/HomeController
# Following command will update log level
http -a KVRM:password POST http://localhost:9000/actuator/loggers/HomeController configuredLevel=DEBUG

# Read the details from Prometheus.txt to find the setup details.

