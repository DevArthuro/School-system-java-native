/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.mycompany.schoolproject.database.ExecuteQuesries;

public class Test {
   public LinkedHashMap<String, LinkedHashMap<Integer, String>> llenado(String document, String password)
   {
       return new ExecuteQuesries().getSubjectsGroup(document, password);
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
