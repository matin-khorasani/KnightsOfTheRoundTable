package Model;

public class User {
    private String username;
    private String password;
    private static int nextId = 1;
    private int id;
    private int gamesPlayed;
    private int gamesWon;
    private int points;
    public User (String username, String password) {
        this.username = username;
        this.password = password;
        this.id = nextId++;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.points = 0;
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
    public int getGamesPlayed() { return gamesPlayed; }

    public void setGamesPlayed(int gamesPlayed) { this.gamesPlayed = gamesPlayed; }

    public int getGamesWon() { return gamesWon; }

    public void setGamesWon(int gamesWon) { this.gamesWon = gamesWon; }

    public int getPoints() { return points; }

    public void setPoints(int points) { this.points = points; }

    public int getId() {
        return id;
    }
}
