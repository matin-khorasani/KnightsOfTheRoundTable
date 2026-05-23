package Controller;

import Model.App;
import Model.User;

public class SignupController {
    private App app;

    public SignupController(App app) {
        this.app = app;
    }

    public String signup(String username, String password) {
        if (!isValidUsername(username))
            return "invalid username";
        if (app.findUserByUsername(username) != null)
            return "username already exists";
        if (!isValidPassword(password))
            return "invalid password";
        User newUser = new User(username, password);
        app.addUser(newUser);
        return "registered Successfully";

    }
    public boolean isValidUsername(String username) {
        if (username.isEmpty()) return false;
        char first = username.charAt(0);
        if (!Character.isLetter(first)) return false;
        return username.matches("^[a-zA-Z0-9_]+$");
    }
    public boolean isValidPassword(String password) {
        if (password.isEmpty()) return false;
        char first = password.charAt(0);
        if (!Character.isLetter(first)) return false;
        if (password.contains(" "))
            return false;
        return password.matches(".*[%@#$^&!].*");
    }
    public String login(String username, String password) {
        User user = app.findUserByUsername(username);
        if (user == null) {
            return "username not found";
        }
        if (!user.getPassword().equals(password)) {
            return "password incorrect";
        }
        app.setCurrentLoggedInUser(user);
        return "logged in successfully";
    }
}
