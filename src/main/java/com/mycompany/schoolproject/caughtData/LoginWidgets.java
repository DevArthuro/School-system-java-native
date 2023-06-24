
package com.mycompany.schoolproject.caughtData;

import java.util.LinkedHashMap;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;

import com.mycompany.schoolproject.Schoolproject;
import com.mycompany.schoolproject.views.Login;

public class LoginWidgets {
    public LinkedHashMap dataUser()
    {
        // Recoger datos 
        Schoolproject interfaceUI = new Schoolproject();
        Login instance_login = interfaceUI.instanceLogin();
        LinkedHashMap data = instance_login.getData();
        JTextField nameUser = (JTextField) data.get("nameUser");
        JPasswordField password = (JPasswordField) data.get("password");
        JComboBox select = (JComboBox) data.get("rolUser");
        String rol = select.getSelectedItem().toString();
        LinkedHashMap<String, String> dataUserClean = new LinkedHashMap<String, String>()
        {{
            put("name", nameUser.getText());
            put("password", password.getText());
            put("role", rol == "-- Eligir Opción --" ? null : rol.toLowerCase());
        }};
        return dataUserClean;
    }
}