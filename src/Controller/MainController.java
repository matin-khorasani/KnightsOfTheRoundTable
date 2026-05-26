package Controller;
import Model.App;
import Model.Enums.KnightName;

import java.util.ArrayList;

public class MainController {
    private App app;
    private ArrayList<KnightName> team1;
    private ArrayList<KnightName> team2;
    private boolean isChoosingPhase;
    private String opponentUsername;
    public MainController(App app) {
        this.app = app;
        this.team1 = new ArrayList<>();
        this.team2 = new ArrayList<>();
        this.isChoosingPhase = false;
        this.opponentUsername = null;
    }

    public String logout() {
        app.setCurrentLoggedInUser(null);
        return "logged out successfully";
    }
    public String showKnightsDetails() {
        StringBuilder output = new StringBuilder();
        output.append("characters:\n");
        output.append("--------------------\n");
        for (KnightName name : KnightName.values()) {
            output.append("name: " + capitalize(name.name()) + " - class: " + name.getKnightClass().name().toLowerCase());
            output.append("\nHP: ").append(name.getMaxHp());
            output.append(" - attack: ").append(name.getAttack());
            output.append(" - magic attack: ").append(name.getMagicAttack());
            output.append(" - defense: ").append(name.getDefense());
            output.append(" - speed: ").append(name.getSpeed());
            output.append("\nskills: ").append(name.getSkills().get(0)).append(" - ");
            output.append(name.getSkills().get(1)).append(" - ");
            output.append(name.getSkills().get(2)).append(" - ");
            output.append(name.getSkills().get(3)).append("\n");
            output.append("--------------------\n");
        }
        return output.toString();
    }
    public String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
    public String showScoreBoard() {
        return "you're now in scoreboard menu";
    }
    public String startGame(String enemyUsername) {
        enemyUsername = enemyUsername.replaceAll("\\s+" , "");
        if (enemyUsername.equals(app.getCurrentLoggedInUser().getUsername()))
            return "you can't play with yourself";
        if (app.findUserByUsername(enemyUsername) == null)
            return "invalid player name";
        this.opponentUsername = enemyUsername;
        this.isChoosingPhase = true;
        this.team1.clear();
        this.team2.clear();
        return "you're playing with " + enemyUsername;
    }
    public String choosingKnight(KnightName knightName) {
        int team = 1;
        if (team1.size() == 2)
            team = 2;
        if (team2.size() == 2)
            team = 0;
        boolean flag = false;
        for (KnightName name : KnightName.values()) {
            if (name.name().equalsIgnoreCase(knightName.name()))
                flag = true;
        }
        if (!flag)
            return "invalid knight name";
        if (team == 1) {
            if (team1.size() == 1 && team1.get(0).name().equalsIgnoreCase(knightName.name()))
                return "you've already chosen this knight";
            team1.add(knightName);
            return "knight selected successfully";
        }
        else if (team == 2) {
            if (team2.size() == 1 && team2.get(0).name().equalsIgnoreCase(knightName.name()))
                return "you've already chosen this knight";
            team2.add(knightName);
            return "knight selected successfully";
        }
        else {
            isChoosingPhase = false;
            return "mobarake kheylia";
        }
    }
    public boolean isChoosingPhase() {
        return isChoosingPhase;
    }

    public ArrayList<KnightName> getTeam1() {
        return team1;
    }

    public ArrayList<KnightName> getTeam2() {
        return team2;
    }

}
