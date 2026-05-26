package Controller;

import Model.App;
import Model.Enums.KnightName;
import Model.Enums.SkillName;
import Model.HpManager;
import Model.Knight;
import Model.User;

import java.util.ArrayList;

public class GameController {
    private App app;
    private HpManager hpManager;
    private MainController mainController;
    private String currentPlayerUsername;
    private Knight currentKnight;
    private ArrayList<Knight> currentTeam;
    private ArrayList<Knight> enemyTeam;
    private ArrayList<KnightName> currentTeamWithKnightName;
    private ArrayList<KnightName> enemyTeamWithKnightName;
    private int turnIndex;

    public GameController(App app, MainController mainController, HpManager hpManager) {
        this.app = app;
        this.mainController = mainController;
        this.hpManager = hpManager;
        this.currentPlayerUsername = app.getCurrentLoggedInUser().getUsername();
        this.currentKnight = mainController.getTeam1().get(0);
        this.currentTeam = mainController.getTeam1();
        this.enemyTeam = mainController.getTeam2();
        this.currentTeamWithKnightName = mainController.getTeam1KnightNames();
        this.enemyTeamWithKnightName = mainController.getTeam2KnightNames();
        this.turnIndex = 0;
    }

    public String showTurn() {
        return "you are now playing as " + currentPlayerUsername + "'s " + app.capitalize(currentKnight.getKnightName().name());
    }

    public void manageTurn() {
        if (currentKnight != null && currentKnight.isAlive()) {
            currentKnight.setAp(currentKnight.getAp(), 2);
        }
        while (true) {
            turnIndex = (turnIndex + 1) % 4;
            if (turnIndex == 0) {
                currentPlayerUsername = app.getCurrentLoggedInUser().getUsername();
                currentKnight = mainController.getTeam1().get(0);
                currentTeam = mainController.getTeam1();
                enemyTeam = mainController.getTeam2();
                currentTeamWithKnightName = mainController.getTeam1KnightNames();
                enemyTeamWithKnightName = mainController.getTeam2KnightNames();
            } else if (turnIndex == 1) {
                currentPlayerUsername = app.getCurrentLoggedInUser().getUsername();
                currentKnight = mainController.getTeam1().get(1);
                currentTeam = mainController.getTeam1();
                enemyTeam = mainController.getTeam2();
                currentTeamWithKnightName = mainController.getTeam1KnightNames();
                enemyTeamWithKnightName = mainController.getTeam2KnightNames();
            } else if (turnIndex == 2) {
                currentPlayerUsername = mainController.getOpponentUsername();
                currentKnight = mainController.getTeam2().get(0);
                currentTeam = mainController.getTeam2();
                enemyTeam = mainController.getTeam1();
                currentTeamWithKnightName = mainController.getTeam2KnightNames();
                enemyTeamWithKnightName = mainController.getTeam1KnightNames();
            } else if (turnIndex == 3) {
                currentPlayerUsername = mainController.getOpponentUsername();
                currentKnight = mainController.getTeam2().get(1);
                currentTeam = mainController.getTeam2();
                enemyTeam = mainController.getTeam1();
                currentTeamWithKnightName = mainController.getTeam2KnightNames();
                enemyTeamWithKnightName = mainController.getTeam1KnightNames();
            }

            if (isGameOver()) {
                break;
            }


            if (!currentKnight.isAlive()) {
                continue;
            }

            if (currentKnight.isStunned()) {
                currentKnight.setStunned(false);
                continue;
            }

            break;
        }
    }

    public void skipTurn() {
        manageTurn();
    }

