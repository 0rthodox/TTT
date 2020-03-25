package tictac.view;

import javafx.scene.image.ImageView;
import tictac.ImageManager;
import tictac.State;

public class Cell extends ImageView {
    private State state = State.EMPTY;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        updateImage();
    }

    public Cell() {
        super(ImageManager.getImage(State.EMPTY));
        setFitWidth(100);
        setFitHeight(100);
    }

    private void updateImage() {
        setImage(ImageManager.getImage(state));
    }


}