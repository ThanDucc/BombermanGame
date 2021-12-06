package uet.oop.bomberman.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import uet.oop.bomberman.File.ReadFile;
import uet.oop.bomberman.File.WriteFile;
import uet.oop.bomberman.GameProcessing;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static uet.oop.bomberman.GameProcessing.Score;

public class WinCotroller implements Initializable {

    private Stage stage;

    private Scene scene;

    private GameProcessing gameProcessing = new GameProcessing();

    private ArrayList<String[]> listHighScore = new ArrayList<>();

    @FXML
    private Label score = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        score.setText(String.valueOf(Score));

    }

    @FXML
    public void Close(ActionEvent event) throws IOException {
        listHighScore = ReadFile.readAndSort();
        boolean checkScore = false;

        if (listHighScore.size() < 5) {
            WriteFile.write(String.valueOf(Score));
            WriteFile.write("\t\t");
            checkScore = true;
        } else {
            String score = listHighScore.get(4)[0];
            if (Integer.parseInt(score) < Score) {
                WriteFile.write(String.valueOf(Score));
                WriteFile.write("\t\t");
                checkScore = true;
            }
        }
        if (checkScore) {
            Parent root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/interface/SaveScore.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root1);
            stage.setScene(scene);
            stage.show();

        } else {
            Parent root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/interface/MainScreen.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root1);
            stage.setScene(scene);
            stage.show();
        }

    }

}

