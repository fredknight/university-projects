DROP DATABASE IF EXISTS ce29x_fk18726;
CREATE DATABASE ce29x_fk18726;
USE ce29x_fk18726;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
	id int(8) NOT NULL AUTO_INCREMENT,
	name varchar(32) NOT NULL,
	postcode varchar(8),
	PRIMARY KEY(id)
);


DROP TABLE IF EXISTS manufacturer;
CREATE TABLE manufacturer (
	id int(8) NOT NULL AUTO_INCREMENT,
	name varchar(32) NOT NULL,
	country varchar(32) NOT NULL,
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS car;
CREATE TABLE car (
	id int(8) NOT NULL AUTO_INCREMENT,
	model varchar(16),
	bodyType varchar(16),
	buildYear int(4),
	retailPrice int(8),
	manufacturerID int(8),
	PRIMARY KEY(id),
	FOREIGN KEY(manufacturerID) REFERENCES manufacturer(id)
);

/*
	"carOwnership" table is present to reduce data redundancy
*/

DROP TABLE IF EXISTS carOwnership;
CREATE TABLE carOwnership (
	id int(8) NOT NULL AUTO_INCREMENT,
	numberPlate varchar(8) NOT NULL,
	userID int(8),
	carID int(8),
	colour varchar(16),
	purchasePrice int(8),
	PRIMARY KEY(id),
	FOREIGN KEY(carID) REFERENCES car(id),
	FOREIGN KEY(userID) REFERENCES users(id)
);

INSERT INTO users VALUES(DEFAULT, "Douglas Valdez", "NE35 9JG");
INSERT INTO users VALUES(DEFAULT, "August Hobbs", "BR2 8QF");
INSERT INTO users VALUES(DEFAULT, "Marlene Foley", "EX5 3AX");
INSERT INTO users VALUES(DEFAULT, "Haydon Metcalfe", "GU46 6FQ");
INSERT INTO users VALUES(DEFAULT, "Karla Meyer", "S5 7TG");
INSERT INTO users VALUES(DEFAULT, "Helin Powers", "MK16 0LD");
INSERT INTO users VALUES(DEFAULT, "Jamila Greenaway", "B94 6RH");
INSERT INTO users VALUES(DEFAULT, "Jez Miller", "DY6 9HP");
INSERT INTO users VALUES(DEFAULT, "Jeffery Cameron", "NN12 8DH");
INSERT INTO users VALUES(DEFAULT, "Fatima Major", "BB8 0HX");
INSERT INTO users VALUES(DEFAULT, "Axel James", "WR2 6HL");
INSERT INTO users VALUES(DEFAULT, "Maisha Harvey", "NR15 2QJ");

INSERT INTO manufacturer VALUES(DEFAULT, "Audi", "Germany");
INSERT INTO manufacturer VALUES(DEFAULT, "BMW", "Germany");
INSERT INTO manufacturer VALUES(DEFAULT, "Bentley", "England");
INSERT INTO manufacturer VALUES(DEFAULT, "Cadillac", "United States");
INSERT INTO manufacturer VALUES(DEFAULT, "Chevrolet", "South Korea");
INSERT INTO manufacturer VALUES(DEFAULT, "Fiat", "Italy");
INSERT INTO manufacturer VALUES(DEFAULT, "Ford", "United States");
INSERT INTO manufacturer VALUES(DEFAULT, "Honda", "Japan");
INSERT INTO manufacturer VALUES(DEFAULT, "Suzuki", "Japan");
INSERT INTO manufacturer VALUES(DEFAULT, "Seat", "Spain");
INSERT INTO manufacturer VALUES(DEFAULT, "Skoda", "Czechia");
INSERT INTO manufacturer VALUES(DEFAULT, "Aston Martin", "England");

INSERT INTO car VALUES(DEFAULT, "A-100", "Sedan", 1990, 6600, 1);
INSERT INTO car VALUES(DEFAULT, "B-200", "Hatchback", 1990, 3000, 3);
INSERT INTO car VALUES(DEFAULT, "C-300", "Coupe", 2005, 7800, 3);
INSERT INTO car VALUES(DEFAULT, "D-400", "Sport Utility", 2005, 6600, 4);
INSERT INTO car VALUES(DEFAULT, "E-500", "Convertible", 1990, 3000, 7);
INSERT INTO car VALUES(DEFAULT, "F-600", "Stalion Wagon", 2012, 7800, 7);
INSERT INTO car VALUES(DEFAULT, "G-700", "Hatchback", 2012, 6600, 2);
INSERT INTO car VALUES(DEFAULT, "H-800", "Sedan", 1990, 3000, 4);
INSERT INTO car VALUES(DEFAULT, "I-900", "Saloon", 2019, 7800, 5);
INSERT INTO car VALUES(DEFAULT, "J-100", "Saloon", 2012, 6600, 6);
INSERT INTO car VALUES(DEFAULT, "K-200", "Convertible", 1990, 3000, 11);
INSERT INTO car VALUES(DEFAULT, "L-300", "Sedan", 2005, 7800, 10);


INSERT INTO carOwnership VALUES(DEFAULT, "AB12 FGS", 3, 2, "Purple", 4430);
INSERT INTO carOwnership VALUES(DEFAULT, "CD34 SDF", 3, 3, "Orange", 3525);
INSERT INTO carOwnership VALUES(DEFAULT, "EF56 GFD", 5, 3, "Green", 7543);
INSERT INTO carOwnership VALUES(DEFAULT, "GH78 GHH", 4, 4, "Yellow", 8367);
INSERT INTO carOwnership VALUES(DEFAULT, "IJ90 RMJ", 3, 8, "Red", 4567);
INSERT INTO carOwnership VALUES(DEFAULT, "KL12 RTY", 6, 9, "Blue", 8476);
INSERT INTO carOwnership VALUES(DEFAULT, "MN34 WER", 8, 11, "Black", 2557);
INSERT INTO carOwnership VALUES(DEFAULT, "OP56 SDG", 7, 12, "Purple", 2477);
INSERT INTO carOwnership VALUES(DEFAULT, "QR78 KIU", 4, 2, "Purple", 6347);
INSERT INTO carOwnership VALUES(DEFAULT, "ST90 TYU", 1, 5, "Purple", 8674);
INSERT INTO carOwnership VALUES(DEFAULT, "UV12 SDB", 9, 3, "Orange", 3676);
INSERT INTO carOwnership VALUES(DEFAULT, "WX34 BDF", 12, 10, "Black", 8464);
