package tictac.view;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import statistics.StatisticsManager;
import tictac.State;
import tictac.GameViewModel;

public class GameView extends GridPane {
    private GameViewModel viewModel = new GameViewModel();
    private Alert winnerAlert = new Alert(Alert.AlertType.INFORMATION);
    private String playerDASR, playerDCAM;

    public GameView(BooleanProperty restartProperty, String playerDASR, String playerDCAM) {
        this.playerDASR = playerDASR;
        this.playerDCAM = playerDCAM;

        //Initializing cells
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                Cell cell = new Cell();
                cell.setOnMouseClicked(event -> {
                    if (cell.getState().equals(State.EMPTY)) {
                        cell.setState(viewModel.getStateAndChange());
                        State winningState = viewModel.checkWinner(convertCellsToStates());
                        if (winningState != null) {
                            showWinner(winningState);
                            restartProperty.setValue(true);
                        }
                    }
                });
                add(cell, i, j);
            }
        }
    }

    private void showWinner(State state) {
        StatisticsManager statsManager = new StatisticsManager();
        String winner;
        if (state.equals(State.EMPTY)) {
            winner = "Draw";
        } else if (state.equals(State.DASR)) {
            winner = "The winner is " + playerDASR + " (DASR)";
            if (!playerDCAM.isEmpty()) {
                statsManager.giveLoss(playerDCAM);
            }
            if (!playerDASR.isEmpty()) {
                statsManager.giveWin(playerDASR);
            }
            statsManager.saveStats();
        } else {
            winner = "The winner is " + playerDCAM + " (DCAM)";
            if (!playerDCAM.isEmpty()) {
                statsManager.giveWin(playerDCAM);
            }
            if (!playerDASR.isEmpty()) {
                statsManager.giveLoss(playerDASR);
            }
            statsManager.saveStats();
        }
        winnerAlert.setContentText(winner);
        winnerAlert.showAndWait();
    }

    private State[][] convertCellsToStates() {
        State[][] convertedCells = new State[3][3];
        int i = 0;
        int j = 0;
        for(Node node : getChildren()) {
            convertedCells[i][j++] = ((Cell)node).getState();
            if (j == 3) {
                j = 0;
                i++;
            }
        }
        return convertedCells;
    }
}
