import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Cell extends Label {
    State state = State.EMPTY;

    Cell(String displayedText, Engine engine) {
        super(displayedText);
        setOnMouseClicked(event -> {
            if (state.equals(State.EMPTY))
            setState(engine.getState());
            engine.reactOnChanges();
        });
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);
    }

    void setState(State state) {
        this.state = state;
        updateState();
    }

    public State getState() {
        return state;
    }

    private void updateState() {
        if (state.equals(State.O)) {
            setText("FUPM");
        } else if (state.equals(State.X)) {
            setText("FAKI");
        } else {
            setText("[  ]");
        }
    }

    boolean full() {
        return !state.equals(State.EMPTY);
    }



}
