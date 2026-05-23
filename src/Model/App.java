package Model;

import java.util.ArrayList;

public class App {
    private ArrayList<User> users = new ArrayList<>();
    private User currentLoggedInUser;

    public App() {
        this.users = new ArrayList<>();
        this.currentLoggedInUser = null;
    }
    public void addUser(User user) {
        users.add(user);
    }
    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }

    public void setCurrentLoggedInUser(User currentLogedInUser) {
        this.currentLoggedInUser = currentLogedInUser;
    }
}
