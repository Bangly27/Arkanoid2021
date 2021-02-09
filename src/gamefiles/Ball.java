package gamefiles;

import javax.swing.*;

public class Ball extends GlobalFunctions {

    private int xdir;
    private int ydir;

    public Ball() {
        createBall();
    }

    private void createBall() {
        loadImage();
        getImageDimensions();
        setStartState();
    }

    private void loadImage() {
        var ball_image = new ImageIcon("src/resources/ball.png");
        image = ball_image.getImage();
    }

    public void setStartState() {
        xdir = 2;
        ydir = -2;
        x = GlobalSettings.BALL_START_X_POS;
        y = GlobalSettings.BALL_START_Y_POS;
    }

    void move() {
        x += xdir;
        y += ydir;

        if (x == 0 || x == -1) {
            setXdir(2);
        }
        if (x == GlobalSettings.WINDOW_WIDTH - imageWidth || x == (GlobalSettings.WINDOW_WIDTH - 25) || x == (GlobalSettings.WINDOW_WIDTH - 24) || x == (GlobalSettings.WINDOW_WIDTH - 26)) {
            setXdir(-2);
        }
        if (y == 0 || y == -1) {
            setYdir(2);
        }
    }

    void setXdir(int x) {
        xdir = x;
    }

    int getXdir() {
        return xdir;
    }

    void setYdir(int y) {
        ydir = y;
    }

    int getYdir() {
        return ydir;
    }
}
