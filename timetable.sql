CREATE DATABASE timetable_system;

USE timetable_system;

CREATE TABLE timetable(
    id INT AUTO_INCREMENT PRIMARY KEY,
    class_name VARCHAR(50),
    day VARCHAR(20),
    period INT,
    subject VARCHAR(50),
    faculty VARCHAR(50),
    room VARCHAR(20)
);

select *from timetable