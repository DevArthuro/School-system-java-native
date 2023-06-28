package logica;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import com.mycompany.schoolproject.database.ExecuteQuesries;
import javax.swing.JOptionPane;


public class UploadAnswers {
    public void validate(LinkedHashMap<Integer, String> answer, String id)
    {
        int correct = 0;
        int incorrect = 0;
        ResultSet rs = new ExecuteQuesries().getQuestionsExam(id);
        for (int i = 1; i <= answer.size(); i++) 
        {
            try{
                if (answer.get(i).equals(rs.getString("letter_correct"))){
                    correct+=1;
                }
                else{
                    incorrect+=1;
                }
                rs.next();
            }catch(Exception e)
            {
                System.out.println(e);
            }
        }
        JOptionPane.showMessageDialog(null,"Correctas %d - Incorrectas %d".formatted(correct, incorrect));
        Double enverQualification = 5.0/answer.size();
        
        JOptionPane.showMessageDialog(null,"Su nota es: %f".formatted(correct * enverQualification));
    }
}
