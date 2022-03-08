CREATE SCHEMA foundations;

CREATE TABLE ERS_USER_ROLES(ROLE_ID VARCHAR PRIMARY KEY, ROLE VARCHAR UNIQUE);

CREATE TABLE ERS_USERS(USER_ID VARCHAR PRIMARY KEY, 
					   USERNAME VARCHAR UNIQUE NOT NULL, 
					   EMAIL VARCHAR UNIQUE NOT NULL, 
					   PASSWORD VARCHAR NOT NULL, 
					   GIVEN_NAME VARCHAR NOT NULL, 
					   SURNAME VARCHAR NOT NULL, 
					   IS_ACTIVE BOOLEAN, 
					   ROLE_ID VARCHAR, 
					   FOREIGN KEY (ROLE_ID) REFERENCES ERS_USER_ROLES (ROLE_ID) ON DELETE CASCADE);

CREATE TABLE ers_reimbursement_types(type_id VARCHAR PRIMARY KEY, type VARCHAR UNIQUE);

CREATE TABLE ers_reimbursement_statuses(status_id VARCHAR PRIMARY KEY, status VARCHAR UNIQUE);

CREATE TABLE ers_reimbursements(reimb_id VARCHAR PRIMARY KEY, 
								amount NUMERIC(6, 2) NOT NULL, 
								submitted TIMESTAMP NOT NULL, 
								resolved TIMESTAMP, 
								description VARCHAR NOT NULL, 
								receipt BYTEA, 
								payment_id VARCHAR, 
								author_id VARCHAR NOT NULL, 
								resolver_id VARCHAR, 
								status_id VARCHAR, 
								type_id VARCHAR, 
								FOREIGN KEY (author_id) REFERENCES ers_users (user_id) ON DELETE CASCADE, 
								FOREIGN KEY (resolver_id) REFERENCES ers_users (user_id) ON DELETE CASCADE, 
								FOREIGN KEY (status_id) REFERENCES ers_reimbursement_statuses (status_id) ON DELETE CASCADE, 
								FOREIGN KEY (type_id) REFERENCES ers_reimbursement_types (type_id) ON DELETE CASCADE);

