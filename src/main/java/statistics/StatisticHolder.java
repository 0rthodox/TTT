package statistics;

import com.sun.deploy.util.StringUtils;
import utils.FileManager;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;

public class StatisticHolder {
    private final Path path;
    private Set<PlayerStatistic> statistics;

    //Getter for statistics
    public Set<PlayerStatistic> getStatistics() {
        return statistics;
    }

    //Constructor
    public StatisticHolder() {
        try {
            path = Paths.get(getClass().getResource("/stats.txt").toURI());
            if (path.toFile().exists()) {
                statistics = parseStats(FileManager.readPath(path));
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // Parsing statistics from strings list
    private Set<PlayerStatistic> parseStats(List<String> stats) {
        AtomicInteger counter = new AtomicInteger();
        return stats
                .stream()
                .collect(Collectors.
                        groupingBy(str -> counter.getAndIncrement() / 3))
                .values()
                .stream()
                .map(strings -> new PlayerStatistic(strings.get(0),
                        Integer.parseInt(strings.get(1)),
                        Integer.parseInt(strings.get(2))))
                .collect(Collectors.toSet());
    }

    // Converting statistics to strings list
    private List<String> stringifyStats() {
        return statistics
                .stream()
                .map(statistic -> {
            List<String> stringedStatistic = new ArrayList<>(3);
            stringedStatistic.add(statistic.getName());
            stringedStatistic.add(String.valueOf(statistic.getWins()));
            stringedStatistic.add(String.valueOf(statistic.getLosses()));
            return stringedStatistic;
        }).collect(Collectors.toList())
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    // Saving statistics to file
    public void saveStatistics() {
        FileManager.save(path, StringUtils.join(stringifyStats(), lineSeparator()));
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
