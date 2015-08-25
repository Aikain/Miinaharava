package fi.gosu.miinaharava;

import fi.gosu.miinaharava.tool.Resources;
import fi.gosu.miinaharava.ui.Kayttoliittyma;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class Hopscotch extends JComponent {

    public static BufferedImage CombNormal;

    private final Hopscotch[] neighborhood;
    private boolean mine, close, draw, created, checked, mark;
    private final int x, y, radius;
    private final Polygon p;
    private final Resources r;
    private final Kayttoliittyma kl;

    public Hopscotch(int x, int y, Resources r, Kayttoliittyma kl) {
        this.neighborhood = new Hopscotch[6];
        this.close = true;
        this.draw = false;
        this.created = false;
        this.mark = false;
        this.x = x;
        this.y = y;
        this.r = r;
        this.kl = kl;
        setBounds(0, 0, kl.getWidth(), kl.getHeight());
        this.p = new Polygon();
        this.radius = (kl.getWidth() < kl.getHeight() ? kl.getWidth() : kl.getHeight()) / ( 4 * this.kl.getDeep() + 6);        
        
        for (int i = 0; i < 6; i++) {
            p.addPoint((int) (x + radius * Math.cos(i * Math.PI / 3)), (int) (y + radius * Math.sin(i * Math.PI / 3)));
        }
    }

    public void generateMinePositions(double probability) {
        this.mine = Math.random() < probability;
        probability = 0.2;
        this.created = true;
        for (Hopscotch hopscotch : neighborhood) {
            if (hopscotch != null && !hopscotch.isCreated()) {
                hopscotch.generateMinePositions(probability);
            }
        }
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isMark() {
        return mark;
    }

    public void toggleMark() {
        this.mark = this.mark ? false : this.close;
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

    public boolean open() {
        this.mark = false;
        if (this.mine) {
            return false;
        } else {
            this.close = false;
            if (neightborhoodBOOMCount() == 0) {
                for (Hopscotch hopscotch : neighborhood) {
                    if (hopscotch != null && hopscotch.isClose()) {
                        hopscotch.open();
                    }
                }
            }
            return true;
        }
    }

    public void openAll() {
        this.close = false;
        this.created = true;
        for (Hopscotch hopscotch : neighborhood) {
            if (hopscotch != null && !hopscotch.isCreated()) {
                hopscotch.openAll();
            }
        }
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
                hopscotch.clearCreated();
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
                    neighborhood[i] = new Hopscotch((int) (x + 1.7*radius * Math.cos((i + 0.5) * Math.PI / 3)), (int) (y + 1.7*radius * Math.sin((i + 0.5) * Math.PI / 3)), this.r, kl);
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

    public int neightborhoodBOOMCount() {
        int n = 0;
        for (Hopscotch hopscotch : neighborhood) {
            if (hopscotch != null && hopscotch.isMine()) {
                n++;
            }
        }
        return n;
    }

    @Override
    public void paint(Graphics g) {
        if (close) {
            g.drawImage(this.r.getNORMAL(), x - radius, y - (int) (2*radius * Math.sqrt(0.75) / 2), 2*radius, 2*radius, null);
            if (mark) {
                g.drawImage(this.r.getFLAG(), x - radius, y - (int) (2*radius * Math.sqrt(0.75) / 2), 2*radius, 2*radius, null);
            }
        } else {
            g.drawImage(this.r.getOpen(0), x - radius, y - (int) (2*radius * Math.sqrt(0.75) / 2), 2*radius, 2*radius, null);
            if (mine) {
                g.drawImage(this.r.getBOMB(), x - radius, y - (int) (2*radius * Math.sqrt(0.75) / 2), 2*radius, 2*radius, null);
            } else {
                int nbc = this.neightborhoodBOOMCount();
                if (nbc > 0) {
                    g.drawImage(this.r.getOpen(nbc), x - radius, y - (int) (2*radius * Math.sqrt(0.75) / 2), 2*radius, 2*radius, null);
                }
            }
        }
    }

    public void addToContainer(Container container) {
        container.add(this);
        this.draw = true;
        for (Hopscotch hopscotch : neighborhood) {
            if (hopscotch != null && !hopscotch.isDraw()) {
                hopscotch.addToContainer(container);
            }
        }
    }

    public Hopscotch getHopscotch(int x, int y) {
        this.checked = true;
        if (this.p.contains(x, y)) {
            return this;
        } else {
            for (Hopscotch hopscotch : neighborhood) {
                if (hopscotch != null && !hopscotch.isChecked()) {
                    Hopscotch h = hopscotch.getHopscotch(x, y);
                    if (h != null){
                        return h;
                    }
                }
            }
            return null;
        }
    }

    public void markHopscotch(int x, int y) {
        this.checked = true;
        if (this.p.contains(x, y)) {
            this.toggleMark();
        } else {
            for (Hopscotch hopscotch : neighborhood) {
                if (hopscotch != null && !hopscotch.isChecked()) {
                    hopscotch.markHopscotch(x, y);
                }
            }
        }
    }

    private boolean isChecked() {
        return checked;
    }

    public void clearChecked() {
        this.checked = false;
        for (Hopscotch hopscotch : neighborhood) {
            if (hopscotch != null && hopscotch.isChecked()) {
                hopscotch.clearChecked();
            }
        }
    }

    public boolean checkWin() {
        this.checked = true;
        if (!this.close || this.mine) {
            for (Hopscotch hopscotch : neighborhood) {
                if (hopscotch != null && !hopscotch.isChecked() && !hopscotch.checkWin()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
