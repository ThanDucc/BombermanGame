module bomberman {
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
    exports uet.oop.bomberman.entities;
    opens uet.oop.bomberman.entities to javafx.fxml, javafx.graphics;
    exports uet.oop.bomberman.entities.monster;
    opens uet.oop.bomberman.entities.monster to javafx.fxml, javafx.graphics;
    exports uet.oop.bomberman.entities.staticEntities;
    opens uet.oop.bomberman.entities.staticEntities to javafx.fxml, javafx.graphics;
    exports uet.oop.bomberman.entities.item;
    opens uet.oop.bomberman.entities.item to javafx.fxml, javafx.graphics;
    exports uet.oop.bomberman.entities.dynamicEntities;
    opens uet.oop.bomberman.entities.dynamicEntities to javafx.fxml, javafx.graphics;

}
