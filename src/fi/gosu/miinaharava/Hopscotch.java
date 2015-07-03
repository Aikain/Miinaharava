package fi.gosu.miinaharava;

import java.awt.Graphics;
import java.awt.Polygon;

public class Hopscotch {

    private final Hopscotch[] neighborhood;
    private boolean mine, close, draw, created;
    private final int x, y;

    public Hopscotch(int x, int y) {
        this.mine = false;
        this.neighborhood = new Hopscotch[6];
        this.close = true;
        this.draw = false;
        this.created = false;
        this.x = x;
        this.y = y;
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
        if (this.neighborhood[(i + 2) % 6] == null) {
            this.neighborhood[(i + 2) % 6] = prev;
        }
        if (this.neighborhood[(i + 3) % 6] == null) {
            this.neighborhood[(i + 3) % 6] = parent;
        }
        if (this.neighborhood[(i + 4) % 6] == null) {
            this.neighborhood[(i + 4) % 6] = next;
        }
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
            if (hopscotch != null && hopscotch.isDraw()) {
                hopscotch.clearDraw();
            }
        }
    }

    public boolean isCreated() {
        return created;
    }

    public void clearCreated() {
        this.created = false;
        for (Hopscotch hopscotch : neighborhood) {
            if (hopscotch != null && hopscotch.isCreated()) {
                hopscotch.clearDraw();
            }
        }
    }

    public Hopscotch getNext(int i) {
        return neighborhood[(i + 1) % 6];
    }

    public Hopscotch getPrev(int i) {
        return neighborhood[(i - 1) % 6];
    }

    public void createNeightborhood(int deep) {
        this.created = true;
        deep--;
        for (int i = 0; i < neighborhood.length; i++) {
            if (neighborhood[i] == null) {
                Hopscotch next = null;
                if (i - 1 >= 0) {
                    next = neighborhood[(i - 1) % 6].getNext(i);
                    if (next == null && neighborhood[(i + 1) % 6] != null) {
                        next = neighborhood[(i + 1) % 6].getPrev(i);
                    }
                }
                if (next == null) {
                    neighborhood[i] = new Hopscotch((int) (x + 50 * Math.cos((i + 0.5) * Math.PI / 3)), (int) (y + 50 * Math.sin((i + 0.5) * Math.PI / 3)));
                } else {
                    neighborhood[i] = next;
                }
            }
        }
        for (int i = 0; i < neighborhood.length; i++) {
            neighborhood[i].setNeighborhood(neighborhood[(i + 1) % 6], this, neighborhood[(i + 5) % 6], i);
            if (deep > 0) {
                if (!neighborhood[i].isCreated() || !containsNull(neighborhood)) {
                    neighborhood[i].createNeightborhood(deep);
                }
            }
        }
    }

    public boolean containsNull(Object[] list) {
        for (Object obj : list) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g) {
        Polygon p = new Polygon();
        for (int i = 0; i < 6; i++) {
            p.addPoint((int) (this.x + 25 * Math.cos(i * Math.PI / 3)), (int) (this.y + 25 * Math.sin(i * Math.PI / 3)));
        }
        g.drawPolygon(p);
        this.draw = true;
        for (Hopscotch hopscotch : neighborhood) {
            if (hopscotch != null && !hopscotch.isDraw()) {
                hopscotch.draw(g);
            }
        }
    }
}