    public String showDetails() {
        StringBuilder output = new StringBuilder();
        output.append(currentPlayerUsername).append("'s ").append(app.capitalize(currentKnight.getKnightName().name())).append(" details: \n");
        output.append("--------------------\n");
        output.append("skills: \n");
        for (int i = 0; i < 4; i++) {
            SkillName skillName = currentKnight.getKnightName().getSkills().get(i);
            output.append(getSkillNameProper(skillName));
            output.append("-> AP: ").append(skillName.getApCost());
            output.append("\n");
        }
        output.append("--------------------\n");
        output.append("AP: ").append(currentKnight.getAp());
        output.append("\n--------------------\n");
        output.append("charms: \n");

        boolean hasCharms = false;

        if (currentKnight.getAttackPercent() > 100) {
            output.append("attack got buffed by: ").append(currentKnight.getAttackPercent() - 100).append("%\n");
            hasCharms = true;
        } else if (currentKnight.getAttackPercent() < 100) {
            output.append("attack got nerfed by: ").append(100 - currentKnight.getAttackPercent()).append("%\n");
            hasCharms = true;
        }

        if (currentKnight.getMagicAttackPercent() > 100) {
            output.append("magic attack got buffed by: ").append(currentKnight.getMagicAttackPercent() - 100).append("%\n");
            hasCharms = true;
        } else if (currentKnight.getMagicAttackPercent() < 100) {
            output.append("magic attack got nerfed by: ").append(100 - currentKnight.getMagicAttackPercent()).append("%\n");
            hasCharms = true;
        }

        if (currentKnight.getDefensePercent() > 100) {
            output.append("defense got buffed by: ").append(currentKnight.getDefensePercent() - 100).append("%\n");
            hasCharms = true;
        } else if (currentKnight.getDefensePercent() < 100) {
            output.append("defense got nerfed by: ").append(100 - currentKnight.getDefensePercent()).append("%\n");
            hasCharms = true;
        }

        if (currentKnight.getSpeedPercent() > 100) {
            output.append("speed got buffed by: ").append(currentKnight.getSpeedPercent() - 100).append("%\n");
            hasCharms = true;
        } else if (currentKnight.getSpeedPercent() < 100) {
            output.append("speed got nerfed by: ").append(100 - currentKnight.getSpeedPercent()).append("%\n");
            hasCharms = true;
        }

        if (!hasCharms) {
            output.append("you have no charms on yourself\n");
        }

        return output.toString().trim();
    }

    public String showStats(KnightName knightName, String username) {
        boolean flag = false;
        Knight targetKnight = null;
        if (!username.equals(app.getCurrentLoggedInUser().getUsername()) && !username.equals(mainController.getOpponentUsername()))
            return "player doesn't exist";
        if (username.equals(app.getCurrentLoggedInUser().getUsername())) {
            if (mainController.getTeam1().get(0).getKnightName().equals(knightName)) {
                targetKnight = mainController.getTeam1().get(0);
                flag = true;
            }
            if (mainController.getTeam1().get(1).getKnightName().equals(knightName)) {
                targetKnight = mainController.getTeam1().get(1);
                flag = true;
            }
        } else {
            if (mainController.getTeam2().get(0).getKnightName().equals(knightName)) {
                targetKnight = mainController.getTeam2().get(0);
                flag = true;
            }
            if (mainController.getTeam2().get(1).getKnightName().equals(knightName)) {
                targetKnight = mainController.getTeam2().get(1);
                flag = true;
            }
        }
        if (!flag) return "knight doesn't exist";
        StringBuilder output = new StringBuilder();
        output.append("name: ").append(app.capitalize(knightName.name())).append(" - class: ").append(knightName.getKnightClass().name().toLowerCase());
        output.append("\nHP: ").append(targetKnight.getHp()).append(" - attack: ").append(knightName.getAttack());
        output.append(" - magic attack: ").append(knightName.getMagicAttack());
        output.append(" - defense: ").append(knightName.getDefense());
        output.append(" - speed: ").append(knightName.getSpeed());
        return output.toString();
    }

    public String attack(KnightName knightName) {
        if (currentKnight.getKnightName() == knightName && !enemyTeamWithKnightName.contains(knightName))
            return "you can't attack yourself";
        if (currentTeamWithKnightName.contains(knightName) && !enemyTeamWithKnightName.contains(knightName))
            return "you can't attack your own teammate";
        if (!enemyTeamWithKnightName.contains(knightName))
            return "enemy doesn't exist";

        Knight defender = enemyTeam.get(enemyTeamWithKnightName.indexOf(knightName));

        if (!defender.isAlive())
            return "enemy doesn't exist";

        if (hpManager.dodgeStatus(currentKnight, defender)) {
            manageTurn();
            return "shokhosh enemy dodge dad\n" + app.capitalize(currentKnight.getKnightName().name()) + " is playing...";
        }

        int attack1 = currentKnight.getCurrentAttack();
        int magicAttack1 = currentKnight.getCurrentMagicAttack();
        int defense2 = defender.getCurrentDefense();

        int damage = Math.max(0, (int) Math.floor((attack1 + magicAttack1) / 2.0) - (int) Math.floor(0.3 * defense2));

        defender.applyDamage(damage, currentKnight);

        StringBuilder output = new StringBuilder();
        output.append("you dealt ").append(damage).append(" damage to ").append(app.capitalize(knightName.name()));

        if (!defender.isAlive()) {
            output.append("\n").append(app.capitalize(knightName.name())).append(" is dead!");
        }

        if (isGameOver()) {
            output.append("\n").append(endGame());
            return output.toString();
        } else {
            manageTurn();
            output.append("\n").append(app.capitalize(currentKnight.getKnightName().name())).append(" is playing...");
        }

        return output.toString();
    }

