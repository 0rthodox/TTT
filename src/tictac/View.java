package tictac;

import cell.Cell;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import stats.Stats;
import stats.StatsManager;
import utils.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class View {
    private StringProperty playerDASRProperty = new SimpleStringProperty("");
    private StringProperty playerDCAMProperty = new SimpleStringProperty("");
    private BooleanProperty restartProperty = new SimpleBooleanProperty(false);
    private ImageManager imageManager = new ImageManager();
    private ViewModel viewModel = new ViewModel();
    private Alert winnerAlert = new Alert(Alert.AlertType.INFORMATION);
    private final int GAP = 10;

    public View() {
        ObjectProperty<State> hasWinner = new SimpleObjectProperty<>();
        hasWinner.bindBidirectional(viewModel.getHasWinnerProperty());
        viewModel.getHasWinnerProperty().addListener((observable, oldValue, newValue) -> {
            StatsManager statsManager = new StatsManager();
            String winner;
            if (newValue.equals(State.EMPTY)) {
                winner = "Draw";
            } else if (newValue.equals(State.DASR)) {
                winner = "The winner is " + playerDASRProperty.getValue() + " (DASR)";
                if (!playerDCAMProperty.getValue().equals("")) {
                    statsManager.giveLoss(playerDCAMProperty.getValue());
                }
                if (!playerDASRProperty.getValue().equals("")) {
                    statsManager.giveWin(playerDASRProperty.getValue());
                }
                statsManager.saveStats();
            } else {
                winner = "The winner is " + playerDCAMProperty.getValue() + " (DCAM)";
                if (!playerDCAMProperty.getValue().equals("")) {
                    statsManager.giveWin(playerDCAMProperty.getValue());
                }
                if (!playerDASRProperty.getValue().equals("")) {
                    statsManager.giveLoss(playerDASRProperty.getValue());
                }
                statsManager.saveStats();
            }
            winnerAlert.setContentText(winner);
            restartProperty.setValue(true);
            winnerAlert.showAndWait();
        });
    }

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
        Label title = new Label("The Match of the Century");
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
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                cell.Cell cell = new Cell(imageManager);
                cell.setOnMouseClicked(event -> {
                    if (cell.getState().equals(State.EMPTY)) {
                        cell.setState(viewModel.getStateAndChange());
                        viewModel.checkWin(convertCells(field));
                    }

                });
                field.add(cell, i, j);
                viewModel.properties.get(i).get(j).bindBidirectional(cell.getStateProperty());
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
        statsStage.getIcons().add(FileManager.readImage("resources/x.png"));
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

    private Integer[][] convertCells(GridPane field) {
        Integer[][] convertedCells = new Integer[3][3];
        int i = 0;
        int j = 0;
        for(Node node : field.getChildren()) {
            convertedCells[i][j] = ((Cell)node).getState().getInteger();
            j++;
            if (j == 3) {
                j = 0;
                i++;
            }
        }
        return convertedCells;
    }

}
