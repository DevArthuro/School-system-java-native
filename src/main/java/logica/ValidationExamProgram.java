package logica;

import com.mycompany.schoolproject.views.MakeToTheExams;
import java.util.Arrays;
import java.util.LinkedHashMap;
import javax.swing.JTabbedPane;

public class ValidationExamProgram {
    // Está función lo que hace es validar si los datos que ingreso el usuario son validos
    public void validateDateExam(JTabbedPane tabla, LinkedHashMap dataProgram)
    {
        MakeToTheExams exam = new MakeToTheExams();
        exam.make(tabla);
        // captamos los datos en memoria para usarlos para fabricar el examen y validar 
        // aplicamos operador ternario por si el dato es nulo no cause error en el StringBuffer
        StringBuffer name = new StringBuffer(dataProgram.get("nameExam") != null ? dataProgram.get("nameExam").toString() : "nulo");
        StringBuffer materia = new StringBuffer(dataProgram.get("options") != null ? dataProgram.get("options").toString() : "nulo");
        StringBuffer numeroPreguntas = new StringBuffer(dataProgram.get("number") != null ? dataProgram.get("number").toString() : "nulo");
        StringBuffer beginDate = new StringBuffer(dataProgram.get("beginDate") != null ? dataProgram.get("beginDate").toString() : "nulo");
        StringBuffer finalDate = new StringBuffer(dataProgram.get("finalDate") != null ? dataProgram.get("finalDate").toString() : "nulo");
        
        System.out.println(beginDate + " : " + finalDate);
        convertToDate(beginDate, finalDate);
    }
    
    public static void convertToDate(StringBuffer beginDate, StringBuffer finalDate)
    {
        
        // Comenzamos un proceso de normalización de la fecha pra poder ser comparada cada una por aparte a la misma vez 
        
        String[] fechaInicio = beginDate.toString().replace('-', ' ').trim().split("/"); // convertimos fecha a string, remplazamos "-" por espacios vacios, quyitamos los espacios, y converimos a lista usando de referencia "/"
        // Ahora iteramos la cadena para hacer un limpiado de datos mas profuncdo y remplazo
        for (int i = 0; i < fechaInicio.length; i++) 
        {
            fechaInicio[i] = fechaInicio[i].trim();
        }
        
        System.out.println(Arrays.toString(fechaInicio));
        
        String[] fechaFin = finalDate.toString().replace('-', ' ').trim().split("/"); // convertimos fecha a string, remplazamos "-" por espacios vacios, quyitamos los espacios, y converimos a lista usando de referencia "/"
        // Ahora iteramos la cadena para hacer un limpiado de datos mas profuncdo y remplazo
        for (int i = 0; i < fechaFin.length; i++) 
        {
            fechaFin[i] = fechaFin[i].trim();
        }
        
        System.out.println(Arrays.toString(fechaFin));
    }
}
