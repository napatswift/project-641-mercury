package ku.cs.models.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResizeableImageView extends ImageView {

    public ResizeableImageView(Image image) {
        super(image);
        setPreserveRatio(true);
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double minWidth(double v) {
        return 0.0;
    }
}
