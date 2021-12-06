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
import uet.oop.bomberman.sound.Sound;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainController extends Thread {

    private GameProcessing gameProcessing = new GameProcessing();

    @FXML
    private Button start = new Button();

    @FXML
    private Button guide = new Button();

    @FXML
    private Button highscore = new Button();

    @FXML
    private Button aboutus = new Button();

    @FXML
    private Button exit = new Button();

    private Stage stage;
    private Scene scene;
    private Parent root;

    public static Sound soundMusic = new Sound(new File("res/sound/MUSIC.wav"));

    public MainController() {
        soundMusic.resume();
    }

    @FXML
    public void StartGame(ActionEvent event) throws Exception {
        soundMusic.stop();
        stage = new Stage();
        gameProcessing = new GameProcessing();
        gameProcessing.start(stage);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void GuideGame(ActionEvent event) throws IOException {
        Parent root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/interface/GuideGame.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root1);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void HighScore(ActionEvent event) throws IOException {
        Parent root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/interface/HighScore.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root1);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void AboutUs(ActionEvent event) throws IOException {
        Parent root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/interface/FeedBack.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root1);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void Exit(ActionEvent event) {
        System.exit(1);
    }

}

