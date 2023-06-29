package logica;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import com.mycompany.schoolproject.database.ExecuteQuesries;
import javax.swing.JOptionPane;
import com.mycompany.schoolproject.Schoolproject;
import java.util.Map;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class UploadAnswers {
    public void validate(LinkedHashMap<Integer, String> answer, String id, JTabbedPane table, JScrollPane scroll)
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
        
        Map<String, String> credentials = new Schoolproject().instanceLogin().auth.credentials;
        String correct_questions = Integer.toString(correct);
        String incorrect_questions = Integer.toString(incorrect);
        String qualification_string = Double.toString(correct * enverQualification);
        LinkedHashMap<String, String> dataQuealifications = new LinkedHashMap<String, String>(){{
            put("id_exam", id);
            put("id_user", new ExecuteQuesries().getDataUser(credentials.get("document"), credentials.get("password")).get("id"));
            put("correct",correct_questions);
            put("incorrects", incorrect_questions);
            put("qualification", qualification_string);
        }};
        new ExecuteQuesries().insertDataQualifications(dataQuealifications);
        table.remove(table.indexOfComponent(scroll));
        
    }
}
