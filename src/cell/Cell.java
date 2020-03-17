package cell;

import javafx.scene.image.ImageView;
import tictac.ImageManager;
import tictac.State;


public class Cell extends ImageView {
    State state = State.EMPTY;
    CellProperties cellProperties = new CellProperties(state, false);

    public CellProperties getCellProperties() {
        return cellProperties;
    }

    ImageManager imageManager;

    public Cell(ImageManager imageManager) {
        super(imageManager.getImage(State.EMPTY));
        setOnMouseClicked(event -> {
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

    private void updateImage() {
        setImage(imageManager.getImage(cellProperties.stateProperty.getValue()));
    }


}
