package tictac;

public class ViewModel {
    private State state = State.EMPTY;

    State getStateAndChange() {
        if (state.equals(State.DCAM)) {
            state = State.DASR;
            return State.DCAM;
        } else {
            state = State.DCAM;
            return State.DASR;
        }
    }

    State checkWin(Integer[][] valuesToCheck) {
        State state = State.EMPTY;
        boolean hasEmptyCell = false;
        Integer[] sum = new Integer[8];
        for(int i = 0; i < 8; ++i) {
            sum[i] = 0;
        }
        for(int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                Integer cellState = valuesToCheck[i][j];
                if (cellState.equals(0)) {
                    hasEmptyCell = true;
                } else {
                    sum[i] += cellState;
                    sum[j + 3] += cellState;
                }
            }
            sum[6] += valuesToCheck[i][i];
            sum[7] += valuesToCheck[i][2 - i];
        }
        for(Integer integer : sum) {
            if(integer.equals(3)) {
                state = State.DASR;
            }
            else if(integer.equals(-3)) {
                state = State.DCAM;
            }
        }
        if (state.equals(State.EMPTY) && hasEmptyCell) {
            state = null;
        }
        return state;
    }
}
