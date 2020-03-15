import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class FieldModel {
    StringProperty[][] cells = new StringProperty[3][3];

    FieldModel() {
        for (StringProperty[] row : cells) {
            for(StringProperty cell : row) {
                cell = new SimpleStringProperty();
            }
        }
    }
    StringProperty[][] getCells() {
        return cells;
    }
}
