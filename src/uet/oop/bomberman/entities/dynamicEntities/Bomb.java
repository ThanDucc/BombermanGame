package uet.oop.bomberman.entities.dynamicEntities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Date;

public class  Bomb extends Entity {

    private Bomber owner;
    private int animate = 0;
    public long timeToExplore = 3000 - 2;
    public long startTime = System.currentTimeMillis();
    private boolean explored = false;
    private boolean actived = false;

    public Bomb(int xUnit, int yUnit, Image img, Bomber owner) {
        super(xUnit, yUnit, img);
        this.owner = owner;
    }


    @Override
    public void update() {
        animate ++;
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 50).getFxImage();
        long countdown = (new Date().getTime()) - startTime;
        if (countdown >= timeToExplore) {
            explore();
        }
    }

    public void explore() {

        img = Sprite.grass.getFxImage();
        explored = true;
    }

    public boolean isExplored() {
        return explored;
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }
}
