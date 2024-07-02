package org.example.pacman;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.pacman.Ghost.Clyde;
import org.example.pacman.Ghost.Inky;
import org.example.pacman.Ghost.Pinky;
import org.example.pacman.Ghost.Shadow;
import org.example.pacman.Map.MapGenerator;

import java.io.IOException;


public class Application extends javafx.application.Application implements EventHandler<KeyEvent> {
    Game game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final int BLOCK_SIZE = 30, FRAME_RATE = 40;
        int X_BLOCKS, Y_BLOCKS;
        double secPerFrame = ((double) 1 / FRAME_RATE);
        Group root = new Group();
        MapGenerator.generate("src\\main\\resources\\drafts\\map1.txt");
        game = new Game(BLOCK_SIZE, root);
        X_BLOCKS = game.getCol();
        Y_BLOCKS = game.getRow();

        Scene scene = new Scene(root, BLOCK_SIZE * X_BLOCKS, BLOCK_SIZE * Y_BLOCKS + 100, Color.BLACK);
        Stage stage = new Stage();
        stage.setResizable(false);
        scene.setOnKeyPressed(this);
        Image favicon = new Image("file:src\\main\\resources\\favicon.png");

        game.drawMap();
        game.setPacman(new Pacman(180, 90, Game.Direction.R, root, BLOCK_SIZE, X_BLOCKS, Y_BLOCKS));
        game.addShadow(new Shadow(180, 210, Game.Direction.U, root, BLOCK_SIZE));
        game.addClyde(new Clyde(180, 270, Game.Direction.U, root, BLOCK_SIZE));
        game.addPinky(new Pinky(180, 300, Game.Direction.U, root, BLOCK_SIZE,
                BLOCK_SIZE * Y_BLOCKS, BLOCK_SIZE * X_BLOCKS));
        game.addInky(new Inky(180, 330, Game.Direction.U, root, BLOCK_SIZE));

        stage.setResizable(false);
        stage.getIcons().add(favicon);
        stage.setTitle("Pacman");
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            private long prevTime = 0;

            @Override
            public void handle(long now) {
                long dt = now - prevTime;
                if (dt > secPerFrame * 1e9) {
                    prevTime = now;
                    game.moveObjects();
                    if (game.checkCollision()) {
                        System.exit(0);
                    }
                }
            }
        };
        timer.start();
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DOWN)) {
            game.getPacman().changeDirection(Game.Direction.D);
        } else if (event.getCode().equals(KeyCode.UP)) {
            game.getPacman().changeDirection(Game.Direction.U);
        } else if (event.getCode().equals(KeyCode.RIGHT)) {
            game.getPacman().changeDirection(Game.Direction.R);
        } else if (event.getCode().equals(KeyCode.LEFT)) {
            game.getPacman().changeDirection(Game.Direction.L);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}