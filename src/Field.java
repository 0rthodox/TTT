import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class Field extends GridPane {
    private final int GAP = 20;
    Field(Engine engine) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                Cell cell = new Cell("0", engine);
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

    List<Boolean> computeField() {
        List<Boolean> booleans = new ArrayList<>();
        for(Node cell : getChildren()) {
            if ((((Cell)cell).getState()).equals(State.X)) {
                booleans.add(true);
            } else {
                booleans.add(false);
            }
        }
        return booleans;
    }
}
