package gamefiles;

import java.awt.*;

public class GlobalFunctions {
    Image image;
    int x;
    int y;
    int imageWidth;
    int imageHeight;

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getImageWidth() {
        return imageWidth;
    }

    int getImageHeight() {
        return imageHeight;
    }

    Image getImage() {
        return image;
    }

    Rectangle getRectantgle() {
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    void getImageDimensions() {
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
}
