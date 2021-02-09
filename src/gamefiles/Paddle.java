package gamefiles;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Paddle extends GlobalFunctions {

    private int move_x;

    public Paddle() {
        createPaddle();
    }

    private void createPaddle() {
        loadImage();
        getImageDimensions();
        setStartState();
    }

    private void loadImage() {
        var paddle_image = new ImageIcon("src/resources/paddle.png");
        image = paddle_image.getImage();
    }

    public void setStartState() {
        x = GlobalSettings.PADDLE_START_X_POS;
        y = GlobalSettings.PADDLE_START_Y_POS;
    }

    void move() {
        x += move_x;
        if (x <= 0) {
            x = 0;
        }
        if (x >= GlobalSettings.WINDOW_WIDTH - imageWidth) {
            x = GlobalSettings.WINDOW_WIDTH - imageWidth;
        }
    }

    void KeyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            move_x = -4;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            move_x = 4;
        }
    }

    void KeyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            move_x = 0;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            move_x = 0;
        }
    }
}
