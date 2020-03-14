import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class View extends VBox {
    View() {
        super(new StatusBar(), new Field(new Engine()));

    }
}
