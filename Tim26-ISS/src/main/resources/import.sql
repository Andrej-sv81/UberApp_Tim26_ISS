
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Marko', 'Markovic', '0662322322', 'marko2@gmail.com', 'Ulica br 1', '$2a$10$GK0sh05Gs.Nl.qWxaP3mYuxfzXaQTwT.BGYxZZMYiqD65SpOSOJk.', 0, 0, 'PASSENGER','PASSENGER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Dejan', 'Dejanovic', '0662869522', 'dejan@gmail.com', 'Ulica br 2', '$2a$10$zAHOze8.qNxnCaAlIJQ/ves/Qgpx11j7F84t/eZvxrmTo2pjpm/uG', 0, 0,  'DRIVER','DRIVER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Nikola', 'Nikolic', '0662300998', 'nikola@gmail.com', 'Ulica br 3', '$2a$10$yj./QXTLlvJ4i6HqZ5Bwhu.eI/ZrGbytxZg0xNXEdFjlqTY46KL8G', 0, 0,  'PASSENGER','PASSENGER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Milica', 'Milic', '0662322444', 'milica@gmail.com', 'Ulica br 4', '$2a$10$fG8iTbwDRccA48SzfNPbkO9FWSHpGO2yUuqWO6zPIrgu.CjNW7Qpa', 0, 0,  'DRIVER','DRIVER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Milos', 'Milosevic', '0661111112', 'milos@gmail.com', 'Ulica br 5', '$2a$10$ZFlBV.XZZcvYKCKQZCfbQeSN530b8qH1N2NgRgcdfIIALhemXdt/.', 0, 0,  'PASSENGER','PASSENGER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Andrej', 'Mitrovic', '0664242322', 'mitrovicandrej5@gmail.com', 'Ulica br 5', '$2a$10$KgR4UklTKH7tyTtm6fgNxubqm5EO768lmKhefmtVBOfDxNu4ptMZ.', 0, 0, 'PASSENGER','PASSENGER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Marko', 'Markovic', '0662322322', 'marko@gmail.com', 'Ulica br 2', '$2a$12$JlCBR5MKE.xgfYlBi6TydOnugqGrnoDORhez4hKYmP0e/D1VdRib.', 0, 0, 'PASSENGER','PASSENGER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Mirko', 'Markovic', '0662322322', 'mirko@gmail.com', 'Ulica br 2', '$2a$12$ybQvZ0BWSYep7r4nRdURWOjX2wdbm1L.jstpUcclyI.j/SHeOEHYK', 0, 0, 'DRIVER','DRIVER');


INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 1', current_time, 1, 2, 1);
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 2', current_time, 1, 2, 2);
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 3', current_time, 1, 3, 1);
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 4', current_time, 2, 5, 2);
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 5', current_time, 2, 4, 1);
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 6', current_time, 1, 3, 0);
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 7', current_time, 1, 2, 1);


INSERT INTO vehicle_type (price_per_km, name) VALUES  (96, 0);
INSERT INTO vehicle_type (price_per_km, name) VALUES  (200, 1);
INSERT INTO vehicle_type (price_per_km, name) VALUES  (120, 2);


INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id) VALUES (2, 0, 0, 0, 0, 1);
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id) VALUES (2, 0, 0, 0, 0, 1);
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id) VALUES (4, 0, 0, 0, 0, 2);
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id) VALUES (4, 0, 0, 0, 0, 3);


INSERT INTO  location  (address, latitude, longitude) VALUES  ('Bulevar oslobođenja 2', 45.263892, 19.830219);


INSERT INTO  vehicle  (vehicle_model, registration_plates, number_of_seats, baby_flag, pet_flag, driver_user_id, vehicle_type_id, location_location_id) VALUES  ('Skoda Octavia', 'NS123AA', 4, 1, 0, 2, 1, 1);

UPDATE  users_table SET  vehicle_id = 1;

