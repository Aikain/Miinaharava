package fi.gosu.miinaharava;

import java.awt.Graphics;
import java.awt.Polygon;

public class Hopscotch {

    private boolean mine;
    private final Hopscotch[] neighborhood;
    private boolean close;
    private boolean draw;

    public Hopscotch() {
        this.mine = false;
        this.neighborhood = new Hopscotch[6];
        this.close = true;
        this.draw = false;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public Hopscotch[] getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(Hopscotch prev, Hopscotch parent, Hopscotch next, int i) {
        this.neighborhood[(i + 2) % 6] = prev;
        this.neighborhood[(i + 3) % 6] = parent;
        this.neighborhood[(i + 4) % 6] = next;
    }

    public boolean isClose() {
        return close;
    }

    public void close() {
        this.close = true;
    }

    public boolean isOpen() {
        return !close;
    }

    public void open() {
        this.close = false;
    }

    public boolean isDraw() {
        return draw;
    }

    public void clearDraw() {
        this.draw = false;
        for (Hopscotch hopscotch : neighborhood) {
            if (hopscotch.isDraw()) {
                hopscotch.clearDraw();
            }
        }
    }

    public void createNeightborhood() {
        for (int i = 0; i < neighborhood.length; i++) {
            if (neighborhood[i] == null) {
                neighborhood[i] = new Hopscotch();
            }
        }
        for (int i = 0; i < neighborhood.length; i++) {
            neighborhood[i].setNeighborhood(neighborhood[(i + 1) % 6], this, neighborhood[(i + 5) % 6], i);
        }
    }

    public void draw(Graphics g) {
        Polygon p = new Polygon();
        for (int i = 0; i < 6; i++) {
            p.addPoint((int) (200 + 50 * Math.cos(i * 2 * Math.PI / 6)), (int) (200 + 50 * Math.sin(i * 2 * Math.PI / 6)));
        }
        g.drawPolygon(p);
        this.draw = true;
    }
}
