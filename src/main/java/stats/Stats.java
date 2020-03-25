package stats;

public class Stats {
    String name;
    Integer wins;
    Integer losses;

    public String getName() {
        return name;
    }

    public Integer getWins() {
        return wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public Stats(String name, Integer wins, Integer losses) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
    }

    public Stats(String name) {
        this.name = name;
    }

    static Stats parseStats(String[] stats) {
        return new Stats(stats[0], Integer.parseInt(stats[1]), Integer.parseInt(stats[2]));
    }

    String[] stringifyStats() {
        String[] stringedStats = new String[3];
        stringedStats[0] = name;
        stringedStats[1] = wins.toString();
        stringedStats[2] = losses.toString();
        return stringedStats;
    }

    public boolean equals(Stats stats) {
        if (stats == null) {
            return false;
        }
        return this.name.equals(stats.name);
    }
}
