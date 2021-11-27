package uet.oop.bomberman.entities.dynamicEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.staticEntity.Brick;
import uet.oop.bomberman.entities.staticEntity.Entity;
import uet.oop.bomberman.entities.staticEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;

public abstract class DynamicEntity extends Entity {

    private boolean isDead;
    private int speed;

    public DynamicEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public boolean isStatus() {
        return isDead;
    }

    public void setStatus(boolean status) {
        this.isDead = status;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public abstract boolean checkCollection(double x, double y);

}
