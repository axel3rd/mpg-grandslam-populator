package org.blonding.mpg.model.bean;

public class PlayerDayJson {

    private int pos;
    private int pts;
    private int diff;
    private int player;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return String.format("Pos=%s/player=%s/pts=%s/diff=%s", pos, player, pts, diff);
    }

}
