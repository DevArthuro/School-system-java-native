package com.mycompany.schoolproject;

import com.mycompany.schoolproject.views.Login;
import com.mycompany.schoolproject.database.TablesMigrations;
import com.mycompany.schoolproject.views.Inicio;

public class Schoolproject {
    static Login window;
    static Inicio windowInicio;
    public static void main(String[] args) {
        TablesMigrations tables = new TablesMigrations();
        window = new Login();
        window.setVisible(true);
    }
    
    public Login instanceLogin()
    {
        return window;
    }
    
    public Inicio instanceInicio()
    {
        return windowInicio;
    }
    
    public void setLogin(Login root)
    {
        window = root;
    }
    
    public void setInicio(Inicio root)
    {
        windowInicio = root;
    }
    
}
