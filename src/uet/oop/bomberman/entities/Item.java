package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Item extends Entity{
    public boolean used = false;
    public static final int bombItem = 1;
    public static final int flameItem = 2;
    public static final int speedItem = 3;
    public int itemtype;
    public Item(int x, int y, Image img, int itemtype) {
        super(x, y, img);
        this.itemtype = itemtype;
    }

    @Override
    public void update() {

    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getItemtype() {
        return itemtype;
    }
}
