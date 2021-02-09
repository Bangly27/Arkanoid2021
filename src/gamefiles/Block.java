package gamefiles;

import javax.swing.*;

public class Block extends GlobalFunctions {

    private boolean blockDestroyed;

    public Block(int x, int y) {
        createBlock(x, y);
    }

    private void createBlock(int x, int y) {
        this.x = x;
        this.y = y;
        blockDestroyed = false;
        loadImage();
        getImageDimensions();
    }

    private void loadImage() {
        var block_image = new ImageIcon("src/resources/block.png");
        image = block_image.getImage();
    }

    boolean isBlockDestroyed() {
        return blockDestroyed;
    }

    void setBlockDestroyed(boolean destroyed) {
        blockDestroyed = destroyed;
    }
}
