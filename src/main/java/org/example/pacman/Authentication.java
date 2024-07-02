package org.example.pacman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Objects;

import static org.example.pacman.Application.scene;

public class Authentication {
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Button loginButton;

    public void signUp(ActionEvent actionEvent) throws IOException {
        Menu.currentMenu = Menu.MenuType.SignUp;
        Application.root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signup.fxml")));
        Application.scene = new Scene(Application.root, 700, 500);
        Application.stage.setTitle("Signup");
        Application.stage.setScene(scene);
        Application.stage.show();
    }

    public void login(ActionEvent actionEvent) {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        if(User.signedUpUsers.containsKey(username)){
            if(User.signedUpUsers.get(username).getPassword().equals(password)){
                System.out.println("Login");
                Menu.currentUser = User.signedUpUsers.get(username);
            }
        }
        System.out.println(username + " " + password);
        usernameInput.clear();
        passwordInput.clear();
    }
}
