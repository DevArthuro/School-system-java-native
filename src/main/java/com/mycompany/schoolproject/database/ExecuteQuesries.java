package com.mycompany.schoolproject.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JOptionPane;

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
        /*
        ExecuteQuesries queries = new ExecuteQuesries();
        LinkedHashMap<String, LinkedHashMap<Integer, String>> schedule = new LinkedHashMap<String, LinkedHashMap<Integer, String>>(){{
            put("monday", new LinkedHashMap<Integer, String>(){{
                put(1, "matematicas");
                put(2,"ingles");
                put(3,"quimica");
                put(4,"fisica");
                put(5,"historia");
                put(6, "tecnologia");
            }});
            put("tuesday", new LinkedHashMap<Integer, String>(){{
                put(1, "emprendimiento");
                put(2,"matematicas");
                put(3,"quimica");
                put(4,"fisica");
                put(5,"historia");
                put(6, "tecnologia");
            }});
            put("wednesday", new LinkedHashMap<Integer, String>(){{
                put(1, "ciencias naturales");
                put(2,"ingles");
                put(3,"sociales");
                put(4,"fisica");
                put(5,"historia");
                put(6, "tecnologia");
            }});
            put("thuesday", new LinkedHashMap<Integer, String>(){{
                put(1, "matematicas");
                put(2,"ingles");
                put(3,"quimica");
                put(4,"fisica");
                put(5,"historia");
                put(6, "tecnologia");
            }});
            put("friday", new LinkedHashMap<Integer, String>(){{
                put(1, "matematicas");
                put(2,"ingles");
                put(3,"quimica");
                put(4,"fisica");
                put(5,"historia");
                put(6, "tecnologia");
            }});
        }};

        queries.insertSchudle(schedule, "1089931383", "12345");
        */
        /* 
        new ExecuteQuesries().getSubjectsGroup("1089931383", "12345");
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
                JOptionPane.showMessageDialog( null, "Contraseña incorrecta");
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
                    "id", rs.getString("id"),
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

    public void insertSchudle(LinkedHashMap<String, LinkedHashMap<Integer, String>> schudle, String document, String password)
    {
        String id_user = this.getDataUser(document, password).get("id");
        LinkedHashMap<Integer, String> monday = schudle.get("monday");
        LinkedHashMap<Integer, String> tuesday = schudle.get("tuesday");
        LinkedHashMap<Integer, String> wednesday = schudle.get("wednesday");
        LinkedHashMap<Integer, String> thuesday = schudle.get("thuesday");
        LinkedHashMap<Integer, String> friday = schudle.get("friday");

        LinkedHashMap[] instanceSchedules = {monday, tuesday, wednesday, thuesday, friday};
        String[] days = {"lunes", "martes", "miercoles", "jueves", "viernes"};

        String[] time = new String[6];
        int count = 0;
        for (LinkedHashMap<Integer, String> item: instanceSchedules) 
        {
            for(int hour = 1; hour <= 6; hour++)
            {
                time[hour-1] = "'%s'".formatted((String) item.get(hour));
            }
            String values = String.join(",", time);
            String query = "INSERT INTO schedule"+ 
            "(id_user,day,one_time,second_time,third_time,fourth_time,fifth_time,sixth_time) VALUES"+
            "('%s','%s',%s)".formatted(id_user, days[count], values);
            try {
                PreparedStatement ps = this.conn.prepareStatement(query);
                ps.executeUpdate();
            }catch(Exception e)
            {
                System.out.println("Error -> " + e);
            }
            count ++;
        }
    }

    public LinkedHashMap<String, LinkedHashMap<Integer, String>> getSubjectsGroup(String document, String password)
    {
        String id_ueser = getDataUser(document, password).get("id");
        LinkedHashMap<String, LinkedHashMap<Integer, String>> datasubjects = new LinkedHashMap<String, LinkedHashMap<Integer, String>>();
        String[] days = {"lunes", "martes", "miercoles", "jueves", "viernes"};
        for (int i = 0; i < 5; i++) 
        {
            String query = "SELECT * FROM schedule WHERE id_user='%s' AND day='%s'".formatted(id_ueser, days[0]);
            try {
                PreparedStatement ps = this.conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                rs.next();
                datasubjects.put(days[i], new LinkedHashMap<Integer, String>(){{
                    put(1, rs.getString("one_time"));
                    put(2, rs.getString("second_time"));
                    put(3, rs.getString("third_time"));
                    put(4, rs.getString("fourth_time"));
                    put(5, rs.getString("fifth_time"));
                    put(6, rs.getString("sixth_time"));
                }});
            }catch (Exception e)
            {
                datasubjects.put(days[i], new LinkedHashMap<Integer, String>(){{
                    put(1, null);
                    put(2, null);
                    put(3, null);
                    put(4, null);
                    put(5, null);
                    put(6, null);
                }});
            }
        }

        return datasubjects;
    }
}
