package uet.oop.bomberman.ControllerClasses;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import uet.oop.bomberman.File.ReadFile;
import uet.oop.bomberman.File.WriteFile;
import uet.oop.bomberman.GameProcessing;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Objects;
import java.util.ResourceBundle;

import static uet.oop.bomberman.ControllerClasses.MainController.soundMusic;

import static uet.oop.bomberman.GameProcessing.Score;


public class SaveScoreController implements Initializable {

    private GameProcessing gameProcessing = new GameProcessing();

    @FXML
    private Label MyScore = new Label();

    @FXML
    private TextField nameGamer = new TextField();

    @FXML
    private Button save = new Button();

    @FXML
    private Button cancel = new Button();

    private Stage stage;
    private Scene scene;
    private Parent root;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String[]> arrayList = ReadFile.read();
        MyScore.setText(String.valueOf(Score));

    }

    @FXML
    public void Cancel(ActionEvent event) throws Exception {
        if (nameGamer.getText().length() == 0) {
            WriteFile.write("Ẩn danh\n");
        }

        soundMusic.pause();
        Parent root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/interface/MainScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root1);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void Save(ActionEvent event) throws IOException {
        if (nameGamer.getText().length() != 0) {
            WriteFile.write(nameGamer.getText());
        } else {
            WriteFile.write("Ẩn danh");
        }
        WriteFile.write("\n");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText(null);
        alert.setContentText("\t---- Lưu điểm thành công ----\n");

        alert.setOnCloseRequest(evt -> {
            soundMusic.pause();
            Parent root1 = null;
            try {
                root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/interface/MainScreen.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root1);
            stage.setScene(scene);
            stage.show();
        });
        alert.show();
    }
}
