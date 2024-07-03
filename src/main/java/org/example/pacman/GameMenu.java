package org.example.pacman;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.pacman.app.Game;
import org.example.pacman.app.Pacman;
import org.example.pacman.ghost.Clyde;
import org.example.pacman.ghost.Inky;
import org.example.pacman.ghost.Pinky;
import org.example.pacman.ghost.Shadow;
import org.example.pacman.map.MapGenerator;
import java.io.IOException;
import java.util.List;

public class GameMenu implements EventHandler<KeyEvent> {
    int BLOCK_SIZE, FRAME_RATE = 40;
    int X_BLOCKS, Y_BLOCKS;
    private final Image favicon = new Image("file:src\\main\\resources\\favicon.png");
    double secPerFrame = ((double) 1 / FRAME_RATE);
    private static Group root;
    private static Game game;
    private static Scene scene;
    private static Stage stage;
    private AnimationTimer timer;
    private int map;

    public void loadGame1() throws Exception{
        BLOCK_SIZE = 40;
        map = 1;
        MapGenerator.generate("src\\main\\resources\\drafts\\map1.txt");
        setUpGame();
        game.setPacman(new Pacman(8 * BLOCK_SIZE, 6 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE, X_BLOCKS, Y_BLOCKS));
        game.addShadow(new Shadow(BLOCK_SIZE, 10 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE));
        game.addClyde(new Clyde(15 * BLOCK_SIZE, 6 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE));
        stage.show();
        setTimer();
        timer.start();
    }

    public void loadGame2() throws Exception {
        BLOCK_SIZE = 25;
        map = 2;
        MapGenerator.generate("src\\main\\resources\\drafts\\map2.txt");
        setUpGame();

        game.setPacman(new Pacman(25 * BLOCK_SIZE, 6 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE, X_BLOCKS, Y_BLOCKS));
        game.addShadow(new Shadow(11 * BLOCK_SIZE, 2 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE));
        game.addShadow(new Shadow(25 * BLOCK_SIZE, 2 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE));
        game.addClyde(new Clyde(33 * BLOCK_SIZE, 2 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE));
        game.addClyde(new Clyde(40 * BLOCK_SIZE, 2 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE));
        game.addPinky(new Pinky(21 * BLOCK_SIZE, 10 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE,
                BLOCK_SIZE * Y_BLOCKS, BLOCK_SIZE * X_BLOCKS));
        game.addPinky(new Pinky(29 * BLOCK_SIZE, 12 * BLOCK_SIZE, Game.Direction.U, root, BLOCK_SIZE,
                BLOCK_SIZE * Y_BLOCKS, BLOCK_SIZE * X_BLOCKS));

        stage.show();
        setTimer();
        timer.start();
    }

    public void loadGame3() throws Exception {
        BLOCK_SIZE = 24;
        map = 3;
        MapGenerator.generate("src\\main\\resources\\drafts\\map3.txt");
        setUpGame();

        game.setPacman(new Pacman(180, 90, Game.Direction.R, root, BLOCK_SIZE, X_BLOCKS, Y_BLOCKS));
        //game.addShadow(new Shadow(180, 210, Game.Direction.U, root, BLOCK_SIZE));
        //game.addClyde(new Clyde(180, 270, Game.Direction.U, root, BLOCK_SIZE));
        game.addPinky(new Pinky(180, 300, Game.Direction.U, root, BLOCK_SIZE,
                BLOCK_SIZE * Y_BLOCKS, BLOCK_SIZE * X_BLOCKS));
        game.addInky(new Inky(180, 330, Game.Direction.U, root, BLOCK_SIZE));

        stage.show();

        setTimer();
        timer.start();
    }

    private void endGame() throws IOException {
        root.getChildren().clear();
        root = null;
        stage.close();
        game.clear();
        timer.stop();
        Application.loadMainMenu();
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

    private void setTimer(){
        timer = new AnimationTimer() {
            private long prevTime = 0;
            @Override
            public void handle(long now) {
                long dt = now - prevTime;
                if (dt > secPerFrame * 1e9) {
                    prevTime = now;
                    game.moveObjects();
                    if (game.checkCollision()) {
                        try {
                            Menu.currentUser.addScore(map, game.getPacman().getScore());
                            endGame();
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        };
    }

    private void setUpGame() throws Exception {
        Application.closeWindow();

        root = new Group();
        game = new Game(BLOCK_SIZE, root);
        X_BLOCKS = game.getCol();
        Y_BLOCKS = game.getRow();
        scene = new Scene(root, BLOCK_SIZE * X_BLOCKS, BLOCK_SIZE * Y_BLOCKS + 50, Color.BLACK);
        stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(favicon);
        stage.setTitle("Game");
        stage.setScene(scene);
        scene.setOnKeyPressed(this);
        game.drawMap();
    }

}
