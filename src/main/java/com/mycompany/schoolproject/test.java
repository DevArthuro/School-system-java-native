/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.schoolproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.now();
        String fechaString = "%d/%d/%d %d:%d".formatted(time.getYear(), time.getMonthValue(), time.getDayOfMonth(), time.getHour(), time.getMinute());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/%s/%s %s:%s".formatted(fechaString.split(" ")[0].split("/")[1].length() == 1 ? "M" : "MM", fechaString.split(" ")[0].split("/")[2].length() == 1 ? "d" : "dd",fechaString.split(" ")[1].split(":")[0].length() == 1 ? "H" : "HH",
        fechaString.split(" ")[1].split(":")[1].length() == 1 ? "m" : "mm"));
        String fecha1 = "2023-06-27 17:00:00";
        String fecha2 = "2023-06-27 17:00:00";

        boolean resultado = compararFechas(fecha1, fecha2);
        System.out.println("¿La fecha izquierda es mayor que la fecha derecha? " + resultado);
    }
    
    public static boolean compararFechas(String fechaIzquierda, String fechaDerecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date fecha1 = formatoFecha.parse(fechaIzquierda);
            Date fecha2 = formatoFecha.parse(fechaDerecha);
            return fecha1.compareTo(fecha2) > 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
