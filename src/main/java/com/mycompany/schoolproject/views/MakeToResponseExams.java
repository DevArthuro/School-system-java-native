/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.schoolproject.views;

import com.mycompany.schoolproject.database.ExecuteQuesries;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import logica.UploadAnswers;


public class MakeToResponseExams {
    public void uploadExam(String id_exam, int lenght, JTabbedPane table, LinkedHashMap<String, String> dataExam)
    {
        ResultSet data = new ExecuteQuesries().getQuestionsExam(id_exam);
        if (data == null)
        {
            JOptionPane.showMessageDialog(null, "Error al encontrar su examen");
        }
        else{
            // Creamos panel para poner examenes 
        JPanel panel = new JPanel();
        // Poner color al panel 
        panel.setBackground(new java.awt.Color(231, 242, 255));
        
        Font font = new Font("arial", Font.BOLD, 20);
        JLabel tittlePanel = new JLabel("Nombre examen: " + dataExam.get("title"));
        tittlePanel.setFont(font);
        tittlePanel.setBounds(80, 10, 320, 50); // Ajusta la anchura a 200 para que se muestre completo
        panel.add(tittlePanel);
        
        //Configuramos las fuentes
        Font fontDate = new Font("arial", Font.BOLD, 12);
        
        // Labels para poner las fechas de apertura y cierre
        JLabel begin = new JLabel("Inicio: " + dataExam.get("inicio"));
        JLabel finish = new JLabel("Final: " + dataExam.get("fin"));
        JLabel asignature = new JLabel("Materia: " + dataExam.get("asignature"));
        JLabel teacher = new JLabel("Profesor(a) %s".formatted(dataExam.get("teacher")));
        // configuraciones globales 
        begin.setFont(fontDate);
        finish.setFont(fontDate);
        asignature.setFont(fontDate);
        teacher.setFont(fontDate);
        begin.setBounds(20, 50, 200, 20);
        finish.setBounds(20, 70, 150, 20);
        asignature.setBounds(180, 50, 150, 20);
        teacher.setBounds(180, 70, 200, 20);
        int ejeY = 90;
        //Agregamos los labels al panel 
        panel.add(begin);
        panel.add(finish);
        panel.add(asignature);
        panel.add(teacher);
        Font fontQuestions = new Font("arial", Font.BOLD, 12);
        LinkedHashMap<Integer, JRadioButton[]> buttons = new LinkedHashMap<Integer, JRadioButton[]>();
        for (int i = 1; i <= lenght; i++) 
        {
            try{
            JLabel preguntaNumero = new JLabel(Integer.toString(i));
            preguntaNumero.setFont(fontQuestions);
            preguntaNumero.setBounds(20, ejeY, 40,20);
            JLabel textoPregunta = new JLabel(data.getString("contend"));
            
            //configuraciones
            textoPregunta.setFont(fontQuestions);
            textoPregunta.setBounds(50, ejeY, 300,20);
            
            
            
            ButtonGroup grupo = new ButtonGroup();
            JRadioButton buttona = new JRadioButton("a");
            JLabel textButtona = new JLabel(data.getString("answer_a"));
            JRadioButton buttonb = new JRadioButton("b");
            JLabel textButtonb = new JLabel(data.getString("answer_b"));
            JRadioButton buttonc = new JRadioButton("c");
            JLabel textButtonc = new JLabel(data.getString("answer_c"));
            JRadioButton buttond = new JRadioButton("d");
            JLabel textButtond = new JLabel(data.getString("answer_d"));
            JLabel[] buttonsText = {textButtona,textButtonb,textButtonc,textButtond};
            JRadioButton[] buttonsSelected = {buttona, buttonb, buttonc, buttond};
            buttons.put(i, buttonsSelected);
            int y = 20;
            for(JRadioButton item : buttonsSelected)
            {
                grupo.add(item);
                item.setFont(fontQuestions);
                item.setBounds(40, ejeY+y, 40, 20);
                panel.add(item);
                y+=20;
            }
            y = 20;
            for(JLabel item : buttonsText)
            {
                item.setFont(fontQuestions);
                item.setBounds(85, ejeY+y, 40, 20);
                panel.add(item);
                y+=20;
            }
            
            panel.add(preguntaNumero);
            panel.add(textoPregunta);
            ejeY+=120;
            data.next();
            }
            catch(Exception e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
            
        }
        
        
        JButton buttonSend = new JButton("Enviar");
        buttonSend.setFont(fontDate);
        buttonSend.setBounds(40, ejeY+10, 100, 30);
        buttonSend.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        LinkedHashMap<Integer, String> selected = new LinkedHashMap<Integer, String>();
                        boolean validate = true;
                        for (int i = 1; i <= buttons.size(); i++) {
                            int answer = 0;
                            for (int j = 0; j < 4; j++) {
                                if (buttons.get(i)[j].isSelected())
                                {
                                    selected.put(i, buttons.get(i)[j].getText());
                                    break;
                                }
                                answer += 1;
                            }
                            if (answer == 4)
                            {
                                validate = false;
                                JOptionPane.showMessageDialog(null, "En la pregunta %d no selecciono opción".formatted(i));
                            }
                        }
                        if(validate)
                        {
                            JOptionPane.showMessageDialog(null, "Se subieron las respuestas");
                            new UploadAnswers().validate(selected, id_exam);
                        }
                    }
                });
        panel.add(buttonSend);
        ejeY+=60;
        Dimension size = new Dimension(480, ejeY);
        // Agregamos la dimensión que definimos 
        panel.setPreferredSize(size);
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBounds(440, 0, 40, 304);
        // Evitamos el scroll horizontal
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Si es estudiante le damos un panel de pendientes 
        panel.setLayout(null);
        table.addTab("Examen %s".formatted(dataExam.get("title")), scroll);
        }
    }
}
