package fi.gosu.miinaharava;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class Hopscotch extends JComponent {

    private final Hopscotch[] neighborhood;
    private boolean mine, close, draw, created, checked;
    private final int x, y;
    private final Polygon p;

    public Hopscotch(int x, int y) {
        if (Math.random() > 0.8) {
            this.mine = true;
        } else {
            this.mine = false;
        }
        this.neighborhood = new Hopscotch[6];
        this.close = true;
        this.draw = false;
        this.created = false;
        this.x = x;
        this.y = y;
        setBounds(0, 0, 1000, 1000);
        this.p = new Polygon();
        for (int i = 0; i < 6; i++) {
            p.addPoint((int) (x + 25 * Math.cos(i * Math.PI / 3)), (int) (y + 25 * Math.sin(i * Math.PI / 3)));
        }
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
        if (this.mine) {
            JOptionPane.showMessageDialog(this, "Hävisit");
            this.openAll();
        } else {
            this.close = false;
            if (neightborhoodBOOMCount() == 0) {
                for (Hopscotch hopscotch : neighborhood) {
                    if (hopscotch != null && hopscotch.isClose()) {
                        hopscotch.open();
                    }
                }
            }
        }
    }

    public void openAll() {
        this.close = false;
        for (Hopscotch hopscotch : neighborhood) {
            if (hopscotch != null && hopscotch.isClose()) {
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
        try {
            if (close) {
                g.drawImage(ImageIO.read(new File("hopscotch-close.png")), x - 25, y - (int) (50 * Math.sqrt(0.75) / 2), 50, (int) (50 * Math.sqrt(0.75)), null);
            } else {
                if (mine) {
                    g.drawImage(ImageIO.read(new File("hopscotch-BOOM.png")), x - 25, y - (int) (50 * Math.sqrt(0.75) / 2), 50, (int) (50 * Math.sqrt(0.75)), null);
                } else {
                    int nbc = this.neightborhoodBOOMCount();
                    if (nbc > 0) {
                        g.drawImage(ImageIO.read(new File("hopscotch-number.png")), x - 25, y - (int) (50 * Math.sqrt(0.75) / 2), 50, (int) (50 * Math.sqrt(0.75)), null);
                        g.setColor(Color.red);
                        g.drawString(nbc + "", x, y);
                    } else {
                        g.drawImage(ImageIO.read(new File("hopscotch-empty.png")), x - 25, y - (int) (50 * Math.sqrt(0.75) / 2), 50, (int) (50 * Math.sqrt(0.75)), null);
                    }
                }
            }
        } catch (IOException ex) {
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

    public void findHopscotch(int x, int y) {
        this.checked = true;
        if (this.p.contains(x, y)) {
            this.open();
        } else {
            for (Hopscotch hopscotch : neighborhood) {
                if (hopscotch != null && !hopscotch.isChecked()) {
                    hopscotch.findHopscotch(x, y);
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
