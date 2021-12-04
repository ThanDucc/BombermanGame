package uet.oop.bomberman.entities;

public class KondoriaRadar {
    private int pD;
    private boolean canMove;
    public KondoriaRadar(int pD, boolean canMove) {
        this.pD = pD;
        this.canMove = canMove;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public int getpD() {
        return pD;
    }
}
