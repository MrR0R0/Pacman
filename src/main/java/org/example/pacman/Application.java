package org.example.pacman;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import org.example.pacman.Database.Connect;


public class Application extends javafx.application.Application implements EventHandler<KeyEvent> {
    static public Game game;
    static public Parent root;
    static public Scene scene;
    static public Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        final int BLOCK_SIZE = 30, FRAME_RATE = 40;
        int X_BLOCKS, Y_BLOCKS;
        double secPerFrame = ((double) 1 / FRAME_RATE);
        User.signedUpUsers = Connect.getUsers();
        stage = primaryStage;
        stage.setResizable(false);
        switch (Menu.currentMenu){
            case Login -> {
                loadLoginMenu();
            }
            case SignUp -> {
                loadSignupMenu();
            }
        }

        //controller.run(primaryStage);
        /*
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
         */
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

    public static void loadSignupMenu() throws IOException {
        Menu.currentMenu = Menu.MenuType.SignUp;
        Application.root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("signup.fxml")));
        Application.scene = new Scene(Application.root, 700, 500);
        Application.stage.setTitle("Signup");
        Application.stage.setScene(scene);
        Application.stage.show();
    }

    public static void loadLoginMenu() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("login.fxml")));
        scene = new Scene(root, 700, 500);
        Application.stage.setTitle("Login");
        Application.stage.setScene(scene);
        Application.stage.show();
    }

}