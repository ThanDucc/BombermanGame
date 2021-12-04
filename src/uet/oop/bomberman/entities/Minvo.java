package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Date;
import java.util.List;

public class Minvo extends DynamicEntity {

    private double dir;
    private int animate;
    private List<Entity> bombs;
    private boolean multiplyAvailble;

    public Minvo(int xUnit, int yUnit, Image img, List<Entity> stillObjs, List<Entity> bombs, boolean multiplyAvailble) {
        super(xUnit, yUnit, img, stillObjs);
        this.bombs = bombs;
        this.multiplyAvailble = multiplyAvailble;
    }

    public int getSpeed() {
        return 1;
    }

    @Override
    public void update() {
        if (isAlive) {
            if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
                dir = Math.random() * 10;
                animate = 0;
            }
            animate++;
            if (dir <= 2.5) {
                if (checkCollection(this.x, this.y + Sprite.SCALED_SIZE)) {
                    y += getSpeed();
                    img = Sprite.movingSprite(Sprite.minvo_left1,
                            Sprite.minvo_left2, Sprite.minvo_left3, animate, 50).getFxImage();
                }
            } else if (dir > 2.5 && dir <= 5) {
                if (checkCollection(this.x, this.y - Sprite.SCALED_SIZE)) {
                    y -= getSpeed();
                    img = Sprite.movingSprite(Sprite.minvo_right1,
                            Sprite.minvo_right2, Sprite.minvo_right3, animate, 50).getFxImage();
                }
            } else if (dir > 5 && dir <= 7.5) {
                if (checkCollection(this.x + Sprite.SCALED_SIZE, this.y)) {
                    x += getSpeed();
                    img = Sprite.movingSprite(Sprite.minvo_right1,
                            Sprite.minvo_right2, Sprite.minvo_right3, animate, 50).getFxImage();
                }
            } else {
                if (checkCollection(this.x - Sprite.SCALED_SIZE, this.y)) {
                    x -= getSpeed();
                    img = Sprite.movingSprite(Sprite.minvo_left1,
                            Sprite.minvo_left2, Sprite.minvo_left3, animate, 50).getFxImage();
                }
            }
        } else {
            img = Sprite.minvo_dead.getFxImage();
            long broken = (new Date().getTime()) - startdead;
            if (multiplyAvailble) {
                if (broken >= 240) {
                    img = Sprite.grass.getFxImage();
                    removeAvailbe = true;
                }
            }
            else {
                animate++;
                if (broken >= 500 && broken <= 520) {
                    img = Sprite.mob_dead1.getFxImage();
                    soundKillEnemy.play();
                }
                if (broken > 520) {
                    img = Sprite.mob_dead1.getFxImage();
                }
                if (broken >= 850) {
                    img = Sprite.mob_dead2.getFxImage();
                }
                if (broken >= 1200) {
                    img = Sprite.mob_dead3.getFxImage();
                }
                if (broken >= 1550) {
                    removeAvailbe = true;
                }
            }
        }
    }


    @Override
    public boolean checkCollection(double x, double y) {
        for (Entity entity : stillObjects) {
            if (entity instanceof Wall) {
                if (entity.getX() == x && entity.getY() == y) {
                    return false;
                }
            }
            else if (entity instanceof Brick) {
                if (entity.getX() == x && entity.getY() == y) {
                    ((Brick) entity).setDestroyed(true);
                    return true;
                }
            }
        }
        for (Entity entity : bombs) {
            if (entity instanceof Bomb) {
                if (entity.getX() == x && entity.getY() == y) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void dead() {
        startdead = System.currentTimeMillis();
        isAlive = false;
    }

    public boolean isMultiplyAvailble() {
        return multiplyAvailble;
    }

}
