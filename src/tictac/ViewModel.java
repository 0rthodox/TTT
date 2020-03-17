package tictac;

import cell.CellProperties;
import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private ObjectProperty<State> hasWinnerProperty = new SimpleObjectProperty<>();
    List<List<CellProperties>> properties = new ArrayList<>(3);
    State state = State.EMPTY;

    ObjectProperty<State> getHasWinnerProperty() {
        return hasWinnerProperty;
    }

    private State getStateAndChange() {
        if (state.equals(State.DCAM)) {
            state = State.DASR;
            return State.DCAM;
        } else {
            state = State.DCAM;
            return State.DASR;
        }
    }
    ViewModel() {
        for(int i = 0; i < 3; ++i) {
            List<CellProperties> propertiesRow = new ArrayList<>(3);
            for (int j = 0; j < 3; ++j) {
                ObjectProperty<State> stateProperty = new SimpleObjectProperty<State>(State.EMPTY);
                stateProperty.addListener((observable, oldValue, newValue) ->{
                    checkState(getWinningState());
                });
                BooleanProperty signalProperty = new SimpleBooleanProperty(false);
                signalProperty.addListener((observable, oldValue, newValue) ->{
                    stateProperty.setValue(getStateAndChange());
                });
                propertiesRow.add(new CellProperties(stateProperty, signalProperty));
            }
            properties.add(propertiesRow);
        }
    }

    private Integer[][] convertCellValues() {
        Integer[][] convertedValues = new Integer[3][3];
        for (int i = 0; i < 3; ++i) {
            for(int j = 0 ; j < 3; ++j) {
                if (properties.get(i).get(j).getStateProperty().getValue().equals(State.DASR)) {
                    convertedValues[i][j] = 1;
                } else if (properties.get(i).get(j).getStateProperty().getValue().equals(State.DCAM)) {
                    convertedValues[i][j] = -1;
                } else {
                    convertedValues[i][j] = 0;
                }
            }
        }
        return convertedValues;
    }

    private State getWinningState() {
        Integer[][] valuesToCheck = convertCellValues();
        Integer[] sum = new Integer[8];
        for(int i = 0; i < 8; ++i) {
            sum[i] = 0;
        }
        for(int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                sum[i] += valuesToCheck[i][j];
                sum[j + 3] += valuesToCheck[i][j];
            }
            sum[6] += valuesToCheck[i][i];
            sum[7] += valuesToCheck[i][2 - i];
        }
        for(Integer integer : sum) {
            if(integer.equals(3)) {
                return State.DASR;
            }
            else if(integer.equals(-3)) {
                return State.DCAM;
            }
        }
        return State.EMPTY;
    }

    private boolean full() {
        for (List<CellProperties> propertiesRow : properties) {
            for (CellProperties cellProperties : propertiesRow) {
                if (cellProperties.getStateProperty().getValue().equals(State.EMPTY)) {
                    return false;
                }
            }
        }
        return true;
    }
    private void checkState(State state) {
        if (state.equals(State.DCAM)) {
            hasWinnerProperty.setValue(State.DCAM);
        } else if (state.equals(State.DASR)) {
            hasWinnerProperty.setValue(State.DASR);
        } else if (full()) {
            hasWinnerProperty.setValue(State.EMPTY);
        }
    }

}
