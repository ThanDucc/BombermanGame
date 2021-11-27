package uet.oop.bomberman.entities.dynamicEntity;

import java.util.Random;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.staticEntity.Brick;
import uet.oop.bomberman.entities.staticEntity.Entity;
import uet.oop.bomberman.entities.staticEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends DynamicEntity {

    private double dir;
    private int animate;

    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
            dir = Math.random() * 10;
            animate = 0;
            Random rand = new Random();
            setSpeed(rand.nextInt(2) + 1);
        }
        animate++;
        if (dir <= 2.5) {
            if (checkCollection(this.x, (this.y + getSpeed() * Sprite.SCALED_SIZE))) {
                y += getSpeed();
                img = Sprite.movingSprite(Sprite.oneal_left1,
                        Sprite.oneal_left2, Sprite.oneal_left3, animate, 40).getFxImage();
            }
        } else if (dir > 2.5 && dir <= 5) {
            if (checkCollection(this.x, (this.y - getSpeed() * Sprite.SCALED_SIZE))) {
                y -= getSpeed();
                img = Sprite.movingSprite(Sprite.oneal_right1,
                        Sprite.oneal_right2, Sprite.oneal_right3, animate, 40).getFxImage();
            }
        } else if (dir > 5 && dir <= 7.5) {
            if (checkCollection(this.x + getSpeed() * Sprite.SCALED_SIZE, this.y)) {
                x += getSpeed();
                img = Sprite.movingSprite(Sprite.oneal_right1,
                        Sprite.oneal_right2, Sprite.oneal_right3, animate, 40).getFxImage();
            }
        } else {
            if (checkCollection(this.x - getSpeed() * Sprite.SCALED_SIZE, this.y)) {
                x -= getSpeed();
                img = Sprite.movingSprite(Sprite.oneal_left1,
                        Sprite.oneal_left2, Sprite.oneal_left3, animate, 40).getFxImage();
            }
        }
    }

    @Override
    public boolean checkCollection(double x, double y) {

        if (this.x < x) {
            for (int i = this.x + 1; i <= x; i += 1) {
                for (Entity entity : BombermanGame.stillObjects) {
                    if (entity instanceof Wall || entity instanceof Brick) {
                        if (entity.getX() == i && entity.getY() == y) {
                            return false;
                        }
                    }
                }
            }
        }
        if (this.x > x) {
            for (int i = this.x - 1; i >= x; i -= 1) {
                for (Entity entity : BombermanGame.stillObjects) {
                    if (entity instanceof Wall || entity instanceof Brick) {
                        if (entity.getX() == i && entity.getY() == y) {
                            return false;
                        }
                    }
                }
            }
        }
        if (this.y < y) {
            for (int i = this.y + 1; i <= y; i += 1) {
                for (Entity entity : BombermanGame.stillObjects) {
                    if (entity instanceof Wall || entity instanceof Brick) {
                        if (entity.getX() == x && entity.getY() == i) {
                            return false;
                        }
                    }
                }
            }
        }
        if (this.y > y) {
            for (int i = this.y - 1; i >= y; i -= 1) {
                for (Entity entity : BombermanGame.stillObjects) {
                    if (entity instanceof Wall || entity instanceof Brick) {
                        if (entity.getX() == x && entity.getY() == i) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

}
