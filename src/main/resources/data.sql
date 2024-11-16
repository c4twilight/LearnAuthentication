-- Insert default roles into the ROLES table
INSERT INTO ROLES (id,name) VALUES (1,'ADMIN');

-- Insert default users into the USERS table
INSERT INTO USERS (ID, username, password) VALUES (786, 'talib123', '$2a$12$BFgMj2qaHvdh7wmF54EUi.9ovNe3XI.Mk8caZiNs29OOylaunV9sW');
INSERT INTO USERS (ID, username, password) VALUES (2, 'john_doe', '$2a$12$abcdefabcdefabcdefabcdefabcdefabcdefabcdefabcdefabcdef');
INSERT INTO USERS_ROLES (ROLES_ID , USER_INFO_ID) VALUES (1, 786);

