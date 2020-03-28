package tictac.view;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import statistics.StatisticsManager;
import tictac.State;
import tictac.GameViewModel;

import java.util.List;
import java.util.stream.Collectors;

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
                        State winningState = viewModel.checkWinner(extractStates());
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
            statsManager.saveStatistics();
        } else {
            winner = "The winner is " + playerDCAM + " (DCAM)";
            if (!playerDCAM.isEmpty()) {
                statsManager.giveWin(playerDCAM);
            }
            if (!playerDASR.isEmpty()) {
                statsManager.giveLoss(playerDASR);
            }
            statsManager.saveStatistics();
        }
        winnerAlert.setContentText(winner);
        winnerAlert.showAndWait();
    }

    private List<State> extractStates() {
        return getChildren()
                .stream()
                .map(x -> ((Cell)x).getState())
                .collect(Collectors.toList());
    }
}
