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
        Boolean startPressed = false;
        BooleanProperty startPressedProperty = new SimpleBooleanProperty(startPressed);

        Boolean exitPressed = false;
        BooleanProperty exitPressedProperty = new SimpleBooleanProperty(exitPressed);
        View view = new View();
        startPressedProperty.addListener((observable, oldValue, newValue) ->
                stage.setScene(new Scene(view.getGamePane())));
        exitPressedProperty.addListener((observable, oldValue, newValue) ->
                stage.close());
        stage.setScene(new Scene(
                view.getChoicePane(startPressedProperty, exitPressedProperty)));
        stage.show();
    }
}
