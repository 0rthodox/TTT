package stats;

import com.sun.deploy.util.StringUtils;
import utils.FileManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.System.lineSeparator;
import static java.lang.System.setOut;

public class StatsManager {
    Path path = Paths.get("resources/stats.txt");
    Set<Stats> stats = new HashSet<>();
    public StatsManager() {
        if (path.toFile().exists()) {
            parseStats(FileManager.readPath(path));
        }
    }

    void parseStats(List<String> stats) {
        int iterator = 0;
        if (stats.size() % 3 == 0) {
            while (iterator < stats.size()) {
                String[] stringedStats = new String[3];
                for (int i = 0; i < 3; ++i) {
                    stringedStats[i] = stats.get(iterator++);
                }
                this.stats.add(Stats.parseStats(stringedStats));
            }
        }
    }
    List<String> stringeStats() {
        List<String> stringedStats = new ArrayList<>();
        for(Stats statsEntry : stats) {
            stringedStats.addAll(Arrays.asList(statsEntry.stringifyStats()));
        }
        return stringedStats;
    }

    public void saveStats() {
        FileManager.save(path, StringUtils.join(stringeStats(), lineSeparator()));
    }

    void addStats(Stats stats) {
        this.stats.add(stats);
    }

    public void giveWinAndLoss(String winner, String loser) {
        updateStats(winner, 1, 0);
        updateStats(loser, 0, 1);
    }

    public void giveWin(String winner) {
        updateStats(winner, 1, 0);
    }

    public void giveLoss(String loser) {
        updateStats(loser, 0, 1);
    }
    private void updateStats(String winner, int wins, int losses) {
        Stats foundStats = get(new Stats(winner));
        if (foundStats == null) {
            stats.add(new Stats(winner, wins, losses));
        } else {
            stats.remove(foundStats);
            foundStats.losses += losses;
            foundStats.wins += wins;
            stats.add(foundStats);
        }
    }
    Stats get(Stats stats) {
        return this.stats.stream().filter(stats::equals).findAny().orElse(null);
    }

    public Set<Stats> getStats() {
        return stats;
    }
}
