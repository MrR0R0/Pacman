package org.example.pacman;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WinController {
    @FXML
    private Label winLabel;

    public void changeLabelText(String newText) {
        winLabel.setText(newText);
    }
}
