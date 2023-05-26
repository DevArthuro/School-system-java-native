package logica;

import java.util.LinkedHashMap;
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
        // Primera parte imprimir las preguntas 
        System.out.println("****** Estas son las preguntas *******");
        // Sacamos las llaves 
        for(int item : this.textosMain.keySet())
        {
            // Imprimimos y obtenemos el texto
            System.out.println(item + " : " + this.textosMain.get(item).getText());
        }
        
        // Imprimir las respuestas correctas seleccionadas 
        System.out.println("****** Estas son las respuestas elegidas *******");
        // Sacamos las llaves como tal 
        for(int item : this.radioButtonsValidate.keySet())
        {
            // Sacamos los radio buttons ya que son 4 en una lista o array
            for(JRadioButton select : this.radioButtonsValidate.get(item))
            {
                // Validamos cual está seleccionado
                if (select.isSelected())
                {
                    // Imprimimos el seleccionado 
                    System.out.println(item + " : " + select.getText());
                    // Cerramos ciclo y seguimos comparando otros radio buttons 
                    break;
                }
            }
        }
        
        System.out.println("****** Imprimir los textos de cada JRadioButton *******");
        
    }
}
