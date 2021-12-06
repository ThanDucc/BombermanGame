package uet.oop.bomberman.entities.dynamicEntities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.sound.Sound;

import java.io.File;
import java.util.List;

public abstract class DynamicEntity extends Entity {

    private int speed;
    public boolean isAlive = true;
    public long startdead;
    public boolean removeAvailbe = false;
    protected List<Entity> stillObjects;

    public DynamicEntity(int xUnit, int yUnit, Image img, List<Entity> stillObjects) {
        super(xUnit, yUnit, img);
        this.stillObjects = stillObjects;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public abstract boolean checkCollection(double x, double y);

    public void dead() {
        startdead = System.currentTimeMillis();
        isAlive = false;
    }

    public boolean isRemoveAvailbe() {
        return removeAvailbe;
    }

    public void setStillObjs(List<Entity> stillObjs) {
        this.stillObjects = stillObjs;
    }

    public final Sound soundKillEnemy = new Sound(new File("res/sound/KILL_ENEMY.wav"));


}
