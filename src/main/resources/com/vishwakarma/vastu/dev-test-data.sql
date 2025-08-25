INSERT INTO City (name) VALUES ('Pune'), ('Mumbai'),
    ('Delhi'), ('Banglore');


INSERT INTO user ( firstName, lastName, password, email) VALUES
('Anil', 'Verma', '21232f297a57a5a743894a0e4a801fc3', 'anil@anil.com'),
('Ketan', 'Pandya', 'ee11cbb19052e40b07aac0ca060c23ee', 'ketan@ketan.com');


INSERT INTO role (role,description) VALUES
('ROLE_ADMIN','Admin Role'),
('ROLE_USER','User Role');


INSERT INTO userrole (roleid,userid) VALUES
(1,1),
(2,1),
(2,2);