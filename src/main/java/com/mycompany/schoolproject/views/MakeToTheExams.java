package com.mycompany.schoolproject.views;

import logica.ValidationTestTeacher;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;

public class MakeToTheExams {
    public JPanel make(JTabbedPane table, LinkedHashMap<String, String> data, Integer numberQuestions)
    {
        // Creamos panel para poner examenes 
        JPanel panel = new JPanel();
        // Poner color al panel 
        panel.setBackground(new java.awt.Color(231, 242, 255));
        
        Font font = new Font("arial", Font.BOLD, 20);
        JLabel tittlePanel = new JLabel("Nombre examen: " + data.get("name"));
        tittlePanel.setFont(font);
        tittlePanel.setBounds(80, 10, 320, 50); // Ajusta la anchura a 200 para que se muestre completo
        panel.add(tittlePanel);
        
        //Configuramos las fuentes
        Font fontDate = new Font("arial", Font.BOLD, 12);
        
        // Labels para poner las fechas de apertura y cierre
        JLabel begin = new JLabel("Inicio: " + data.get("inicio"));
        JLabel finish = new JLabel("Final: " + data.get("fin"));
        JLabel asignature = new JLabel("Materia: " + data.get("asignature"));
        
        // configuraciones globales 
        begin.setFont(fontDate);
        finish.setFont(fontDate);
        asignature.setFont(fontDate);
        begin.setBounds(20, 50, 200, 20);
        finish.setBounds(20, 70, 150, 20);
        asignature.setBounds(180, 50, 150, 20);
        
        //Agregamos los labels al panel 
        panel.add(begin);
        panel.add(finish);
        panel.add(asignature);
        
        
        // Aca agregaremos los campos para preguntas y los combobox para las respuestas correctas
        /*
        El llenado se hara con ayuda de un for para ir colocando elementos
        Para guardar los elementos básicamente se guardan en un LinkedHashMap
        Se hara en una función aparte 
        */
        // Grilla de pocisionamiento  
        Integer[] grid = {30, 100, 300, 40}; //grilla de pocisionamiento
        Font fontQuestionary = new Font("arial", Font.BOLD, 10);
        
        // Guarda las preguntas
        LinkedHashMap<Integer, JTextField> textQuestions = new LinkedHashMap<>();
        //Guarda los radio buttons osea las opciones a,b,c,d -> buscar la seleccionada
        LinkedHashMap<Integer, JRadioButton[]> optionsLetters = new LinkedHashMap<>();
        // Guarda los texto de las preguntas a,b,c,d pero especificamente los textos de cada radio button 
        LinkedHashMap<Integer, JTextField[]> optionsText = new LinkedHashMap<>();
        
        for(Integer pregunta = 1; pregunta <= numberQuestions; pregunta++)
        {
            int gridY = grid[1];
            grid[1] += 150;
            
            // creación de elemtnos y configuración 
            JLabel numeroPregunta = new JLabel(pregunta.toString());
            JTextField campoPregunta = new JTextField();
            textQuestions.put(pregunta, campoPregunta);
            // configuracipones adicionales       
            numeroPregunta.setFont(fontQuestionary);
            campoPregunta.setFont(fontQuestionary);
                    
            //posicionamiento 
            numeroPregunta.setBounds(grid[0], gridY, 30, 40);
            campoPregunta.setBounds(grid[0] + 30, gridY, 300, 30);
             
            // Agregar opciones de respuesta a, b, c, d y la estructuración 
            String[] letras = {"a", "b", "c", "d"};
            // pocision de la grilla en y
            int pos = 25;
            
            ButtonGroup grupo = new ButtonGroup(); // Agrupamos los JRadioButtons para que solo haya una selacción 
            //Creación de listas almacenadoras y posterior agracion a los hashmap
            JRadioButton[] buttonsLetter = new JRadioButton[4];
            JTextField[] textFields = new JTextField[4];
            //for para poner los botones
            for (int i = 0; i < 4; i++) 
            {
                // declaración de elementos 
                JTextField text = new JTextField();
                JRadioButton boton = new JRadioButton(letras[i]);
                
                // Configuraciones 
                text.setFont(fontQuestionary);
                boton.setFont(fontQuestionary);
                
                // Pocisionamiento de elementos 
                boton.setBounds(grid[0] + 30, gridY + pos, 40, 40);
                text.setBounds(grid[0] + 70, gridY + pos+10, 300, 20);
                
                // Agregación de elementos al panel
                panel.add(boton);
                panel.add(text);
                
                // Agregar los radio buttons 
                grupo.add(boton);
                
                // Aumento de pocisiones en y
                pos += 25;
                
                // Almacenaje de la botonera y su texto 
                buttonsLetter[i] = boton;
                textFields[i] = text;
            }
            // Agregar elementos a diccionarios, serán validados en otra clase 
            optionsLetters.put(pregunta, buttonsLetter);
            optionsText.put(pregunta, textFields);
                    
            //agregar al panel 
            panel.add(numeroPregunta);
            panel.add(campoPregunta);
        }
        
        int heightPanel = grid[1];// height del panel para adaptarle un scroll bar
        // boton enviar form
        JButton boton = new JButton("Enviar"); // boton para enviar form
        //agregar acción al boton -> Recoger datos
        boton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                //  Creamos la instancia donde enviaremos la validación -> pasamos paramtros y validamos 
                ValidationTestTeacher validate = new ValidationTestTeacher(textQuestions, optionsLetters, optionsText);
            }
        });
        // agregar boton
        int gridY = grid[1];
        heightPanel += 120;
        boton.setBounds(30, gridY + 30 , 150, 40);
        panel.add(boton);
        
        
        // Le damos una dimensión al panel 
        Dimension size = new Dimension(480, heightPanel);
        // Agregamos la dimensión que definimos 
        panel.setPreferredSize(size);
        // Le damos un scroll bar al panel pra el desplazamiento 
        // Crear un JScrollPane y agregar el JPanel a él
        JScrollPane scrollPane = new JScrollPane(panel);
        // Le damos coordenadas al scroll pane
        scrollPane.setBounds(440, 0, 40, 304);
        // Evitamos el scroll horizontal
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
        
        
        
        
        panel.setLayout(null);
        // Agregamos el scrollpane a la tabla por que el scroll contiene el panel ya 
        table.addTab("Asignar preguntas", scrollPane);
        
        return panel;
    }
    
    public void selectorsQuestions()
    {
        
    }
    
}
