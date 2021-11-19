package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.dynamicEntity.Balloom;
import uet.oop.bomberman.entities.dynamicEntity.DynamicEntity;
import uet.oop.bomberman.entities.dynamicEntity.Oneal;
import uet.oop.bomberman.entities.staticEntity.*;
import uet.oop.bomberman.entities.dynamicEntity.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<DynamicEntity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setTitle("GAME BOMBERMAN");
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();

        DynamicEntity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }

    public void createMap() throws FileNotFoundException {
        String url = "res/levels/Level1.txt";

        FileInputStream fileInputStream;
        char[][] loadMap = new char[50][50];

        fileInputStream = new FileInputStream(url);
        Scanner scanner = new Scanner(fileInputStream);
        String line1 = scanner.nextLine();

        String[] number = line1.split(" ");
        int heigth = Integer.parseInt(number[1]);
        int width = Integer.parseInt(number[2]);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigth; j++) {
                Entity object = new Grass(i, j, Sprite.grass.getFxImage());
                stillObjects.add(object);
            }
        }

        for (int i = 0; i < heigth; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < width; j++) {
                loadMap[i][j] = line.charAt(j);
            }
        }

        for (int i = 0; i < heigth; i++) {
            for (int j = 0; j < width; j++) {
                Entity entity;
                switch (loadMap[i][j]) {
                    case '#' -> {
                        entity = new Wall(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(entity);
                    }
                    case '*' -> {
                        entity = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(entity);
                    }
                    case 'x' -> {
                        entity = new Portal(j, i, Sprite.portal.getFxImage());
                        stillObjects.add(entity);
                    }
                    case '1' -> {
                        entity = new Balloom(j, i, Sprite.balloom_right1.getFxImage());
                        entities.add((DynamicEntity) entity);
                    }
                    case '2' -> {
                        entity = new Oneal(j, i, Sprite.oneal_right1.getFxImage());
                        entities.add((DynamicEntity) entity);
                    }
                    case 'f' -> {
                        entity = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                        stillObjects.add(entity);
                    }
                    case 'b' -> {
                        entity = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                        stillObjects.add(entity);
                    }
                    case 's' -> {
                        entity = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                        stillObjects.add(entity);
                    }
                }
            }
        }

    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
