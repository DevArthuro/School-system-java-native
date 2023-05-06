package com.mycompany.schoolproject.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MultimediaApi {
    public void config(JFrame root)
    {
        String rootRoute = System.getProperty("user.dir");
        rootRoute = rootRoute + "\\src\\main\\java\\com\\mycompany\\schoolproject\\files\\icono.png";
        Image icono = Toolkit.getDefaultToolkit().getImage(rootRoute); 
        root.setTitle("Aula 360°");
        root.setIconImage(icono);
        root.setResizable(false);
        root.getContentPane().setBackground(Color.white);
    }
    
    public void configPanelTop(JFrame root)
    {
        root.setSize(769, 500);
        // Se instancia panel y se agrega las siguientes confiraciones
        /*
        1. color
        2. dimension
        3. le retiramos el layout para ordenar por coordenadas
        */
        JPanel panel = new JPanel();
        panel.setBackground(Color.gray);
        panel.setPreferredSize(new Dimension(765, 55));
        panel.setLayout(null);
        
        /*
        Se instancian tres JLabels del menú 
        1. Asignamos dos litas una que guardas los labels y otra que guarda el nombre de las imagenes 
        */
        JLabel menuOptions = new JLabel();
        JLabel logo = new JLabel();
        JLabel outside = new JLabel();
        JLabel[] labels = {menuOptions, logo, outside}; 
        String[] images = {"menu45x45.png", "iconInicio45x45.png", "outside.png"};
        
        /*
        1. array de coordenadas sobre cada label encima del panel 
        */
        int[][] arrayCordenadas = {{15, 5, 45, 45},{360, 5, 45, 45},{700, 5, 45, 45}};
        
        // Definimos el contador de el array de coordenadas y de las imagenes, para recorres el array
        int count = 0;
        
        // Definimos una variable root que va a almacenar la ruta de la imagen
        String route;
        
        // Recorremos cada label y lo configuramos 1 a 1 
        for (JLabel label: labels ) 
        {
            // definimos el vecotr de cordenadas especifico del label
            int[] vector = arrayCordenadas[count];
            // Le damos las cordenada definidas al label
            /*
            Loa vectores se estructuran de la siguiente manera
            ventor 0 -> pocision en x
            vector 1 -> pocision en y
            vector 2 -> tamaño width
            vector 3 -> tamaño en height 
            */
            label.setBounds(vector[0], vector[1], vector[2],vector[3]);
            
            // después de configurar cada label se le agrega al panel 
            panel.add(label);
            // Traemos toda la ruta hasta el directorio de trabajo
            route = System.getProperty("user.dir");
            //les agregamos una imagen a cada label con la ruta de cada imagen 
            /*
            La estructura de la url es de la siguiente manera 
            1. route -> significa carpeta de desarrollo quiere decir se para sobre "schoolproject"
            2. ruta de la imagen 
            3. la variable de la imagen especifica 
            */
            label.setIcon(new javax.swing.ImageIcon(route + "\\src\\main\\java\\com\\mycompany\\schoolproject\\files\\" + images[count]));
            // aumentamos el contador para escalar el array de imagenes y cordenada a la par 
            count += 1;
        }
        // agregamos el panel al frame principal
        root.getContentPane().add(panel);
        // empaquetamos el panel y se guarda el cambio 
        root.pack();
        
    }
}
