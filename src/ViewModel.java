import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    List<List<ObjectProperty<State>>> cells = new ArrayList<>(3);
    List<List<BooleanProperty>> signals = new ArrayList<>(3);
    List<List<CellProperties>> properties = new ArrayList<>(3);
    BooleanProperty signalProperty = new SimpleBooleanProperty(false);
    Engine engine = new Engine();
    ViewModel() {
        for(int i = 0; i < 3; ++i) {
            List<CellProperties> propertiesRow = new ArrayList<>(3);
            for (int j = 0; j < 3; ++j) {
                ObjectProperty<State> stateProperty = new SimpleObjectProperty<State>(State.EMPTY);
                stateProperty.addListener((observable, oldValue, newValue) ->{

                });
                BooleanProperty signalProperty = new SimpleBooleanProperty(false);
                signalProperty.addListener((observable, oldValue, newValue) ->{
                    stateProperty.setValue(engine.getState());

                });
                propertiesRow.add(new CellProperties(stateProperty, signalProperty));
            }
            properties.add(propertiesRow);
        }
    }

    public List<List<ObjectProperty<State>>> getCells() {
        return cells;
    }
    public List<List<BooleanProperty>> getSignals() {
        return signals;
    }

    public Engine getEngine() {
        return engine;
    }
}
