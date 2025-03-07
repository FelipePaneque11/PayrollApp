package payrollsystem;

public class SessionManager {
    private static SessionManager instance = null;
    private String user;
    private int id;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) instance = new SessionManager();
        
        return instance;
    }
    
    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    public int getID(){
        return id;
    }
    
    public void clearSession() {
        instance = null; // Clear session on logout
    }
}
