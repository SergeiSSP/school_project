CREATE TABLE groups
(
    group_id SERIAL PRIMARY KEY,
    group_name character varying(50) NOT NULL
);

CREATE TABLE students
(
    student_id SERIAL PRIMARY KEY,
    group_id INT,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    FOREIGN KEY (group_id)
        REFERENCES groups (group_id)
    ON DELETE SET NULL
);

CREATE TABLE courses
(
    course_id SERIAL PRIMARY KEY,
    course_name character varying(50) NOT NULL,
    course_description TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS students_courses
(
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    FOREIGN KEY (course_id)
        REFERENCES courses (course_id),
    FOREIGN KEY (student_id)
        REFERENCES students (student_id)
    ON DELETE CASCADE
);