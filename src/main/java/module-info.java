module org.example.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.pacman to javafx.fxml;
    exports org.example.pacman;
    exports org.example.pacman.Map;
}