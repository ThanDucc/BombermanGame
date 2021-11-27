package uet.oop.bomberman.entities.staticEntity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

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

    public void setImg(Image img) {
        this.img = img;
    }

    public int getW() {
        return (int) Math.round(this.img.getWidth());
    }

    public int getH() {
        return (int) Math.round(this.img.getHeight());
    }

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();


    /**
     * kiem tra va cham
     * @param x toa do x
     * @param y toa do y
     * @return boolean
     */
    public abstract boolean checkCollection(double x, double y);

    public boolean collide(Entity e) {
        int sumX = this.getX()+this.getW();
        int sumY = this.getY()+this.getH();
        return sumX >= e.getX() + 10 && sumY >= e.getY() && this.getX() <= e.getX() + e.getW() && this.getY() <= e.getY() + e.getH();
    }

}
