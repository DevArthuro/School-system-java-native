package com.mycompany.schoolproject.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TablesMigrations {
    
    ConnectionDataBase instance = new ConnectionDataBase();
    Connection conn = instance.conn();
    
    public TablesMigrations()
    {
        String[] request = {tableUsers()};
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
    
    public static String tableUsers()
    {
        String query = "CREATE TABLE IF NOT EXISTS users("
                + "id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,"
                + "name VARCHAR(300) NOT NULL,"
                + "document BIGINT NOT NULL UNIQUE,"
                + "phone_number BIGINT NOT NULL,"
                + "password VARCHAR(50) NOT NULL"
                + "role ENUM('estudiante', 'profesor') default NULL"
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON  UPDATE CURRENT_TIMESTAMP"
                + ")ENGINE=INNODB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_spanish_ci;";
        return query;
    }
    
    public static void tableExams()
    {
            //
    }
}
