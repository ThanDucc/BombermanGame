package uet.oop.bomberman.entities.monsters;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.dynamicEntities.Bomb;
import uet.oop.bomberman.entities.dynamicEntities.DynamicEntity;
import uet.oop.bomberman.entities.staticEntities.Brick;
import uet.oop.bomberman.entities.staticEntities.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends DynamicEntity {

    private double dir;
    private int animate;
    private List<Entity> bombs;

    public Oneal(int xUnit, int yUnit, Image img, List<Entity> stillObjects, List<Entity> bombs ) {
        super(xUnit, yUnit, img, stillObjects);
        this.bombs = bombs;
    }


    @Override
    public void update() {
        if (isAlive) {
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
                            Sprite.oneal_left2, Sprite.oneal_left3, animate, 50).getFxImage();
                }
            } else if (dir > 2.5 && dir <= 5) {
                if (checkCollection(this.x, (this.y - getSpeed() * Sprite.SCALED_SIZE))) {
                    y -= getSpeed();
                    img = Sprite.movingSprite(Sprite.oneal_right1,
                            Sprite.oneal_right2, Sprite.oneal_right3, animate, 50).getFxImage();
                }
            } else if (dir > 5 && dir <= 7.5) {
                if (checkCollection(this.x + getSpeed() * Sprite.SCALED_SIZE, this.y)) {
                    x += getSpeed();
                    img = Sprite.movingSprite(Sprite.oneal_right1,
                            Sprite.oneal_right2, Sprite.oneal_right3, animate, 50).getFxImage();
                }
            } else {
                if (checkCollection(this.x - getSpeed() * Sprite.SCALED_SIZE, this.y)) {
                    x -= getSpeed();
                    img = Sprite.movingSprite(Sprite.oneal_left1,
                            Sprite.oneal_left2, Sprite.oneal_left3, animate, 50).getFxImage();
                }
            }
        } else {
            animate++;
            long broken = (new Date().getTime()) - startdead;
            img = Sprite.oneal_dead.getFxImage();

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

    @Override
    public boolean checkCollection(double x, double y) {
        if (this.x < x) {
            for (int i = this.x + 1; i <= x; i += 1) {
                for (Entity entity : stillObjects) {
                    if (entity instanceof Wall || entity instanceof Brick) {
                        if (entity.getX() == i && entity.getY() == y) {
                            return false;
                        }
                    }
                }
                for (Entity entity : bombs) {
                    if (entity instanceof Bomb) {
                        if (entity.getX() == i && entity.getY() == y) {
                            return false;
                        }
                    }
                }
            }
        }
        if (this.x > x) {
            for (int i = this.x - 1; i >= x; i -= 1) {
                for (Entity entity : stillObjects) {
                    if (entity instanceof Wall || entity instanceof Brick) {
                        if (entity.getX() == i && entity.getY() == y) {
                            return false;
                        }
                    }
                }
                for (Entity entity : bombs) {
                    if (entity instanceof Bomb) {
                        if (entity.getX() == i && entity.getY() == y) {
                            return false;
                        }
                    }
                }
            }
        }
        if (this.y < y) {
            for (int i = this.y + 1; i <= y; i += 1) {
                for (Entity entity : stillObjects) {
                    if (entity instanceof Wall || entity instanceof Brick) {
                        if (entity.getX() == x && entity.getY() == i) {
                            return false;
                        }
                    }
                }
                for (Entity entity : bombs) {
                    if (entity instanceof Bomb) {
                        if (entity.getX() == x && entity.getY() == i) {
                            return false;
                        }
                    }
                }
            }
        }
        if (this.y > y) {
            for (int i = this.y - 1; i >= y; i -= 1) {
                for (Entity entity : stillObjects) {
                    if (entity instanceof Wall || entity instanceof Brick) {
                        if (entity.getX() == x && entity.getY() == i) {
                            return false;
                        }
                    }
                }
                for (Entity entity : bombs) {
                    if (entity instanceof Bomb) {
                        if (entity.getX() == x && entity.getY() == i) {
                            return false;
                        }
                    }
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

}