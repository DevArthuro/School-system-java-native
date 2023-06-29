package com.mycompany.schoolproject.views;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import java.util.Date; 
import java.util.LinkedHashMap;
import javax.swing.JSpinner;
import logica.ValidationExamProgram;
import com.mycompany.schoolproject.database.ManagementDataBase.registreSchedule;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import com.mycompany.schoolproject.database.ExecuteQuesries;
import com.mycompany.schoolproject.Schoolproject;


public class Restrictions {
    
    // definimos una variable que espera el JTable, esta nos servira para interactuar con el usuario 
    JTabbedPane tabla;
    
    // funcion que modificara la tabla de opciones del inicio
    /*
    Recibe dos parametros 
    1. recibe la tabla 
    2. Recibe el rol ya que debemos saber si el que entra es estudiante o profesor 
    
    Retorna el panel y este será modificado en otra función 
    */
    String dateFinal;
    public JPanel tableReder(JTabbedPane tabla, String rol)
    {
        this.tabla = tabla;
        // Instanciamos un panel para agregar al table
        JPanel panel = new JPanel();
        panel.setBackground(new java.awt.Color(231, 242, 255));
        // Le damos una dimensión al panel 
        // Verificamos que tipo de rol es 
        switch (rol)
        {
            case "profesor" -> 
            {
                Dimension size = new Dimension(480, 304);
                // Agregamos la dimensión que definimos 
                panel.setPreferredSize(size);
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
                int ejeY = 0;
                Font font = new Font("arial", Font.BOLD, 20);
                JLabel tittlePanel = new JLabel("Responder Examenes");
                tittlePanel.setFont(font);
                ejeY += 10;
                tittlePanel.setBounds(150,ejeY, 250, 50);
                panel.add(tittlePanel);
                LinkedHashMap<String, String> exams = new ExecuteQuesries().getAllExams();
                for (String item : exams.keySet()) 
                {
                    LinkedHashMap<String, String> dataExam = new ExecuteQuesries().requestData(exams.get(item));
                    Font fontTitle = new Font("arial", Font.BOLD, 13);
                    JButton buttonExam = new JButton("presentar");
                    ejeY += 70;
                    buttonExam.setFont(fontTitle);
                    buttonExam.setBounds(150,ejeY+20, 100, 20);
                    
                    buttonExam.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Llamar a la función uploadExam()
                            new MakeToResponseExams().uploadExam(exams.get(item),Integer.parseInt(dataExam.get("questions")), tabla, dataExam,buttonExam);
                        }
                    });
                    String document = new Schoolproject().instanceLogin().auth.getCredentials().get("document");
                    String password = new Schoolproject().instanceLogin().auth.getCredentials().get("password");
                    String id_user = new ExecuteQuesries().getDataUser(document, password).get("id");
                    buttonExam.setEnabled(!new ExecuteQuesries().requestQualifications(id_user, exams.get(item)));
                    JLabel title = new JLabel(item);
                    title.setFont(fontTitle);
                    
                    title.setBounds(40,ejeY, 100, 50);
                    Font fontdata = new Font("arial", Font.BOLD, 11);
                    JLabel data = new JLabel("Profesor(a): %s".formatted(dataExam.get("teacher")));
                    data.setFont(fontdata);
                    data.setBounds(20,ejeY+20, 480, 50);
                    JLabel dataAditional1 = new JLabel("titulo examen: %s, numero preguntas: %s, materia: %s".formatted(dataExam.get("title"), dataExam.get("questions"), dataExam.get("asignature")));
                    dataAditional1.setFont(fontdata);
                    dataAditional1.setBounds(20,ejeY+35, 480, 50);
                    JLabel dataDates = new JLabel("Fechas -> inicio: %s, fin: %s".formatted(dataExam.get("inicio"), dataExam.get("fin")));
                    dataDates.setFont(fontdata);
                    dataDates.setBounds(20,ejeY+50, 480, 50);
                    panel.add(buttonExam);
                    panel.add(data);
                    panel.add(dataAditional1);
                    panel.add(dataDates);
                    panel.add(title);
                }
                ejeY += 100;
                Dimension size = new Dimension(480, ejeY);
                // Agregamos la dimensión que definimos 
                panel.setPreferredSize(size);
                panel.setLayout(null);
                // Añadimos un scroll bar al pendientes ya que pueden aver examenes 
                JScrollPane scroll = new JScrollPane(panel);
                scroll.setBounds(440, 0, 40, 304);
                // Evitamos el scroll horizontal
                scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                // Si es estudiante le damos un panel de pendientes 
                tabla.addTab("Pendientes", scroll);
                break;
            }
            default ->
            {
                //
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
        materias.setModel(new DefaultComboBoxModel<>(new registreSchedule().dataStudent));
        materias.setBounds(150, 65, 140, 30);
        panel.add(materias);
        
        //Agregamos en un label estatico la fecha actual, que hace referncia a la hora incio de examen 
        // Obtener la fecha actual
        LocalDate today = LocalDate.now();
        LocalDateTime time = LocalDateTime.now();
        int year = today.getYear(),
            mounth = today.getMonthValue(),
            day = today.getDayOfMonth(),
            hour = time.getHour(), 
            minute = time.getMinute();
        
        JLabel dateLocal = new JLabel(day+ " / " + mounth + " / " + year  + " - " + hour + ":" + minute); 
        dateLocal.setBounds(160, 95, 140, 30);
        panel.add(dateLocal);
        
        JButton botonDateBegin = new JButton("Fecha Final");
        botonDateBegin.setBounds(150, 120, 140, 30);
        panel.add(botonDateBegin);
        botonDateBegin.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Llamar a la función uploadExam()
            String fecha = giveDate();
            dateFinal = fecha;
            }
        });
        
        JSpinner numberQuestions = new JSpinner();
        numberQuestions.setBounds(150, 155, 60, 30);
        panel.add(numberQuestions);
        
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
            // generamos un diccionarios con los campos del JOptionPane y con el dato captado 
            LinkedHashMap validation = new LinkedHashMap<>()
            {{
                put("nameExam", nameText.getText());
                put("options", materias);
                put("number", numberQuestions.getValue());
                put("beginDate", dateLocal.getText());
                put("finalDate", dateFinal);
            }};
            // generamos una instancia de una capa logica que nos ayudara a validar
            ValidationExamProgram examValidation = new ValidationExamProgram();
            // Pasamos la tabla para agregar el panel 
            // agregamos un argumento de linkedhashmap que nos mandara los datos ingresador por el usuario 
            examValidation.validateDateExam(this.tabla, validation);
        }
    }
    
    // Clase que lanza ventana emergente para seleccionar una fecha 
    public String giveDate() {
    // Instanciamos Calendar para tener un formato de fecha 
    Calendar calendar = Calendar.getInstance();
    // Sacamos la fecha actual
    LocalDate dateNow = LocalDate.now();
    //insertamos en el calendario, las fechas actuales con la instancia de LocalDate
    calendar.set(dateNow.getYear(), Calendar.DAY_OF_MONTH, dateNow.getDayOfMonth());
    // Creamos un modelo de mostrar fecha le instertamos como fecha lo que definimos en calendar
    /*
    En los paratros hicimos lo siguiente 
    - En valor insertamos la fecha actual 
    - en comienzo y fin, lo anulamos 
    - y en el ultimo modificamos el mes por el actual 
    */
    SpinnerDateModel dateModel = new SpinnerDateModel(calendar.getTime(), null, null, dateNow.getMonthValue());
    // insertamos el modelo a un JSpinner 
    JSpinner spinner = new JSpinner(dateModel);
    // Le damos un formto al spinner para proyectar la fecha 
    spinner.setEditor(new JSpinner.DateEditor(spinner, "dd-MM-yyyy - HH:mm")); // formato de fecha con HH para horas de 0 a 23
    //creamos el panel donde insertaremos el campo de JSppiner 
    JPanel panel = new JPanel();
    // Lo agregamos al panel 
    panel.add(spinner);
    // Definimos la ventana emergente y le insertamos el panel y le asignamos un boton unicamente 
    int result = JOptionPane.showOptionDialog(null, panel, "Seleccione una fecha", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

    // validamos si le dio al boton 
    if (result == JOptionPane.OK_OPTION) {
        // Obtenemos lo que se puso en el Spinner en formato fecha
        Date date = (Date) spinner.getValue();
        // Definimos el formato que queremos captar, o el que el usuario entro en el campo
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // formato de fecha con HH para horas de 0 a 23
        return format.format(date); // retornamos la hora de cierre
    } else {
        return null;
    }
}


}
