package uet.oop.bomberman.entities.dynamicEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.staticEntity.Entity;

public class Balloom extends DynamicEntity {

    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean collisionCheck(Entity entity) {
        return false;
    }
}