    public String useSkill(String skillInput, String targetKnightInput) {
        SkillName skillName = null;
        String normalizedSkill = skillInput.trim().replace(" ", "_").toUpperCase();
        for (SkillName sn : SkillName.values()) {
            if (sn.name().equals(normalizedSkill)) {
                skillName = sn;
                break;
            }
        }
        if (skillName == null) {
            return "skill doesn't exist";
        }
        if (!currentKnight.getKnightName().getSkills().contains(skillName)) {
            return "you don't have this skill";
        }

        boolean needsTarget = isSkillTargetSpecific(skillName);
        boolean isEnemyTarget = isSkillEnemyTarget(skillName);

        if (targetKnightInput != null && !needsTarget) {
            return "this skill doesn't need target";
        }

        Knight targetKnight = null;
        if (targetKnightInput != null) {
            KnightName targetKnightName = null;
            String normalizedTarget = targetKnightInput.trim().toUpperCase();
            for (KnightName kn : KnightName.values()) {
                if (kn.name().equals(normalizedTarget)) {
                    targetKnightName = kn;
                    break;
                }
            }

            if (targetKnightName == null) {
                return "selected knight doesn't exist";
            }

            if (isEnemyTarget) {
                int idx = enemyTeamWithKnightName.indexOf(targetKnightName);
                if (idx != -1) targetKnight = enemyTeam.get(idx);
            } else {
                int idx = currentTeamWithKnightName.indexOf(targetKnightName);
                if (idx != -1) targetKnight = currentTeam.get(idx);
            }

            if (targetKnight == null) {
                return "selected knight doesn't exist";
            }

            if (skillName != SkillName.REVIVE && !targetKnight.isAlive()) {
                return "selected knight doesn't exist";
            }
        }

        if (targetKnightInput == null && needsTarget) {
            return "this skill needs a target";
        }

        if (currentKnight.getAp() < skillName.getApCost()) {
            return "you don't have enough AP";
        }

        currentKnight.setAp(currentKnight.getAp() - skillName.getApCost(), 0);

        StringBuilder output = new StringBuilder();
        executeSkillActions(skillName, targetKnight, output);

        if (isGameOver()) {
            output.append("\n").append(endGame());
        } else {
            manageTurn();
            output.append("\n").append(app.capitalize(currentKnight.getKnightName().name())).append(" is playing...");
        }

        return output.toString();
    }

