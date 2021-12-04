package uet.oop.bomberman.HighScore;

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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class HighScoreController implements Initializable {

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
        MyScore.setText(String.valueOf(arrayList.get(arrayList.size()-1)[0]));

    }

    @FXML
    public void Cancel(ActionEvent event) throws Exception {
        if (nameGamer.getText().length() == 0) {
            WriteFile.write("Ẩn danh\n");
        }

//        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
        stage.close();
    }

    @FXML
    public void Save(ActionEvent event) throws IOException {
        if (nameGamer.getText().length() != 0) {
            WriteFile.write(nameGamer.getText());
        }
        else {
            WriteFile.write("Ẩn danh");
        }
        WriteFile.write("\n");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.close();
    }

//    public void Home() throws IOException {
//        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("HighC.fxml")));
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
}
