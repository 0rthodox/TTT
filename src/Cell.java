import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class Cell extends ImageView {
    State state = State.EMPTY;
    CellProperties cellProperties = new CellProperties(state, false);

    public CellProperties getCellProperties() {
        return cellProperties;
    }

    ImageManager imageManager;

    Cell(ImageManager imageManager) {
        super(imageManager.getImage(State.EMPTY));
        setOnMouseClicked(event -> {
            System.out.println("Mouse clicked");
            if (state.equals(State.EMPTY))
                cellProperties.signalProperty.setValue(true);
        });
        cellProperties.stateProperty.addListener((observable, oldValue, newValue) -> {
            updateImage();
        });
        setFitWidth(100);
        setFitHeight(100);
        this.imageManager = imageManager;
    }

    void setState(State state) {
        this.state = state;
        updateImage();
    }

    public State getState() {
        return state;
    }

    private void updateImage() {
        setImage(imageManager.getImage(cellProperties.stateProperty.getValue()));
    }

    boolean full() {
        return !state.equals(State.EMPTY);
    }



}
