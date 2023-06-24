package com.mycompany.schoolproject.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TablesMigrations {
    
    ConnectionDataBase instance = new ConnectionDataBase();
    Connection conn = instance.conn();
    static String[] tables = {"users", "schedule"};
    
    public TablesMigrations()
    {
        String[] request = {users(), schedule()};
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
    
    public String[] getTables()
    {
        return tables;
    }
}
