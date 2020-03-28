package tictac;

import java.util.List;

public class GameViewModel {
    private State state = State.EMPTY;

    public State getStateAndChange() {
        if (state.equals(State.DCAM)) {
            state = State.DASR;
            return State.DCAM;
        } else {
            state = State.DCAM;
            return State.DASR;
        }
    }

    public State checkWinner(List<State> states) {
        int[][] values = convertStatesToIntegers(states);
        boolean hasEmptyCell = false;
        int[] sum = new int[8];
        for(int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                int cellState = values[i][j];
                if (cellState == 0) {
                    hasEmptyCell = true;
                } else {
                    sum[i] += cellState;
                    sum[j + 3] += cellState;
                }
            }
            sum[6] += values[i][i];
            sum[7] += values[i][2 - i];
        }
        for(int num : sum) {
            if(num == 3) {
                return State.DASR;
            }
            else if(num == -3) {
                return State.DCAM;
            }
        }
        if (hasEmptyCell) {
            return null;
        }
        return State.EMPTY;
    }

    private int[][] convertStatesToIntegers(List<State> states) {
        int[][] integers = new int[3][3];
            int i = 0;
            int j = 0;
            for(State state : states) {
                integers[i][j++] = state.getInteger();
                if (j == 3) {
                    j = 0;
                    i++;
                }
        }
        return integers;

    }
}
