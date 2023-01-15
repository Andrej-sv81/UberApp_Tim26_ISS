INSERT INTO users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, password_date, type) VALUES ('Marko', 'Markovic', '0662322322', 'marko@gmail.com', 'Ulica br 1', 'marko123', 0, 0, NOW()-400, 'PASSENGER');
INSERT INTO users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, password_date, type) VALUES ('Dejan', 'Dejanovic', '0662869522', 'dejan@gmail.com', 'Ulica br 2', 'dejan123', 0, 0, NOW()-400, 'DRIVER');
INSERT INTO users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, password_date, type) VALUES ('Nikola', 'Nikolic', '0662300998', 'nikola@gmail.com', 'Ulica br 3', 'nikola123', 0, 0, NOW()-400, 'PASSENGER');
INSERT INTO users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, password_date, type) VALUES ('Milica', 'Milic', '0662322444', 'milica@gmail.com', 'Ulica br 4', 'milica123', 0, 0, NOW()-400, 'DRIVER');
INSERT INTO users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, password_date, type) VALUES ('Milos', 'Milosevic', '0661111112', 'milos@gmail.com', 'Ulica br 5', 'milos123', 0, 0, NOW()-400, 'PASSENGER');
INSERT INTO users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, password_date, type) VALUES ('Andrej', 'Mitrovic', '0664242322', 'mitrovicandrej5@gmail.com', 'Ulica br 5', 'andrej123', 0, 0, NOW()-400, 'PASSENGER');


INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 1', current_time, 1, 2, 1);
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 2', current_time, 1, 2, 2);
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 3', current_time, 1, 3, 1);
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 4', current_time, 2, 5, 2);
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 5', current_time, 2, 4, 1);
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 6', current_time, 1, 3, 0);
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 7', current_time, 1, 2, 1);


INSERT INTO vehicle_type (price_per_km, name) VALUES  (120, 0);
INSERT INTO vehicle_type (price_per_km, name) VALUES  (500, 1);
INSERT INTO vehicle_type (price_per_km, name) VALUES  (450, 2);


INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id) VALUES (2, 0, 0, 0, 0, 1);
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id) VALUES (2, 0, 0, 0, 0, 1);
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id) VALUES (4, 0, 0, 0, 0, 2);
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id) VALUES (4, 0, 0, 0, 0, 3);


