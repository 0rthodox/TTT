package tictac;

import cell.Cell;
import javafx.beans.property.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import stats.StatsManager;


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
        TextField textFieldDASR = new TextField();
        TextField textFieldDCAM = new TextField();
        CheckBox modeBox = new CheckBox();
        textFieldDASR.textProperty().bindBidirectional(playerDASRProperty);
        textFieldDCAM.textProperty().bindBidirectional(playerDCAMProperty);
        modeBox.selectedProperty().bindBidirectional(soloModeProperty);
        BooleanProperty startPressed = new SimpleBooleanProperty(false);
        startPressed.bindBidirectional(startPressedProperty);
        BooleanProperty exitPressed = new SimpleBooleanProperty(false);
        exitPressed.bindBidirectional(exitPressedProperty);
        Button startButton = new Button("Start");
        Button exitButton = new Button("exit");
        startButton.setOnAction(event -> {
            startPressed.setValue(true);
        });
        exitButton.setOnAction(event -> {
            exitPressed.setValue(true);
        });
        choicePane.add(new Label("DASR's player:"), 0, 0);
        choicePane.add(textFieldDASR, 1, 0);
        choicePane.add(new Label("DCAM's player:"), 0, 1);
        choicePane.add(textFieldDCAM, 1, 1);
        choicePane.add(new Label("Solo mode:"), 0, 2);
        choicePane.add(modeBox, 1, 2);
        choicePane.add(startButton, 0, 3);
        choicePane.add(exitButton, 1, 3);
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
}
