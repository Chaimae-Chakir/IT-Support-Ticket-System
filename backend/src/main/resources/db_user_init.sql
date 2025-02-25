-- sqlplus / as sysdba
-- ALTER SESSION SET CONTAINER=XE;

-- CREATE USER c##chakir IDENTIFIED BY chakir2001;
-- GRANT CONNECT, RESOURCE TO c##chakir;
-- ALTER USER c##chakir DEFAULT TABLESPACE users;
-- ALTER USER c##chakir QUOTA UNLIMITED ON users;

-- SELECT username FROM all_users WHERE username = 'c##chakir';

-- sqlplus c##chakir/chakir2001@localhost:1521/xe
