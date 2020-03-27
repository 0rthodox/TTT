package statistics;

import com.sun.deploy.util.StringUtils;
import utils.FileManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.System.lineSeparator;

public class StatisticsManager {
    Path path = Paths.get("src/main/resources/stats.txt");
    Set<Statistics> stats = new HashSet<>();
    public StatisticsManager() {
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
                this.stats.add(Statistics.parseStats(stringedStats));
            }
        }
    }
    List<String> stringeStats() {
        List<String> stringedStats = new ArrayList<>();
        for(Statistics statsEntry : stats) {
            stringedStats.addAll(Arrays.asList(statsEntry.stringifyStats()));
        }
        return stringedStats;
    }

    public void saveStats() {
        System.out.println("Saving");
        FileManager.save(path, StringUtils.join(stringeStats(), lineSeparator()));
    }

    public void giveWin(String winner) {
        updateStats(winner, 1, 0);
    }

    public void giveLoss(String loser) {
        updateStats(loser, 0, 1);
    }
    private void updateStats(String winner, int wins, int losses) {
        Statistics foundStats = get(new Statistics(winner));
        if (foundStats == null) {
            stats.add(new Statistics(winner, wins, losses));
        } else {
            stats.remove(foundStats);
            foundStats.losses += losses;
            foundStats.wins += wins;
            stats.add(foundStats);
        }
    }
    Statistics get(Statistics stats) {
        return this.stats.stream().filter(stats::equals).findAny().orElse(null);
    }

    public Set<Statistics> getStats() {
        return stats;
    }
}
