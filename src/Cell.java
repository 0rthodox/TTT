import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Cell extends Label {
    State state = State.EMPTY;
    Cell(String displayedText) {
        super(displayedText);
    }

}
