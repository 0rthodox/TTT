package cell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.ImageView;
import tictac.ImageManager;
import tictac.State;


public class Cell extends ImageView {
    State state = State.EMPTY;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        updateImage();
    }

    ImageManager imageManager;

    public Cell(ImageManager imageManager) {
        super(imageManager.getImage(State.EMPTY));
        setFitWidth(100);
        setFitHeight(100);
        this.imageManager = imageManager;
    }

    private void updateImage() {
        setImage(imageManager.getImage(state));
    }


}
