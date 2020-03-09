import javafx.beans.property.StringProperty;

public class FieldModel {
    StringProperty[][] cells = new StringProperty[3][3];
    StringProperty[][] getCells() {
        return cells;
    }
}
