package uet.oop.bomberman.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import uet.oop.bomberman.GameProcessing;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static uet.oop.bomberman.ControllerClasses.MainController.soundMusic;

public class FeedBackController {

    private GameProcessing gameProcessing = new GameProcessing();

    @FXML
    private Button back = new Button();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void BackToMain(ActionEvent event) throws IOException {
        soundMusic.pause();
        Parent root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/interface/MainScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root1);
        stage.setScene(scene);
        stage.show();
    }

}

