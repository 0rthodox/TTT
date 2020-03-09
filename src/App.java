import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        View view = new View();
        primaryStage.setScene(new Scene(view));
        primaryStage.show();
    }
}
