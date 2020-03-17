package cell;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import tictac.State;

public class CellProperties {

    ObjectProperty<State> stateProperty;
    BooleanProperty signalProperty;

    CellProperties(State state, Boolean bool) {
        stateProperty = new SimpleObjectProperty<>(state);
        signalProperty = new SimpleBooleanProperty(bool);
    }


    public ObjectProperty<State> getStateProperty() {
        return stateProperty;
    }

    public BooleanProperty getSignalProperty() {
        return signalProperty;
    }

    public CellProperties(ObjectProperty<State> stateProperty, BooleanProperty signalProperty) {
        this.stateProperty = stateProperty;
        this.signalProperty = signalProperty;
    }

    public void bind(CellProperties cellProperties) {
        signalProperty.bindBidirectional(cellProperties.signalProperty);
        stateProperty.bindBidirectional(cellProperties.stateProperty);
    }
}
