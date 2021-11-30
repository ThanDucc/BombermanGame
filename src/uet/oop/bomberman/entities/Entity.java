package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    protected boolean removeAvailbe = false;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public int getW() {
        return (int) Math.round(this.img.getWidth());
    }

    public int getH() {
        return (int) Math.round(this.img.getHeight());
    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

    public boolean collide(Entity e) {
        int sumX = this.getX()+this.getW();
        int sumY = this.getY()+this.getH();
        return sumX >= e.getX() + 10 && sumY >= e.getY() + 5 && this.getX() <= e.getX() + e.getW() - 5 && this.getY() <= e.getY() + e.getH() - 10;
    }

    public boolean collide(Entity e, int sX, int sY) {
        int sumX = this.getX()+this.getW();
        int sumY = this.getY()+this.getH();
        return sumX >= e.getX() + (10 + sX)
                && sumY >= e.getY() + 5 + (sY)
                && this.getX() <= e.getX() + e.getW() - (5 + sY)
                && this.getY() <= e.getY() + e.getH() - (10 + sX);
    }

    public boolean isRemoveAvailbe() {
        return this.removeAvailbe;
    }

}
