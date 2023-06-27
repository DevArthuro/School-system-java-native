 package com.mycompany.schoolproject.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TablesMigrations {
    
    ConnectionDataBase instance = new ConnectionDataBase();
    Connection conn = instance.conn();
    static String[] tables = {"users", "schedule", "exams", "questions"};
    
    public TablesMigrations()
    {
        String[] request = {users(), schedule(), exams(), questions()};
        for(String sql : request)
        {
            try
            {
                PreparedStatement querys = conn.prepareCall(sql);
                querys.executeUpdate();
            }
            catch(Exception e)
            {
                System.out.println("Error " + e);
            }
            
        }        
    }
    
    public static String users()
    {
        String query = "CREATE TABLE IF NOT EXISTS users("
                + "id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,"
                + "name VARCHAR(300) NOT NULL,"
                + "document BIGINT NOT NULL UNIQUE,"
                + "phone_number BIGINT NOT NULL,"
                + "password VARCHAR(50) NOT NULL,"
                + "role ENUM('estudiante', 'profesor') default NULL,"
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
                + ")ENGINE=INNODB CHARACTER SET=utf8mb4 COLLATE=utf8mb4_spanish_ci;";
        return query;
    }
    
    public static String schedule()
    {
        String query = "CREATE TABLE IF NOT EXISTS schedule("+
            "id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,"+
            "id_user BIGINT NOT NULL,"+
            "INDEX (id_user),"+
            "day ENUM('lunes','martes','miercoles','jueves','viernes') DEFAULT NULL,"+
            "one_time VARCHAR(60) NULL,"+
            "second_time VARCHAR(60) NULL,"+
            "third_time VARCHAR(60) NULL,"+
            "fourth_time VARCHAR(60) NULL,"+
            "fifth_time VARCHAR(60) NULL,"+
            "sixth_time VARCHAR(60) NULL,"+
            "FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,"+
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"+
            "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"+
            ")ENGINE=INNODB CHARACTER SET=utf8mb4 COLLATE=utf8mb4_spanish_ci;";
                

        return query;
    }
    
    public static String exams()
    {
        String query = "CREATE TABLE IF NOT EXISTS exams("
                + "id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,"
                + "id_teacher BIGINT NOT NULL,"
                + "INDEX (id_teacher),"
                + "title VARCHAR(40),"
                + "number_question INT NOT NULL,"
                + "begin_submit DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + "max_submit DATETIME,"
                + "status ENUM('activo', 'inactivo') DEFAULT 'activo',"
                + "INDEX(id_teacher),"
                + "asignature VARCHAR(60) NOT NULL,"
                + "FOREIGN KEY (id_teacher) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,"
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
                + ") ENGINE=InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;";
        
        return query;
    }
    
    public static String questions()
    {
        String query = "CREATE TABLE IF NOT EXISTS questions("
                + "id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,"
                + "id_exam BIGINT NOT NULL,"
                + "INDEX (id_exam),"
                + "number_question INT NOT NULL,"
                + "contend VARCHAR(100) NOT NULL,"
                + "answer_a VARCHAR(100) NOT NULL,"
                + "answer_b VARCHAR(100) NOT NULL,"
                + "answer_c VARCHAR(100) NOT NULL,"
                + "answer_d VARCHAR(100) NOT NULL,"
                + "letter_correct VARCHAR(1) NOT NULL,"
                + "FOREIGN KEY (id_exam) REFERENCES exams(id) ON DELETE CASCADE ON UPDATE CASCADE,"
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
                + ")ENGINE=INNODB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;";
        return query;
    }

    public String[] getTables()
    {
        return tables;
    }
}
