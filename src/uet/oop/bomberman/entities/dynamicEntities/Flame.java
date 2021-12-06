package uet.oop.bomberman.entities.dynamicEntities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Date;

public class Flame extends Entity {
    public static final int VER = 0;
    public static final int HOR = 1;

    public static final int LTOP = 4;
    public static final int LDOWN = 5;
    public static final int LLEFT = 6;
    public static final int LRIGHT = 7;
    public static final int CENTRE = 10;

    public int pos;
    private int animate = 0;
    public long flamingTime = 500 - 2;
    public long startTime = System.currentTimeMillis();
    public boolean disapper = false;
    public Flame(int x, int y, Image img, int pos) {
        super(x, y, img);
        this.pos = pos;
    }

    @Override
    public void update() {
        animate++;
        switch (pos) {
            case VER -> img = Sprite.movingSprite(Sprite.explosion_vertical,
                    Sprite.explosion_vertical1,
                    Sprite.explosion_vertical2, animate, 20).getFxImage();
            case HOR -> img = Sprite.movingSprite(Sprite.explosion_horizontal,
                    Sprite.explosion_horizontal1,
                    Sprite.explosion_horizontal2, animate, 20).getFxImage();
            case LTOP -> img = Sprite.movingSprite(Sprite.explosion_vertical_top_last,
                    Sprite.explosion_vertical_top_last1,
                    Sprite.explosion_vertical_top_last2, animate, 20).getFxImage();
            case LDOWN -> img = Sprite.movingSprite(Sprite.explosion_vertical_down_last,
                    Sprite.explosion_vertical_down_last1,
                    Sprite.explosion_vertical_down_last2, animate, 20).getFxImage();
            case LLEFT -> img = Sprite.movingSprite(Sprite.explosion_horizontal_left_last,
                    Sprite.explosion_horizontal_left_last1,
                    Sprite.explosion_horizontal_left_last2, animate, 20).getFxImage();
            case LRIGHT -> img = Sprite.movingSprite(Sprite.explosion_horizontal_right_last,
                    Sprite.explosion_horizontal_right_last1,
                    Sprite.explosion_horizontal_right_last2, animate, 20).getFxImage();
            case CENTRE -> img = Sprite.movingSprite(Sprite.bomb_exploded,
                    Sprite.bomb_exploded1,
                    Sprite.bomb_exploded2, animate, 20).getFxImage();
        }

        long flaming = (new Date().getTime()) - startTime;
        if (flaming >= flamingTime) {
            img = Sprite.grass.getFxImage();
            disapper = true;
        }
    }

    public boolean isDisapper() {
        return disapper;
    }

    public int getPos() {
        return pos;
    }
}
