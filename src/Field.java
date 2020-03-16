import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class Field extends GridPane {
    private final int GAP = 20;
    ImageManager imageManager = new ImageManager();
    Field(Engine engine) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                Cell cell = new Cell(imageManager);
                this.add(cell, i, j);
            }
        }
        this.setHgap(GAP);
        this.setVgap(GAP);
        VBox.setVgrow(this, Priority.ALWAYS);
        HBox.setHgrow(this, Priority.ALWAYS);
        engine.setField(this);
    }
    boolean full() {
        for(Node cell : getChildren()) {
            if (!(((Cell)cell).full())) {
                return false;
            }
        }
        return true;
    }

    List<Integer> computeField() {
        List<Integer> values = new ArrayList<>();
        for(Node cell : getChildren()) {
            if ((((Cell)cell).getState()).equals(State.DASR)) {
                values.add(1);
            } else if ((((Cell)cell).getState()).equals(State.DCAM)) {
                values.add(-1);
            } else {
                values.add(0);
            }
        }
        return values;
    }

    void reset() {
        System.out.println("Field : resetting");
        for(Node node : getChildren()) {
            Cell cell = (Cell)node;
            cell.setState(State.EMPTY);
        }
    }
}
