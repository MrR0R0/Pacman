package org.example.pacman;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameOverController {
    @FXML
    private Label loseLabel;

    public void changeLabelText(String newText) {
        loseLabel.setText(newText);
    }
}