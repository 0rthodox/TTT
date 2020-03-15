import java.util.List;

public class Engine {
    State state = State.DASR;
    Field field;

    public State getState() {
        return state;
    }

    public void changeState() {
        state = (state.equals(State.DASR)) ? State.DCAM : State.DASR;
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
            System.out.println(integer);
            if(integer.equals(3)) {
                return State.DASR;
            }
            else if(integer.equals(-3)) {
                return State.DCAM;
            }
        }
        return State.EMPTY;
    }
    void reactOnChanges() {
        State winner = checkWin();
        if(winner.equals(State.DASR)) {
            System.out.println("FAKI Champion");
            reset(winner);
        } else if (winner.equals(State.DCAM)) {
            System.out.println("FUPM Champion");
            reset(winner);
        } else if (field.full()) {
            System.out.println("Draw");
            reset(State.DASR);
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
