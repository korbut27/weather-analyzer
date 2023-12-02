CREATE TABLE weather
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    temp_c      DOUBLE       NOT NULL,
    wind_kph    DOUBLE       NOT NULL,
    pressure_in DOUBLE       NOT NULL,
    humidity    DOUBLE       NOT NULL,
    `condition` VARCHAR(255) NOT NULL,
    location    VARCHAR(255) NOT NULL,
    update_time DATETIME     NOT NULL
);