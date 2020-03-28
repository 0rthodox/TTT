package statistics;

import com.sun.deploy.util.StringUtils;
import utils.FileManager;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.System.lineSeparator;

public class StatisticsManager {
    private final Path path;
    private final Set<PlayerStatistic> statistics;

    //Getter for statistics
    public Set<PlayerStatistic> getStatistics() {
        return statistics;
    }

    //Constructor
    public StatisticsManager() {
        statistics = new HashSet<>();
        try {
            path = Paths.get(getClass().getResource("/stats.txt").toURI());
            if (path.toFile().exists()) {
                parseStats(FileManager.readPath(path));
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // Parsing statistics from strings list
    private void parseStats(List<String> stats) {
        int iterator = 0;
        if (stats.size() % 3 == 0) {
            while (iterator < stats.size()) {
                String[] stringedStats = new String[3];
                for (int i = 0; i < 3; ++i) {
                    stringedStats[i] = stats.get(iterator++);
                }
                this.statistics.add(PlayerStatistic.parseStats(stringedStats));
            }
        }
    }

    // Converting statistics to strings list
    private List<String> stringeStats() {
        List<String> stringedStats = new ArrayList<>();
        for(PlayerStatistic statsEntry : statistics) {
            stringedStats.addAll(Arrays.asList(statsEntry.stringifyStats()));
        }
        return stringedStats;
    }

    // Saving statistics to file
    public void saveStatistics() {
        FileManager.save(path, StringUtils.join(stringeStats(), lineSeparator()));
    }

    //Updating winner statistics
    public void giveWin(String winner) {
        findOrCreateNewStatistic(winner).increaseWins();
    }

    //Updating loser statistics
    public void giveLoss(String loser) {
        findOrCreateNewStatistic(loser).increaseLosses();
    }

    //Finding desired statistics in set or creating new if doesn't exist
    private PlayerStatistic findOrCreateNewStatistic(String player) {
        PlayerStatistic found = statistics
                .stream()
                .filter(statistic -> statistic.getName().equals(player))
                .findAny()
                .orElse(null);
        if (found == null) {
            PlayerStatistic newStat = new PlayerStatistic(player, 0, 0);
            statistics.add(newStat);
            return newStat;
        } else {
            return found;
        }
    }
}
