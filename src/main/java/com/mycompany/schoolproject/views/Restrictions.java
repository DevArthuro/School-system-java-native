package com.mycompany.schoolproject.views;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import java.util.Date; 
import javax.swing.JSpinner;

public class Restrictions {
    // funcion que modificara la tabla de opciones del inicio
    /*
    Recibe dos parametros 
    1. recibe la tabla 
    2. Recibe el rol ya que debemos saber si el que entra es estudiante o profesor 
    
    Retorna el panel y este será modificado en otra función 
    */
    public JPanel tableReder(JTabbedPane tabla, String rol)
    {
        // Instanciamos un panel para agregar al table
        JPanel panel = new JPanel();
        panel.setBackground(new java.awt.Color(231, 242, 255));
        // Le damos una dimensión al panel 
        Dimension size = new Dimension(480, 304);
        // Agregamos la dimensión que definimos 
        panel.setPreferredSize(size);
        // Verificamos que tipo de rol es 
        switch (rol)
        {
            case "profesor" -> 
            {
                // si es profesor crea un panel de "programar examenes"
                Font font = new Font("arial", Font.BOLD, 20);
                JLabel tittlePanel = new JLabel("Programar Examenes");
                tittlePanel.setFont(font);
                tittlePanel.setBounds(150, 10, 250, 50); // Ajusta la anchura a 200 para que se muestre completo
                panel.add(tittlePanel);
                JButton button = new JButton("Subir examen");
                button.setBounds(180, 150, 140, 30);
                panel.setLayout(null);
                // Agregar evento onClick
                button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                      // Llamar a la función uploadExam()
                        uploadExam();
                    }
                });
                panel.add(button);
                tabla.addTab("Programar exámenes", panel);
                break;
            }
            case "estudiante" -> 
            {
                // Si es estudiante le damos un panel de pendientes 
                tabla.addTab("Pendientes", panel);
                break;
            }
        }
        // Rertornamos el panel 
        return panel;
    }
    
    // Funcion para rederizar el JOptionPane emergente 
    public void uploadExam() {
        // instanciamos una Clase de JPanel
        JPanel panel = new JPanel();
        // Se anula el layout por defecto
        panel.setLayout(null);
        
        // le damos medida al panel
        panel.setPreferredSize(new Dimension(300, 200));
        
        /*
        En el siguiente apartado se realizaran los widgets que llevara el JOptionPane
        Todos los widgets se guardaran sobre un panel de forma que al JOptionPane le demos el Panel y lo renderice 
        */
        
        
        // Se define el texto donde entrara el nombre del examen y se agrega al panel
        JTextField nameText = new JTextField();
        nameText.setBounds(150, 35, 140, 30);
        panel.add(nameText);
        
        // Se define un selector que hará las veces de seleccionar una materia
        // Se le asigna un modelo y se agrega al panel
        JComboBox materias = new JComboBox();
        materias.setModel(new DefaultComboBoxModel<>(new String[] {"--Elija Materia--", "materia1", "materia2"}));
        materias.setBounds(150, 65, 140, 30);
        panel.add(materias);
        
        //Agregamos en un label estatico la fecha actual, que hace referncia a la hora incio de examen 
        // Obtener la fecha actual
        LocalDate today = LocalDate.now();
        
        // Imprimir la fecha actual en formato ISO
        JLabel dateLocal = new JLabel(today.toString()); 
        dateLocal.setBounds(150, 95, 140, 30);
        panel.add(dateLocal);
        
        JButton botonDateBegin = new JButton("Ingrese Fecha de comienzo");
        botonDateBegin.setBounds(150, 130, 140, 30);
        panel.add(botonDateBegin);
        botonDateBegin.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Llamar a la función uploadExam()
            String fecha = giveDate();
            System.out.println(fecha);
            }
        });
        
        // Creamos los labels con su nombre 
        JLabel label1 = new JLabel("Nombre Examen");
        JLabel label2 = new JLabel("Materia");
        JLabel label3 = new JLabel("Fecha Apetura");
        JLabel label4 = new JLabel("Fecha de cierre");
        JLabel label5 = new JLabel("Número de preguntas");
        // Con la variable "positionY" los ordenaremos en la coordenada "y" la coordena "x" ya va por defecto 
        int positionY = 10;
        // Se define una lista de labels para realizar lo siguiente 
        // agregarle las configuraciones a todos a la vez 
        JLabel[] labels = {label1, label2, label3, label4, label5};
        
        // Definimos un for que itera los labels y les agregamos la configuraciones dentro del for 
        for(JLabel label : labels){
            // les damos las coordenadas y tamaño 
            label.setBounds(10, positionY, 150, 80);
            // Se agrega al panel 
            panel.add(label);
            // Se aumenta la variable "positionY" para el siguiente label 
            positionY += 30;
        }
        // Se define el JOptionPane
        int option = JOptionPane.showOptionDialog(
        null,                               // Componente padre (null para centrarlo en la pantalla)
            panel,                              // Panel personalizado
            "Configurar Examen",                        // Título del diálogo
            JOptionPane.DEFAULT_OPTION,       // Botones a mostrar
            JOptionPane.PLAIN_MESSAGE,          // Tipo de mensaje
            null,                               // Icono (null para no mostrar)
            new String[] {"Aceptar"}, // Textos de los botones
            null
        );                              // Botón predeterminado

        // si el usuario da aceptar entra a este condicional 
        if (option == JOptionPane.OK_OPTION) {
            // El usuario ha hecho clic en el botón "Aceptar"
            System.out.println("El usuario ha hecho clic en Aceptar");
        }
    }
    public String giveDate() {
        Date dateThisMoment = new Date();
        int day = dateThisMoment.getDay();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Calendar.MONTH, day);
        SpinnerDateModel dateModel = new SpinnerDateModel(calendar.getTime(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(dateModel);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy")); // formato de fecha

        JPanel panel = new JPanel();
        panel.add(spinner);

        int result = JOptionPane.showOptionDialog(null, panel, "Seleccione una fecha", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (result == JOptionPane.OK_OPTION) {
            Date date = (Date) spinner.getValue();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); // formato de fecha
            return format.format(date);
        } else {
            return null;
        }
    }

}
