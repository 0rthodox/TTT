import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Field extends GridPane {
    FieldModel fieldModel = new FieldModel();
    Field() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                Cell cell = new Cell("ij");
                cell.textProperty().bind(fieldModel.getCells()[i][j]);
                this.add(cell, i, j);
            }
        }

    }
}
