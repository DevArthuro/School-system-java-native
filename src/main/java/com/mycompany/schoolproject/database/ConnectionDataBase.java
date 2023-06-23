package com.mycompany.schoolproject.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDataBase {
    public Connection conn()
    {
        Connection conn = null;
        try
        {
            String url = "jdbc:mysql://localhost:3307/school_project";
            String user = "root"; 
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        }
        catch(Exception e)
        {
            System.out.println("Error" + e);
            return conn;
        }
    }
}