    private void executeSkillActions(SkillName skillName, Knight target, StringBuilder output) {
        String skillNameProper = getSkillNameProper(skillName);

        switch (skillName) {
            case SHIELD_BASH: {
                int damage = Math.max(0, currentKnight.calculateDamage(target, skillName));
                target.applyDamage(damage, currentKnight);
                output.append(skillNameProper).append(" dealt ").append(damage).append(" damage to ").append(app.capitalize(target.getKnightName().name()));
                if (!target.isAlive()) output.append("\n").append(app.capitalize(target.getKnightName().name())).append(" is dead!");
                target.setStunned(true);
                output.append("\n").append(app.capitalize(target.getKnightName().name())).append(" got stunned");
                break;
            }
            case FORTIFY: {
                for (Knight k : currentTeam) {
                    if (k.isAlive()) k.setDefensePercent(120);
                }
                output.append("team's defense buffed by 20%");
                break;
            }
            case STRIKE_COMMAND: {
                boolean first = true;
                for (Knight k : enemyTeam) {
                    if (k.isAlive()) {
                        int damage = Math.max(0, currentKnight.calculateDamage(k, skillName));
                        k.applyDamage(damage, currentKnight);
                        if (!first) output.append("\n");
                        output.append(skillNameProper).append(" dealt ").append(damage).append(" damage to ").append(app.capitalize(k.getKnightName().name()));
                        if (!k.isAlive()) output.append("\n").append(app.capitalize(k.getKnightName().name())).append(" is dead!");
                        first = false;
                    }
                }
                break;
            }
            case ARMOR_BREAK: {
                for (Knight k : enemyTeam) {
                    if (k.isAlive()) k.setDefensePercent(85);
                }
                output.append("enemy's defense nerfed by 15%");
                break;
            }
            case RALLY: {
                for (Knight k : currentTeam) {
                    if (k.isAlive()) k.applyHeal(20, false);
                }
                output.append("team got healed by 20%");
                break;
            }
            case SLASH:
            case HEAVY_STRIKE:
            case FIREBALL: {
                int damage = Math.max(0, currentKnight.calculateDamage(target, skillName));
                target.applyDamage(damage, currentKnight);
                output.append(skillNameProper).append(" dealt ").append(damage).append(" damage to ").append(app.capitalize(target.getKnightName().name()));
                if (!target.isAlive()) output.append("\n").append(app.capitalize(target.getKnightName().name())).append(" is dead!");
                break;
            }
            case LIFE_STEAL: {
                int damage = Math.max(0, currentKnight.calculateDamage(target, skillName));
                target.applyDamage(damage, currentKnight);
                output.append(skillNameProper).append(" dealt ").append(damage).append(" damage to ").append(app.capitalize(target.getKnightName().name()));
                if (!target.isAlive()) output.append("\n").append(app.capitalize(target.getKnightName().name())).append(" is dead!");
                target.setAttackPercent(80);
                output.append("\n").append(app.capitalize(target.getKnightName().name())).append("'s attack nerfed by 20%");
                break;
            }
            case BERSERK: {
                target.applyHeal(-20, false);
                if (!target.isAlive()) output.append(app.capitalize(target.getKnightName().name())).append(" died!");
                else output.append(app.capitalize(target.getKnightName().name())).append(" lost 20% HP");
                target.setAttackPercent(160);
                output.append("\n").append(app.capitalize(target.getKnightName().name())).append("'s attack buffed by 60%");
                break;
            }
            case LIGHTNING_STRIKE: {
                boolean first = true;
                for (Knight k : enemyTeam) {
                    if (k.isAlive()) {
                        int damage = Math.max(0, currentKnight.calculateDamage(k, skillName));
                        k.applyDamage(damage, currentKnight);
                        if (!first) output.append("\n");
                        output.append(skillNameProper).append(" dealt ").append(damage).append(" damage to ").append(app.capitalize(k.getKnightName().name()));
                        if (!k.isAlive()) output.append("\n").append(app.capitalize(k.getKnightName().name())).append(" is dead!");
                        first = false;
                    }
                }
                break;
            }
            case ICE_BLAST: {
                int damage = Math.max(0, currentKnight.calculateDamage(target, skillName));
                target.applyDamage(damage, currentKnight);
                output.append(skillNameProper).append(" dealt ").append(damage).append(" damage to ").append(app.capitalize(target.getKnightName().name()));
                if (!target.isAlive()) output.append("\n").append(app.capitalize(target.getKnightName().name())).append(" is dead!");
                target.setSpeedPercent(80);
                output.append("\n").append(app.capitalize(target.getKnightName().name())).append("'s speed nerfed by 20%");
                break;
            }
            case ARCANE_SURGE: {
                target.setMagicAttackPercent(130);
                output.append(app.capitalize(target.getKnightName().name())).append("'s magic attack buffed by 30%");
                break;
            }
            case SILENCE: {
                target.setStunned(true);
                output.append(app.capitalize(target.getKnightName().name())).append(" got stunned");
                break;
            }
            case HEAL: {
                target.applyHeal(40, false);
                output.append(app.capitalize(target.getKnightName().name())).append(" got healed by 40%");
                break;
            }
            case GROUP_HEAL: {
                for (Knight k : currentTeam) {
                    if (k.isAlive()) k.applyHeal(20, false);
                }
                output.append("team got healed by 20%");
                break;
            }
            case REVIVE: {
                if (target.isAlive()) {
                    output.append(app.capitalize(target.getKnightName().name())).append(" is not dead");
                } else {
                    target.applyHeal(10, true);
                    output.append("teammate revived");
                }
                break;
            }
            case CLEANSE: {
                for (Knight k : currentTeam) {
                    if (k.isAlive()) {
                        if (k.getAttackPercent() < 100) k.setAttackPercent(100);
                        if (k.getMagicAttackPercent() < 100) k.setMagicAttackPercent(100);
                        if (k.getDefensePercent() < 100) k.setDefensePercent(100);
                        if (k.getSpeedPercent() < 100) k.setSpeedPercent(100);
                    }
                }
                output.append("debuffs removed");
                break;
            }
        }
    }

    private boolean isSkillTargetSpecific(SkillName skillName) {
        return skillName == SkillName.SHIELD_BASH || skillName == SkillName.SLASH ||
                skillName == SkillName.HEAVY_STRIKE || skillName == SkillName.LIFE_STEAL ||
                skillName == SkillName.BERSERK || skillName == SkillName.FIREBALL ||
                skillName == SkillName.ICE_BLAST || skillName == SkillName.ARCANE_SURGE ||
                skillName == SkillName.SILENCE || skillName == SkillName.HEAL || skillName == SkillName.REVIVE;
    }

