import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

public class StatusBar extends GridPane {
    StatusBar(String playerDASRName, String playerDCAMName) {
        Label playerDASR = new Label(playerDASRName);
        playerDASR.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(playerDASR, Priority.ALWAYS);
        Label playerDCAM = new Label(playerDCAMName);
        playerDCAM.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(playerDCAM, Priority.ALWAYS);
        this.getChildren().addAll(playerDASR, playerDCAM);
    }
}
