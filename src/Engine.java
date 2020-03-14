import java.util.List;

public class Engine {
    FieldModel fieldModel;
    State state = State.X;
    Field field;

    public State getState() {
        return state;
    }

    public void changeState() {
        state = (state.equals(State.X)) ? State.O : State.X;
    }

    Integer[][] convertCellValues(List<Boolean> booleans) {
        Integer[][] convertedValues = new Integer[3][3];
        int i = 0;
        int j = 0;
        for(Boolean bool : booleans) {
            j++;
            if (j > 2) {
                j = 0;
                i++;
            }
            if(bool) {
                convertedValues[i][j] = 1;
            } else {
                convertedValues[i][j] = 0;
            }
        }
        return convertedValues;
    }

    public void setField(Field field) {
        this.field = field;
    }

    void checkWin() {
        if (field.full()) {
            System.out.println(1);
            Integer[][] valuesToCheck = convertCellValues(field.computeField());
            Integer[] rowSum = new Integer[3];
            Integer[] columnSum = new Integer[3];
            for(int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    rowSum[i] += valuesToCheck[i][j];
                    columnSum[j] += valuesToCheck[i][j];
                }
            }
            for(Integer sum : rowSum) {
                if(sum.equals(3)) {
                    System.out.println("FAKI wins");
                }
                else if(sum.equals(0)) {
                    System.out.println("FUPM wins");
                }
            }
        }
    }
}
