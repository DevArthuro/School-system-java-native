package logica;

import com.mycompany.schoolproject.views.MakeToTheExams;
import java.util.Arrays;
import java.util.LinkedHashMap;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class ValidationExamProgram {

    //Declaramos variables de clase para interactuar con el examen por toda la clase 
    String name,signature, dateLimit;
    Integer numeroPreguntas;
    
    // Está función lo que hace es validar si los datos que ingreso el usuario son validos
    public void validateDateExam(JTabbedPane tabla, LinkedHashMap dataProgram) {
        MakeToTheExams exam = new MakeToTheExams();
        
        // captamos los datos en memoria para usarlos para fabricar el examen y validar 
        // aplicamos operador ternario por si el dato es nulo no cause error en el StringBuffer
        this.name = dataProgram.get("nameExam") != null ? dataProgram.get("nameExam").toString() : "nulo";
        this.signature = this.validateAsignature((JComboBox) dataProgram.get("options")).toString();
        this.numeroPreguntas = Integer.parseInt(dataProgram.get("number") != null ? dataProgram.get("number").toString() : "0");
        String beginDate = dataProgram.get("beginDate") != null ? dataProgram.get("beginDate").toString() : "nulo";
        String finalDate = dataProgram.get("finalDate") != null ? dataProgram.get("finalDate").toString() : "nulo";
        // diccionario para verificar ñas fechas 
        LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();

        if (!(finalDate == "nulo"))
        {
            // Estas variables nos indican que lleno todos los campos por esa razón 
            boolean validationsName = false, validationSignature = false;
            data = this.convertToDate(beginDate, finalDate);
            if (this.validateName(this.name.strip()))
            {
                validationsName = true;
                data.put("name", this.name);;
            }
            else
            {
                JOptionPane.showConfirmDialog(null, "No lleno el campo de nombre");
            }
            if (this.signature != "false")
            {
                validationSignature = true;
                data.put("asignature", this.signature);
            }
            else
            {
                JOptionPane.showConfirmDialog(null, "No validó la asignatura");
            }
            if(validationsName && validationSignature)
            {
                if (this.numeroPreguntas != 0 && this.numeroPreguntas > 0)
                {
                    exam.make(tabla, data, this.numeroPreguntas);
                }
                else
                {
                    JOptionPane.showConfirmDialog(null, "Usted no hará ninguna pregunta");
                }
                
            }
                
        }
        else
        {
            JOptionPane.showConfirmDialog(null, "No asigno fecha de cierre");
        }
        // Acá mandamos las variables a la ventana 
        
    }
    
    // Configuramos las fechas y retornamos
    public LinkedHashMap<String, String> convertToDate(String beginDate, String finalDate) {
        // Comenzamos un proceso de normalización de la fecha pra poder ser comparada cada una por aparte a la misma vez 

        String[] dateBegin = beginDate.replace('-', ' ').trim().split("/"); // convertimos fecha a string, remplazamos "-" por espacios vacios, quyitamos los espacios, y converimos a lista usando de referencia "/"
        // Ahora iteramos la cadena para hacer un limpiado de datos mas profuncdo y remplazo
        for (int i = 0; i < dateBegin.length; i++) {
            dateBegin[i] = dateBegin[i].trim();
        }

        String[] dateFinish = finalDate.replace('-', ' ').trim().split("/"); // convertimos fecha a string, remplazamos "-" por espacios vacios, quyitamos los espacios, y converimos a lista usando de referencia "/"
        // Ahora iteramos la cadena para hacer un limpiado de datos mas profuncdo y remplazo
        for (int i = 0; i < dateFinish.length; i++) {
            dateFinish[i] = dateFinish[i].trim();
        }

        // Atomizamos las fecha para que sean analizadas 
        int dayFinish = Integer.parseInt(dateFinish[0]),
                monthFinish = Integer.parseInt(dateFinish[1]),
                yearFinish = Integer.parseInt(dateFinish[2].split(" ")[0]),
                hourFinish = Integer.parseInt(dateFinish[2].split(" ")[1].split(":")[0]),
                minuteFinish = Integer.parseInt(dateFinish[2].split(" ")[1].split(":")[1]);

        // Se le agregan mas cosas a está cadena ya que tiene mas espaciado 
        int dayBegin = Integer.parseInt(dateBegin[0]),
                monthBegin = Integer.parseInt(dateBegin[1]),
                yearBegin = Integer.parseInt(dateBegin[2].split(" ")[0]),
                hourBegin = Integer.parseInt(dateBegin[2].replace("   ", " ").split(" ")[1].split(":")[0]),
                minuteBegin = Integer.parseInt(dateBegin[2].replace("   ", " ").split(" ")[1].split(":")[1]);
        
        // Aplicamos conversión de hora si hora es mayor a 12
        hourFinish = hourBegin > 12 ? hourFinish = this.validateTime(hourFinish) : hourFinish;
        
        
        
        // Imprimimos la fecha de inicio y limite 
        String begin = dayBegin + "/" + (monthBegin < 10 ? "0"+monthBegin : monthBegin)  + "/" + yearBegin + " " + hourBegin + ":" + minuteBegin;
        String finish = dayFinish + "/" + (monthFinish < 10 ? "0"+monthFinish : monthFinish)+ "/" + yearFinish +" " + hourFinish +":"+minuteFinish;
        
        LinkedHashMap<String, String> date = new LinkedHashMap<String, String>(){{
            put("inicio", begin);
            put("fin", finish);
        }};
        
        
        return date;
    }
    
    // Casteamos a hora militar
    public int validateTime(int time) {
        //diccionario para conversión
        LinkedHashMap<Integer, Integer> militaryTime = new LinkedHashMap<Integer, Integer>();
        
        // metemos al diccionario la hora normal y equivalencia en hora militar
        int militaryCount = 13;
        for (int i = 1; i <= 12; i++) {
            militaryTime.put(i, militaryCount);
            // varificamos 24 ya que en hora militar es zero
            militaryCount = militaryCount != 24 ? militaryCount + 1 : 0;
        }
        
        // retornamos la conversiób 
        return militaryTime.get(time);
    }
    
    public boolean validateName(String name)
    {
        System.out.println("nombre" + name);
        if (name == "")
        {
            return false;
        }
        return true;
        
    }
    // Está función nos sirve para evaluar el JComboBox que el usuario seleccione
    public Object validateAsignature(JComboBox options)
    {
        // Sinp señlecciona nada el indice es "0" y retornamos la función 
        int index = options.getSelectedIndex();
        if (index == 0)
        {
            return false;
        }
        // Sino entro en la condición anterior sacamos el elemento y lo retornamos 
        String materia = options.getSelectedItem().toString();
        
        return materia;
    }
}
