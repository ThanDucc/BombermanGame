module bomb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;

    opens uet.oop.bomberman to javafx.fxml;
    exports uet.oop.bomberman;
    exports uet.oop.bomberman.Control;
    opens uet.oop.bomberman.Control to javafx.fxml;
    exports uet.oop.bomberman.HighScore;
    opens uet.oop.bomberman.HighScore to javafx.fxml;


}