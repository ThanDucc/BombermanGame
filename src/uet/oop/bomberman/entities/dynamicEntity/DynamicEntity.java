package uet.oop.bomberman.entities.dynamicEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.staticEntity.Entity;

public abstract class DynamicEntity extends Entity {

    private boolean status;
    private int speed;

    public DynamicEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public abstract boolean canMove();

}
