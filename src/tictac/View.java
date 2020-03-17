package tictac;

import cell.Cell;
import javafx.beans.property.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import stats.Stats;
import stats.StatsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class View {
    private StringProperty playerDASRProperty = new SimpleStringProperty();
    private StringProperty playerDCAMProperty = new SimpleStringProperty();
    private Boolean soloMode = false;
    private BooleanProperty soloModeProperty = new SimpleBooleanProperty(soloMode);
    private BooleanProperty restartProperty = new SimpleBooleanProperty(false);
    private ImageManager imageManager = new ImageManager();
    private ViewModel viewModel = new ViewModel();
    private ObjectProperty<State> hasWinner = new SimpleObjectProperty<>();
    private Alert winnerAlert = new Alert(Alert.AlertType.INFORMATION);
    private final int GAP = 10;

    public View() {
        hasWinner.bindBidirectional(viewModel.getHasWinnerProperty());
        hasWinner.addListener((observable, oldValue, newValue) -> {
            StatsManager statsManager = new StatsManager();
            String winner;
            if (newValue.equals(State.EMPTY)) {
                winner = "Draw";
            } else if (newValue.equals(State.DASR)) {
                winner = "The winner is " + playerDASRProperty.getValue() + " (DASR)";
                statsManager.giveWinAndLoss(playerDASRProperty.getValue(), playerDCAMProperty.getValue());
                statsManager.saveStats();
            } else {
                winner = "The winner is " + playerDCAMProperty.getValue() + " (DCAM)";
                statsManager.giveWinAndLoss(playerDCAMProperty.getValue(), playerDASRProperty.getValue());
                statsManager.saveStats();
            }
            winnerAlert.setContentText(winner);
            restartProperty.setValue(true);
            winnerAlert.showAndWait();
        });
    }

    public GridPane getChoicePane(BooleanProperty startPressedProperty, BooleanProperty exitPressedProperty) {
        GridPane choicePane = new GridPane();
        choicePane.setHgap(GAP);
        choicePane.setVgap(GAP);

        TextField textFieldDASR = new TextField();
        TextField textFieldDCAM = new TextField();
        CheckBox modeBox = new CheckBox();
        BooleanProperty startPressed = new SimpleBooleanProperty(false);
        BooleanProperty exitPressed = new SimpleBooleanProperty(false);

        textFieldDASR.textProperty().bindBidirectional(playerDASRProperty);
        textFieldDCAM.textProperty().bindBidirectional(playerDCAMProperty);
        modeBox.selectedProperty().bindBidirectional(soloModeProperty);
        startPressed.bindBidirectional(startPressedProperty);
        exitPressed.bindBidirectional(exitPressedProperty);

        Button statistics = new Button("Statistics");
        Button startButton = new Button("Start");
        Button exitButton = new Button("exit");

        statistics.setOnAction(event -> showStatistics());
        startButton.setOnAction(event -> startPressed.setValue(true));
        exitButton.setOnAction(event -> exitPressed.setValue(true));

        choicePane.add(new Label("DASR's player:"), 0, 0);
        choicePane.add(textFieldDASR, 1, 0);
        choicePane.add(new Label("DCAM's player:"), 0, 1);
        choicePane.add(textFieldDCAM, 1, 1);
        choicePane.add(new Label("Solo mode:"), 0, 2);
        choicePane.add(modeBox, 1, 2);
        choicePane.add(new Label("Show statistics:"), 0, 3);
        choicePane.add(statistics, 1, 3);
        choicePane.add(startButton, 0, 4);
        choicePane.add(exitButton, 1, 4);
        return choicePane;
    }
    public GridPane getGamePane(BooleanProperty restartProperty) {
        restartProperty.bindBidirectional(this.restartProperty);
        GridPane field = new GridPane();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                cell.Cell cell = new Cell(imageManager);
                cell.getCellProperties().bind(viewModel.properties.get(i).get(j));
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
}
