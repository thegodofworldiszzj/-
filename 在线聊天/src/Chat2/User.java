package Chat2;

public class User {
    private String username;
    private String password;


    public User(String strmsg) {
        String[] msg = strmsg.split(",");
        this.username = msg[0];
        this.password = msg[1];
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

}