package stats;

import com.sun.deploy.util.StringUtils;
import utils.FileManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.System.lineSeparator;

public class StatsManager {
    Path path = Paths.get("resources/stats.txt");
    Set<Stats> stats = new HashSet<>();
    StatsManager() {
        if (path.toFile().exists()) {
            parseStats(FileManager.readPath(path));
        }
    }

    void parseStats(List<String> stats) {
        int iterator = 0;
        while (iterator < stats.size()) {
            String[] stringedStats = new String[3];
            for (int i = 0; i < 3; ++i) {
                stringedStats[i] = stats.get(iterator++);
            }
            this.stats.add(Stats.parseStats(stringedStats));
        }
    }
    List<String> stringeStats() {
        List<String> stringedStats = new ArrayList<>();
        for(Stats statsEntry : stats) {
            stringedStats.addAll(Arrays.asList(statsEntry.stringifyStats()));
        }
        return stringedStats;
    }

    void saveStats() {
        FileManager.save(path, StringUtils.join(stringeStats(), lineSeparator()));
    }

    void addStats(Stats stats) {
        this.stats.add(stats);
    }

}
