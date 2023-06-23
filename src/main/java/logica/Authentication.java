/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import com.mycompany.schoolproject.caughtData.LoginWidgets;
import com.mycompany.schoolproject.database.ExecuteQuesries;

public class Authentication {

    public boolean auth()
    {
        LoginWidgets lw = new LoginWidgets();
        ExecuteQuesries db = new ExecuteQuesries();
        
        String nameUser = (String) lw.dataUser().get("name");
        String password = (String) lw.dataUser().get("password");
        String role = (String) lw.dataUser().get("role");
        boolean auth = db.requestDataUser(nameUser, role, password);
        
        return auth;
    }
}
