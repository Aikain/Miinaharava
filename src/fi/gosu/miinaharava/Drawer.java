package fi.gosu.miinaharava;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Drawer extends JPanel {

    private final Hopscotch hopscotch;

    public Drawer(Hopscotch hopscotch) {
        this.hopscotch = hopscotch;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.hopscotch.draw(g);
        this.hopscotch.clearDraw();
    }

}
