
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Marko', 'Markovic', '0662322322', 'marko2@gmail.com', 'Ulica br 1', '$2a$10$GK0sh05Gs.Nl.qWxaP3mYuxfzXaQTwT.BGYxZZMYiqD65SpOSOJk.', 0, 1, 'PASSENGER','PASSENGER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Dejan', 'Dejanovic', '0662869522', 'dejan@gmail.com', 'Ulica br 2', '$2a$10$zAHOze8.qNxnCaAlIJQ/ves/Qgpx11j7F84t/eZvxrmTo2pjpm/uG', 0, 1,  'DRIVER','DRIVER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Nikola', 'Nikolic', '0662300998', 'nikola@gmail.com', 'Ulica br 3', '$2a$10$yj./QXTLlvJ4i6HqZ5Bwhu.eI/ZrGbytxZg0xNXEdFjlqTY46KL8G', 0, 1,  'PASSENGER','PASSENGER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Milica', 'Milic', '0662322444', 'milica@gmail.com', 'Ulica br 4', '$2a$10$fG8iTbwDRccA48SzfNPbkO9FWSHpGO2yUuqWO6zPIrgu.CjNW7Qpa', 0, 1,  'DRIVER','DRIVER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Milos', 'Milosevic', '0661111112', 'milos@gmail.com', 'Ulica br 5', '$2a$10$ZFlBV.XZZcvYKCKQZCfbQeSN530b8qH1N2NgRgcdfIIALhemXdt/.', 0, 1,  'PASSENGER','PASSENGER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Andrej', 'Mitrovic', '0664242322', 'mitrovicandrej5@gmail.com', 'Ulica br 5', '$2a$10$KgR4UklTKH7tyTtm6fgNxubqm5EO768lmKhefmtVBOfDxNu4ptMZ.', 0, 1, 'PASSENGER','PASSENGER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Marko', 'Markovic', '0662322322', 'marko@gmail.com', 'Ulica br 2', '$2a$12$JlCBR5MKE.xgfYlBi6TydOnugqGrnoDORhez4hKYmP0e/D1VdRib.', 0, 1, 'PASSENGER','PASSENGER');
insert into users_table (name, surname, telephone_number, email, address, password, blocked_flag, active_flag, role, type) values ('Mirko', 'Markovic', '0662322322', 'mirko@gmail.com', 'Ulica br 2', '$2a$12$ybQvZ0BWSYep7r4nRdURWOjX2wdbm1L.jstpUcclyI.j/SHeOEHYK', 0, 1, 'DRIVER','DRIVER');


INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 1', current_time, 1, 2, 'RIDE');
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 2', current_time, 1, 2, 'RIDE');
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 3', current_time, 1, 3, 'RIDE');
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 4', current_time, 2, 5, 'RIDE');
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 5', current_time, 2, 4, 'RIDE');
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 6', current_time, 1, 3, 'RIDE');
INSERT INTO  message  (message, time_sent, sender_user_id, receiver_user_id, message_type)VALUES  ('Test Poruka 7', current_time, 1, 2, 'RIDE');


INSERT INTO vehicle_type (price_per_km, name) VALUES  (96, 'STANDARD');
INSERT INTO vehicle_type (price_per_km, name) VALUES  (200, 'LUXURIOUS');
INSERT INTO vehicle_type (price_per_km, name) VALUES  (120, 'VAN');


INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (2, 0, 0, 0, 'STARTED', 1, 250, '2022-1-20 12:23:20', '2022-1-20 12:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (2, 0, 0, 0, 'ACCEPTED', 1, 250, '2022-2-20 12:23:20', '2022-2-20 12:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (2, 0, 0, 0, 'ACCEPTED', 1, 350, '2022-12-20 12:23:20', '2022-12-20 12:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (2, 0, 0, 0, 'ACCEPTED', 1, 550, '2022-11-20 12:23:20', '2022-11-20 12:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (2, 0, 0, 0, 'ACCEPTED', 1, 150, '2022-10-20 12:23:20', '2022-10-20 12:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (2, 0, 0, 0, 'ACCEPTED', 1, 50, '2022-5-20 12:23:20', '2022-5-20 12:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (2, 0, 0, 0, 'ACCEPTED', 1, 650, '2022-5-20 13:23:20', '2022-5-20 13:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (2, 0, 0, 0, 'ACCEPTED', 1, 2150, '2022-6-20 12:23:20', '2022-6-20 12:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (2, 0, 0, 0, 'ACCEPTED', 1, 1250, '2022-7-20 12:23:20', '2022-7-20 12:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (2, 0, 0, 0, 'ACCEPTED', 1, 230, '2022-8-20 12:23:20', '2022-8-20 12:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (2, 0, 0, 0, 'ACCEPTED', 1, 110, '2022-9-20 12:23:20', '2022-9-20 12:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (4, 0, 0, 0, 'ACCEPTED', 1, 220, '2022-12-20 12:23:20', '2022-12-20 12:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (4, 0, 0, 0, 'ACCEPTED', 1, 100, '2022-12-20 12:23:20', '2022-12-20 12:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time, end_time) VALUES (4, 0, 0, 0, 'ACCEPTED', 1, 350, '2022-12-20 12:23:20', '2022-12-20 12:35:23');
INSERT INTO ride  (driver_id, panic_flag, baby_flag, pet_flag, ride_state, vehicle_type_id, total_cost, start_time) VALUES (4, 0, 0, 0, 'REJECTED', 1, 20, '2022-12-20 12:23:20');


INSERT INTO  rejection_message  (rejection_reason, ride_ride_id, user_user_id, time_of_rejection) VALUES  ('Izgubljen taksi', 15, 6, '2022-12-20 12:23:20');

UPDATE ride SET rejection_message_rmessage_id = 1 WHERE ride_id = 15;

INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (1,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (2,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (3,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (4,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (5,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (6,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (7,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (8,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (9,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (10,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (11,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (12,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (13,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (14,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (15,6);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (1,1);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (2,1);
INSERT INTO  passenger_rides  (ride_id, user_id) VALUES (3,1);


INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (1,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (2,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (3,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (4,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (5,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (6,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (7,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (8,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (9,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (10,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (11,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (12,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (13,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (14,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (15,6);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (1,1);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (2,1);
INSERT INTO  ride_passengers  (ride_ride_id, passengers_user_id) VALUES (3,1);



INSERT INTO  location  (address, latitude, longitude) VALUES  ('Bulevar Oslobođenja 2', 45.263892, 19.830219);
INSERT INTO  location  (address, latitude, longitude) VALUES  ('Kisačka 66', 45.265725, 19.835957);
INSERT INTO  location  (address, latitude, longitude) VALUES  ('Pozorišni Trg', 45.254368, 19.842607);
INSERT INTO  location  (address, latitude, longitude) VALUES  ('Arse Teodorovića 16', 45.256421, 19.839991);
INSERT INTO  location  (address, latitude, longitude) VALUES  ('Bulevar Kralja Petra I 50', 45.259223, 19.825414);
INSERT INTO  location  (address, latitude, longitude) VALUES  ('Rumenačka 49', 45.263640, 19.817468);



INSERT INTO  route  (distance_in_km, start_location_location_id, destination_location_id) VALUES  (1.5, 1, 2);
INSERT INTO  route  (distance_in_km, start_location_location_id, destination_location_id) VALUES  (1.7, 1, 3);
INSERT INTO  route  (distance_in_km, start_location_location_id, destination_location_id) VALUES  (1.4, 1, 4);
INSERT INTO  route  (distance_in_km, start_location_location_id, destination_location_id) VALUES  (1.0, 1, 5);
INSERT INTO  route  (distance_in_km, start_location_location_id, destination_location_id) VALUES  (1.1, 1, 6);
INSERT INTO  route  (distance_in_km, start_location_location_id, destination_location_id) VALUES  (1.7, 3, 1);
INSERT INTO  route  (distance_in_km, start_location_location_id, destination_location_id) VALUES  (1.6, 3, 2);
INSERT INTO  route  (distance_in_km, start_location_location_id, destination_location_id) VALUES  (1.4, 4, 1);
INSERT INTO  route  (distance_in_km, start_location_location_id, destination_location_id) VALUES  (1.3, 4, 2);


INSERT INTO ride_routes (ride_ride_id, routes_route_id) VALUES (1,1);


INSERT INTO  vehicle  (vehicle_model, registration_plates, number_of_seats, baby_flag, pet_flag, driver_user_id, vehicle_type_id, location_location_id) VALUES  ('Skoda Octavia', 'NS123AA', 4, 1, 0, 2, 1, 1);


UPDATE  users_table SET  vehicle_id = 1 WHERE user_id = 2;


INSERT INTO  document  (name, picture,driver_id) VALUES  ('vozacka', '19.830219',8);
INSERT INTO  document  (name, picture,driver_id) VALUES  ('kristijan', '19.830219',8);
INSERT INTO  document  (name, picture,driver_id) VALUES  ('licna', '19.830219',1);


INSERT INTO favorite_ride (baby_flag, name, pet_flag, vehicle_type_id) VALUES (1, 'Home To Work', 1, 1);
INSERT INTO favorite_ride (baby_flag, name, pet_flag, vehicle_type_id) VALUES (1, 'Home To Jenna', 1, 1);
INSERT INTO favorite_ride (baby_flag, name, pet_flag, vehicle_type_id) VALUES (1, 'Home To School', 1, 1);
INSERT INTO favorite_ride (baby_flag, name, pet_flag, vehicle_type_id) VALUES (0, 'Home To Mike', 0, 1);
INSERT INTO favorite_ride (baby_flag, name, pet_flag, vehicle_type_id) VALUES (1, 'Work To Gym', 0, 1);
INSERT INTO favorite_ride (baby_flag, name, pet_flag, vehicle_type_id) VALUES (0, 'Gym To Home', 1, 1);
INSERT INTO favorite_ride (baby_flag, name, pet_flag, vehicle_type_id) VALUES (1, 'Home To Grandma', 0, 1);
INSERT INTO favorite_ride (baby_flag, name, pet_flag, vehicle_type_id) VALUES (1, 'Home To Shop', 1, 1);
INSERT INTO favorite_ride (baby_flag, name, pet_flag, vehicle_type_id) VALUES (1, 'Home To Dentist', 1, 1);

INSERT INTO favorite_ride_passengers (favorite_ride_fav_ride_id, passengers_user_id) VALUES (1, 6);
INSERT INTO favorite_ride_passengers (favorite_ride_fav_ride_id, passengers_user_id) VALUES (2, 6);
INSERT INTO favorite_ride_passengers (favorite_ride_fav_ride_id, passengers_user_id) VALUES (3, 6);
INSERT INTO favorite_ride_passengers (favorite_ride_fav_ride_id, passengers_user_id) VALUES (4, 6);
INSERT INTO favorite_ride_passengers (favorite_ride_fav_ride_id, passengers_user_id) VALUES (5, 6);
INSERT INTO favorite_ride_passengers (favorite_ride_fav_ride_id, passengers_user_id) VALUES (6, 6);
INSERT INTO favorite_ride_passengers (favorite_ride_fav_ride_id, passengers_user_id) VALUES (7, 6);
INSERT INTO favorite_ride_passengers (favorite_ride_fav_ride_id, passengers_user_id) VALUES (8, 6);
INSERT INTO favorite_ride_passengers (favorite_ride_fav_ride_id, passengers_user_id) VALUES (9, 6);

INSERT INTO favorite_ride_routes (favorite_ride_fav_ride_id, routes_route_id) VALUES (1,1);
INSERT INTO favorite_ride_routes (favorite_ride_fav_ride_id, routes_route_id) VALUES (2,2);
INSERT INTO favorite_ride_routes (favorite_ride_fav_ride_id, routes_route_id) VALUES (3,3);
INSERT INTO favorite_ride_routes (favorite_ride_fav_ride_id, routes_route_id) VALUES (4,4);
INSERT INTO favorite_ride_routes (favorite_ride_fav_ride_id, routes_route_id) VALUES (5,6);
INSERT INTO favorite_ride_routes (favorite_ride_fav_ride_id, routes_route_id) VALUES (6,7);
INSERT INTO favorite_ride_routes (favorite_ride_fav_ride_id, routes_route_id) VALUES (7,9);
INSERT INTO favorite_ride_routes (favorite_ride_fav_ride_id, routes_route_id) VALUES (8,8);
INSERT INTO favorite_ride_routes (favorite_ride_fav_ride_id, routes_route_id) VALUES (9,5);