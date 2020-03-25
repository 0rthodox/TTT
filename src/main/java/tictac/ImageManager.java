package tictac;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.FileManager;

public class ImageManager {
    //TODO: static class
    private static final Image IMAGE_DASR = FileManager.readImage("src/main/resources/dasr.png");
    private static final Image IMAGE_DCAM = FileManager.readImage("src/main/resources/dcam.png");
    private static final Image IMAGE_QUESTION_MARK = FileManager.readImage("src/main/resources/qMark.jpg");

    public static Image getImage(State state) {
        if (state.equals(State.DASR)) {
            return IMAGE_DASR;
        } else if (state.equals(State.DCAM)) {
            return IMAGE_DCAM;
        } else {
            return IMAGE_QUESTION_MARK;
        }
    }


}
