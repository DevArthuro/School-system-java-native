package com.mycompany.schoolproject.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExecuteQuesries {
    Connection conn = new ConnectionDataBase().conn();
    public static void main(String[] args) {
        
    }
    
    public boolean insertData(String tableName, String[] columnNames, String[] valoresColumns) 
    {
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
        try{
            ResultSet resultSet = conn.prepareStatement("SELECT COUNT(*) FROM schedule WHERE id_user='%s'".formatted(id_user)).executeQuery();
            resultSet.next();
            if (resultSet.getInt(1)<=0)
            {
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
            else{
                JOptionPane.showMessageDialog(null,"Ya tiene un horario asigando no puede crear mas");
            }
        }catch(Exception e)
        {
            System.out.println(e);
        }
        
        
    }

    public LinkedHashMap<String, LinkedHashMap<Integer, String>> getSubjectsGroup(String document, String password)
    {
        String id_ueser = getDataUser(document, password).get("id");
        LinkedHashMap<String, LinkedHashMap<Integer, String>> datasubjects = new LinkedHashMap<String, LinkedHashMap<Integer, String>>();
        String[] days = {"lunes", "martes", "miercoles", "jueves", "viernes"};
        for (int i = 0; i < 5; i++) 
        {
            String query = "SELECT * FROM schedule WHERE id_user='%s' AND day='%s'".formatted(id_ueser, days[i]);
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

    public boolean requestDocumentExists(String document)
    {
        String query = "SELECT document FROM users WHERE document='%s';".formatted(document);
        try{
            ResultSet rs = conn.prepareStatement(query).executeQuery();
            rs.next();
            if (rs.getString("document") == null)
            {
                return false;
            }
            return true;
        }catch(Exception e)
        {
            return false;
        }
    }
    
    public boolean insertExam(String document, String password, LinkedHashMap<String, String> data)
    {   
        String id_teacher = getDataUser(document,password).get("id");
        //Normalizacion de la fechas 
        String fechaString = data.get("fin");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy %s:%s".formatted(fechaString.split(" ")[1].split(":")[0].length() == 1 ? "H" : "HH",
                fechaString.split(" ")[1].split(":")[1].length() == 1 ? "m" : "mm"));
        LocalDateTime fecha = LocalDateTime.parse(fechaString, formatter);
        System.out.println(fecha);
        String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String query = "INSERT INTO exams (id_teacher, title, number_question, max_submit, asignature) VALUES ('%s','%s','%s','%s','%s')".formatted(
              id_teacher, data.get("name"), data.get("number"), fechaFormateada, data.get("asignature")        
        );
        try{
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.executeUpdate();
            return true;
        }catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
    
}
