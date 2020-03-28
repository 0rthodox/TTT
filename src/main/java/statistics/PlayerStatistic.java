package statistics;

public class PlayerStatistic {
    private final String name;
    private int wins;
    private int losses;

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    void increaseWins() {
        wins++;
    }

    void increaseLosses() {
        losses++;
    }

    PlayerStatistic(String name, int wins, int losses) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
    }

    //Parsing statistic from strings
    static PlayerStatistic parseStats(String[] stats) {
        return new PlayerStatistic(stats[0], Integer.parseInt(stats[1]), Integer.parseInt(stats[2]));
    }

    //Converting statistic to strings
    String[] stringifyStats() {
        String[] stringedStats = new String[3];
        stringedStats[0] = name;
        stringedStats[1] = String.valueOf(wins);
        stringedStats[2] = String.valueOf(losses);
        return stringedStats;
    }

}