    private boolean isSkillEnemyTarget(SkillName skillName) {
        return skillName == SkillName.SHIELD_BASH || skillName == SkillName.STRIKE_COMMAND ||
                skillName == SkillName.ARMOR_BREAK || skillName == SkillName.SLASH ||
                skillName == SkillName.HEAVY_STRIKE || skillName == SkillName.LIFE_STEAL ||
                skillName == SkillName.FIREBALL || skillName == SkillName.LIGHTNING_STRIKE ||
                skillName == SkillName.ICE_BLAST || skillName == SkillName.SILENCE;
    }

    private String getSkillNameProper(SkillName skillName) {
        return skillName.name().toLowerCase().replace("_", " ");
    }

    public boolean isGameOver() {
        boolean team1Dead = !mainController.getTeam1().get(0).isAlive() && !mainController.getTeam1().get(1).isAlive();
        boolean team2Dead = !mainController.getTeam2().get(0).isAlive() && !mainController.getTeam2().get(1).isAlive();
        return team1Dead || team2Dead;
    }

    private String endGame() {
        User user1 = app.getCurrentLoggedInUser();
        User user2 = app.findUserByUsername(mainController.getOpponentUsername());

        boolean team1Dead = !mainController.getTeam1().get(0).isAlive() && !mainController.getTeam1().get(1).isAlive();

        User winnerUser, loserUser;
        ArrayList<Knight> winnerTeam, loserTeam;

        if (team1Dead) {
            winnerUser = user2;
            loserUser = user1;
            winnerTeam = mainController.getTeam2();
            loserTeam = mainController.getTeam1();
        } else {
            winnerUser = user1;
            loserUser = user2;
            winnerTeam = mainController.getTeam1();
            loserTeam = mainController.getTeam2();
        }

        winnerUser.setGamesPlayed(winnerUser.getGamesPlayed() + 1);
        loserUser.setGamesPlayed(loserUser.getGamesPlayed() + 1);
        winnerUser.setGamesWon(winnerUser.getGamesWon() + 1);

        int winnerKnight1Points = calculateKnightPoints(winnerTeam.get(0));
        int winnerKnight2Points = calculateKnightPoints(winnerTeam.get(1));
        int totalWinnerPoints = winnerKnight1Points + winnerKnight2Points;
        winnerUser.setPoints(winnerUser.getPoints() + totalWinnerPoints);

        int loserKnight1Points = calculateKnightPoints(loserTeam.get(0));
        int loserKnight2Points = calculateKnightPoints(loserTeam.get(1));
        int totalLoserPoints = loserKnight1Points + loserKnight2Points;
        loserUser.setPoints(loserUser.getPoints() + totalLoserPoints);

        StringBuilder output = new StringBuilder();
        output.append("war has ended\n");
        output.append("--------------------\n");
        output.append("winner: ").append(winnerUser.getUsername()).append(" - points: ").append(totalWinnerPoints).append("\n");
        output.append(getKnightEndGameString(winnerUser.getUsername(), winnerTeam.get(0), winnerKnight1Points)).append("\n");
        output.append(getKnightEndGameString(winnerUser.getUsername(), winnerTeam.get(1), winnerKnight2Points)).append("\n");
        output.append("--------------------\n");
        output.append("loser: ").append(loserUser.getUsername()).append(" - points: ").append(totalLoserPoints).append("\n");
        output.append(getKnightEndGameString(loserUser.getUsername(), loserTeam.get(0), loserKnight1Points)).append("\n");
        output.append(getKnightEndGameString(loserUser.getUsername(), loserTeam.get(1), loserKnight2Points));

        mainController.getTeam1().clear();
        mainController.getTeam2().clear();
        mainController.getTeam1KnightNames().clear();
        mainController.getTeam2KnightNames().clear();

        return output.toString();
    }

    private int calculateKnightPoints(Knight knight) {
        int damageDealt = knight.getDamageDealt();
        int initialHp = knight.getKnightName().getMaxHp();
        int damageTaken = Math.max(0, initialHp - knight.getHp());
        double c = knight.isAlive() ? 1.5 : 1.0;
        return (int) ((damageDealt - damageTaken) * c);
    }

    private String getKnightEndGameString(String username, Knight knight, int points) {
        String status = knight.isAlive() ? "alive" : "dead";
        return username + "'s " + app.capitalize(knight.getKnightName().name()) +
                ": damage dealt: " + knight.getDamageDealt() +
                " - HP remained: " + knight.getHp() +
                " - " + status + " - point: " + points;
    }
}