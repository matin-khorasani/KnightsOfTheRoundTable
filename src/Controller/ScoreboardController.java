package Controller;

import Model.App;
import Model.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreboardController {
    private App app;

    public ScoreboardController(App app) {
        this.app = app;
    }

    public String showScoreboard(String sortType) {
        // ایجاد یک کپی از لیست یوزرها برای اینکه ترتیب لیست اصلی در App به هم نریزد
        ArrayList<User> sortedUsers = new ArrayList<>(app.getUsers());

        // بررسی نوع مرتب‌سازی با ignoring case طبق داکیومنت
        if (sortType.equalsIgnoreCase("games_played")) {
            Collections.sort(sortedUsers, new Comparator<User>() {
                @Override
                public int compare(User u1, User u2) {
                    if (u2.getGamesPlayed() != u1.getGamesPlayed()) {
                        return Integer.compare(u2.getGamesPlayed(), u1.getGamesPlayed()); // کاهشی
                    }
                    return Integer.compare(u1.getId(), u2.getId()); // اولویت با اونی که زودتر ساخته شده
                }
            });
            return formatOutput(sortedUsers, "games_played");

        } else if (sortType.equalsIgnoreCase("point")) {
            Collections.sort(sortedUsers, new Comparator<User>() {
                @Override
                public int compare(User u1, User u2) {
                    if (u2.getPoints() != u1.getPoints()) {
                        return Integer.compare(u2.getPoints(), u1.getPoints()); // کاهشی
                    }
                    return Integer.compare(u1.getId(), u2.getId());
                }
            });
            return formatOutput(sortedUsers, "point");

        } else if (sortType.equalsIgnoreCase("games_won")) {
            Collections.sort(sortedUsers, new Comparator<User>() {
                @Override
                public int compare(User u1, User u2) {
                    if (u2.getGamesWon() != u1.getGamesWon()) {
                        return Integer.compare(u2.getGamesWon(), u1.getGamesWon());
                    }
                    return Integer.compare(u1.getId(), u2.getId());
                }
            });
            return formatOutput(sortedUsers, "games_won");
        }

        return "invalid sort type";
    }
    private String formatOutput(ArrayList<User> users, String type) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            output.append(user.getUsername()).append("-> ");
            if (type.equals("games_played")) {
                output.append("games played: ").append(user.getGamesPlayed());
            } else if (type.equals("point")) {
                output.append("points: ").append(user.getPoints());
            } else if (type.equals("games_won")) {
                output.append("games won: ").append(user.getGamesWon());
            }
            if (i < users.size() - 1) {
                output.append("\n");
            }
        }
        return output.toString();
    }
}