import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tictac.View;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        BooleanProperty startPressedProperty = new SimpleBooleanProperty(false);
        BooleanProperty restartProperty = new SimpleBooleanProperty(false);
        BooleanProperty exitPressedProperty = new SimpleBooleanProperty(false);
        View view = new View();
        startPressedProperty.addListener((observable, oldValue, newValue) ->
                primaryStage.setScene(new Scene(view.getGamePane(restartProperty))));
        exitPressedProperty.addListener((observable, oldValue, newValue) ->
                primaryStage.close());
        restartProperty.addListener((observable, oldValue, newValue) -> {
            primaryStage.close();
            Stage newStage = new Stage();
            start(newStage);
        });
        primaryStage.setScene(new Scene(
                view.getChoicePane(startPressedProperty, exitPressedProperty)));
        primaryStage.show();
    }
}
