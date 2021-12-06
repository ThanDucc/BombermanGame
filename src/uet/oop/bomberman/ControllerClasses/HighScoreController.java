package uet.oop.bomberman.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import uet.oop.bomberman.File.ReadFile;
import uet.oop.bomberman.GameProcessing;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static uet.oop.bomberman.ControllerClasses.MainController.soundMusic;

public class HighScoreController implements Initializable {

    private GameProcessing gameProcessing = new GameProcessing();

    @FXML
    private Button back = new Button();

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ArrayList<String[]> listHighScore = new ArrayList<>();

    @FXML
    private Label nametop1 = new Label();

    @FXML
    private Label nametop2 = new Label();

    @FXML
    private Label nametop3 = new Label();

    @FXML
    private Label nametop4 = new Label();

    @FXML
    private Label nametop5 = new Label();

    @FXML
    private Label scoretop1 = new Label();

    @FXML
    private Label scoretop2 = new Label();

    @FXML
    private Label scoretop3 = new Label();

    @FXML
    private Label scoretop4 = new Label();

    @FXML
    private Label scoretop5 = new Label();

    private List<Label> listName = List.of(new Label[]{nametop1, nametop2, nametop3, nametop4, nametop5});

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listHighScore = ReadFile.readAndSort();
        nametop1.setText(listHighScore.get(0)[1]);
        nametop2.setText(listHighScore.get(1)[1]);
        nametop3.setText(listHighScore.get(2)[1]);
        nametop4.setText(listHighScore.get(3)[1]);
        nametop5.setText(listHighScore.get(4)[1]);

        scoretop1.setText(listHighScore.get(0)[0]);
        scoretop2.setText(listHighScore.get(1)[0]);
        scoretop3.setText(listHighScore.get(2)[0]);
        scoretop4.setText(listHighScore.get(3)[0]);
        scoretop5.setText(listHighScore.get(4)[0]);

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

