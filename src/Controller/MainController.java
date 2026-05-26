package Controller;
import Model.App;
import Model.Enums.KnightName;
import Model.Knight;

import java.util.ArrayList;

public class MainController {
    private App app;
    private ArrayList<Knight> team1;
    private ArrayList<Knight> team2;
    private ArrayList<KnightName> team1KnightNames;
    private ArrayList<KnightName> team2KnightNames;
    private boolean isChoosingPhase;
    private String opponentUsername;
    public MainController(App app) {
        this.app = app;
        this.team1 = new ArrayList<>();
        this.team2 = new ArrayList<>();
        this.team1KnightNames = new ArrayList<>();
        this.team2KnightNames = new ArrayList<>();
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
            output.append("name: " + app.capitalize(name.name()) + " - class: " + name.getKnightClass().name().toLowerCase());
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
        Knight targetKnight = new Knight(knightName);
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
            if (team1.size() == 1 && team1.get(0).getKnightName().name().equalsIgnoreCase(knightName.name()))
                return "you've already chosen this knight";
            team1.add(targetKnight);
            team1KnightNames.add(targetKnight.getKnightName());
            return "knight selected successfully";
        }
        else if (team == 2) {
            if (team2.size() == 1 && team2.get(0).getKnightName().name().equalsIgnoreCase(knightName.name()))
                return "you've already chosen this knight";
            team2.add(targetKnight);
            team2KnightNames.add(targetKnight.getKnightName());
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

    public ArrayList<Knight> getTeam1() {
        return team1;
    }

    public ArrayList<Knight> getTeam2() {
        return team2;
    }

    public String getOpponentUsername() {
        return opponentUsername;
    }

    public ArrayList<KnightName> getTeam1KnightNames() {
        return team1KnightNames;
    }

    public ArrayList<KnightName> getTeam2KnightNames() {
        return team2KnightNames;
    }
}
