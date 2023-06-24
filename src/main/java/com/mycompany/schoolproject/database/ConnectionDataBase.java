package com.mycompany.schoolproject.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import io.github.cdimascio.dotenv.Dotenv;
import kotlin.contracts.Returns;
public class ConnectionDataBase {
    public Connection conn()
    {
        Connection conn = null;
        try
        {
            String url = String.format("jdbc:%s://%s:%s/%s", 
            env().get("db"), 
            env().get("host"), 
            env().get("port"),
            env().get("dbs-name"));
            String user = env().get("user"); 
            String password = env().get("password");
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
    public Map<String, String> env()
    {
        Dotenv enviroment = Dotenv.load();
        Map<String, String> env = Map.of(
            "db", enviroment.get("DBS"),
            "host", enviroment.get("HOST_DB"),
            "port", enviroment.get("PORT_DB"),
            "dbs-name", enviroment.get("DBS_NAME"),
            "user", enviroment.get("USER"),
            "password", enviroment.get("PASSWORD")
        );

        return env;
    }
}
