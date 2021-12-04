package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import uet.oop.bomberman.Control.Controller;
import uet.oop.bomberman.HighScore.ReadFile;
import uet.oop.bomberman.HighScore.WriteFile;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class GameProcessing extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 15;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Entity> bombs = new ArrayList<>();

    private Bomber bomberman;
    private List<Entity> flames = new ArrayList<>();
    public Portal portal;
    private List<Entity> barrier = new ArrayList<>();

    private List<DynamicEntity> monsters = new ArrayList<>();
    private Controller keyboard;
    private List<Entity> grasses = new ArrayList<>();

    private int bombActived = 0;
    private long itemBUsedTime;
    private long itemFUsedTime;
    private long itemSUsedTime;

    private boolean lose = false;
    private boolean win = false;

    public static char[][] loadMap = new char[50][50];

    private final Sound soundBackground = new Sound(new File("res/sound/BACKGROUND.wav"));
    private final Sound soundBomberDie = new Sound(new File("res/sound/BOMBER_DIE.wav"));
    private final Sound soundLoseGame = new Sound(new File("res/sound/LOSE.wav"));
    private final Sound soundBomSet = new Sound(new File("res/sound/BOM_SET.wav"));
    private final Sound soundHenGio = new Sound(new File("res/sound/HEN_GIO.wav"));
    private final Sound soundBomNo = new Sound(new File("res/sound/BOM_NO.wav"));
    private final Sound soundLevelUp = new Sound(new File("res/sound/LEVEL_UP.wav"));
    private final Sound soundGetItem = new Sound(new File("res/sound/GET_ITEM.wav"));

    private int Score = 0;
    private int level = 1;
    private int left = 3;

    private ArrayList<String[]> listHighScore = new ArrayList<>();

    public int getScore() {
        return Score;
    }

    @Override
    public void start(Stage stage) throws Exception {
        soundBackground.playBackground();

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);
        Controller keyboard = new Controller(scene);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (lose) {
                    left--;

                    soundBomberDie.play();
                    soundBackground.stop();
                    soundHenGio.stop();
                    soundBomNo.stop();

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    soundLoseGame.play();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    if (left == 0) {
                        alert.setTitle("LOSE");
                        alert.setHeaderText(null);
                        alert.setContentText("\t---- NON ----\n\n\tĐIỂM CỦA BẠN:   " + Score);

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
                            alert.setOnCloseRequest(evt -> {
                                root.getChildren().clear();
                                Parent parent = null;
                                try {
                                    parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/HighScore.fxml")));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Group root = new Group();
                                root.getChildren().add(parent);
                                stage.setScene(new Scene(root));
                                stage.show();

                            });
                        } else {
                            alert.setOnCloseRequest(evt -> stage.close());
                        }

                    } else {
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("\t----   BẠN ĐÃ THUA, BẠN CÒN " + left + " MẠNG" + "   ----\n\n\tĐIỂM HIỆN TẠI CỦA BẠN:   " + Score);

                        alert.setOnCloseRequest(evt -> {
                            lose = false;
                            Controller keyboard = new Controller(scene);
                            bomberman = new Bomber(1, 3, Sprite.player_right.getFxImage(), keyboard, stillObjects);
                            stillObjects.clear();
                            monsters.clear();
                            flames.clear();
                            barrier.clear();
                            bombs.clear();

                            try {
                                createMap(level);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            this.start();
                            soundBackground.playBackground();
                        });

                    }
                    this.stop();
                    alert.show();

                }
                if (win) {
                    level++;
                    if (left < 5) {
                        left++;
                    }

                    soundLevelUp.play();
                    soundBackground.stop();
                    soundHenGio.stop();
                    soundBomNo.stop();
                    if (level == 3) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("WIN");
                        alert.setHeaderText(null);
                        alert.setContentText("\t----   CHÚC MỪNG BẠN ĐÃ PHÁ ĐẢO TRÒ CHƠI   ----\n\n\tĐIỂM CỦA BẠN:   " + Score);

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
                            alert.setOnCloseRequest(evt -> {
                                root.getChildren().clear();
                                Parent parent = null;
                                try {
                                    parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/HighScore.fxml")));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Group root = new Group();
                                root.getChildren().add(parent);
                                stage.setScene(new Scene(root));
                                stage.show();

                            });
                        } else {
                            alert.setOnCloseRequest(evt -> stage.close());
                        }

                        this.stop();
                        alert.show();
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("WIN");
                        alert.setHeaderText(null);
                        alert.setContentText("\t----   GAME LÀ DỄ,  NEXT LEVEL   ----\n\n\tĐIỂM HIỆN TẠI CỦA BẠN:   " + Score);
                        alert.setOnCloseRequest(evt -> {
                            win = false;
                            bomberman.dead();

                            stillObjects.clear();
                            monsters.clear();
                            flames.clear();
                            barrier.clear();
                            bombs.clear();

                            Controller keyboard = new Controller(scene);
                            bomberman = new Bomber(1, 3, Sprite.player_right.getFxImage(), keyboard, stillObjects);
                            try {
                                createMap(level);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            this.start();
                            soundBackground.playBackground();
                        });

                        this.stop();
                        alert.show();
                    }

                }

                render();
                update();
            }
        };
        timer.start();
        bomberman = new Bomber(1, 3, Sprite.player_right.getFxImage(), keyboard, stillObjects);

        createMap(level);


    }

    public void createMap(int level) throws FileNotFoundException {

        String url = "res/levels/Level" + level + ".txt";

        FileInputStream fileInputStream;

        fileInputStream = new FileInputStream(url);
        Scanner scanner = new Scanner(fileInputStream);
        String line1 = scanner.nextLine();

        String[] number = line1.split(" ");
        int heigth = Integer.parseInt(number[1]);
        int width = Integer.parseInt(number[2]);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigth; j++) {
                Entity object = new Grass(i + 1, j + 2, Sprite.grass.getFxImage());
                grasses.add(object);
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < 2; j++) {
                Entity object = new Grass(i, j, Sprite.score.getFxImage());
                grasses.add(object);
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
                        entity = new Wall(j, i + 2, Sprite.wall.getFxImage());
                        stillObjects.add(entity);
                    }
                    case '*' -> {
                        entity = new Brick(j, i + 2, Sprite.brick.getFxImage());
                        stillObjects.add(entity);
                    }
                    case 'x' -> {
                        entity = new Portal(j, i + 2, Sprite.portal.getFxImage());
                        stillObjects.add(entity);
                        entity = new Brick(j, i + 2, Sprite.brick.getFxImage());
                        stillObjects.add(entity);
                    }
                    case '1' -> {
                        entity = new Balloom(j, i + 2, Sprite.balloom_right1.getFxImage(), stillObjects, bombs);
                        monsters.add((DynamicEntity) entity);
                    }
                    case '2' -> {
                        entity = new Oneal(j, i + 2, Sprite.oneal_right1.getFxImage(), stillObjects, bombs);
                        monsters.add((DynamicEntity) entity);
                    }
                    case 'b' -> {
                        stillObjects.add(new Item(j, i + 2, Sprite.powerup_bombs.getFxImage(), Item.bombItem));
                        stillObjects.add(new Brick(j, i + 2, Sprite.brick.getFxImage()));
                    }

                    case 'f' -> {
                        stillObjects.add(new Item(j, i + 2, Sprite.powerup_flames.getFxImage(), Item.flameItem));
                        stillObjects.add(new Brick(j, i + 2, Sprite.brick.getFxImage()));
                    }
                    case 's' -> {
                        stillObjects.add(new Item(j, i + 2, Sprite.powerup_speed.getFxImage(), Item.speedItem));
                        stillObjects.add(new Brick(j, i + 2, Sprite.brick.getFxImage()));
                    }
                    case 'k' -> {
                        monsters.add(new Kondoria(j, i + 2, Sprite.kondoria_right1.getFxImage(), stillObjects, bombs, bomberman, true));
                    }
                    case 'm' -> {
                        monsters.add(new Minvo(j, i + 2, Sprite.minvo_right1.getFxImage(), stillObjects, bombs, true));
                    }
                }
            }
        }


    }

    public void levelFinish() {
        win = true;
    }

    public void endGame() {
        lose = true;
    }

    public void update() {

        gc.setFont(Font.font("Comic Sans MS", FontWeight.SEMI_BOLD, 30));
        gc.setFill(Color.WHITE);

        gc.fillText("LEVEL", 100, 45);
        gc.fillText(String.valueOf(level), 210, 45);
        gc.fillText("SCORE", 400, 45);
        gc.fillText(String.valueOf(Score), 525, 45);
        gc.fillText("LEFT", 750, 45);
        gc.fillText(String.valueOf(left), 850, 45);

        if (bomberman.isRemoveAvailbe()) {
            endGame();
        }
        if (bombActived <= bomberman.getBombSize()) {
            if (bomberman.isBombPlanted()) {
                bomberman.setBombAreaReleased(false);
                int locX = (bomberman.getX() + 15) / Sprite.SCALED_SIZE;
                int locY = (bomberman.getY() + 15) / Sprite.SCALED_SIZE;
                Bomb bomb = new Bomb(locX, locY, Sprite.bomb.getFxImage(), bomberman);
                bombs.add(bomb);
                soundBomSet.play();
                soundHenGio.play();
                bombActived++;
            }
        }
        if (bomberman.getSpeed() > 1) {
            long itemSCoundown = (new Date().getTime()) - itemSUsedTime;
            if (itemSCoundown >= 10000) {
                bomberman.setSpeed(1);
            }
        }
        if (bomberman.getBombSize() > 1) {
            long itemBCoundown = (new Date().getTime()) - itemBUsedTime;
            if (itemBCoundown >= 10000) {
                bomberman.setBombSize(1);

            }
        }
        if (bomberman.getFrameSize() > 2) {
            long itemFCoundown = (new Date().getTime()) - itemFUsedTime;
            if (itemFCoundown >= 20000) {
                bomberman.setFrameSize(2);
            }
        }

        bomberman.bombCollide(bombs);

        for (int i = 0; i < bombs.size(); i++) {
            if (bombs.get(i) instanceof Bomb) {
                if (((Bomb) bombs.get(i)).isExplored()) {
                    createFlames(i);
                    soundBomNo.play();
                    bombs.remove(bombs.get(i));
                    bombActived--;
                }
            }
        }

        for (int i = 0; i < monsters.size(); i++) {
            monsters.get(i).setStillObjs(stillObjects);
            if (monsters.get(i).isRemoveAvailbe()) {
                if (monsters.get(i) instanceof Kondoria) {
                    if (((Kondoria) monsters.get(i)).isMultiplyAvailble()) {
                        int dX = (monsters.get(i).getX() + 16) / Sprite.SCALED_SIZE;
                        int dY = (monsters.get(i).getY() + 16) / Sprite.SCALED_SIZE;
                        Kondoria kondoria1 = new Kondoria(dX, dY, Sprite.kondoria_left3.getFxImage(), stillObjects, bombs, bomberman, false);
                        Kondoria kondoria2 = new Kondoria(dX, dY, Sprite.kondoria_right1.getFxImage(), stillObjects, bombs, bomberman, false);
                        monsters.add(kondoria1);
                        monsters.add(kondoria2);
                    }
                }
                if (monsters.get(i) instanceof Minvo) {
                    if (((Minvo) monsters.get(i)).isMultiplyAvailble()) {
                        int dX = (monsters.get(i).getX() + 16) / Sprite.SCALED_SIZE;
                        int dY = (monsters.get(i).getY() + 16) / Sprite.SCALED_SIZE;
                        monsters.add(new Minvo(dX, dY, Sprite.minvo_right1.getFxImage(), stillObjects, bombs, false));
                    }

                }
                monsters.remove(monsters.get(i));
            } else if (monsters.get(i).collide(bomberman)) {
                //bomberman.dead();
            }
        }
        for (int i = 0; i < flames.size(); i++) {
            if (flames.get(i) instanceof Flame) {
                if (((Flame) flames.get(i)).isDisapper()) {
                    flames.remove(flames.get(i));
                    continue;
                } else if (flames.get(i).collide(bomberman, 5, 2)) {
                    bomberman.dead();
                }
                for (DynamicEntity e : monsters) {
                    if (e instanceof Balloom) {
                        if (flames.get(i).collide(e, 5, 2)) {
                            e.dead();
                            Score += 30;
                        }
                    } else if (e instanceof Oneal) {
                        if (flames.get(i).collide(e, 5, 2)) {
                            e.dead();
                            Score += 50;
                        }
                    } else if (e instanceof Kondoria) {
                        if (flames.get(i).collide(e, 5, 2)) {
                            e.dead();
                            Score += 90;
                        }
                    } else if (e instanceof Minvo) {
                        if (flames.get(i).collide(e, 5, 2)) {
                            e.dead();
                            Score += 70;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < stillObjects.size(); i++) {
            if (stillObjects.get(i) instanceof Brick) {
                if (stillObjects.get(i).isRemoveAvailbe()) {
                    stillObjects.remove(stillObjects.get(i));
                }
            } else if (stillObjects.get(i) instanceof Portal) {
                boolean check = false;
                if (stillObjects.get(i).collide(bomberman) && monsters.size() == 0) {
                    for (Entity stillObject : stillObjects) {
                        if (stillObject instanceof Brick) {
                            if (stillObjects.get(i).getX() == stillObject.getX()
                                    && stillObjects.get(i).getY() == stillObject.getY()) {
                                check = true;
                                break;
                            }
                        }
                    }
                    if (!check) {
                        bomberman.setX(stillObjects.get(i).getX());
                        bomberman.setY(stillObjects.get(i).getY());
                        levelFinish();
                    }
                }
            } else if (stillObjects.get(i) instanceof Item) {
                if (stillObjects.get(i).collide(bomberman)) {
                    switch (((Item) stillObjects.get(i)).getItemtype()) {
                        case Item.flameItem -> {
                            bomberman.setFrameSize(4);
                            itemFUsedTime = System.currentTimeMillis();
                            ((Item) stillObjects.get(i)).setUsed(true);
                            soundGetItem.play();
                            Score += 400;
                        }
                        case Item.speedItem -> {
                            bomberman.setSpeed(3);
                            itemSUsedTime = System.currentTimeMillis();
                            ((Item) stillObjects.get(i)).setUsed(true);
                            soundGetItem.play();
                            Score += 400;
                        }
                        case Item.bombItem -> {
                            bomberman.setBombSize(3);
                            itemBUsedTime = System.currentTimeMillis();
                            ((Item) stillObjects.get(i)).setUsed(true);
                            soundGetItem.play();
                            Score += 400;
                        }
                    }
                }
                if (((Item) stillObjects.get(i)).isUsed()) {
                    stillObjects.remove(stillObjects.get(i));
                }
            }
        }
        flames.forEach(Entity::update);
        bombs.forEach(Entity::update);
        monsters.forEach(Entity::update);
        stillObjects.forEach(Entity::update);
        bomberman.update();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grasses.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        flames.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        monsters.forEach(g -> g.render(gc));
        bomberman.render(gc);

    }

    public void createFlames(int i) {
        //Khoi tao 1 flame.
        Image topImg = Sprite.explosion_vertical.getFxImage();
        Image botImg = Sprite.explosion_vertical.getFxImage();

        Image leftImg = Sprite.explosion_horizontal.getFxImage();
        Image rightImg = Sprite.explosion_horizontal.getFxImage();

        int locX = (bombs.get(i).getX() + 25) / Sprite.SCALED_SIZE;
        int locY = (bombs.get(i).getY() + 25) / Sprite.SCALED_SIZE;

        int posTop = Flame.VER;
        int posBot = Flame.VER;
        int posLeft = Flame.HOR;
        int posRight = Flame.HOR;

        boolean blockTop = false;
        boolean blockBot = false;
        boolean blockLeft = false;
        boolean blockRight = false;

        Flame flame = new Flame(locX, locY, Sprite.bomb_exploded.getFxImage(), 10);
        flames.add(flame);
        for (int y = 1; Math.abs(y) <= bomberman.getFrameSize(); y++) {
            if (Math.abs(y) == bomberman.getFrameSize()) {
                topImg = Sprite.explosion_vertical_top_last.getFxImage();
                botImg = Sprite.explosion_vertical_down_last.getFxImage();
                leftImg = Sprite.explosion_horizontal_left_last.getFxImage();
                rightImg = Sprite.explosion_horizontal_right_last.getFxImage();
                posTop = Flame.LTOP;
                posLeft = Flame.LLEFT;
                posRight = Flame.LRIGHT;
                posBot = Flame.LDOWN;
            }
            Flame topFlame = new Flame(locX, locY - y, topImg, posTop);
            Flame botFlame = new Flame(locX, locY + y, botImg, posBot);
            Flame leftFlame = new Flame(locX - y, locY, leftImg, posLeft);
            Flame rightFlame = new Flame(locX + y, locY, rightImg, posRight);

            for (Entity e : stillObjects) {
                if (e instanceof Wall) {
                    if (topFlame.collide(e)) {
                        blockTop = true;
                    }
                    if (botFlame.collide(e)) {
                        blockBot = true;
                    }
                    if (leftFlame.collide(e)) {
                        blockLeft = true;
                    }
                    if (rightFlame.collide(e)) {
                        blockRight = true;
                    }
                } else if (e instanceof Brick) {
                    if (topFlame.collide(e)) {
                        topImg = Sprite.explosion_vertical_top_last.getFxImage();
                        posTop = Flame.LTOP;
                        Flame BricktopFlame = new Flame(locX, locY - y, topImg, posTop);
                        if (!blockTop) {
                            flames.add(BricktopFlame);
                            ((Brick) e).setDestroyed(true);
                            ((Brick) e).setStartTimeBroken(System.currentTimeMillis());
                        }
                        blockTop = true;

                    }
                    if (botFlame.collide(e)) {
                        botImg = Sprite.explosion_vertical_down_last.getFxImage();
                        posTop = Flame.LDOWN;
                        Flame BrickbotFlame = new Flame(locX, locY + y, botImg, posBot);
                        if (!blockBot) {
                            flames.add(BrickbotFlame);
                            ((Brick) e).setDestroyed(true);
                            ((Brick) e).setStartTimeBroken(System.currentTimeMillis());

                        }
                        blockBot = true;

                    }
                    if (leftFlame.collide(e)) {
                        leftImg = Sprite.explosion_horizontal_left_last.getFxImage();
                        posLeft = Flame.LLEFT;
                        Flame BrickleftFlame = new Flame(locX - y, locY, leftImg, posLeft);
                        if (!blockLeft) {
                            flames.add(BrickleftFlame);
                            ((Brick) e).setDestroyed(true);
                            ((Brick) e).setStartTimeBroken(System.currentTimeMillis());

                        }
                        blockLeft = true;
                    }
                    if (rightFlame.collide(e)) {
                        rightImg = Sprite.explosion_horizontal_right_last.getFxImage();
                        posRight = Flame.LRIGHT;
                        Flame BrickrightFlame = new Flame(locX + y, locY, rightImg, posRight);
                        if (!blockRight) {
                            flames.add(BrickrightFlame);
                            ((Brick) e).setDestroyed(true);
                            ((Brick) e).setStartTimeBroken(System.currentTimeMillis());
                        }
                        blockRight = true;
                    }
                }
            }
            if (!blockTop) {
                flames.add(topFlame);
            }
            if (!blockBot) {
                flames.add(botFlame);
            }
            if (!blockLeft) {
                flames.add(leftFlame);
            }
            if (!blockRight) {
                flames.add(rightFlame);
            }
        }
    }


}
