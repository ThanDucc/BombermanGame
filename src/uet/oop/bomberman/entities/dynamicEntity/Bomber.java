package uet.oop.bomberman.entities.dynamicEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.staticEntity.Entity;

public class Bomber extends DynamicEntity {

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public void update() {
        //this.x += 4;
    }

    @Override
    public boolean collisionCheck(Entity entity) {
        return false;
    }

}
