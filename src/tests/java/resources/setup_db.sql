USE test_travel_agency;

-- Countries
INSERT INTO countries (id, country) VALUES (NULL, 'Ukraine');
INSERT INTO countries (id, country) VALUES (NULL, 'Italy');
INSERT INTO countries (id, country) VALUES (NULL, 'Spain');
INSERT INTO countries (id, country) VALUES (NULL, 'USA');

-- Cities
INSERT INTO cities (id, city, country_id) VALUES (NULL, 'Lviv', 1);
INSERT INTO cities (id, city, country_id) VALUES (NULL, 'Kyiv', 1);
INSERT INTO cities (id, city, country_id) VALUES (NULL, 'Bologna', 2);
INSERT INTO cities (id, city, country_id) VALUES (NULL, 'Milano', 2);
INSERT INTO cities (id, city, country_id) VALUES (NULL, 'Barcelona', 3);
INSERT INTO cities (id, city, country_id) VALUES (NULL, 'Las Vegas', 4);

-- Hotels
-- Lviv's hotels
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'Lviv', 'Chornovola Ave', 6, '_3', 1);
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'Grand Hotel', 'Svobody Ave', '13', '_5', 1);
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'Panorama', 'Svobody Ave', '45', '_4', 1);
-- Kiev's hotels
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'ALFAVITO', 'Predslavynska St', '35D', '_4', 2);
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'Ibis', 'Taras Shevchenko boulevardt', '25', '_3', 2);
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'Myr', 'Holosiivskyi St', '70', '_3', 2);
-- Bologna's hotels
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'Ramada Encore', 'Via Ferrarese', '164', '_4', 3);
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'Cosmopolitan', 'Via del Commercio Associato', '9', '_4', 3);
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'Maggiore', 'Via Emilia Ponente', '62/3', '_3', 3);
-- Milano's hotels
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'MEININGER', 'Piazza Monte Titano', '10', '_4', 4);
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'San Francisco', 'Viale Lombardia', '55', '_3', 4);
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'San Siro', 'Via Achille', '10', '_3', 4);
-- Barcelona's hotels
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'Fabrizzio\'s Petit', 'Carrer del Bruc', '65', '_3', 5);
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'Alimara', 'Carrer de Berruguete', '126', '_4', 5);
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'Barcelona Century', 'Carrer de Valencia', '154', '_3', 5);
-- Las Vegas
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'MGM Grand', 'Las Vegas Blvd', '3799 S', '_4', 6);
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'Westgate Las Vegas Resort & Casino', ' Paradise Rd', '3000', '_4', 6);
INSERT INTO hotels (id, hotel_name, street, street_number, stars, city_id) 
	VALUES (NULL, 'Treasure Island', 'Las Vegas Blvd', '3300 S', '_4', 6);




-- Rooms in Lviv 
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 301, 'ECONOM', 'SINGLE', 1, 1);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 302, 'ECONOM', 'APARTMENT', 1, 1);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 405, 'STANDARD', 'DOUBLE', 1, 1);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 250, 'BUSINESS', 'SINGLE', 2, 1);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 260, 'STANDARD', 'SINGLE', 2, 1);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 270, 'STANDARD', 'APARTMENT', 2, 1);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 12, 'ECONOM', 'TRIPLE', 3, 1);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 11, 'ECONOM', 'SINGLE', 3, 1);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 16, 'ECONOM', 'TRIPLE', 3, 1);

-- Rooms in Kiev
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 234, 'PREMIUM', 'SINGLE', 1, 2);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 232, 'STANDARD', 'DOUBLE', 1, 2);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 123, 'STANDARD', 'APARTMENT', 1, 2);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 32, 'ECONOM', 'APARTMENT', 2, 2);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 43, 'PREMIUM', 'TRIPLE', 2, 2);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 23, 'STANDARD', 'TRIPLE', 2, 2);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 3623, 'ECONOM', 'TRIPLE', 3, 2);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 3456, 'STANDARD', 'DOUBLE', 3, 2);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 1235, 'ECONOM', 'TRIPLE', 3, 2);
    
-- Rooms in Bologna
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 322, 'BUSINESS', 'SINGLE', 1, 3);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 331, 'BUSINESS', 'DOUBLE', 1, 3);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 455, 'STANDARD', 'SINGLE', 1, 3);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 324, 'BUSINESS', 'SINGLE', 2, 3);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 143, 'STANDARD', 'SINGLE', 2, 3);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 423, 'BUSINESS', 'APARTMENT', 2, 3);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 432, 'ECONOM', 'APARTMENT', 3, 3);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 19, 'BUSINESS', 'SINGLE', 3, 3);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 8, 'ECONOM', 'TRIPLE', 3, 3);
    
-- Rooms in Milano
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 103, 'ECONOM', 'SINGLE', 1, 4);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 102, 'PREMIUM', 'DOUBLE', 1, 4);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 104, 'STANDARD', 'DOUBLE', 1, 4);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 257, 'BUSINESS', 'APARTMENT', 2, 4);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 268, 'STANDARD', 'SINGLE', 2, 4);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 272, 'STANDARD', 'SINGLE', 2, 4);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 112, 'ECONOM', 'TRIPLE', 3, 4);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 129, 'ECONOM', 'SINGLE', 3, 4);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 113, 'ECONOM', 'TRIPLE', 3, 4);
    
