package gamefiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel {
    private Timer timer;
    private Ball ball;
    private Paddle paddle;
    private Block[] blocks;
    private boolean isRunning = true;
    private String message = "Game Over :(";
    private String score = "Your score: 0";
    private int blockDestroyed = 0;

    public Board() {
        createBoard();
    }

    private void createBoard() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                paddle.KeyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                paddle.KeyReleased(e);
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ESCAPE) {
                    resetGame();
                }
            }
        });
        setFocusable(true);
        setPreferredSize(new Dimension(GlobalSettings.WINDOW_WIDTH, GlobalSettings.WINDOW_HEIGHT));
        createGame();
    }

    private void resetGame() {
        ball.setStartState();
        paddle.setStartState();
        score = "Your score: 0";
        blockDestroyed = 0;
        isRunning = true;
        for (int i = 0; i < GlobalSettings.NUMBER_OF_BLOCKS; i++) {
            blocks[i].setBlockDestroyed(false);
        }
        timer.restart();
    }

    private void createGame() {
        blocks = new Block[GlobalSettings.NUMBER_OF_BLOCKS];
        ball = new Ball();
        paddle = new Paddle();
        int block_number = 0;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                blocks[block_number] = new Block(j * 80 + 25, i * 30 + 50);
                block_number++;
            }
        }

        timer = new Timer(10, new StartGame());
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        if (isRunning) {
            drawGame(g2d);
        } else {
            gameFinished(g2d);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawGame(Graphics g2d) {
        g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getImageWidth(), ball.getImageHeight(), this);
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getImageWidth(), paddle.getImageHeight(), this);
        for (int i = 0; i < GlobalSettings.NUMBER_OF_BLOCKS; i++) {
            if (!blocks[i].isBlockDestroyed()) {
                g2d.drawImage(blocks[i].getImage(), blocks[i].getX(), blocks[i].getY(), blocks[i].getImageWidth(), blocks[i].getImageHeight(), this);
            }
        }
        var font = new Font("Myanmar Text", Font.ITALIC, 40);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(score, 5, 35);
    }

    private void gameFinished(Graphics2D g2d) {
        var font = new Font("Myanmar Text", Font.ITALIC, 40);
        FontMetrics fontMetrics = this.getFontMetrics(font);
        if (message.equals("Congratulation!")) {
            g2d.setColor(Color.GREEN);
        }
        if (message.equals("Game Over :(")) {
            g2d.setColor(Color.BLACK);
        }
        g2d.setFont(font);
        g2d.drawString(message, (GlobalSettings.WINDOW_WIDTH - fontMetrics.stringWidth(message)) / 2, GlobalSettings.WINDOW_WIDTH / 2 - 25);
        score = "Your score: " + blockDestroyed;
        g2d.drawString(score, (GlobalSettings.WINDOW_WIDTH - fontMetrics.stringWidth(score)) / 2, GlobalSettings.WINDOW_WIDTH / 2 + 25);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ESCAPE) {
                    resetGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_ESCAPE) {
                    resetGame();
                }
            }
        });
    }

    private class StartGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playGame();
        }
    }

    private void playGame() {
        ball.move();
        paddle.move();
        paddleCollisions();
        blocksCollisions();
        areBlocksDestroyed();
        repaint();
        if (ball.getRectantgle().getMaxY() > GlobalSettings.BOTTOM_EDGE) {
            stopGame();
        }
    }

    private void stopGame() {
        isRunning = false;
        timer.stop();
    }

    private void paddleCollisions() {
        if ((ball.getRectantgle()).intersects(paddle.getRectantgle())) {
            int paddleLPos = (int) paddle.getRectantgle().getMinX();
            int ballLPos = (int) ball.getRectantgle().getMinX();
            int ballWidth = (int) ball.getRectantgle().getWidth();
            int ballCenter = ballLPos + (ballWidth / 2);

            int first = paddleLPos + 16;
            int second = paddleLPos + 32;
            int third = paddleLPos + 48;
            int fourth = paddleLPos + 64;

            if (ballCenter < first) {
                ball.setXdir(-2);
                ball.setYdir(-2);
            }
            if (ballCenter >= first && ballLPos < second) {
                ball.setXdir(-1 * ball.getXdir());
                ball.setYdir(-2);
            }
            if (ballCenter >= second && ballLPos < third) {
                ball.setXdir(0);
                ball.setYdir(-2);
            }
            if (ballCenter >= third && ballLPos < fourth) {
                ball.setXdir(-1 * ball.getXdir());
                ball.setYdir(-2);
            }
            if (ballCenter > fourth) {
                ball.setXdir(2);
                ball.setYdir(-2);
            }
        }
    }

    private void areBlocksDestroyed() {
        for (int i = 0, j = 0; i < GlobalSettings.NUMBER_OF_BLOCKS; i++) {
            if (blocks[i].isBlockDestroyed()) {
                j++;
            }
            if (j == GlobalSettings.NUMBER_OF_BLOCKS) {
                message = "Congratulation!";
                stopGame();
            }
        }
    }

    private void blocksCollisions() {
        for (int i = 0; i < GlobalSettings.NUMBER_OF_BLOCKS; i++) {
            if ((ball.getRectantgle()).intersects(blocks[i].getRectantgle())) {

                int ballLeft = (int) ball.getRectantgle().getMinX();
                int ballBottom = (int) ball.getRectantgle().getHeight();
                int ballRight = (int) ball.getRectantgle().getWidth();
                int ballTop = (int) ball.getRectantgle().getMinY();

                var pointRight = new Point(ballLeft + ballRight, ballTop + (ballBottom / 2));
                var pointLeft = new Point(ballLeft, ballTop + (ballBottom / 2));
                var pointTop = new Point(ballLeft + (ballRight / 2), ballTop);
                var pointBottom = new Point(ballLeft + (ballRight / 2), ballTop + ballBottom);

                var pointTopLeft = new Point(ballLeft, ballTop);
                var pointTopRight = new Point(ballLeft + ballRight, ballTop);
                var pointBottomLeft = new Point(ballLeft, ballTop + ballBottom);
                var pointBottomRight = new Point(ballLeft + ballRight, ballTop + ballBottom);

                if (!blocks[i].isBlockDestroyed()) {

                    if (blocks[i].getRectantgle().contains(pointRight)) {
                        ball.setXdir(-2);
                    } else if (blocks[i].getRectantgle().contains(pointLeft)) {
                        ball.setXdir(2);
                    }
                    if (blocks[i].getRectantgle().contains(pointTop)) {
                        ball.setYdir(2);
                    } else if (blocks[i].getRectantgle().contains(pointTopLeft)) {
                        ball.setXdir(-1 * ball.getXdir());
                        ball.setYdir(-1 * ball.getXdir());
                    } else if (blocks[i].getRectantgle().contains(pointTopRight)) {
                        ball.setXdir(-1 * ball.getXdir());
                        ball.setYdir(-1 * ball.getXdir());
                    }
                    if (blocks[i].getRectantgle().contains(pointBottom)) {
                        ball.setYdir(-2);
                    } else if (blocks[i].getRectantgle().contains(pointBottomLeft)) {
                        ball.setXdir(-1 * ball.getXdir());
                        ball.setYdir(-1 * ball.getXdir());
                    } else if (blocks[i].getRectantgle().contains(pointBottomRight)) {
                        ball.setXdir(-1 * ball.getXdir());
                        ball.setYdir(-1 * ball.getXdir());
                    }

                    blockDestroyed += 1;
                    blocks[i].setBlockDestroyed(true);
                    score = "Your score: " + blockDestroyed;
                }
            }
        }
    }
}

