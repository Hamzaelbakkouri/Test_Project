DROP TABLE IF EXISTS person;

CREATE TABLE person (
                        id UUID PRIMARY KEY,
                        first_name VARCHAR(100),
                        last_name VARCHAR(100),
                        birthday DATE,
                        city VARCHAR(100),
                        country VARCHAR(100),
                        avatar VARCHAR(255),
                        company VARCHAR(100),
                        job_position VARCHAR(100),
                        mobile VARCHAR(20) UNIQUE,
                        username VARCHAR(100) UNIQUE,
                        email VARCHAR(100) UNIQUE,
                        password VARCHAR(255),
                        role VARCHAR(50)
);

INSERT INTO person (id, first_name, last_name, birthday, city, country, avatar, company, job_position, mobile, username, email, password, role)
VALUES
    ('8c8a431d-d5af-4475-9dd4-001c5de97485', 'John', 'Doe', '1990-05-15', 'New York', 'USA', 'https://example.com/avatar1.jpg', 'ABC Company', 'Manager', '123456789', 'muser1', 'john@example.com', '$2a$10$MhRVbyX.JxhvHsDGRb7Zr.c7ssM/FlQ8wnR7eYhI01IabqJmkCvf.', 0),
    ('0f42aef8-5dc1-4189-93cf-05e42930c9e4', 'Jane', 'Smith', '1985-08-20', 'Los Angeles', 'USA', 'https://example.com/avatar2.jpg', 'XYZ Corp', 'Developer', '987654321', 'muser2', 'jane@example.com', '$2a$10$88tvX7U8L.u5kymboRsTQuURkoylO7rOMZm/zjK46cncjt5N7Bk.i', 0),
    ('cc62b7a0-89ed-4fd4-8193-f2bce9c59e34', 'Alice', 'Johnson', '1982-12-10', 'London', 'UK', 'https://example.com/avatar3.jpg', 'PQR Ltd', 'Designer', '555555555', 'muser3', 'alice@example.com', '$2a$10$SMqG/PKPy3do7GqyOZqnQOFsbctxoGsobgj5J/e7dGNYHjuhAV7S6', 1);
