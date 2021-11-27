package uet.oop.bomberman.entities.dynamicEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.staticEntity.Entity;

public class Bomber extends DynamicEntity {

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean checkCollection(double x, double y) {
        return false;
    }

}
