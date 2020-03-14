public class Player {
    State state;
    Player(State state) {
        this.state = state;
    }

    State getState() {
        return state;
    }
}
