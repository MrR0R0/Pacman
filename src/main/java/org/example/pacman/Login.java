package org.example.pacman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static org.example.pacman.Application.loadSignupMenu;

public class Login {
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Button loginButton;

    public void signUp(ActionEvent actionEvent) throws IOException {
        loadSignupMenu();
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
