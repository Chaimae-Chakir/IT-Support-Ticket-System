-- Adjust display settings for better readability in SQL*Plus
SET LINESIZE 200;
SET PAGESIZE 50;
SET LONG 5000;
SET LONGCHUNKSIZE 5000;
SET TRIMSPOOL ON;
SET WRAP OFF;

-- Format columns for better readability
COLUMN id FORMAT 99999;
COLUMN username FORMAT A20;
COLUMN password FORMAT A60;
COLUMN role FORMAT A15;
COLUMN title FORMAT A40;
COLUMN description FORMAT A80;
COLUMN priority FORMAT A10;
COLUMN category FORMAT A15;
COLUMN creation_date FORMAT A30;
COLUMN status FORMAT A15;
COLUMN created_by FORMAT 99999;
COLUMN ticket_id FORMAT 99999;
COLUMN user_id FORMAT 99999;
COLUMN content FORMAT A80;
COLUMN created_at FORMAT A30;
COLUMN action FORMAT A50;
COLUMN timestamp FORMAT A30;

-- Enable AutoCommit to persist changes
SET AUTOCOMMIT ON;

-- Drop existing tables only if they exist to avoid ORA-00942
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE audit_logs CASCADE CONSTRAINTS';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
END IF;
END;
/

BEGIN
EXECUTE IMMEDIATE 'DROP TABLE comments CASCADE CONSTRAINTS';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
END IF;
END;
/

BEGIN
EXECUTE IMMEDIATE 'DROP TABLE tickets CASCADE CONSTRAINTS';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
END IF;
END;
/

BEGIN
EXECUTE IMMEDIATE 'DROP TABLE users CASCADE CONSTRAINTS';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
END IF;
END;
/

-- Create Users Table
CREATE TABLE users (
                       id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       username VARCHAR2(255) UNIQUE NOT NULL,
                       password VARCHAR2(255) NOT NULL,
                       role VARCHAR2(20) CHECK (role IN ('EMPLOYEE', 'IT_SUPPORT'))
);

-- Create Tickets Table
CREATE TABLE tickets (
                         id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         title VARCHAR2(255) NOT NULL,
                         description VARCHAR2(1024),
                         priority VARCHAR2(10) CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH')),
                         category VARCHAR2(20) CHECK (category IN ('NETWORK', 'HARDWARE', 'SOFTWARE', 'OTHER')),
                         creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         status VARCHAR2(20) CHECK (status IN ('NEW', 'IN_PROGRESS', 'RESOLVED')),
                         created_by NUMBER NOT NULL,
                         FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE
);

-- Create Comments Table
CREATE TABLE comments (
                          id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          ticket_id NUMBER NOT NULL,
                          created_by NUMBER NOT NULL,
                          content VARCHAR2(1024),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE,
                          FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE
);

-- Create Audit Logs Table
CREATE TABLE audit_logs (
                            id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            ticket_id NUMBER NOT NULL,
                            user_id NUMBER NOT NULL,
                            action VARCHAR2(255),
                            timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (ticket_id) REFERENCES tickets(id) ON DELETE CASCADE,
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert Sample Users
INSERT INTO users (username, password, role) VALUES ('employee1', '$2a$10$ppdNXiBHLIl2hYn7ZF6EeO1jkKXfRT88aZwt8MEKtlP6f16wvSpdO', 'EMPLOYEE');
INSERT INTO users (username, password, role) VALUES ('employee2', '$2a$10$ppdNXiBHLIl2hYn7ZF6EeO1jkKXfRT88aZwt8MEKtlP6f16wvSpdO', 'EMPLOYEE');
INSERT INTO users (username, password, role) VALUES ('it_support1', '$2a$10$ppdNXiBHLIl2hYn7ZF6EeO1jkKXfRT88aZwt8MEKtlP6f16wvSpdO', 'IT_SUPPORT');
INSERT INTO users (username, password, role) VALUES ('it_support2', '$2a$10$ppdNXiBHLIl2hYn7ZF6EeO1jkKXfRT88aZwt8MEKtlP6f16wvSpdO', 'IT_SUPPORT');

-- Insert Sample Tickets
INSERT INTO tickets (title, description, priority, category, status, created_by)
VALUES ('Unable to access work server', 'The work server is not responding since yesterday afternoon.', 'HIGH', 'NETWORK', 'NEW', 1);

INSERT INTO tickets (title, description, priority, category, status, created_by)
VALUES ('System failure during startup', 'The system displays an error message on booting.', 'MEDIUM', 'HARDWARE', 'IN_PROGRESS', 2);

INSERT INTO tickets (title, description, priority, category, status, created_by)
VALUES ('Application freezing upon login', 'The application becomes unresponsive after logging in.', 'HIGH', 'SOFTWARE', 'RESOLVED', 1);

INSERT INTO tickets (title, description, priority, category, status, created_by)
VALUES ('Scanner malfunction', 'The scanner is not responding when trying to scan documents.', 'LOW', 'HARDWARE', 'NEW', 2);

-- Insert Sample Comments
INSERT INTO comments (ticket_id, created_by, content) VALUES (1, 3, 'Investigating server connection issues, updates will follow.');
INSERT INTO comments (ticket_id, created_by, content) VALUES (2, 4, 'Running diagnostics to identify the issue.');
INSERT INTO comments (ticket_id, created_by, content) VALUES (3, 3, 'Software update applied and issue resolved.');
INSERT INTO comments (ticket_id, created_by, content) VALUES (4, 4, 'Checked the power cable and reset the device.');

-- Insert Sample Audit Logs
INSERT INTO audit_logs (ticket_id, user_id, action) VALUES (1, 3,  'Ticket created: Unable to access work server');
INSERT INTO audit_logs (ticket_id, user_id, action) VALUES (2, 4, 'Ticket status updated to IN_PROGRESS');
INSERT INTO audit_logs (ticket_id, user_id, action) VALUES (3, 3, 'Ticket status updated to RESOLVED');
INSERT INTO audit_logs (ticket_id, user_id, action) VALUES (4, 4, 'Comment added: Checked the power cable and reset the device');

-- Commit changes
COMMIT;

-- Verify data persistence
SELECT * FROM users;
SELECT * FROM tickets;
SELECT * FROM comments;
SELECT * FROM audit_logs;

