package com.mycompany.schoolproject;

import com.mycompany.schoolproject.views.Login;
import com.mycompany.schoolproject.database.TablesMigrations;

public class Schoolproject {
    static Login window;
    public static void main(String[] args) {
        //TablesMigrations tables = new TablesMigrations();
        window = new Login();
        window.setVisible(true);
    }
    
    public Login instanceLogin()
    {
        return window;
    }
    
}
