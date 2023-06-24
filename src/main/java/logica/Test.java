/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class Test {
   public LinkedHashMap<String, LinkedHashMap<Integer, String>> llenado()
   {
       LinkedHashMap<String, LinkedHashMap<Integer, String>> schuldle = new LinkedHashMap<String, LinkedHashMap<Integer, String>>(){{
            put("lunes", new LinkedHashMap<Integer, String>(){{
                put(1, "");
                put(2, "");
                put(3, "");
                put(4, "");
                put(5, "");
                put(6, "");
            }});
            put("martes", new LinkedHashMap<Integer, String>(){{
                put(1, "");
                put(2, "");
                put(3, "");
                put(4, "");
                put(5, "");
                put(6, "");
            }});
            put("miercoles", new LinkedHashMap<Integer, String>(){{
                put(1, "");
                put(2, "");
                put(3, "Sociales");
                put(4, "");
                put(5, "");
                put(6, "");
            }});
            put("jueves", new LinkedHashMap<Integer, String>(){{
                put(1, "");
                put(2, "");
                put(3, "");
                put(4, "");
                put(5, "");
                put(6, "");
            }});
            put("viernes", new LinkedHashMap<Integer, String>(){{
                put(1, "");
                put(2, "");
                put(3, "");
                put(4, "");
                put(5, "");
                put(6, "");
            }});
        }};

       
       return schuldle;
   }
   
   public LinkedHashMap<String, String>  datosUser(Map<String, String> data)
   {
       LinkedHashMap<String, String> mapeo = new LinkedHashMap<String, String>(){{
           put("nombre", data.get("name"));
           put("identificación", data.get("document"));
           put("telefono", data.get("phone"));
           put("Rol", data.get("role"));
           put("vinculacion", data.get("start"));
       }};
       
       return mapeo;
   }
}
