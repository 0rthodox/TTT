import javafx.scene.image.Image;
import utils.FileManager;

public class ImageManager {
    private Image imageDASR = FileManager.readImage("resources/dasr.png");
    private Image imageDCAM = FileManager.readImage("resources/dcam.png");
    private Image imageQMark = FileManager.readImage("resources/qMark.jpg");

    Image getImage(State state) {
        if (state.equals(State.DASR)) {
            return imageDASR;
        } else if (state.equals(State.DCAM)) {
            return imageDCAM;
        } else {
            return imageQMark;
        }
    }
}
