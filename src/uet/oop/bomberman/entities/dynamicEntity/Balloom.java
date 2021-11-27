package uet.oop.bomberman.entities.dynamicEntity;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.staticEntity.Brick;
import uet.oop.bomberman.entities.staticEntity.Entity;
import uet.oop.bomberman.entities.staticEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class Balloom extends DynamicEntity {

    private double dir;
    private int animate;

    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public int getSpeed() {
        return 1;
    }

    @Override
    public void update() {
        if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
            dir = Math.random() * 10;
            animate = 0;
        }
        animate++;
        if (dir <= 2.5) {
            if (checkCollection(this.x, this.y + Sprite.SCALED_SIZE)) {
                y += getSpeed();
                img = Sprite.movingSprite(Sprite.balloom_left1,
                        Sprite.balloom_left2, Sprite.balloom_left3, animate, 40).getFxImage();
            }
        } else if (dir > 2.5 && dir <= 5) {
            if (checkCollection(this.x, this.y - Sprite.SCALED_SIZE)) {
                y -= getSpeed();
                img = Sprite.movingSprite(Sprite.balloom_right1,
                        Sprite.balloom_right2, Sprite.balloom_right3, animate, 40).getFxImage();
            }
        } else if (dir > 5 && dir <= 7.5) {
            if (checkCollection(this.x + Sprite.SCALED_SIZE, this.y)) {
                x += getSpeed();
                img = Sprite.movingSprite(Sprite.balloom_right1,
                        Sprite.balloom_right2, Sprite.balloom_right3, animate, 40).getFxImage();
            }
        } else {
            if (checkCollection(this.x - Sprite.SCALED_SIZE, this.y)) {
                x -= getSpeed();
                img = Sprite.movingSprite(Sprite.balloom_left1,
                        Sprite.balloom_left2, Sprite.balloom_left3, animate, 40).getFxImage();
            }
        }
    }

    @Override
    public boolean checkCollection(double x, double y) {
        for (Entity entity : BombermanGame.stillObjects) {
            if (entity instanceof Wall || entity instanceof Brick) {
                if (entity.getX() == x && entity.getY() == y) {
                    return false;
                }
            }
        }
        return true;
    }

}