DROP TABLE IF EXISTS asiakkaat CASCADE;
DROP TABLE IF EXISTS kategoriat CASCADE;
DROP TABLE IF EXISTS postinumerot CASCADE;
DROP TABLE IF EXISTS roolit CASCADE;
DROP TABLE IF EXISTS tilaukset CASCADE;
DROP TABLE IF EXISTS tilausrivit CASCADE;
DROP TABLE IF EXISTS tuotteet CASCADE;
DROP TABLE IF EXISTS tyontekijat CASCADE;

CREATE TABLE kategoriat (
    id BIGSERIAL PRIMARY KEY,
    nimi VARCHAR(100) NOT NULL
);

CREATE TABLE postinumerot (
    id BIGSERIAL PRIMARY KEY,
    postinumero VARCHAR(5) NOT NULL,
    postitoimipaikka VARCHAR(100) NOT NULL
);

CREATE TABLE roolit (
    id BIGSERIAL PRIMARY KEY,
    nimi VARCHAR(100) NOT NULL
);

CREATE TABLE asiakkaat (
    id BIGSERIAL PRIMARY KEY,
    nimi VARCHAR(100) NOT NULL,
    katuosoite VARCHAR(100) NOT NULL,
    y_tunnus VARCHAR(10) NOT NULL,
    deleted_at DATE,
    postinumero_id INT,
    FOREIGN KEY (postinumero_id) REFERENCES postinumerot(id)
);

CREATE TABLE tyontekijat (
    id BIGSERIAL PRIMARY KEY,
    etunimi VARCHAR(50) NOT NULL,
    sukunimi VARCHAR(50) NOT NULL,
    salasana VARCHAR(100) NOT NULL,
    sahkoposti VARCHAR(100) NOT NULL UNIQUE,
    deleted_at DATE,
    rooli_id INT,
    FOREIGN KEY (rooli_id) REFERENCES roolit(id)
);

CREATE TABLE tilaukset (
    id BIGSERIAL PRIMARY KEY,
    pvm DATE,
    edited_at DATE,
    deleted_at DATE,
    tyontekija_id INT,
    asiakas_id INT,
    FOREIGN KEY (tyontekija_id) REFERENCES tyontekijat(id),
    FOREIGN KEY (asiakas_id) REFERENCES asiakkaat(id)
);

CREATE TABLE tuotteet (
    id BIGSERIAL PRIMARY KEY,
    nimi VARCHAR(100) NOT NULL,
    hinta DECIMAL(10, 2) NOT NULL,
    deleted_at DATE,
    kategoria_id INT,
    FOREIGN KEY (kategoria_id) REFERENCES kategoriat(id)
);

CREATE TABLE tilausrivit (
    id BIGSERIAL PRIMARY KEY,
    hinta DECIMAL(10, 2) NOT NULL,
    alennus DECIMAL(10, 2) NOT NULL,
    maara INT NOT NULL,
    tuote_id INT,
    tilaus_id INT,
    FOREIGN KEY (tuote_id) REFERENCES tuotteet(id),
    FOREIGN KEY (tilaus_id) REFERENCES tilaukset(id)
);

INSERT INTO kategoriat (nimi) VALUES
('Drinkit'),
('Kurssit'),
('Tapahtumat');

INSERT INTO postinumerot (postinumero, postitoimipaikka) VALUES 
('00100', 'Helsinki'),
('36500', 'Kouvola');

INSERT INTO roolit (nimi) VALUES
('KAYTTAJA'),
('ADMIN');

INSERT INTO asiakkaat (nimi, katuosoite, y_tunnus, deleted_at, postinumero_id) VALUES
('Asiakasyritys Oy', 'Esimerkkikatu 1', '1234567-8', NULL, 1),
('Toinen Asiakas Oy', 'Testikatu 2', '2345678-9', NULL, 2),
('Poistettu Asiakas Oy', 'Poistetunkatu 3', '3456789-0', '2024-11-04 15:30:00', 1);

INSERT INTO tyontekijat (etunimi, sukunimi, sahkoposti, salasana, deleted_at, rooli_id) VALUES
('Juha', 'Juhanen', 'juha.juhanen@sahkoposti.fi', '$2a$10$es76Go.W1/f5/Jms3SBvnuVG20qxUXzXq1KYvJUTxYH6PLxABzafO', NULL, 1),
('Marja', 'Marjanen', 'marja.marjanen@sahkoposti.fi', '$2a$10$uZpnGGH1amn9MEKspLG/Q.PUe58g7Ti8It3y9EWNt/1WcEk6GocdO', NULL, 2),
('Pekka', 'Pouta', 'pekka.pouta@sahkoposti.fi', 'salasana', '2024-11-02 08:15:00', 1),
('Anna', 'Korhonen', 'anna.korhonen@sahkoposti.fi', 'salasana', '2024-11-02 08:15:00', 1),
('Jari', 'Laine', 'jari.laine@sahkoposti.fi', 'salasana', '2024-11-02 08:15:00', 1);

INSERT INTO tilaukset (pvm, edited_at, deleted_at, tyontekija_id, asiakas_id) VALUES
('2024-10-01 10:00:00', NULL, NULL, 1, 1),
('2024-10-15 14:30:00', NULL, NULL, 2, 2),
('2024-09-20 09:45:00', '2024-09-21 09:45:00', NULL, 3, 1),
('2024-11-01 08:15:00', NULL, '2024-11-02 08:15:00', 4, 3);

INSERT INTO tuotteet (nimi, hinta, deleted_at, kategoria_id) VALUES
('Margarita', 12.00, NULL, 1),
('G&T', 8.00, NULL, 1),
('Negroni', 15.00, NULL, 1),
('Iltakurssi', 120.00, NULL, 2),
('Tapahtuma', 1200.00, '2024-09-21 09:45:00', 3),
('Päiväkurssi', 80.00, NULL, 2);

INSERT INTO tilausrivit (hinta, alennus, maara, tuote_id, tilaus_id) VALUES
(1200.00, 100.00, 1, 1, 1),
(800.00, 50.00, 2, 2, 1),
(150.00, 0.00, 3, 3, 2),
(1200.00, 0.00, 1, 1, 2);

SELECT * FROM asiakkaat;
SELECT * FROM kategoriat;
SELECT * FROM postinumerot;
SELECT * FROM roolit;
SELECT * FROM tilaukset;
SELECT * FROM tilausrivit;
SELECT * FROM tuotteet;
SELECT * FROM tyontekijat;