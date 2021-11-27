package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.image.Image;

public class FlameItem extends Entity {

    public FlameItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean checkCollection(double x, double y) {
        return false;
    }
}
