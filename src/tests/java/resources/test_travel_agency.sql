DROP DATABASE IF EXISTS test_travel_agency;

CREATE DATABASE IF NOT EXISTS test_travel_agency;

USE test_travel_agency;

CREATE TABLE IF NOT EXISTS countries(
	id INT PRIMARY KEY AUTO_INCREMENT,
    country VARCHAR(256) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS cities(
	id INT PRIMARY KEY AUTO_INCREMENT,
    city VARCHAR(256) NOT NULL,
    country_id INT,
    FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE CASCADE,
	CONSTRAINT city_in_country UNIQUE (city, country_id)
);

CREATE TABLE IF NOT EXISTS hotels(
	id INT PRIMARY KEY AUTO_INCREMENT,
    hotel_name VARCHAR(128),
    street VARCHAR(128),
    street_number VARCHAR(8),
    stars ENUM('_3', '_4', '_5'),
    city_id INT,
    FOREIGN KEY (city_id) REFERENCES cities(id) ON DELETE CASCADE,
    CONSTRAINT hotel_in_city UNIQUE (hotel_name, street, street_number, city_id)
);

CREATE TABLE IF NOT EXISTS rooms(
	id INT PRIMARY KEY AUTO_INCREMENT,
    chamber_number INT UNIQUE,
    luxury ENUM('ECONOM', 'STANDARD', 'BUSINESS', 'PREMIUM'),
    bedrooms ENUM('SINGLE', 'DOUBLE', 'TRIPLE', 'APARTMENT'),
    hotel_id INT,
    city_id INT,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE,
    FOREIGN KEY (city_id) REFERENCES cities(id) ON DELETE CASCADE,
    CONSTRAINT room_in_hotel UNIQUE (chamber_number, hotel_id)
);

CREATE TABLE IF NOT EXISTS users(
	id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(256) NOT NULL UNIQUE,
    phone_number VARCHAR(13) NOT NULL,
    password_hash VARCHAR(256) NOT NULL,
    salt VARCHAR(32) NOT NULL,
    firstname VARCHAR(256) NOT NULL,
    lastname VARCHAR(256) NOT NULL,
    role ENUM('CLIENT', 'ADMIN') NOT NULL,
    birth_date DATE
);

CREATE TABLE IF NOT EXISTS bookings(
	id INT PRIMARY KEY AUTO_INCREMENT,
    usr_id INT,
    order_date DATETIME,
    checkin DATE,
    checkout DATE,
    room_id INT,
    hotel_id INT,
    FOREIGN KEY (usr_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE
);

-- Join table for users and countries
CREATE TABLE IF NOT EXISTS usr_country(
	usr_id INT,
    country_id INT,
    FOREIGN KEY (usr_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS travel_visas(
   id INT PRIMARY KEY AUTO_INCREMENT,
   visa_number VARCHAR(32) NOT NULL,
   issued DATE NOT NULL,
   expiration_date DATE NOT NULL,
   country_id INT NOT NULL,
   usr_id INT NOT NULL,
   FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE CASCADE,
   FOREIGN KEY (usr_id) REFERENCES users(id) ON DELETE CASCADE,
   CONSTRAINT visa_identity UNIQUE (visa_number, country_id)
);
