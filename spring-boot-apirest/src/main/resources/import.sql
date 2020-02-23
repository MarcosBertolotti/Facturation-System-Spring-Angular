/* Populate tables */

insert into regions (name) values ('Sudamérica');
insert into regions (name) values ('Centroamérica');
insert into regions (name) values ('Norteamérica');
insert into regions (name) values ('Europa');
insert into regions (name) values ('Asia');
insert into regions (name) values ('Africa');
insert into regions (name) values ('Oceanía');
insert into regions (name) values ('Antártida');

INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(1, 'Marcos', 'Bertolotti', 'marcos@mail.com', '2020-08-01', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(2, 'John', 'Doe', 'john.doe@gmail.com', '2020-08-02', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(4, 'Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2020-08-03', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(4, 'Jane', 'Doe', 'jane.doe@gmail.com', '2020-08-04', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(4, 'Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2020-08-05', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(3, 'Erich', 'Gamma', 'erich.gamma@gmail.com', '2020-08-06', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(4, 'Richard', 'Helm', 'richard.helm@gmail.com', '2020-08-07', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(4, 'Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2020-08-08', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(4, 'John', 'Vlissides', 'john.vlissides@gmail.com', '2020-08-09', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(4, 'James', 'Gosling', 'james.gosling@gmail.com', '2020-08-010', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(4, 'Bruce', 'Lee', 'bruce.lee@gmail.com', '2020-08-11', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(5, 'Johnny', 'Doe', 'johnny.doe@gmail.com', '2020-08-12', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(5, 'John', 'Roe', 'john.roe@gmail.com', '2020-08-13', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(5, 'Jane', 'Roe', 'jane.roe@gmail.com', '2020-08-14', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(5, 'Richard', 'Doe', 'richard.doe@gmail.com', '2020-08-15', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(5, 'Janie', 'Doe', 'janie.doe@gmail.com', '2020-08-16', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(6, 'Phillip', 'Webb', 'phillip.webb@gmail.com', '2020-08-17', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(6, 'Stephane', 'Nicoll', 'stephane.nicoll@gmail.com', '2020-08-18', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(6, 'Sam', 'Brannen', 'sam.brannen@gmail.com', '2020-08-19', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(7, 'Juergen', 'Hoeller', 'juergen.Hoeller@gmail.com', '2020-08-20', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(2, 'Janie', 'Roe', 'janie.roe@gmail.com', '2020-08-21', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(2, 'John', 'Smith', 'john.smith@gmail.com', '2020-08-22', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(2, 'Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2020-08-23', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(2, 'John', 'Stiles', 'john.stiles@gmail.com', '2020-08-24', '');
INSERT INTO clients (region_id, first_name, last_name, email, create_at, photo) VALUES(3, 'Richard', 'Roe', 'stiles.roe@gmail.com', '2020-08-25', '');

insert into users (username, password, enabled, first_name, last_name, email) values ('marcos', '$2a$10$8.1oYP9Wem46F5.X26BrZ.54s7hmxrsOyeZalfpNqCcIngqNjW.GC', 1, "Marcos", "Bertolotti", "marcos@gmail.com");
insert into users (username, password, enabled, first_name, last_name, email) values ('admin', '$2a$10$pwmhQCyn/Fu07djaBCfA3Ot7TYkdhk6zdXo3QpyQ3jHTpHCIQjl1G', 1, "Jorge", "Ramirez", "jorgelin@gmail.com");

insert into roles (name) values ('ROLE_USER');
insert into roles (name) values ('ROLE_ADMIN');

insert into users_roles (user_id, role_id) values (1, 1);
insert into users_roles (user_id, role_id) values (2, 2);
insert into users_roles (user_id, role_id) values (2, 1);
