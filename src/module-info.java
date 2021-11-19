module bomb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;

    opens uet.oop.bomberman to javafx.fxml;
    exports uet.oop.bomberman;

}