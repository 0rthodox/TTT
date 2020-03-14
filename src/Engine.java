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

    Integer[][] convertCellValues(List<Integer> values) {
        Integer[][] convertedValues = new Integer[3][3];
        int i = 0;
        int j = 0;
        for(Integer integer : values) {
            convertedValues[i][j] = integer;
            j++;
            if (j > 2) {
                j = 0;
                i++;
            }
        }
        return convertedValues;
    }

    public void setField(Field field) {
        this.field = field;
    }

    State checkWin() {
        Integer[][] valuesToCheck = convertCellValues(field.computeField());
        Integer[] rowSum = new Integer[3];
        Integer[] columnSum = new Integer[3];
        for(int i = 0; i < 3; ++i) {
            rowSum[i] = 0;
            columnSum[i] = 0;
        }
        for(int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                rowSum[i] += valuesToCheck[i][j];
                columnSum[j] += valuesToCheck[i][j];
            }
        }
        for(Integer sum : rowSum) {
            System.out.println(sum);
            if(sum.equals(3)) {
                return State.X;
            }
            else if(sum.equals(-3)) {
                return State.O;
            }
        }
        for(Integer sum : columnSum) {
            System.out.println(sum);
            if(sum.equals(3)) {
                return State.X;
            }
            else if(sum.equals(-3)) {
                return State.O;
            }
        }
        return State.EMPTY;
    }
    void reactOnChanges() {
        State winner = checkWin();
        if(winner.equals(State.X)) {
            System.out.println("FAKI Champion");
            reset(winner);
        } else if (winner.equals(State.O)) {
            System.out.println("FUPM Champion");
            reset(winner);
        } else if (field.full()) {
            System.out.println("Draw");
            reset(State.X);
        } else {
            changeState();
        }
    }
    void reset(State newState) {
        System.out.println("Engine : resetting");
        state = newState;
        field.reset();
    }
}
