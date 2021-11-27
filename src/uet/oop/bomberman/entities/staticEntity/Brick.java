package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.image.Image;

public class Brick extends Entity {

    private boolean isDestroyed;

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }


    @Override
    public void update() {

    }

    @Override
    public boolean checkCollection(double x, double y) {
        return false;
    }
}