-- Rooms in Barcelona
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 111, 'STANDARD', 'SINGLE', 1, 5);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 55, 'ECONOM', 'DOUBLE', 1, 5);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 48, 'PREMIUM', 'DOUBLE', 1, 5);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 987, 'BUSINESS', 'APARTMENT', 2, 5);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 986, 'STANDARD', 'SINGLE', 2, 5);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 985, 'PREMIUM', 'DOUBLE', 2, 5);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 768, 'STANDARD', 'TRIPLE', 3, 5);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 675, 'PREMIUM', 'SINGLE', 3, 5);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 665, 'ECONOM', 'DOUBLE', 3, 5);
    
-- Rooms in Las Vegas
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 1234, 'ECONOM', 'APARTMENT', 1, 6);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 2134, 'PREMIUM', 'DOUBLE', 1, 6);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 1212, 'STANDARD', 'APARTMENT', 1, 6);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 5335, 'PREMIUM', 'SINGLE', 2, 6);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 3498, 'PREMIUM', 'SINGLE', 2, 6);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 3423, 'STANDARD', 'SINGLE', 2, 6);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 5654, 'PREMIUM', 'APARTMENT', 3, 6);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 7654, 'ECONOM', 'SINGLE', 3, 6);
INSERT INTO rooms (id, chamber_number, luxury, bedrooms, hotel_id, city_id)
	VALUES (NULL, 5332, 'PREMIUM', 'TRIPLE', 3, 6);

    
-- Users
INSERT INTO users (id, email, phone_number, password_hash, salt, firstname, lastname, role, birth_date)
	VALUES (NULL, 'admin@gmail.com', '+38097222334', 'hash', 'salt', 'Admin', 'Admin', 'ADMIN', NULL);
INSERT INTO users (id, email, phone_number, password_hash, salt, firstname, lastname, role, birth_date)
	VALUES (NULL, 'user@gmail.com', '+38097222334', 'hash', 'salt', 'Max', 'Pain', 'CLIENT', NULL);
INSERT INTO users (id, email, phone_number, password_hash, salt, firstname, lastname, role, birth_date)
	VALUES (NULL, 'igor@gmail.com', '+38096542334', 'hash', 'salt', 'Igor', 'Corvus', 'CLIENT', '1990-12-12');
INSERT INTO users (id, email, phone_number, password_hash, salt, firstname, lastname, role, birth_date)
	VALUES (NULL, 'neo@gmail.com', '+38096542123', 'hash', 'salt', 'Mr', 'Anderson', 'CLIENT', '1986-01-05');
INSERT INTO users (id, email, phone_number, password_hash, salt, firstname, lastname, role, birth_date)
	VALUES (NULL, 'house@gmail.com', '+380123222334', 'hash', 'salt', 'Dr', 'House', 'CLIENT', '1996-03-04');
    
-- Travel visas
INSERT INTO travel_visas (id, visa_number, issued, expiration_date, country_id, usr_id) 
	VALUES (NULL, '0123456789', '2015.03.10', '2015.06.10', 2, 2);
INSERT INTO travel_visas (id, visa_number, issued, expiration_date, country_id, usr_id) 
	VALUES (NULL, '675654567', '2018.03.09', '2018.07.10', 2, 2);
INSERT INTO travel_visas (id, visa_number, issued, expiration_date, country_id, usr_id) 
	VALUES (NULL, '09878768', '2019.05.05', '2019.07.07', 2, 3);
INSERT INTO travel_visas (id, visa_number, issued, expiration_date, country_id, usr_id) 
	VALUES (NULL, '345665659', '2020.01.01', '2020.03.03', 3, 3);
INSERT INTO travel_visas (id, visa_number, issued, expiration_date, country_id, usr_id) 
	VALUES (NULL, '09878768', '2009.07.06', '2019.08.07', 4, 4);
INSERT INTO travel_visas (id, visa_number, issued, expiration_date, country_id, usr_id) 
	VALUES (NULL, '234536234', '2010.10.10', '2010.12.12', 3, 4);
    
-- Bookings
INSERT INTO bookings (id, usr_id, order_date, checkin, checkout, room_id, hotel_id)
	VALUES (NULL, 2, '2015.03.10', '2015.06.10', '2015.06.10', 1, 1);
INSERT INTO bookings (id, usr_id, order_date, checkin, checkout, room_id, hotel_id)
	VALUES (NULL, 2, '2019.10.09', '2019.11.11', '2019.12.11', 1, 1);
INSERT INTO bookings (id, usr_id, order_date, checkin, checkout, room_id, hotel_id)
    VALUES (NULL, 2, '2019.10.09', '2021.07.07', '2021.08.08', 1, 1);
INSERT INTO bookings (id, usr_id, order_date, checkin, checkout, room_id, hotel_id)
	VALUES (NULL, 2, '2019.10.09', '2020.01.01', '2020.02.02', 2, 5);
INSERT INTO bookings (id, usr_id, order_date, checkin, checkout, room_id, hotel_id)
	VALUES (NULL, 3, '2019.10.14', '2020.03.03', '2020.03.04', 3, 6);
INSERT INTO bookings (id, usr_id, order_date, checkin, checkout, room_id, hotel_id)
	VALUES (NULL, 4, '2019.10.05', '2020.01.01', '2020.02.02', 1, 7);
    
-- usr_country
INSERT INTO usr_country (usr_id, country_id) VALUES (2, 2);
INSERT INTO usr_country (usr_id, country_id) VALUES (2, 2);
INSERT INTO usr_country (usr_id, country_id) VALUES (3, 2);
INSERT INTO usr_country (usr_id, country_id) VALUES (3, 3);
INSERT INTO usr_country (usr_id, country_id) VALUES (4, 4);
INSERT INTO usr_country (usr_id, country_id) VALUES (4, 4);
