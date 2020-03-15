import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class View {
    StringProperty playerDASRProperty = new SimpleStringProperty();
    StringProperty playerDCAMProperty = new SimpleStringProperty();
    private Boolean soloMode = false;
    BooleanProperty soloModeProperty = new SimpleBooleanProperty(soloMode);

    GridPane getChoicePane(BooleanProperty startPressedProperty, BooleanProperty exitPressedProperty) {
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
    VBox getGamePane() {
        return new VBox(new StatusBar(playerDASRProperty.getValue(), playerDCAMProperty.getValue()), new Field(new Engine()));
    }
}
