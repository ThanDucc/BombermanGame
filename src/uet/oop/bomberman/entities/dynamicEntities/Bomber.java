package uet.oop.bomberman.entities.dynamicEntities;

import javafx.scene.image.Image;
import uet.oop.bomberman.Control.Controller;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.staticEntities.Brick;
import uet.oop.bomberman.entities.staticEntities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Date;
import java.util.List;

public class Bomber extends Entity {

    private double pad;
    private double prePad = 0;
    private int dir;
    private double speed = 1;
    private int bombSize = 1;
    private int frameSize = 2;
    public Controller controller;
    public List<Entity> stillObjs;
    long startTime = System.currentTimeMillis();
    public boolean bombPlanted = false;
    private int animate = 0;
    public boolean alive = true;
    public long startdead;
    public boolean bombAreaReleased = false;
    private int bombCountdown = 3500;


    public Bomber(int x, int y, Image img, Controller controller, List<Entity> stillObjs) {
        super(x, y, img);
        this.controller = controller;
        this.stillObjs = stillObjs;
    }

    public void move() {
        animate++;
        int res = this.controller.control();

        switch (res) {
            case Controller.MOVE_W -> {
                pad = -2;
                dir = Controller.MOVE_W;
            }
            case Controller.MOVE_A -> {
                pad = -2;
                dir = Controller.MOVE_A;
            }
            case Controller.MOVE_S -> {
                pad = 2;
                dir = Controller.MOVE_S;
            }
            case Controller.MOVE_D -> {
                pad = 2;
                dir = Controller.MOVE_D;
            }
            case 0 -> {
                prePad = pad;
                pad = 0;
            }
        }
        if (controller.isBombPlanted()) {
            if (controller.getBombCountdown() <= 0) {
                startTime = System.currentTimeMillis();
                bombPlanted = true;

                controller.setBombCountdown(this.bombCountdown);
            } else {
                bombPlanted = false;
                long countdown = (new Date().getTime()) - startTime;
                if (countdown >= controller.getBombCountdown()) {
                    controller.setBombCountdown(0);
                }
            }
        }
        for (Entity e : stillObjs) {
            if (e instanceof Wall || e instanceof Brick) {
                if (this.collide(e)) {
                    if (dir == Controller.MOVE_W || dir == Controller.MOVE_S) {
                        if (pad != 0) {
                            this.y -= 5 * pad;
                        } else {
                            this.y -= 5 * prePad;
                        }
                        return;
                    } else if (dir == Controller.MOVE_D || dir == Controller.MOVE_A) {
                        if (pad != 0) {
                            this.x -= 5 * pad;
                        } else {
                            this.x -= 5 * prePad;
                        }
                        return;
                    }
                }
            }
        }
        if (isAlive()) {
            go();
        } else {
            img = Sprite.movingSprite(Sprite.player_dead1,
                    Sprite.player_dead2,
                    Sprite.player_dead3, animate, 40).getFxImage();

            long broken = (new Date().getTime()) - startdead;
            if (broken >= 130) {
                img = Sprite.grass.getFxImage();
                this.removeAvailbe = true;
            }
        }
    }

    public void go() {
        if (dir == Controller.MOVE_W || dir == Controller.MOVE_S) {
            if (dir == Controller.MOVE_W) {
                if (pad != 0) {
                    img = Sprite.movingSprite(Sprite.player_up,
                            Sprite.player_up_1, Sprite.player_up_2, animate, 40).getFxImage();
                } else {
                    img = Sprite.player_up.getFxImage();
                }
            } else {
                if (pad != 0) {
                    img = Sprite.movingSprite(Sprite.player_down,
                            Sprite.player_down_1, Sprite.player_down_2, animate, 40).getFxImage();
                } else {
                    img = Sprite.player_down.getFxImage();
                }
            }
            y += pad * speed;
        } else if (dir == Controller.MOVE_A || dir == Controller.MOVE_D) {
            if (dir == Controller.MOVE_A) {
                if (pad != 0) {
                    img = Sprite.movingSprite(Sprite.player_left,
                            Sprite.player_left_1, Sprite.player_left_2, animate, 40).getFxImage();
                } else {
                    img = Sprite.player_left.getFxImage();
                }
            } else {
                if (pad != 0) {
                    img = Sprite.movingSprite(Sprite.player_right,
                            Sprite.player_right_1, Sprite.player_right_2, animate, 40).getFxImage();
                } else {
                    img = Sprite.player_right.getFxImage();
                }

            }
            x += pad * speed;
        }
    }


    @Override
    public void update() {
        move();
    }

    public boolean isBombPlanted() {
        return bombPlanted;
    }

    public void setBombPlanted(boolean bombPlanted) {
        this.bombPlanted = bombPlanted;
    }

    public boolean isBombAreaReleased() {
        return bombAreaReleased;
    }

    public boolean isAlive() {
        return alive;
    }

    public void dead() {
        startdead = System.currentTimeMillis();
        alive = false;

    }

    public void bombCollide(List<Entity> bombs) {
        for (Entity e : bombs) {
            if (e instanceof Bomb) {
                if (bombAreaReleased) {
                    if (this.collide(e)) {
                        if (dir == Controller.MOVE_W || dir == Controller.MOVE_S) {
                            if (pad != 0) {
                                this.y -= 5 * pad;
                            } else {
                                this.y -= 5 * prePad;
                            }
                            return;
                        } else if (dir == Controller.MOVE_D || dir == Controller.MOVE_A) {
                            if (pad != 0) {
                                this.x -= 5 * pad;
                            } else {
                                this.x -= 5 * prePad;
                            }
                            return;
                        }
                    }
                } else if (!((Bomb) e).isActived()) {
                    if (!this.collide(e)) {
                        bombAreaReleased = true;
                        ((Bomb) e).setActived(true);
                    }
                }
            }
        }
    }


    public void setBombAreaReleased(boolean bombAreaReleased) {
        this.bombAreaReleased = bombAreaReleased;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setBombSize(int bombSize) {
        this.bombSize = bombSize;
        this.bombCountdown = 5000 - (bombSize - 1) * 1500;
    }

    public void setFrameSize(int frameSize) {
        this.frameSize = frameSize;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public int getBombSize() {
        return bombSize;
    }

    public double getSpeed() {
        return speed;
    }


}
