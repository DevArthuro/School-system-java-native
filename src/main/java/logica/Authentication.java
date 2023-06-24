package logica;

import com.mycompany.schoolproject.caughtData.LoginWidgets;
import com.mycompany.schoolproject.database.ExecuteQuesries;
import java.util.Map;

public class Authentication {
    boolean verify = false;
    String actor;
    Map<String, String> credentials = Map.of();
    public boolean auth()
    {
        LoginWidgets lw = new LoginWidgets();
        ExecuteQuesries db = new ExecuteQuesries();
        
        String nameUser = (String) lw.dataUser().get("name");
        String password = (String) lw.dataUser().get("password");
        String role = (String) lw.dataUser().get("role");
        boolean auth = db.requestDataUser(nameUser, role, password);
        if (auth)
        {
            verify = auth;
            actor = role;
            credentials = Map.of("document", nameUser, "password", password);
        }
        return auth;
    }
    
    public boolean userSystem()
    {
        return verify;
    }
    
    public String actorRequest()
    {
        return actor;
    }
    
    public void resetUser()
    {
        actor = null;
        verify = false;
        credentials = Map.of();
    }
    
    public Map<String, String> getCredentials()
    {
        return credentials;
    }
}
