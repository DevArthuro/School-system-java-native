# School Project

This is a Java 17 project that implements a school system where users can register, login, and program their schedules. The system has two types of actors: "student" and "teacher". The schedule configuration is different for each actor. For teachers, they can assign grades or classrooms to their schedule. For students, they can select subjects to include in their schedule.

## Features

### User Registration and Login
Users can register and log into the system. The "User ID" field should be filled with the identification document, and the password can be of any type and role.

### Schedule Programming
Teachers and students can program their schedules according to their respective requirements.

For teachers, after logging in, they will be shown their assigned schedule and a section to program exams. They can input the exam name, subject, completion date, and number of questions. Then, a tab will appear where they can add multiple-choice questions. They should specify the correct option for each question and provide the text for each option. Finally, they can publish the exam, which will be uploaded to a database and removed from the interface. There will be a "View Results" button where teachers can check who has taken the exam, the score, the correct and incorrect answers, and the response date.

For students, after logging in, they will see the same user interface but with a "Pending Exams" section. They will only see the exams that are available to them. There will be a "Take Exam" button, and once clicked, the exam will become available in the next tab. Students can answer the questions and instantly receive their score. Afterward, the exam will disappear or become unavailable for future logins.

## Getting Started

To configure the database, you need to register it in the .env file. Fill in all the required fields with the MySQL database information.

To start the project, run the `SchoolProject` file located in the package `com.mycompany.schoolproject`. This file serves as the entry point for the project and initializes everything.

The project is organized into different packages, following a layered architecture approach. It uses the persistence of data through a logical layer, and the view interacts with this logic.

## Dependencies

The project requires Java 17 and a MySQL database.

Make sure to set up the database and configure the connection details in the .env file.

´mysql-connector-java-8.0.17.jar´: This library allows Java to connect to a MySQL database.
´java-dotenv-5.2.2.jar´: This library provides support for loading environment variables from a .env file.
Make sure to include these JAR files in your project's classpath.

## Conclusion

This School Project provides a user-friendly interface for a school system, allowing students and teachers to manage their schedules and perform tasks such as exam programming and taking exams. The project follows a layered architecture approach to separate concerns and facilitate data persistence and interaction with the user interface.
