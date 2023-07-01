package com.mycompany.schoolproject.database;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import logica.ResponseExams;

public class ExecuteQuesries {
    Connection conn = new ConnectionDataBase().conn();
    public static void main(String[] args) {
        ExecuteQuesries queries = new ExecuteQuesries();
        queries.getAllExams();
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
        String query = "SELECT password, role FROM users WHERE document = '"+document+"';";
        try
        {
            PreparedStatement ps = this.conn.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            result.next();
            String passwordDBS = result.getString(1);
            String roleDB = result.getString(2);
            if (password.equals(passwordDBS) && rol.equals(roleDB))
            {
                return true;
            }
            else
            {
                if (!password.equals(passwordDBS)){
                    JOptionPane.showMessageDialog( null, "Contraseña incorrecta");
                    
                }
                if (!rol.equals(roleDB)){
                    JOptionPane.showMessageDialog(null, "Usted no es %s".formatted(rol));
                }
                return false;
            }
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Documento no valido");
            return false;
        }
    }
    
    public Map<String, String> getDataUser(String document, String password)
    {
        String query = "SELECT * FROM users WHERE document='%s' AND password='%s';".formatted(document, password);
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
            e.printStackTrace();
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
            e.printStackTrace();
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
    
    public int insertExam(String document, String password, LinkedHashMap<String, String> data)
    {   
        String id_teacher = getDataUser(document,password).get("id");
        //Normalizacion de la fechas 
        String fechaString = data.get("fin");
        System.out.println(fechaString.split(" ")[1].split(":")[0].length() == 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("%s/%s/yyyy %s:%s".formatted(fechaString.split(" ")[0].split("/")[0].length() == 1 ? "d" : "dd",fechaString.split(" ")[0].split("/")[1].length() == 1 ? "M" : "MM",fechaString.split(" ")[1].split(":")[0].length() == 1 ? "H" : "HH",
        fechaString.split(" ")[1].split(":")[1].length() == 1 ? "m" : "mm"));
        LocalDateTime fecha = LocalDateTime.parse(fechaString, formatter);
        String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String query = "INSERT INTO exams (id_teacher, title, number_question, max_submit, asignature) VALUES ('%s','%s','%s','%s','%s')".formatted(
              id_teacher, data.get("name"), data.get("number"), fechaFormateada, data.get("asignature")        
        );
        try{
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.executeUpdate();
            PreparedStatement psGet = this.conn.prepareStatement("SELECT MAX(id) FROM exams");
            ResultSet rs = psGet.executeQuery();
            rs.next();
            return Integer.parseInt(rs.getString(1));
        }catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            return -1;
        }
    }
    
    public void insertQuestions(int id_exam, int numberQuestion, String contend, String answers, String correct_letter)
    {
        String query = "INSERT INTO questions "
                + "(id_exam,number_question,contend,answer_a,answer_b,answer_c,answer_d,letter_correct) "
                + "VALUES "
                + "('%d','%d','%s',%s,'%s')".formatted(id_exam, numberQuestion, contend, answers, correct_letter);
        try{
            PreparedStatement ps = this.conn.prepareStatement(query);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
    
    public LinkedHashMap getAllExams()
    {
        LinkedHashMap<String, String> exams = new LinkedHashMap<String, String>();
        ResponseExams dates = new ResponseExams();
        String currentDate = dates.obtenerFechaActual();
        
        String query = "SELECT id,max_submit FROM exams WHERE status = 'activo';";
        String queryCount = "SELECT COUNT(id) FROM exams WHERE status = 'activo';";
        try{
            PreparedStatement ps = this.conn.prepareStatement(query);
            PreparedStatement psCount = this.conn.prepareStatement(queryCount);
            ResultSet rsCount = psCount.executeQuery();
            rsCount.next();
            int count = rsCount.getInt(1);
            ResultSet rs = ps.executeQuery();
            rs.next();
            for (int i = 1; i <= count; i++) {
                String key = "exam"+i;
                String dateFinish = rs.getString("max_submit");
                if (dates.compararFechas(dateFinish, dates.obtenerFechaActual())){
                    exams.put(key, rs.getString("id"));
                }
                else{
                    String queryUpdate = "UPDATE exams SET status = 'inactivo' WHERE id = '%s';".formatted(rs.getString("id"));
                    PreparedStatement psUpdate = this.conn.prepareStatement(queryUpdate);
                    psUpdate.executeUpdate();
                }
                rs.next();
            }
            
        }catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
        return exams;
    }
    
    public LinkedHashMap requestData(String id)
    {
        String query = "SELECT id_teacher,title,number_question,begin_submit,max_submit,asignature FROM exams WHERE id='%s';".formatted(id);
        
        try{
            PreparedStatement ps = this.conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String id_teacher = rs.getString("id_teacher");
            String queryNameTeacher = "SELECT (name) FROM users WHERE id='%s';".formatted(id_teacher);
            PreparedStatement psName = this.conn.prepareStatement(queryNameTeacher);
            ResultSet rsName = psName.executeQuery();
            rsName.next();
            String nameTeacher = rsName.getString("name");
            LinkedHashMap<String, String> dataExam = new LinkedHashMap<String, String>(){{
               put("teacher", nameTeacher);
               put("title", rs.getString("title"));
               put("questions", rs.getString("number_question"));
               put("asignature", rs.getString("asignature"));
               put("inicio", rs.getString("begin_submit"));
               put("fin", rs.getString("max_submit"));
            }};
            return dataExam;
        }catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
    
    public ResultSet getQuestionsExam(String id)
    {
        String query = "SELECT * FROM questions WHERE id_exam='%s'".formatted(id);
        try
        {
            PreparedStatement ps = this.conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs;
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            ResultSet rs = null;
            return rs;
        }
    }
    
    public boolean insertDataQualifications(LinkedHashMap<String, String> data)
    {
        String query = "INSERT INTO qualifications (id_student,id_exam,qualification,corrects,incorrects) "
                + "VALUES "
                + "('%s','%s','%s','%s','%s')".formatted(
                data.get("id_user"),
                data.get("id_exam"),
                data.get("qualification"),
                data.get("correct"),
                data.get("incorrects")
                );
        try{
            this.conn.prepareStatement(query).executeUpdate();
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean requestQualifications(String id_student, String id_exam)
    {
        String query = "SELECT COUNT(id) FROM qualifications WHERE id_student='%s' AND id_exam='%s'".formatted(id_student, id_exam);
        try{
            ResultSet rs = this.conn.prepareStatement(query).executeQuery();
            rs.next();
            if (rs.getInt(1)>0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }
    
    public String[][] dataExams()
    {
        String countRegister = "SELECT COUNT(id) FROM qualifications";
        String getAllData = "SELECT * FROM qualifications";
        try
        {
            ResultSet rs = this.conn.prepareStatement(countRegister).executeQuery();
            rs.next();
            int register = rs.getInt(1);
            ResultSet rsData = this.conn.prepareStatement(getAllData).executeQuery();
            rsData.next();
            String[][] dataQualifications = new String[register][7];
            for (int i = 0; i < register; i++) 
            {
                System.out.println(rsData.getString("id_student"));
                System.out.println();
                String name = getName(rsData.getString("id_student"), "users", "name");
                dataQualifications[i][0] = name;
                String exam = getName(rsData.getString("id_exam"), "exams", "title");
                dataQualifications[i][1] = exam;
                String qualification = rsData.getString("qualification");
                dataQualifications[i][2] = qualification;
                String corrects = rsData.getString("corrects");
                dataQualifications[i][3] = corrects;
                String incorrects = rsData.getString("incorrects");
                dataQualifications[i][4] = incorrects;
                String creado = rsData.getString("created_at");
                dataQualifications[i][5] = creado;
                rsData.next();
            }
            return dataQualifications;
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            return new String[][]{};
        }
    }
    
    public String getName(String id_user, String table, String column)
    {
        String query = "SELECT %s FROM %s WHERE id='%s';".formatted(column,table,id_user);
        try{
            ResultSet rs = this.conn.prepareStatement(query).executeQuery();
            rs.next();
            return rs.getString(1);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
            return "";
        }
    }
}
