package uet.oop.bomberman.entities.monsters;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.dynamicEntities.Bomb;
import uet.oop.bomberman.entities.dynamicEntities.Bomber;
import uet.oop.bomberman.entities.dynamicEntities.DynamicEntity;
import uet.oop.bomberman.entities.staticEntities.Brick;
import uet.oop.bomberman.entities.staticEntities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Kondoria extends DynamicEntity {
    public List<Entity> bombs;

    public static final int upPriority = 1;
    public static final int downPriority = 2;
    public static final int leftPriority = 3;
    public static final int rightPriority = 4;
    private boolean moved = false;
    private int predX;
    private int predY;
    private int goTo;
    private boolean firstMove = true;
    private boolean multiplyAvailble;
    private int animate = 0;


    private List<KondoriaRadar> priorityDirect = new ArrayList<>();
    private Bomber target;

    public Kondoria(int x, int y, Image img, List<Entity> stillObjs, List<Entity> bombs, Bomber bomber, boolean multiplyAvailble) {
        super(x, y, img, stillObjs);
        this.bombs = bombs;
        this.target = bomber;
        predX = x / Sprite.SCALED_SIZE;
        predY = y / Sprite.SCALED_SIZE;
        this.multiplyAvailble = multiplyAvailble;
    }

    @Override
    public void update() {
        animate++;
        if (isAlive) {
            /*
             * Priority levels:
             * 0. presentpriority   40%
             * 1. nextpriority      40%
             * 2. !nextpriority     10%
             * 3. turnback.         10%
             */
            setPriorityDirect();
            movementCheck();
            canMove();
            if (firstMove) {
                goTo = priorityDirect.get(0).getpD();
                firstMove = false;
            }
            if (moved) {
                int kondoDirect = (int) (Math.random() * 10);

                if (kondoDirect >= 0 && kondoDirect <= 3 && priorityDirect.get(0).isCanMove()) {
                    goTo = priorityDirect.get(0).getpD();
                } else if (kondoDirect > 3 && kondoDirect <= 7 && priorityDirect.get(1).isCanMove()) {
                    goTo = priorityDirect.get(1).getpD();
                } else if (kondoDirect == 8 && priorityDirect.get(2).isCanMove()) {
                    goTo = priorityDirect.get(2).getpD();
                } else if (priorityDirect.get(3).isCanMove()) {
                    goTo = priorityDirect.get(3).getpD();
                }
                moved = false;
            }
            move(goTo);
            for (Entity e : stillObjects) {
                if (e instanceof Wall || e instanceof Brick) {
                    if (this.collide(e,5,5)) {
                        switch (goTo) {
                            case upPriority -> this.y += 2;
                            case downPriority -> this.y -= 2;
                            case leftPriority -> this.x += 2;
                            case rightPriority -> this.x -= 2;
                        }
                        moved = true;
                    }
                }
            }
            for (Entity e: bombs) {
                if (e instanceof Bomb) {
                    if (this.collide(e,5,5)) {
                        switch (goTo) {
                            case upPriority -> this.y += 2;
                            case downPriority -> this.y -= 2;
                            case leftPriority -> this.x += 2;
                            case rightPriority -> this.x -= 2;
                        }
                        moved = true;
                    }
                }
            }

        } else {
            img = Sprite.kondoria_dead.getFxImage();
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
        return false;
    }

    public void setPriorityDirect() {
        priorityDirect.clear();
        KondoriaRadar pX;
        KondoriaRadar pY;
        if (this.getX() - target.getX() >= 0) {
            pX = new KondoriaRadar(leftPriority, true);
        } else {
            pX = new KondoriaRadar(rightPriority, true);
        }
        if (this.getY() - target.getY() >= 0) {
            pY = new KondoriaRadar(upPriority, true);
        } else {
            pY = new KondoriaRadar(downPriority, true);
        }
        if ((int) (Math.random() * 2) == 0) {
            priorityDirect.add(pX);
            priorityDirect.add(pY);
            priorityDirect.add((pY.getpD() == upPriority) ? new KondoriaRadar(downPriority, true) : new KondoriaRadar(upPriority, true));
            priorityDirect.add((pX.getpD() == leftPriority)? new KondoriaRadar(rightPriority, true) : new KondoriaRadar(leftPriority, true));
        } else {
            priorityDirect.add(pY);
            priorityDirect.add(pX);
            priorityDirect.add((pX.getpD() == leftPriority)? new KondoriaRadar(rightPriority, true) : new KondoriaRadar(leftPriority, true));
            priorityDirect.add((pY.getpD() == upPriority) ? new KondoriaRadar(downPriority, true) : new KondoriaRadar(upPriority, true));
        }
    }

    public void move(int direct) {
        switch (direct) {
            case upPriority -> {
                img = Sprite.movingSprite(Sprite.kondoria_left1,
                        Sprite.kondoria_right2, animate, 20).getFxImage();
                this.y -= 2;
            }
            case downPriority -> {
                img = Sprite.movingSprite(Sprite.kondoria_left2,
                        Sprite.kondoria_right1, animate, 20).getFxImage();
                this.y += 2;
            }
            case leftPriority -> {
                img = Sprite.movingSprite(Sprite.kondoria_left1,
                        Sprite.kondoria_left2,
                        Sprite.kondoria_left3, animate, 20).getFxImage();
                this.x -= 2;
            }
            case rightPriority -> {
                img = Sprite.movingSprite(Sprite.kondoria_right1,
                        Sprite.kondoria_right2,
                        Sprite.kondoria_right3, animate, 20).getFxImage();
                this.x += 2;
            }
        }
    }

    public void canMove() {
        int checkUp = (this.getY() - 32 + 7) / Sprite.SCALED_SIZE;
        int checkDown = (this.getY() + 2 * 32 - 7) / Sprite.SCALED_SIZE;
        int checkLeft = (this.getX() - 32 + 7) / Sprite.SCALED_SIZE;
        int checkRight = (this.getX() + 2 * 32 - 7) / Sprite.SCALED_SIZE;

        int scaleX = (this.getX() +16) / Sprite.SCALED_SIZE;
        int scaleY = (this.getY() +16) / Sprite.SCALED_SIZE;
        int entityX;
        int entityY;

        boolean canMoveUp = true;
        boolean canMoveDown = true;
        boolean canMoveLeft = true;
        boolean canMoveRight = true;

        for(Entity e : stillObjects) {
            if (e instanceof Wall || e instanceof Brick) {
                entityX = e.getX()  / Sprite.SCALED_SIZE;
                entityY = e.getY() / Sprite.SCALED_SIZE;
                if (entityY == checkDown && entityX == scaleX) {
                    //Cant move down;
                    canMoveDown = false;
                } else if (entityY == checkUp && entityX == scaleX) {
                    //Cant move up;
                    canMoveUp = false;
                }
                if (entityX == checkLeft && entityY == scaleY) {
                    //Cant move left;
                    canMoveLeft = false;
                } else if (entityX == checkRight && entityY == scaleY) {
                    //Cant move right;
                    canMoveRight = false;
                }
            }
        }
        for (Entity e : bombs) {
            if (e instanceof Bomb) {
                entityX = e.getX()  / Sprite.SCALED_SIZE;
                entityY = e.getY() / Sprite.SCALED_SIZE;
                if (entityY == checkDown && entityX == scaleX) {
                    //Cant move down;
                    canMoveDown = false;
                } else if (entityY == checkUp && entityX == scaleX) {
                    //Cant move up;
                    canMoveUp = false;
                }
                if (entityX == checkLeft && entityY == scaleY) {
                    //Cant move left;
                    canMoveLeft = false;
                } else if (entityX == checkRight && entityY == scaleY) {
                    //Cant move right;
                    canMoveRight = false;
                }
            }
        }
        for (KondoriaRadar kR : priorityDirect) {
            switch (kR.getpD()) {
                case upPriority -> kR.setCanMove(canMoveUp);
                case downPriority -> kR.setCanMove(canMoveDown);
                case leftPriority -> kR.setCanMove(canMoveLeft);
                case rightPriority -> kR.setCanMove(canMoveRight);
            }
        }
    }

    public void movementCheck() {
        int pX = this.getX() / Sprite.SCALED_SIZE;
        int pY = this.getY() / Sprite.SCALED_SIZE;
        if (pX != predX || pY != predY) {
            predX = pX;
            predY = pY;
            moved = true;
        }
    }

    public boolean isMultiplyAvailble() {
        return multiplyAvailble;
    }

}
