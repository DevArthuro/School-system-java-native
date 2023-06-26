package logica;

import java.util.LinkedHashMap;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;



public class ValidationTestTeacher {
    // Inicializamos las variables de clase que van a interactuar toda la clase
    LinkedHashMap<Integer, JTextField> textosMain;
    LinkedHashMap<Integer, JRadioButton[]> radioButtonsValidate;
    LinkedHashMap<Integer, JTextField[]> textEverRadioButton;
    // Constructor que valida y inicializa las variables de clase 
    public ValidationTestTeacher(LinkedHashMap<Integer, JTextField> textosPreguntas, LinkedHashMap<Integer, JRadioButton[]> optionsLetters, LinkedHashMap<Integer, JTextField[]> optionsText)
    {
        // Las definimos con los parametros mandados por teclado 
        this.textosMain = textosPreguntas;
        this.radioButtonsValidate = optionsLetters;
        this.textEverRadioButton = optionsText;
        
        // Ejecutamos la función de impresión 
        this.print();
    }
    
    // Función de impresión 
    public void print()
    {
        boolean save = true;
        // Primera parte imprimir las preguntas 
        System.out.println("****** Estas son las preguntas *******");
        // Sacamos las llaves 
        for(int item : this.textosMain.keySet())
        {
            // Imprimimos y obtenemos el texto
            System.out.println(item + " : " + this.textosMain.get(item).getText());
            if (this.textosMain.get(item).getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "Los campos de preguntas deben ir diligenciados");
                save = false;
            }
                
        }
        
        // Imprimir las respuestas correctas seleccionadas 
        System.out.println("****** Estas son las respuestas elegidas *******");
        // Sacamos las llaves como tal 
        for(int item : this.radioButtonsValidate.keySet())
        {
            // Sacamos los radio buttons ya que son 4 en una lista o array
            int verify = 0;
            for(JRadioButton select : this.radioButtonsValidate.get(item))
            {
                // Validamos cual está seleccionado
                if (select.isSelected())
                {
                    // Imprimimos el seleccionado 
                    System.out.println(item + " : " + select.getText());
                    // Cerramos ciclo y seguimos comparando otros radio buttons 
                    verify += 1;
                    break;
                }
            }
            if (verify == 4){
                JOptionPane.showMessageDialog(null, "No tiene seleccionado algun JButton");
                save = false;
            }
        }
        
        System.out.println("****** Imprimir los textos de cada JRadioButton *******");
        
        for (int i = 1; i <= textEverRadioButton.size(); i++) 
        {
            System.out.println(textEverRadioButton);
            System.out.println("para la pregunta %d\nEstan los siguientes textos en respuestas....".formatted(i));
            for (JTextField item : textEverRadioButton.get(i))
            {
                if (item.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(null, "Los campos de respuesta deben estar diligenciados");
                    save = false;
                }
                System.out.println(item.getText());
            }
            if(!save){
                break;
            }
        }
    }
}
