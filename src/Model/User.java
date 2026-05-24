package Model;

public class User {
    private String username;
    private String password;
    private static int nextId = 1;
    private int id;
    public User (String username, String password) {
        this.username = username;
        this.password = password;
        this.id = nextId++;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
