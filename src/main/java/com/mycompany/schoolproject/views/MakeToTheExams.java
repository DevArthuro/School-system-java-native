package com.mycompany.schoolproject.views;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MakeToTheExams {
    public JPanel make(JTabbedPane table)
    {
        // Creamos panel para poner examenes 
        JPanel panel = new JPanel();
        // Poner color al panel 
        panel.setBackground(new java.awt.Color(231, 242, 255));
        // Le damos una dimensión al panel 
        Dimension size = new Dimension(480, 304);
        // Agregamos la dimensión que definimos 
        panel.setPreferredSize(size);

        Font font = new Font("arial", Font.BOLD, 20);
        JLabel tittlePanel = new JLabel("Escriba sus preguntas");
        tittlePanel.setFont(font);
        tittlePanel.setBounds(150, 10, 250, 50); // Ajusta la anchura a 200 para que se muestre completo
        panel.add(tittlePanel);
        panel.setLayout(null);
                // Agregar evento onClick
              
        table.addTab("Asignar preguntas", panel);
        
        
        return panel;
    }
}
