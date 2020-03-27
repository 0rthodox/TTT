package tictac.view;

import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;

import static java.lang.System.lineSeparator;


public class MainView extends VBox {
    private final int GAP = 10;
    private final int HEADING_TEXT_SIZE = 24;


    BooleanProperty startPressed = new SimpleBooleanProperty(false);
    BooleanProperty exitPressed = new SimpleBooleanProperty(false);

    TextField textFieldDASR = new TextField();
    TextField textFieldDCAM = new TextField();

    public MainView(List properties) {

        GridPane choicePane = new GridPane();
        choicePane.setHgap(GAP);
        choicePane.setVgap(GAP);

        textFieldDASR.setPromptText("Anonymous");
        textFieldDCAM.setPromptText("Anonymous");

        textFieldDASR.textProperty().bindBidirectional((StringProperty)properties.get(2));
        textFieldDCAM.textProperty().bindBidirectional((StringProperty)properties.get(3));
        startPressed.bindBidirectional((BooleanProperty)properties.get(0));
        exitPressed.bindBidirectional((BooleanProperty)properties.get(1));

        Button statistics = new Button("Statistics");
        Button startButton = new Button("Start");
        Button exitButton = new Button("Exit");

        statistics.setOnAction(event -> new StatisticsWindow());
        startButton.setOnAction(event -> startPressed.setValue(true));
        exitButton.setOnAction(event -> exitPressed.setValue(true));

        exitButton.defaultButtonProperty().bindBidirectional(exitPressed);

        Label title = new Label("WELCOME TO"+ lineSeparator() + "THE MATCH OF THE CENTURY");
        title.setFont(new Font("Comic Sans MS", HEADING_TEXT_SIZE));
        title.setTextFill(Color.rgb(115, 102, 189));
        title.setTextAlignment(TextAlignment.CENTER);

        choicePane.add(new Label("DASR player:"), 0, 0);
        choicePane.add(textFieldDASR, 1, 0);
        choicePane.add(new Label("DCAM player:"), 0, 1);
        choicePane.add(textFieldDCAM, 1, 1);
        choicePane.add(new Label("Show statistics:"), 0, 3);
        choicePane.add(statistics, 1, 3);
        choicePane.add(startButton, 0, 4);
        choicePane.add(exitButton, 1, 4);

        this.getChildren().addAll(title, choicePane);
    }

}
