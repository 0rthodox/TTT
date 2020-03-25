package tictac.view;

import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import stats.Stats;
import stats.StatsManager;
import tictac.State;
import tictac.ViewModel;
import utils.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class View {
    private StringProperty playerDASRProperty = new SimpleStringProperty("");
    private StringProperty playerDCAMProperty = new SimpleStringProperty("");
    private BooleanProperty restartProperty = new SimpleBooleanProperty(false);
    private ViewModel viewModel = new ViewModel();
    private Alert winnerAlert = new Alert(Alert.AlertType.INFORMATION);
    private final int GAP = 10;

    public VBox getChoicePane(BooleanProperty startPressedProperty, BooleanProperty exitPressedProperty) {
        GridPane choicePane = new GridPane();
        choicePane.setHgap(GAP);
        choicePane.setVgap(GAP);

        TextField textFieldDASR = new TextField();
        TextField textFieldDCAM = new TextField();
        BooleanProperty startPressed = new SimpleBooleanProperty(false);
        BooleanProperty exitPressed = new SimpleBooleanProperty(false);

        textFieldDASR.setPromptText("Anonymous");
        textFieldDCAM.setPromptText("Anonymous");

        textFieldDASR.textProperty().bindBidirectional(playerDASRProperty);
        textFieldDCAM.textProperty().bindBidirectional(playerDCAMProperty);
        startPressed.bindBidirectional(startPressedProperty);
        exitPressed.bindBidirectional(exitPressedProperty);

        Button statistics = new Button("Statistics");
        Button startButton = new Button("Start");
        Button exitButton = new Button("exit");

        statistics.setOnAction(event -> showStatistics());
        startButton.setOnAction(event -> startPressed.setValue(true));
        exitButton.setOnAction(event -> exitPressed.setValue(true));

        Label welcoming = new Label("Welcome to");
        Label title = new Label("THE MATCH OF THE CENTURY");
        title.setFont(new Font("Comic Sans MS", 30));
        title.setTextFill(Color.rgb(115, 102, 189));
        title.setAlignment(Pos.CENTER);

        choicePane.add(new Label("DASR player:"), 0, 0);
        choicePane.add(textFieldDASR, 1, 0);
        choicePane.add(new Label("DCAM player:"), 0, 1);
        choicePane.add(textFieldDCAM, 1, 1);
        choicePane.add(new Label("Show statistics:"), 0, 3);
        choicePane.add(statistics, 1, 3);
        choicePane.add(startButton, 0, 4);
        choicePane.add(exitButton, 1, 4);
        return new VBox(welcoming, title, choicePane);
    }
    public GridPane getGamePane(BooleanProperty restartProperty) {
        restartProperty.bindBidirectional(this.restartProperty);
        GridPane field = new GridPane();
        //field.getScene().getWindow()
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                Cell cell = new Cell();
                cell.setOnMouseClicked(event -> {
                    //TODO:((Cell)event.getSource()).getScene()
                    if (cell.getState().equals(State.EMPTY)) {
                        cell.setState(viewModel.getStateAndChange());
                        State winningState = viewModel.checkWinner(convertCells(field));
                        if (winningState != null) {
                            showWinner(winningState);
                        }
                    }
                });
                field.add(cell, i, j);
            }
        }
        return field;
    }
    private void showStatistics() {
        Set<Stats> stats = new StatsManager().getStats();
        List<Label[]> labels = new ArrayList<>(stats.size());
        for(Stats statsEntry : stats) {
            Label[] labeledStats = new Label[3];
            labeledStats[0] = new Label(statsEntry.getName());
            labeledStats[1] = new Label(statsEntry.getWins().toString());
            labeledStats[2] = new Label(statsEntry.getLosses().toString());
            labels.add(labeledStats);
        }
        Stage statsStage = new Stage();
        statsStage.getIcons().add(FileManager.readImage("src/main/resources/x.png"));
        statsStage.setTitle("Stats");
        GridPane statsPane = new GridPane();
        statsPane.setHgap(GAP);
        statsPane.setVgap(GAP);
        int i = 0;
        statsPane.addRow(i++, new Label("Player:"), new Label("Wins:"), new Label("Losses:"));
        for(Label[] labeledStats : labels) {
            statsPane.addRow(i++, labeledStats[0], labeledStats[1], labeledStats[2]);
        }
        statsStage.setScene(new Scene(statsPane));
        statsStage.setResizable(false);
        statsStage.show();
    }

    private int[][] convertCells(GridPane field) {
        int[][] convertedCells = new int[3][3];
        int i = 0;
        int j = 0;
        for(Node node : field.getChildren()) {
            convertedCells[i][j++] = ((Cell)node).getState().getInteger();
            if (j == 3) {
                j = 0;
                i++;
            }
        }
        return convertedCells;
    }

    private void showWinner(State state) {
        StatsManager statsManager = new StatsManager();
        String winner;
        if (state.equals(State.EMPTY)) {
            winner = "Draw";
        } else if (state.equals(State.DASR)) {
            winner = "The winner is " + playerDASRProperty.getValue() + " (DASR)";
            if (!playerDCAMProperty.getValue().isEmpty()) {
                statsManager.giveLoss(playerDCAMProperty.getValue());
            }
            if (!playerDASRProperty.getValue().isEmpty()) {
                statsManager.giveWin(playerDASRProperty.getValue());
            }
            statsManager.saveStats();
        } else {
            winner = "The winner is " + playerDCAMProperty.getValue() + " (DCAM)";
            if (!playerDCAMProperty.getValue().isEmpty()) {
                statsManager.giveWin(playerDCAMProperty.getValue());
            }
            if (!playerDASRProperty.getValue().isEmpty()) {
                statsManager.giveLoss(playerDASRProperty.getValue());
            }
            statsManager.saveStats();
        }
        winnerAlert.setContentText(winner);
        restartProperty.setValue(true);
        winnerAlert.showAndWait();
    }

}
