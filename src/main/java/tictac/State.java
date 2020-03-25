package tictac;

public enum State {
    DASR(1),
    DCAM(-1),
    EMPTY(0);

    private int integer;

    State(int integer) {
        this.integer = integer;
    }

    public int getInteger() {
        return integer;
    }
}
