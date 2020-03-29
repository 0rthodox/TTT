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
}
