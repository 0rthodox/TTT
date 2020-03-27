package tictac;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tictac.view.GameView;
import tictac.view.MainView;
import utils.FileManager;

import java.util.ArrayList;
import java.util.List;


public class TicTacApplication extends Application {

    @Override
    public void start(Stage primaryStage) {

        //Initializing properties
        BooleanProperty startPressedProperty = new SimpleBooleanProperty(false);
        BooleanProperty restartProperty = new SimpleBooleanProperty(false);
        BooleanProperty exitPressedProperty = new SimpleBooleanProperty(false);
        StringProperty playerDASRProperty = new SimpleStringProperty("");
        StringProperty playerDCAMProperty = new SimpleStringProperty("");

        //Properties holder
        List properties = new ArrayList<>(4);
        properties.add(startPressedProperty);
        properties.add(exitPressedProperty);
        properties.add(playerDASRProperty);
        properties.add(playerDCAMProperty);

        // Adding property listeners
        startPressedProperty.addListener((observable, oldValue, newValue) ->
                primaryStage.setScene(new Scene(
                        new GameView(restartProperty, playerDASRProperty.getValue(),
                                playerDCAMProperty.getValue()))));
        exitPressedProperty.addListener((observable, oldValue, newValue) ->
                primaryStage.close());
        restartProperty.addListener((observable, oldValue, newValue) -> {
            primaryStage.close();
            Stage newStage = new Stage();
            start(newStage);
        });

        // Initializing stage
        primaryStage.setResizable(false);
        primaryStage.setTitle("The Match of the Century");
        primaryStage.getIcons().add(FileManager.readImage("src/main/resources/x.png"));
        primaryStage.setScene(new Scene(
                new MainView(properties)));
        primaryStage.show();
    }
}
