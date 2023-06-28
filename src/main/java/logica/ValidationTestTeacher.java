package logica;

import java.util.LinkedHashMap;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import com.mycompany.schoolproject.database.ExecuteQuesries;
import com.mycompany.schoolproject.Schoolproject;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



public class ValidationTestTeacher {
    // Inicializamos las variables de clase que van a interactuar toda la clase
    LinkedHashMap<Integer, JTextField> textosMain;
    LinkedHashMap<Integer, JRadioButton[]> radioButtonsValidate;
    LinkedHashMap<Integer, JTextField[]> textEverRadioButton;
    LinkedHashMap<String, String> data;
    int lenght;
    JScrollPane pane;
    JPanel panelQuestions;
    // Constructor que valida y inicializa las variables de clase 
    public ValidationTestTeacher(LinkedHashMap<Integer, JTextField> textosPreguntas, LinkedHashMap<Integer, JRadioButton[]> optionsLetters, LinkedHashMap<Integer, JTextField[]> optionsText, LinkedHashMap<String, String> data)
    {
        // Las definimos con los parametros mandados por teclado 
        this.textosMain = textosPreguntas;
        this.radioButtonsValidate = optionsLetters;
        this.textEverRadioButton = optionsText;
        this.data = data;
        this.lenght = textosPreguntas.size();
        this.pane = pane;
        this.panelQuestions = panelQuestions;
        // Ejecutamos la función de impresión 
    }
    
    // Función de impresión 
    public boolean validations()
    {
        boolean save = true;
        for(int item : this.textosMain.keySet())
        {
            // Imprimimos y obtenemos el texto
            if (this.textosMain.get(item).getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "Los campos de preguntas deben ir diligenciados");
                save = false;
                break;
            }
                
        }
        
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
                    break; 
                    
                }
                verify += 1;
                
            }
            if (verify == 4){
                JOptionPane.showMessageDialog(null, "No tiene seleccionado algun JButton");
                save = false;
                break;
            }
            
        }
        
        for (int i = 1; i <= textEverRadioButton.size(); i++) 
        {
            for (JTextField item : textEverRadioButton.get(i))
            {
                if (item.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(null, "Los campos de respuesta deben estar diligenciados");
                    save = false;
                    break;
                }
            }
            if(!save){
                break;
            }
        }
        
        if (save)
        {
            JOptionPane.showMessageDialog(null, "Todo listo");
            String document = new Schoolproject().instanceLogin().auth.credentials.get("document");
            String password = new Schoolproject().instanceLogin().auth.credentials.get("password");
            data.put("number", Integer.toString(this.lenght));
            int id_exam = new ExecuteQuesries().insertExam(document, password, data);
            if (id_exam != -1){
                return validate_exam(id_exam);
            }
                
        }
        
        return false;
    }
    
    public boolean validate_exam(int id_exam)
    {
        try{
            
        
        for (int i = 1; i <= this.lenght; i++) 
        {
            String textQuestion = textosMain.get(i).getText();
            LinkedHashMap<String, String> textLetters= new LinkedHashMap<String, String>();
            String correctLetter = "";
            for (int j = 0; j < 4; j++) 
            {
                if (radioButtonsValidate.get(i)[j].isSelected())
                {
                    correctLetter = radioButtonsValidate.get(i)[j].getText();
                }
                textLetters.put(radioButtonsValidate.get(i)[j].getText(), "'"+textEverRadioButton.get(i)[j].getText()+"'");
            }
            new ExecuteQuesries().insertQuestions(id_exam, i, textQuestion, String.join(", ", textLetters.values()), correctLetter);
            
        }
        return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
}
