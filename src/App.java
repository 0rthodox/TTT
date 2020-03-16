import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        Stage stage = primaryStage;
        BooleanProperty startPressedProperty = new SimpleBooleanProperty(false);
        BooleanProperty restartProperty = new SimpleBooleanProperty(false);
        BooleanProperty exitPressedProperty = new SimpleBooleanProperty(false);
        View view = new View();
        startPressedProperty.addListener((observable, oldValue, newValue) ->
                stage.setScene(new Scene(view.getGamePane(restartProperty))));
        exitPressedProperty.addListener((observable, oldValue, newValue) ->
                stage.close());
        restartProperty.addListener((observable, oldValue, newValue) -> {
            stage.close();
            Stage newStage = new Stage();
            start(newStage);
        });
        stage.setScene(new Scene(
                view.getChoicePane(startPressedProperty, exitPressedProperty)));
        stage.show();
    }
}
