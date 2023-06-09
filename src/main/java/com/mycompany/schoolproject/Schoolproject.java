package com.mycompany.schoolproject;

import com.mycompany.schoolproject.views.Inicio;
import com.mycompany.schoolproject.database.TablesMigrations;

public class Schoolproject {

    public static void main(String[] args) {
        TablesMigrations tables = new TablesMigrations();
        Inicio window = new Inicio();
        window.setVisible(true);
    }
}
