import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class StatusBar extends HBox {
    StatusBar() {
        Label lPlayer = new Label("Left Player");
        Label rPlayer = new Label("Right Player");
        this.getChildren().addAll(lPlayer, rPlayer);
    }
}
