package uet.oop.bomberman;

import javafx.application.Application;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Entity;

import java.util.List;


public class BombermanGame extends Application {
    private GameProcessing gameProcessing;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameProcessing = new GameProcessing();
        gameProcessing.start(primaryStage);
    }


}
