package logica;

import com.mycompany.schoolproject.views.MakeToTheExams;
import java.util.Arrays;
import java.util.LinkedHashMap;
import javax.swing.JTabbedPane;

public class ValidationExamProgram {

    //Declaramos variables de clase para interactuar con el examen por toda la clase 
    String name, materia, numeroPreguntas, dateLimit;

    // Está función lo que hace es validar si los datos que ingreso el usuario son validos
    public void validateDateExam(JTabbedPane tabla, LinkedHashMap dataProgram) {
        MakeToTheExams exam = new MakeToTheExams();
        exam.make(tabla);
        // captamos los datos en memoria para usarlos para fabricar el examen y validar 
        // aplicamos operador ternario por si el dato es nulo no cause error en el StringBuffer
        this.name = dataProgram.get("nameExam") != null ? dataProgram.get("nameExam").toString() : "nulo";
        this.materia = dataProgram.get("options") != null ? dataProgram.get("options").toString() : "nulo";
        this.numeroPreguntas = dataProgram.get("number") != null ? dataProgram.get("number").toString() : "nulo";
        String beginDate = dataProgram.get("beginDate") != null ? dataProgram.get("beginDate").toString() : "nulo";
        String finalDate = dataProgram.get("finalDate") != null ? dataProgram.get("finalDate").toString() : "nulo";

        this.convertToDate(beginDate, finalDate);
    }

    public void convertToDate(String beginDate, String finalDate) {
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
        
        // convert militar time to normal time
        hourFinish = yearBegin > 12 || yearBegin == 0 ? this.validateTime(hourFinish) : hourFinish;
        
        // Validamos las fechas y calculamos 
        int year = yearFinish - yearBegin;
        int month = monthFinish - monthBegin;
        int day = dayFinish - dayBegin;
        int hour = hourFinish - hourBegin;
        int minute = minuteFinish - minuteBegin;
        
        // imprimimos el calculo
        System.out.println(year + "año, "+month+"mes, "+day+"day, "+ hour +"hora, "+minute+"minute ");
        
    }
    
    // validamos la hora militar
    public int validateTime(int time) {
        //diccionario para conversión
        LinkedHashMap<Integer, Integer> militaryTime = new LinkedHashMap<Integer, Integer>();
        
        // metemos al diccionario la hora noraml y equivalkencia en hora militar
        int militaryCount = 13;
        for (int i = 1; i <= 12; i++) {
            militaryTime.put(i, militaryCount);
            // varificamos 24 ya que en hora militar es zero
            militaryCount = militaryCount != 24 ? militaryCount + 1 : 0;
        }
        
        // retornamos la conversiób 
        return militaryTime.get(time);
    }
}
