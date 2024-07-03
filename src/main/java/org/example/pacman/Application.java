package org.example.pacman;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import org.example.pacman.database.Connect;


public class Application extends javafx.application.Application {
    static public Parent root;
    static public Scene scene;
    static public Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        User.signedUpUsers = Connect.getUsers();
        stage = primaryStage;
        stage.setResizable(false);
        loadLoginMenu();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            try {
                User.saveToDatabase();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            primaryStage.close();
            Platform.exit();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void loadSignupMenu() throws IOException {
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

    public static void loadMainMenu() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("main.fxml")));
        scene = new Scene(root, 950, 500);
        Application.stage.setTitle("Main Menu");
        Application.stage.setScene(scene);
        Application.stage.show();
    }

    public static void closeWindow(){
        stage.close();
    }

}