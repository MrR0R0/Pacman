module org.example.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;

    opens org.example.pacman to javafx.fxml;
    exports org.example.pacman;
    exports org.example.pacman.map;
    exports org.example.pacman.app;
    opens org.example.pacman.app to javafx.fxml;
}