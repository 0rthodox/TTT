import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class Cell extends ImageView {
    State state = State.EMPTY;
    ImageManager imageManager;

    Cell(ImageManager imageManager, Engine engine) {
        super(imageManager.getImage(State.EMPTY));
        setOnMouseClicked(event -> {
            if (state.equals(State.EMPTY))
            setState(engine.getState());
            engine.reactOnChanges();
        });
        VBox.setVgrow(this, Priority.NEVER);
        HBox.setHgrow(this, Priority.NEVER);
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
        setImage(imageManager.getImage(state));
    }

    boolean full() {
        return !state.equals(State.EMPTY);
    }



}
