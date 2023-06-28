package logica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ResponseExams {
    public boolean compararFechas(String fechaMayor, String fechaMenor) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date fecha1 = formatoFecha.parse(fechaMayor);
            Date fecha2 = formatoFecha.parse(fechaMenor);
            return fecha1.compareTo(fecha2) > 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public String obtenerFechaActual() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fechaActual = new Date();
        return formatoFecha.format(fechaActual);
    }
}
