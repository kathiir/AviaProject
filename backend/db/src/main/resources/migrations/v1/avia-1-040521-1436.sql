
create TABLE city
(
 city_id SERIAL PRIMARY KEY,
 place_id VARCHAR(200),
 place_name VARCHAR(200),
 city_code  VARCHAR(200),
 country_name VARCHAR(200),
 UNIQUE (place_id, place_name,city_id , country_name)


);
create TABLE recent_city
(
 id SERIAL PRIMARY KEY,
 city_id INTEGER,
 user_id VARCHAR(200),
CONSTRAINT recent_city_city_id FOREIGN KEY (city_id) REFERENCES city (city_id)

);
create TABLE  flights(
flight_id SERIAL PRIMARY KEY,
origin_place INTEGER,
destination_place INTEGER,
outbound_date DATE,
inbound_date DATE,
cost FLOAT,
CONSTRAINT flights_origin_place FOREIGN KEY (origin_place) REFERENCES city (city_id) ON delete RESTRICT,
CONSTRAINT flights_destination_place FOREIGN KEY (destination_place) REFERENCES city (city_id) ON delete RESTRICT
);

create TABLE recent_flights
(
recent_flights_id SERIAL PRIMARY KEY,
flight_id INTEGER,
user_id VARCHAR(200),
UNIQUE (flight_id, user_id),
CONSTRAINT recent_flights_flight_id FOREIGN KEY (flight_id) REFERENCES flights (flight_id)
);
create TABLE favorite_flights
(
favorite_flights_id SERIAL   PRIMARY KEY,
flight_id INTEGER,
user_id VARCHAR(200),
UNIQUE (flight_id, user_id),
CONSTRAINT favorite_flight_flight_id FOREIGN KEY (flight_id) REFERENCES flights (flight_id) ON delete RESTRICT
);
create TABLE user_purchase
(
user_purchase_id SERIAL PRIMARY KEY,
flight_id INTEGER,
user_id VARCHAR(200),
count_passenger INTEGER,
flight_cost INTEGER,
CONSTRAINT user_purchase_flight_id FOREIGN KEY (flight_id) REFERENCES flights (flight_id)
);


