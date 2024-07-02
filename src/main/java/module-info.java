module org.example.pacman {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.pacman to javafx.fxml;
    exports org.example.pacman;
    exports org.example.pacman.Map;
}