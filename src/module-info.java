module bomb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;

    opens uet.oop.bomberman to javafx.fxml, javafx.graphics;
    exports uet.oop.bomberman;
    exports uet.oop.bomberman.Control;
    opens uet.oop.bomberman.Control to javafx.fxml, javafx.graphics;
    exports uet.oop.bomberman.File;
    opens uet.oop.bomberman.File to javafx.fxml, javafx.graphics;

    exports uet.oop.bomberman.ControllerClasses;
    opens uet.oop.bomberman.ControllerClasses to javafx.fxml, javafx.graphics;
}