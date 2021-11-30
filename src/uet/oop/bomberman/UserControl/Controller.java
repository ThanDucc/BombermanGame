package uet.oop.bomberman.UserControl;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.awt.*;

public class Controller {
    private Scene scene;
    public int cmd;

    public static final int MOVE_W = 1;
    public static final int MOVE_A = 2;
    public static final int MOVE_S = 3;
    public static final int MOVE_D = 4;

    public boolean bombPlanted = false;
    public int bombCountdown = 0;

    long startTime = System.currentTimeMillis();
    long elaspedTime = 0L;

    final BooleanProperty WPressed = new SimpleBooleanProperty(false);
    final BooleanProperty APressed = new SimpleBooleanProperty(false);
    final BooleanProperty SPressed = new SimpleBooleanProperty(false);
    final BooleanProperty DPressed = new SimpleBooleanProperty(false);

    /*
    final BooleanBinding WandAPressed = WPressed.and(APressed);
    final BooleanBinding WandDPressed = WPressed.and(DPressed);
    final BooleanBinding SandAPressed = SPressed.and(APressed);
    final BooleanBinding SandDPressed = SPressed.and(DPressed);
    */


    public Controller(Scene scene) {
        this.scene = scene;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public int getCmd() {
        return cmd;
    }

    public int control() {
        this.scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode().toString()) {
                case "W":
                    cmd = MOVE_W;
                    WPressed.set(true);
                    break;
                case "A":
                    cmd = MOVE_A;
                    APressed.set(true);
                    break;
                case "S":
                    cmd = MOVE_S;
                    SPressed.set(true);
                    break;
                case "D":
                    cmd = MOVE_D;
                    DPressed.set(true);
                    break;
                case "ENTER":
                    bombPlanted = true;
                    break;
                default:
                    break;
            }
        });

        this.scene.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode().toString()) {
                case "W":
                    WPressed.set(false);
                    break;
                case "A":
                    APressed.set(false);
                    break;
                case "S":
                    SPressed.set(false);
                    break;
                case "D":
                    DPressed.set(false);
                    break;
                case "ENTER":
                    bombPlanted = false;
                    break;
                default:
                    break;
            }
            if (!WPressed.get() && !SPressed.get() && !APressed.get() && !DPressed.get()) {
                cmd = 0;
            } else if (WPressed.get()) {
                cmd = MOVE_W;
            } else if (APressed.get()) {
                cmd = MOVE_A;
            } else if (SPressed.get()) {
                cmd = MOVE_S;
            } else if (DPressed.get()) {
                cmd = MOVE_D;
            }
        });
        return cmd;
    }

    public boolean isBombPlanted() {
        return this.bombPlanted;
    }

    public int getBombCountdown() {
        return bombCountdown;
    }

    public void setBombCountdown(int bombCountdown) {
        this.bombCountdown = bombCountdown;
    }
}
