package uet.oop.bomberman.entities;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Date;

public class Brick extends Entity {
    public boolean Destroyed = false;
    public long startTimeBroken;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }
    public int animate;

    @Override
    public void update() {
        if (Destroyed) {
            animate ++;
            img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animate, 50).getFxImage();
            long countdown = (new Date().getTime()) - startTimeBroken;
            if (countdown >= 130) {
                this.removeAvailbe = true;
            }
        }
    }

    public boolean collide(Entity e) {
        return false;
    }

    public boolean isDestroyed() {
        return Destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        Destroyed = destroyed;
    }

    public void setStartTimeBroken(long startTimeBroken) {
        this.startTimeBroken = startTimeBroken;
    }
}
