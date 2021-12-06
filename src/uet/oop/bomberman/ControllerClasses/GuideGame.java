package uet.oop.bomberman.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import uet.oop.bomberman.GameProcessing;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static uet.oop.bomberman.ControllerClasses.MainController.soundMusic;

public class GuideGame implements Initializable {

    private GameProcessing gameProcessing = new GameProcessing();
    @FXML
    private Button Start = new Button();

    @FXML
    private Button back = new Button();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void StartGame(ActionEvent event) throws Exception {
        soundMusic.stop();
        Stage primaryStage = new Stage();
        gameProcessing = new GameProcessing();
        gameProcessing.start(primaryStage);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

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

