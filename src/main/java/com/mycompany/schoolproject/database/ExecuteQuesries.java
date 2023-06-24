package com.mycompany.schoolproject.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class ExecuteQuesries {
    Connection conn = new ConnectionDataBase().conn();
    public static void main(String[] args) {
        /*
        ExecuteQuesries queries = new ExecuteQuesries();
        String[] columns = {"name","document","phone_number","password","role"};
        String[] values = {"carlitos", "1089931334123283", "321313", "1234", "profesor"};

        if (queries.requestDataUser("32132", "profesor", "1234"))
        {
            System.out.println("valido");
        }
        else
        {
            System.out.println("invalido");
        }*/
        
        /*
        ExecuteQuesries queries = new ExecuteQuesries();
        queries.getDataUser("123456789", "12345");
        */
    }
    
    public boolean insertData(String tableName, String[] columnNames, String[] valoresColumns) {
        for (int i = 0; i < valoresColumns.length; i++) // For para agregar comillas simples a los valores de la dbs
        {
            valoresColumns[i] = "'"+valoresColumns[i]+"'";
        }
        String columns = String.join(",", columnNames);
        String valores = String.join(",", valoresColumns);
        

        String query = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + valores + ");";
        try
        {
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.executeUpdate();
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean requestDataUser(String document, String rol, String password)
    {
        String query = "SELECT password FROM users WHERE document = '"+document+"' AND role = '"+rol+"';";
        try
        {
            PreparedStatement ps = this.conn.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            result.next();
            String passwordDBS = result.getString(1);
            if (password.equals(passwordDBS))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public Map<String, String> getDataUser(String document, String password)
    {
        String query = "SELECT * FROM users WHERE document=%s AND password=%s".formatted(document, password);
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Map<String, String> data = Map.of(
                    "name", rs.getString("name"),
                    "document", rs.getString("document"),
                    "phone", rs.getString("phone_number"),
                    "role",rs.getString("role"),
                    "start", rs.getString("created_at")        
            );
            return data;
           
        }catch(Exception e)
        {
            System.out.println("Error -> "+e);
            Map<String, String> data = Map.of();
            return data;
        }
         
    }
}
