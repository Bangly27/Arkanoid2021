package gamefiles;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow() {
        createGame();
    }

    public void createGame() {
        add(new Board());
        setTitle("Arkanoid 2021");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var game = new GameWindow();
            game.setVisible(true);
        });
    }
}
