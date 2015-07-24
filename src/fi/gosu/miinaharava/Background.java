package fi.gosu.miinaharava;

import java.awt.Graphics;
import javax.swing.JComponent;

public class Background extends JComponent {

    private final Resources r;
    private final int width, height;

    Background(Resources r, int width, int height) {
        this.r = r;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(r.getBACKROUND(), 0, 0, width, height, this);
    }

}
